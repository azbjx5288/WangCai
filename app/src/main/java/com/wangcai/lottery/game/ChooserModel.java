package com.wangcai.lottery.game;

import android.content.Context;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.data.MethodList;
import com.wangcai.lottery.data.MethodType;

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

/**
 * “常用玩法”的数据处理类。包含读写历史，排序
 * Created by Alashi on 2016/2/25.
 */
public class ChooserModel extends DataSetObservable {
    private static final String TAG = ChooserModel.class.getSimpleName();
    private static final boolean DEBUG = false;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final String TAG_HISTORICAL_RECORD = "historical-record";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_WEIGHT = "weight";
    private static final String ATTRIBUTE_TIME = "time";

    private static final Object sRegistryLock = new Object();
    private static final Map<String, ChooserModel> sDataModelRegistry = new HashMap<>();

    private final Context context;
    private final String historyFileName;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList<>();
    private final List<MethodInfo> methodInfos = new ArrayList<>();
    private final Object mInstanceLock = new Object();

    private boolean mCanReadHistoricalData = true;
    private boolean mHistoricalRecordsChanged = true;
    private boolean mReadShareHistoryCalled = false;

    private MethodSorter methodSorter = new MethodSorter();
    private int mHistoryMaxSize = DEFAULT_HISTORY_MAX_LENGTH;

    public static ChooserModel get(Context context, String historyFileName) {
        synchronized (sRegistryLock) {
            ChooserModel chooserModel = sDataModelRegistry.get(historyFileName);
            if (chooserModel == null) {
                chooserModel = new ChooserModel(context, historyFileName);
                sDataModelRegistry.put(historyFileName, chooserModel);
            }
            return chooserModel;
        }
    }

    private ChooserModel(Context context, String historyFileName) {
        this.context = context.getApplicationContext();
        if (!TextUtils.isEmpty(historyFileName) && !historyFileName.endsWith(HISTORY_FILE_EXTENSION)) {
            this.historyFileName = historyFileName + HISTORY_FILE_EXTENSION;
        } else {
            this.historyFileName = historyFileName;
        }
    }

    public void setMethodList(ArrayList<MethodList> methodList) {
        synchronized (mInstanceLock) {
            methodInfos.clear();
            if (methodList != null) {
                for (MethodList methodList1 : methodList) {
                    for (MethodType methodGroup : methodList1.getChildren()) {
                        if (methodGroup.getChildren() != null) {
                            for (Method method : methodGroup.getChildren()) {
                                methodInfos.add(new MethodInfo(method));
                            }
                        }
                    }
                }
            }

            readHistoricalDataIfNeeded();
            pruneExcessiveHistoricalRecordsIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    public void addChoosedMethod(Method method) {
        synchronized (mInstanceLock) {
            if (method != null && !TextUtils.isEmpty(method.getNameEn())) {
                addHisoricalRecord(new HistoricalRecord(method.getNameEn(), System.currentTimeMillis(), DEFAULT_HISTORICAL_RECORD_WEIGHT));
            }
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

    public MethodInfo getMethodInfo(int index) {
        synchronized (mInstanceLock) {
            ensureConsistentState();
            if (index >= 0 && index < methodInfos.size()) {
                return methodInfos.get(index);
            } else {
                return null;
            }
        }
    }

    public List<MethodInfo> getLotteryInfos() {
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
        if (mCanReadHistoricalData && mHistoricalRecordsChanged && !TextUtils.isEmpty(historyFileName)) {
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
            PersistHistoryAsyncTask asyncTask = new PersistHistoryAsyncTask();
            asyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, 0);
//            asyncTask.executeOnExecutor(new ArrayList<>(mHistoricalRecords), historyFileName);
//            AsyncTask.executeOnExecutor(new PersistHistoryAsyncTask(),);
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
                throw new XmlPullParserException("Share records file does not start with " + TAG_HISTORICAL_RECORDS + " tag.");
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
        private final Map<String, MethodInfo> mPackageNameToActivityMap = new HashMap<>();

        public void sort(List<MethodInfo> methods, List<HistoricalRecord> historicalRecords) {
            if (historicalRecords == null || historicalRecords.size() == 0) {
                return;
            }
            Map<String, MethodInfo> componentNameToActivityMap = mPackageNameToActivityMap;
            componentNameToActivityMap.clear();

            final int activityCount = methods.size();
            for (int i = 0; i < activityCount; i++) {
                MethodInfo methodInfo = methods.get(i);
                methodInfo.weight = 0.0f;
                componentNameToActivityMap.put(methodInfo.method.getNameEn(), methodInfo);
            }

            final int lastShareIndex = historicalRecords.size() - 1;
            float nextRecordWeight = 1;
            for (int i = lastShareIndex; i >= 0; i--) {
                HistoricalRecord historicalRecord = historicalRecords.get(i);
                MethodInfo activity = componentNameToActivityMap.get(historicalRecord.name);
                if (activity != null) {
                    activity.weight += historicalRecord.weight * nextRecordWeight;
                    nextRecordWeight = nextRecordWeight * WEIGHT_DECAY_COEFFICIENT;
                }
            }

            Collections.sort(methods);

            if (DEBUG) {
                for (int i = 0; i < activityCount; i++) {
                    Log.i(TAG, "Sorted: " + methods.get(i));
                }
            }
        }
    }

    public final static class MethodInfo implements Comparable<MethodInfo> {
        public final Method method;
        public float weight;

        public MethodInfo(Method method) {
            this.method = method;
        }

        @Override
        public int compareTo(MethodInfo another) {
            return Float.floatToIntBits(another.weight) - Float.floatToIntBits(weight);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append("Info:").append(method.getNameCn()).append(",").append(method.getNameCn());
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
            if (mHistoricalRecords.size() == 0) {
                return null;
            }
            List<HistoricalRecord> historicalRecords = mHistoricalRecords;
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