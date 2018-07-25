package com.wangcai.lottery.material;

import android.content.Context;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.game.ChooserModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import android.support.v4.os.AsyncTaskCompat;

/**
 * “常用彩种”的数据处理类。包含读写历史，排序
 * Created by Ace.Dong on 2017/11/16.
 */
public class RecordType extends DataSetObservable {
    private static final String TAG = RecordType.class.getSimpleName();
    private static final boolean DEBUG = false;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final String TAG_HISTORICAL_RECORDS = "lottory-historical-records";
    private static final String TAG_HISTORICAL_RECORD = "lottory-historical-record";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_WEIGHT = "weight";
    private static final String ATTRIBUTE_TIME = "time";

    private static final Object sRegistryLock = new Object();
    private static final Map<String, RecordType> sDataModelRegistry = new HashMap<>();

    private final Context context;
    private final String historyFileName;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList<>();
    private final List<LotteryInfo> methodInfos = new ArrayList<>();
    private final Object mInstanceLock = new Object();

    private boolean mCanReadHistoricalData = true;
    private boolean mHistoricalRecordsChanged = true;
    private boolean mReadShareHistoryCalled = false;

    private MethodSorter methodSorter = new MethodSorter();
    private int mHistoryMaxSize = DEFAULT_HISTORY_MAX_LENGTH;

    public static RecordType get(Context context, String historyFileName) {
        synchronized (sRegistryLock) {
            RecordType recordType = sDataModelRegistry.get(historyFileName);
            if (recordType == null) {
                recordType = new RecordType(context, historyFileName);
                sDataModelRegistry.put(historyFileName, recordType);
            }
            return recordType;
        }
    }

    private RecordType(Context context, String historyFileName) {
        this.context = context;
        if (!TextUtils.isEmpty(historyFileName) && !historyFileName.endsWith(HISTORY_FILE_EXTENSION)) {
            this.historyFileName = historyFileName + HISTORY_FILE_EXTENSION;
        } else {
            this.historyFileName = historyFileName;
        }
    }

    public void setLotteryList(List<Lottery> lotteryList) {
        synchronized (mInstanceLock) {
            methodInfos.clear();
            if (lotteryList != null) {
                for (Lottery lottery : lotteryList) {
                    methodInfos.add(new LotteryInfo(lottery));
                }
            }

            readHistoricalDataIfNeeded();
            pruneExcessiveHistoricalRecordsIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    public void addChoosedLottery(Lottery lottery) {
        synchronized (mInstanceLock) {
            addHisoricalRecord(new HistoricalRecord(lottery.getName(), System.currentTimeMillis(), DEFAULT_HISTORICAL_RECORD_WEIGHT));
        }
    }

    public void setHistoryMaxSize(int historyMaxSize) {
        synchronized (mInstanceLock) {
            if (mHistoryMaxSize == historyMaxSize) {
                return;
            }
            mHistoryMaxSize = historyMaxSize;
            pruneExcessiveHistoricalRecordsIfNeeded();
            if (sortActivitiesIfNeeded()) {
                notifyChanged();
            }
        }
    }

    public int getHistorySize() {
        synchronized (mInstanceLock) {
            ensureConsistentState();
            return mHistoricalRecords.size();
        }
    }

    public LotteryInfo getLotteryInfo(int index) {
        synchronized (mInstanceLock) {
            ensureConsistentState();
            if (index >= 0 && index < methodInfos.size()) {
                return methodInfos.get(index);
            } else {
                return null;
            }
        }
    }

    public List<LotteryInfo> getLotteryInfos() {
        synchronized (mInstanceLock) {
            ensureConsistentState();
            return methodInfos;
        }
    }

    private void ensureConsistentState() {
        boolean stateChanged = readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (stateChanged) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() {
        if (methodSorter != null && !methodInfos.isEmpty() && !mHistoricalRecords.isEmpty()) {
            methodSorter.sort(methodInfos, Collections.unmodifiableList(mHistoricalRecords));
            return true;
        }
        return false;
    }

    private boolean readHistoricalDataIfNeeded() {
        if (mCanReadHistoricalData && mHistoricalRecordsChanged &&
                !TextUtils.isEmpty(historyFileName)) {
            mCanReadHistoricalData = false;
            mReadShareHistoryCalled = true;
            readHistoricalData();
            return true;
        }
        return false;
    }

    private boolean addHisoricalRecord(HistoricalRecord historicalRecord) {
        readHistoricalDataIfNeeded();
        final boolean added = mHistoricalRecords.add(historicalRecord);
        if (added) {
            mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return added;
    }

    private void persistHistoricalDataIfNeeded() {
        if (!mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        }
        if (!mHistoricalRecordsChanged) {
            return;
        }
        mHistoricalRecordsChanged = false;
        if (!TextUtils.isEmpty(historyFileName)) {
            PersistHistoryAsyncTask asyncTask=new PersistHistoryAsyncTask();
            asyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, 0);
//            AsyncTaskCompat.executeParallel(new PersistHistoryAsyncTask(), new ArrayList<>(mHistoricalRecords), historyFileName);
        }
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        final int pruneCount = mHistoricalRecords.size() - mHistoryMaxSize;
        if (pruneCount <= 0) {
            return;
        }
        mHistoricalRecordsChanged = true;
        for (int i = 0; i < pruneCount; i++) {
            HistoricalRecord prunedRecord = mHistoricalRecords.remove(0);
            if (DEBUG) {
                Log.i(TAG, "Pruned: " + prunedRecord);
            }
        }
    }

    /**
     * 从xml读取历史数据
     */
    private void readHistoricalData() {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(historyFileName);
        } catch (FileNotFoundException fnfe) {
            if (DEBUG) {
                Log.i(TAG, "Could not open historical records file: " + historyFileName);
            }
            return;
        }
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, "UTF-8");

            int type = XmlPullParser.START_DOCUMENT;
            while (type != XmlPullParser.END_DOCUMENT && type != XmlPullParser.START_TAG) {
                type = parser.next();
            }

            if (!TAG_HISTORICAL_RECORDS.equals(parser.getName())) {
                throw new XmlPullParserException("Share records file does not start with "
                        + TAG_HISTORICAL_RECORDS + " tag.");
            }

            List<HistoricalRecord> historicalRecords = mHistoricalRecords;
            historicalRecords.clear();

            while (true) {
                type = parser.next();
                if (type == XmlPullParser.END_DOCUMENT) {
                    break;
                }
                if (type == XmlPullParser.END_TAG || type == XmlPullParser.TEXT) {
                    continue;
                }
                String nodeName = parser.getName();
                if (!TAG_HISTORICAL_RECORD.equals(nodeName)) {
                    throw new XmlPullParserException("Share records file not well-formed.");
                }

                String activity = parser.getAttributeValue(null, ATTRIBUTE_NAME);
                final long time = Long.parseLong(parser.getAttributeValue(null, ATTRIBUTE_TIME));
                final float weight = Float.parseFloat(parser.getAttributeValue(null, ATTRIBUTE_WEIGHT));
                HistoricalRecord readRecord = new HistoricalRecord(activity, time, weight);
                historicalRecords.add(readRecord);

                if (DEBUG) {
                    Log.i(TAG, "Read " + readRecord.toString());
                }
            }

            if (DEBUG) {
                Log.i(TAG, "Read " + historicalRecords.size() + " historical records.");
            }
        } catch (XmlPullParserException | IOException xppe) {
            Log.e(TAG, "Error reading historical recrod file: " + historyFileName, xppe);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    /* ignore */
                }
            }
        }
    }

    public final static class HistoricalRecord {
        public final String name;
        public final long time;
        public final float weight;

        public HistoricalRecord(String name, long time, float weight) {
            this.name = name;
            this.time = time;
            this.weight = weight;
        }
    }

    private static class MethodSorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<String, LotteryInfo> mPackageNameToActivityMap = new HashMap<>();

        public void sort(List<LotteryInfo> lotterys, List<HistoricalRecord> historicalRecords) {
            Map<String, LotteryInfo> componentNameToActivityMap = mPackageNameToActivityMap;
            componentNameToActivityMap.clear();

            final int activityCount = lotterys.size();
            for (int i = 0; i < activityCount; i++) {
                LotteryInfo lotteryInfo = lotterys.get(i);
                lotteryInfo.weight = 0.0f;
                componentNameToActivityMap.put(lotteryInfo.lottery.getName(), lotteryInfo);
            }

            final int lastShareIndex = historicalRecords.size() - 1;
            float nextRecordWeight = 1;
            for (int i = lastShareIndex; i >= 0; i--) {
                HistoricalRecord historicalRecord = historicalRecords.get(i);
                LotteryInfo activity = componentNameToActivityMap.get(historicalRecord.name);
                if (activity != null) {
                    activity.weight += historicalRecord.weight * nextRecordWeight;
                    nextRecordWeight = nextRecordWeight * WEIGHT_DECAY_COEFFICIENT;
                }
            }

            Collections.sort(lotterys);

            if (DEBUG) {
                for (int i = 0; i < activityCount; i++) {
                    Log.i(TAG, "Sorted: " + lotterys.get(i));
                }
            }
        }
    }

    public final static class LotteryInfo implements Comparable<LotteryInfo> {
        public final Lottery lottery;
        public float weight;

        public LotteryInfo(Lottery lottery) {
            this.lottery = lottery;
        }

        @Override
        public int compareTo(LotteryInfo another) {
            return Float.floatToIntBits(another.weight) - Float.floatToIntBits(weight);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append("Info:").append(lottery.getName()).append(",").append(lottery.getIdentifier());
            builder.append("; weight:").append(new BigDecimal(weight));
            builder.append("]");
            return builder.toString();
        }
    }

    /**
     * 写历史数据到xml
     */
    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {

        @Override
        @SuppressWarnings("unchecked")
        public Void doInBackground(Object... args) {
            List<HistoricalRecord> historicalRecords =  mHistoricalRecords;
            String hostoryFileName = historyFileName;

            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(hostoryFileName, Context.MODE_PRIVATE);
            } catch (FileNotFoundException fnfe) {
                Log.e(TAG, "Error writing historical recrod file: " + hostoryFileName, fnfe);
                return null;
            }

            XmlSerializer serializer = Xml.newSerializer();

            try {
                serializer.setOutput(fos, null);
                serializer.startDocument("UTF-8", true);
                serializer.startTag(null, TAG_HISTORICAL_RECORDS);

                final int recordCount = historicalRecords.size();
                for (int i = 0; i < recordCount; i++) {
                    HistoricalRecord record = historicalRecords.remove(0);
                    serializer.startTag(null, TAG_HISTORICAL_RECORD);
                    serializer.attribute(null, ATTRIBUTE_NAME, record.name);
                    serializer.attribute(null, ATTRIBUTE_TIME, String.valueOf(record.time));
                    serializer.attribute(null, ATTRIBUTE_WEIGHT, String.valueOf(record.weight));
                    serializer.endTag(null, TAG_HISTORICAL_RECORD);
                    if (DEBUG) {
                        Log.i(TAG, "Wrote " + record.toString());
                    }
                }

                serializer.endTag(null, TAG_HISTORICAL_RECORDS);
                serializer.endDocument();

                if (DEBUG) {
                    Log.i(TAG, "Wrote " + recordCount + " historical records.");
                }
            } catch (IllegalArgumentException | IllegalStateException | IOException iae) {
                Log.e(TAG, "Error writing historical recrod file: " + historicalRecords, iae);
            } finally {
                mCanReadHistoricalData = true;
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        /* ignore */
                    }
                }
            }
            return null;
        }
    }
}