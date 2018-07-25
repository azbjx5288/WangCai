package com.wangcai.lottery.material;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.wangcai.lottery.BuildConfig;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.PayMoneyCommand;
import com.wangcai.lottery.data.Series;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created on 2016/01/14.
 *
 * @author ACE
 * @功能描述: 信息存储
 */
public class ConstantInformation {

    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final long ONE_HOUR = 60 * 60 * 1000L;
    public static final long ONE_MINUTE = 60 * 1000L;
    public static final long ONE_SECOND = 1000L;
    private static long lastClickTime;
    private static Lottery lotteryEmblem = null;
    private static Map<String, int[]> sLotteryLogo = new HashMap<>();
    private static List<String> issueSerials = new ArrayList<>();
    private static String infogather = "";
    private static String[] multiple = new String[235];
    private static String sort[] = null;
    private static String[] chaseissue = {"5期", "10期", "15期", "20期", "25期", "30期"};
    private static String[] province = {"深圳", "安徽", "北京", "广东", "云南", "福建", "甘肃", "广西", "贵州",
            "海南", "河北", "河南", "黑龙江", "湖北", "湖南", "吉林", "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东",
            "山西", "陕西", "上海", "四川", "天津", "西藏", "新疆", "浙江", "重庆", "香港", "澳门", "台湾"};
    private static Map<String, String[]> cityMap = new HashMap<>();

    static {

        for (int m = 1, l = multiple.length; m <= l; m++) {
            multiple[m - 1] = m + "倍";
        }
        //重庆时时彩
        sLotteryLogo.put("CQSSC", new int[]{R.drawable.cqssc, R.drawable.cqssc});
        //山东11选5
        sLotteryLogo.put("SD115", new int[]{R.drawable.sd11x5, R.drawable.sd11x5});
        //黑龙江时时彩
        sLotteryLogo.put("HLJSSC", new int[]{R.drawable.hljssc, R.drawable.hljssc});
        //新疆时时彩
        sLotteryLogo.put("XJSSC", new int[]{R.drawable.xjssc, R.drawable.xjssc});
        //天津时时彩
        sLotteryLogo.put("TJSSC", new int[]{R.drawable.tjssc, R.drawable.tjssc});
        //排列三/五
        sLotteryLogo.put("PLW", new int[]{R.drawable.plw, R.drawable.plw});
        //旺旺分分彩
        sLotteryLogo.put("WWFFC", new int[]{R.drawable.wcffc, R.drawable.wcffc});
        //旺旺三分彩
        sLotteryLogo.put("WW3FC", new int[]{R.drawable.wc3fc, R.drawable.wc3fc});
        //旺旺极速3D
        sLotteryLogo.put("WW3D", new int[]{R.drawable.wwjs3d, R.drawable.wwjs3d});
        //旺旺五分彩
        sLotteryLogo.put("WW5FC", new int[]{R.drawable.wc5fc, R.drawable.wc5fc});
        // 江西11选5
        sLotteryLogo.put("JX115", new int[]{R.drawable.jx115, R.drawable.jx115});
        // 广东11选5
        sLotteryLogo.put("GD115", new int[]{R.drawable.gd115, R.drawable.gd115});
        //旺旺11选5
        sLotteryLogo.put("WW115", new int[]{R.drawable.wc115, R.drawable.wc115});
        //福建11选5
        sLotteryLogo.put("FJ115", new int[]{R.drawable.fj115, R.drawable.fj115});
        //江苏快三
        sLotteryLogo.put("JSK3", new int[]{R.drawable.jsk3, R.drawable.jsk3});
        //甘肃快三
        sLotteryLogo.put("GSK3", new int[]{R.drawable.gsk3, R.drawable.gsk3});
        //北京PK拾
        sLotteryLogo.put("BJPK10", new int[]{R.drawable.bjpk10, R.drawable.bjpk10});
        //旺旺极速PK10
        sLotteryLogo.put("WWPK10", new int[]{R.drawable.wwjspk10, R.drawable.wwjspk10});
        //3D
        sLotteryLogo.put("F3D", new int[]{R.drawable.f3d, R.drawable.f3d});
        //北京五分彩
        sLotteryLogo.put("BJ5FC", new int[]{R.drawable.bj5fc, R.drawable.bj5fc});
        //缅甸百秒彩
        sLotteryLogo.put("MDBMC", new int[]{R.drawable.mdbmc, R.drawable.mdbmc});
        //旺彩11选5三分彩
        sLotteryLogo.put("PG115SFC", new int[]{R.drawable.sfc115, R.drawable.sfc115});
        //旺旺快三分分彩
        sLotteryLogo.put("WWK3", new int[]{R.drawable.wck3ffc, R.drawable.wck3ffc});
        //广西快三
        sLotteryLogo.put("GXK3", new int[]{R.drawable.gxks, R.drawable.gxks});
        //北京11选5
        sLotteryLogo.put("BJ115", new int[]{R.drawable.bj11x5, R.drawable.bj11x5});
        //安徽快三
        sLotteryLogo.put("AHK3", new int[]{R.drawable.ahks, R.drawable.ahks});
        //山西11选5
        sLotteryLogo.put("SX115", new int[]{R.drawable.sx11x5, R.drawable.sx11x5});
        //河北快三
        sLotteryLogo.put("HEBK3", new int[]{R.drawable.hbks, R.drawable.hbks});
        //福建快三
        sLotteryLogo.put("FJK3", new int[]{R.drawable.fjks, R.drawable.fjks});
        //上海11选5
        sLotteryLogo.put("SH115", new int[]{R.drawable.sh115, R.drawable.sh115});
        //河南快三
        sLotteryLogo.put("HNK3", new int[]{R.drawable.hnk3, R.drawable.hnk3});
        //重庆快乐十分
        sLotteryLogo.put("CQKLSF", new int[]{R.drawable.cqkl10f, R.drawable.cqkl10f});
        //广东快乐十分
        sLotteryLogo.put("GDKLSF", new int[]{R.drawable.gdkl10f, R.drawable.gdkl10f});
        //四川快乐十二
        sLotteryLogo.put("SCKL12", new int[]{R.drawable.sckl12, R.drawable.sckl12});
        //浙江快乐十二
        sLotteryLogo.put("ZJKL12", new int[]{R.drawable.zjkl12, R.drawable.zjkl12});
        //辽宁快乐十二
        sLotteryLogo.put("LNKL12", new int[]{R.drawable.lnkl12, R.drawable.lnkl12});
        //台湾宾果
        sLotteryLogo.put("TWBINGO", new int[]{R.drawable.twbg, R.drawable.twbg});
        //北京快乐8
        sLotteryLogo.put("BJKL8", new int[]{R.drawable.bjkl8, R.drawable.bjkl8});
        //旺旺快乐8分分彩
        sLotteryLogo.put("WWKENO", new int[]{R.drawable.wckl8ffc, R.drawable.wckl8ffc});
        //11选5三分彩
        sLotteryLogo.put("WW115SFC", new int[]{R.drawable.sfc115, R.drawable.sfc115});
        //腾讯分分彩
        sLotteryLogo.put("TXFFC", new int[]{R.drawable.id_lottery_txffc, R.drawable.id_lottery_txffc});

        //上海时时乐
        sLotteryLogo.put("SSL", new int[]{R.drawable.shssl, R.drawable.shssl});
        //贵州11选5
        sLotteryLogo.put("GZ115", new int[]{R.drawable.gz115, R.drawable.gz115});
        //天津11选5
        sLotteryLogo.put("TJ115", new int[]{R.drawable.tj115, R.drawable.tj115});
        //内蒙古11选5
        sLotteryLogo.put("NMG115", new int[]{R.drawable.nmg115, R.drawable.nmg115});
        //上海11选5
        sLotteryLogo.put("SH115", new int[]{R.drawable.sh11x5, R.drawable.sh11x5});
        //河北11选5
        sLotteryLogo.put("HB115", new int[]{R.drawable.hb115, R.drawable.hb115});
        //江苏11选5
        sLotteryLogo.put("JS115", new int[]{R.drawable.js115, R.drawable.js115});
        //安徽11选5
        sLotteryLogo.put("AH115", new int[]{R.drawable.ah115, R.drawable.ah115});
        //浙江11选5
        sLotteryLogo.put("ZJ115", new int[]{R.drawable.zj115, R.drawable.zj115});
        //河南快三
        sLotteryLogo.put("HNK3", new int[]{R.drawable.hnk3, R.drawable.hnk3});
        //辽宁11选5
        sLotteryLogo.put("LN115", new int[]{R.drawable.ln115, R.drawable.ln115});

        //百家乐
        sLotteryLogo.put("gabjl", new int[]{R.drawable.ic_ga_baccarat, R.drawable.ic_ga_baccarat});
        //幸运大转盘
        sLotteryLogo.put("gaxydzp", new int[]{R.drawable.ic_ga_xydzp, R.drawable.ic_ga_xydzp});
        //龙虎斗
        sLotteryLogo.put("galh", new int[]{R.drawable.ic_ga_longhu_bucket, R.drawable.ic_ga_longhu_bucket});
        //极速骰宝
        sLotteryLogo.put("gasb", new int[]{R.drawable.ic_ga_speed, R.drawable.ic_ga_speed});
        //21点
        sLotteryLogo.put("gahjk", new int[]{R.drawable.ic_ga_21dian, R.drawable.ic_ga_21dian});
        //赢三张
        sLotteryLogo.put("gaszpk", new int[]{R.drawable.ic_ga_win_three, R.drawable.ic_ga_win_three});
        //西施早餐
        sLotteryLogo.put("gaxszc", new int[]{R.drawable.ic_ga_brnn_xs, R.drawable.ic_ga_brnn_xs});
        //水果机
        sLotteryLogo.put("gasgj", new int[]{R.drawable.ic_ga_sgj, R.drawable.ic_ga_sgj});
        //Ga趣味牛牛
        sLotteryLogo.put("gaqwnn", new int[]{R.drawable.ic_ga_qwnn, R.drawable.ic_ga_qwnn});
        //GA江苏骰宝
        sLotteryLogo.put("gajstb", new int[]{R.drawable.ic_ga_jstb01, R.drawable.ic_ga_jstb01});
        //Ga三国水果机
        sLotteryLogo.put("gasgsgj", new int[]{R.drawable.ic_ga_sgsgj, R.drawable.ic_ga_sgsgj});
        //Ga百人牛牛
        sLotteryLogo.put("gabrnn", new int[]{R.drawable.ic_ga_brnn_br, R.drawable.ic_ga_brnn_br});
        //Ga俄罗斯轮盘
        sLotteryLogo.put("gaelslp", new int[]{R.drawable.elslp_, R.drawable.elslp_});
        //Ga德州扑克
        sLotteryLogo.put("gadzpk", new int[]{R.drawable.dzpk, R.drawable.dzpk});
        //Ga神奇弹珠
        sLotteryLogo.put("gasqdz", new int[]{R.drawable.sqdz_logo02, R.drawable.sqdz_logo02});
        //Ga加勒比扑克
        sLotteryLogo.put("gajlbpk", new int[]{R.drawable.jlbpk_logo, R.drawable.jlbpk_logo});
        //Ga打鼹鼠
        sLotteryLogo.put("gadys", new int[]{R.drawable.dds_logo, R.drawable.dds_logo});
        //Ga大富翁
        sLotteryLogo.put("gadfw", new int[]{R.drawable.icon_dfw, R.drawable.icon_dfw});
        //Ga石头剪刀布
        sLotteryLogo.put("gastjdb", new int[]{R.drawable.icon_stjdb, R.drawable.icon_stjdb});
        //Ga新百家乐
        sLotteryLogo.put("gaxbjl", new int[]{R.drawable.xbjl_logo, R.drawable.xbjl_logo});
        //Ga三公
        sLotteryLogo.put("gasg", new int[]{R.drawable.icon_sg, R.drawable.icon_sg});
        //GA百人三公
        sLotteryLogo.put("gabrsg", new int[]{R.drawable.br3g, R.drawable.br3g});
        //超级龙虎斗
        sLotteryLogo.put("gacjlhd", new int[]{R.drawable.cjlhd, R.drawable.cjlhd});
        //奔驰宝马
        sLotteryLogo.put("gabcbm", new int[]{R.drawable.bcbm, R.drawable.bcbm});


        cityMap.put("深圳", new String[]{"罗湖区", "福田区", "盐田区", "南山区", "龙岗区", "宝安区", "光明新区", "市内"});
        cityMap.put("安徽", new String[]{"安庆", "蚌埠", "巢湖", "池州", "滁州", "阜阳", "合肥", "淮北", "淮南",
                "黄山", "六安", "马鞍山", "宿州", "铜陵", "芜湖", "宣城", "亳州"});
        cityMap.put("北京", new String[]{"北京"});
        cityMap.put("广东", new String[]{"深圳", "潮州", "东莞", "佛山", "广州", "河源", "惠州", "江门", "揭阳",
                "茂名", "梅州", "清远", "汕头", "汕尾", "韶关", "阳江", "云浮", "湛江", "肇庆", "中山", "珠海"});
        cityMap.put("云南", new String[]{"昆明", "曲靖", "大理", "保山", "玉溪", "楚雄", "丽江", "德宏", "迪庆",
                "红河", "临沧", "昭通", "怒江", "思茅", "文山", "西双版纳"});
        cityMap.put("福建", new String[]{"福州", "龙岩", "南平", "宁德", "莆田", "泉州", "三明", "厦门", "漳州"});
        cityMap.put("甘肃", new String[]{"白银", "定西", "甘南藏族自治州", "嘉峪关", "金昌", "酒泉", "兰州", "临夏回族自治州",
                "陇南", "平凉", "庆阳", "天水", "武威", "张掖"});
        cityMap.put("广西", new String[]{"南宁", "玉林", "百色", "北海", "崇左", "防城港", "桂林", "贵港", "河池",
                "贺州", "来宾", "柳州", "钦州", "梧州"});
        cityMap.put("贵州", new String[]{"安顺", "毕节", "贵阳", "六盘水", "黔东南苗族侗族自治州", "黔南布依族苗族自治州",
                "黔西南布依族苗族自治州", "铜仁", "遵义"});
        cityMap.put("海南", new String[]{"白沙黎族自治县", "保亭黎族苗族自治县", "昌江黎族自治县", "澄迈县", "定安县", "东方",
                "海口", "乐东黎族自治县", "临高县", "陵水黎族自治县", "琼海", "琼中黎族苗族自治县", "三亚", "屯昌县", "万宁", "文昌",
                "五指山", "儋州"});
        cityMap.put("河北", new String[]{"保定", "沧州", "承德", "邯郸", "衡水", "廊坊", "秦皇岛", "石家庄", "唐山",
                "邢台", "张家口"});
        cityMap.put("河南", new String[]{"安阳", "鹤壁", "济源", "焦作", "开封", "洛阳", "南阳", "平顶山", "三门峡",
                "商丘", "新乡", "信阳", "许昌", "郑州", "周口", "驻马店", "漯河", "濮阳"});
        cityMap.put("黑龙江", new String[]{"大庆", "大兴安岭", "哈尔滨", "鹤岗", "黑河", "鸡西", "佳木斯", "牡丹江",
                "七台河", "齐齐哈尔", "双鸭山", "绥化", "伊春"});
        cityMap.put("湖北", new String[]{"鄂州", "恩施土家族苗族自治州", "黄冈", "黄石", "荆门", "荆州", "潜江", "神农架林区",
                "十堰", "随州", "天门", "武汉", "仙桃", "咸宁", "襄樊", "孝感", "宜昌"});
        cityMap.put("湖南", new String[]{"常德", "长沙", "郴州", "衡阳", "怀化", "娄底", "邵阳", "湘潭",
                "湘西土家族苗族自治州", "益阳", "永州", "岳阳", "张家界", "株洲"});
        cityMap.put("吉林", new String[]{"白城", "白山", "长春", "吉林", "辽源", "四平", "松原", "通化", "延边朝鲜族自治州"});
        cityMap.put("江苏", new String[]{"常州", "淮安", "连云港", "南京", "南通", "苏州", "宿迁", "泰州", "无锡",
                "徐州", "盐城", "扬州", "镇江"});
        cityMap.put("江西", new String[]{"抚州", "赣州", "吉安", "景德镇", "九江", "南昌", "萍乡", "上饶", "新余",
                "宜春", "鹰潭"});
        cityMap.put("辽宁", new String[]{"鞍山", "本溪", "朝阳", "大连", "丹东", "抚顺", "阜新", "葫芦岛", "锦州",
                "辽阳", "盘锦", "沈阳", "铁岭", "营口"});
        cityMap.put("内蒙古", new String[]{"阿拉善盟", "巴彦淖尔盟", "包头", "赤峰", "鄂尔多斯", "呼和浩特", "呼伦贝尔",
                "通辽", "乌海", "乌兰察布盟", "锡林郭勒盟", "兴安盟"});
        cityMap.put("宁夏", new String[]{"固原", "石嘴山", "吴忠", "银川"});
        cityMap.put("青海", new String[]{"果洛藏族自治州", "海北藏族自治州", "海东", "海南藏族自治州", "海西蒙古族藏族自治州",
                "黄南藏族自治州", "西宁", "玉树藏族自治州"});
        cityMap.put("山东", new String[]{"滨州", "德州", "东营", "菏泽", "济南", "济宁", "莱芜", "聊城", "临沂",
                "青岛", "日照", "泰安", "威海", "潍坊", "烟台", "枣庄", "淄博"});
        cityMap.put("山西", new String[]{"长治", "大同", "晋城", "晋中", "临汾", "吕梁", "朔州", "太原", "忻州",
                "阳泉", "运城", "五指山"});
        cityMap.put("陕西", new String[]{"安康", "宝鸡", "汉中", "商洛", "铜川", "渭南", "西安", "咸阳", "延安", "榆林"});
        cityMap.put("上海", new String[]{"上海"});
        cityMap.put("四川", new String[]{"阿坝藏族羌族自治州", "巴中", "成都", "达州", "德阳", "甘孜藏族自治州", "张家口",
                "广安", "广元", "乐山", "凉山彝族自治州", "眉山", "绵阳", "南充", "内江", "攀枝花", "遂宁", "雅安", "宜宾",
                "资阳", "自贡", "泸州"});
        cityMap.put("天津", new String[]{"天津"});
        cityMap.put("西藏", new String[]{"阿里", "昌都", "拉萨", "林芝", "那曲", "日喀则", "山南"});
        cityMap.put("新疆", new String[]{"阿克苏", "阿拉尔", "巴音郭楞蒙古自治州", "博尔塔拉蒙古自治州", "昌吉回族自治州", "哈密",
                "和田", "喀什", "克拉玛依", "克孜勒苏柯尔克孜自治州", "石河子", "图木舒克", "吐鲁番", "乌鲁木齐", "五家渠",
                "伊犁哈萨克自治州"});
        cityMap.put("浙江", new String[]{"杭州", "湖州", "嘉兴", "金华", "丽水", "宁波", "绍兴", "台州", "温州",
                "舟山", "衢州"});
        cityMap.put("重庆", new String[]{"重庆"});
        cityMap.put("香港", new String[]{"香港岛", "九龙半岛", "新界本土", "新界离岛"});
        cityMap.put("澳门", new String[]{"澳门半岛", "氹仔岛", "路环岛"});
        cityMap.put("台湾", new String[]{"台湾"});
    }

    public static RecordType getLotteryModel(Context context) {
        return RecordType.get(context, "lottery_model_history");
    }

    public static int getLotteryLogo(String lotteryID, boolean available) {
        int[] ids = sLotteryLogo.get(lotteryID);
        if (ids != null) {
            return available ? ids[0] : ids[1];
        }
        return R.drawable.jia;
    }

    public static RecordTime getLasttime(String currenttime, String end) {
        RecordTime recTime = new RecordTime();
        try {
            Date d1 = df.parse(currenttime);
            Date d2 = df.parse(end);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-0"));
            calendar.setTimeInMillis(diff);
            recTime.setDay(calendar.get(Calendar.DATE) - 1);
            recTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            recTime.setMinute(calendar.get(Calendar.MINUTE));
            recTime.setSecond(calendar.get(Calendar.SECOND));
        } catch (Exception e) {
        }
        return recTime;
    }

    public static Lottery getLotteryEmblem() {
        return lotteryEmblem;
    }

    public static void setLotteryEmblem(Lottery lottery) {
        lotteryEmblem = lottery;
    }

    public static String[] getMultiple() {
        return multiple;
    }

    public static String[] getChaseissue() {
        return chaseissue;
    }

    public static String[] getProvince() {
        return province;
    }

    public static String[] getCityList(String province) {
        return cityMap.get(province);
    }

    public static String randomToken() {
        return RandomStringUtils.randomNumeric(20);
    }

    public static List<String> getSort() {
        List<Series> seriess = com.wangcai.lottery.app.WangCaiApp.getUserCentre().getSeriesList();
        List<String> sort = new ArrayList<>();
        sort.add("常用彩种");
        for (int i = 0; i < seriess.size(); i++) {
            Series series = seriess.get(i);
            if (series.getGameType() == 1) {
                sort.add(series.getName());
            }
        }
        return sort;
    }

    public static boolean afterDate(String dateStr) {
        boolean flag = false;
        Date nowDate = new Date();
        Date beforeDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        //非空才可以执行
        if (dateStr != null && !"".equals(dateStr)) {
            try {
                //将日期字符串解析为日期类型
                beforeDate = sdf.parse(dateStr);
                //判断是否在当前日期之后
                //true表示在当前日期之前;false表示在当前日期之后
                flag = nowDate.after(beforeDate);
                /*if (flag) {
                    Log.e("该日在今日之前","------------>"+flag);
                } else {
                    Log.e("该日在今日之后","------------>"+flag);
                }*/
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static List<String> getIssueSerials() {
        return issueSerials;
    }

    public static void setIssueSerials(String issue, int maxissue, int quantity) {
        if (issue == null || issue.length() <= 0) {
            return;
        }
        issueSerials.clear();
        String serial = "";
        int issueIndex = issue.indexOf("-");
        if (issueIndex != -1) {

            serial = issue.substring(issueIndex + 1, issue.length());
            int plus = Integer.parseInt(serial);
            int i = 0;
            while (true) {
                if (i >= quantity) {
                    break;
                }
                issueSerials.add(String.format("%0" + serial.length() + "d", plus));
                /*if(plusIssueSerial==issuecount){ //最后一期 重新计算
                    day++;
                }*/
                plus = plus + 1;
                i++;
            }
        }
    }

    public static String getDateFormat(Date date) {
        return df.format(date);
    }

    public static String isUrl(String url) {
        StringBuffer urlBuffer = new StringBuffer();
        if ("http:".equals(url.substring(0, 5))) {
            urlBuffer = urlBuffer.append(url);
        } else if ("https:".equals(url.substring(0, 6))) {
            urlBuffer = urlBuffer.append(url);
        } else {
            urlBuffer = urlBuffer.append("http://").append(url);
        }
        return urlBuffer.toString();
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= ONE_SECOND) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    public static String getInfogather() {
        return infogather;
    }

    public static String gatherInfo(Activity activity, PayMoneyCommand command) {
        TelephonyManager mTm = (TelephonyManager) activity.getSystemService(Context
                .TELEPHONY_SERVICE);
        String imei = null;
        String iesi = null;
        try {
            /*imei = mTm.getDeviceId();
            iesi = mTm.getSubscriberId();*/
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return "手机IMEI号：" + imei + ",\n手机IESI号：" + iesi + ",\n手机型号：" + android.os.Build.MODEL +
                ",\n手机品牌：" + android.os.Build.BRAND + ",\nAndroid：" + android.os.Build.VERSION
                .SDK_INT + ",\nAppName：" + BuildConfig.VERSION_NAME + ",\nAppCode：" + BuildConfig
                .VERSION_CODE + ",\nUserID：" + WangCaiApp.getUserCentre().getUserID() + "," +
                "\nData：" + df.format(new Date());
    }

    public static ArrayList<String> LEGAL_NUMBER_SSC = new ArrayList<String>() {
        {
            add("0");
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
        }
    };

    public static ArrayList<String> LEGAL_NUMBER_SYXW = new ArrayList<String>() {
        {
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
            add("01");
            add("02");
            add("03");
            add("04");
            add("05");
            add("06");
            add("07");
            add("08");
            add("09");
            add("10");
            add("11");
        }
    };

    public static ArrayList<String> LEGAL_NUMBER_PK10 = new ArrayList<String>() {
        {
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
            add("01");
            add("02");
            add("03");
            add("04");
            add("05");
            add("06");
            add("07");
            add("08");
            add("09");
            add("10");
        }
    };

    public static ArrayList<String> LEGAL_NUMBER_KL12 = new ArrayList<String>() {
        {
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
            add("01");
            add("02");
            add("03");
            add("04");
            add("05");
            add("06");
            add("07");
            add("08");
            add("09");
            add("10");
            add("11");
            add("12");
        }
    };

    public static ArrayList<String> LEGAL_NUMBER_KL10 = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"));

    public static String[] NUMBER_PK10 = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",};

    public static HashMap<String, String> DXDS_MAP = new HashMap<String, String>() {{
        put("0", "小");
        put("1", "大");
        put("2", "双");
        put("3", "单");
    }};

    public static HashMap<String, String> DRAGON_TIGER_MAP = new HashMap<String, String>() {{
        put("1", "龙");
        put("0", "虎");
    }};

    public static HashMap<String, String> DRAGON_TIGER_SUN_MAP = new HashMap<String, String>() {{
        put("2", "龙");
        put("0", "虎");
        put("1", "和");
    }};

    public static HashMap<String, String> DING_DAN_SHUANG_MAP = new HashMap<String, String>() {{
        put("0", "0单5双");
        put("1", "1单4双");
        put("2", "2单3双");
        put("3", "3单2双");
        put("4", "4单1双");
        put("5", "5单0双");
    }};

    public static HashMap<String, String> DRAGON_DXDX_MAP = new HashMap<String, String>() {{
        put("0", "小");
        put("1", "大");
    }};

    public static HashMap<String, String> SPECIAL_NUB_MAP = new HashMap<String, String>() {{
        put("0", "豹子");
        put("1", "顺子");
        put("2", "对子");
    }};

    public static HashMap<String, String> DRAGON_DSDS_MAP = new HashMap<String, String>() {{
        put("1", "单");
        put("0", "双");
    }};

    public static HashMap<String, String> HZ_JIOUHE_MAP = new HashMap<String, String>() {{
        put("1", "奇");
        put("0", "偶");
        put("2", "和");
    }};

    public static HashMap<String, String> KL8_SZX_MAP = new HashMap<String, String>() {{
        put("2", "上");
        put("1", "中");
        put("0", "下");
    }};

    public static HashMap<String, String> DAXIAO810_DX_MAP = new HashMap<String, String>() {{
        put("2", "大");
        put("1", "810");
        put("0", "小");
    }};

    public static HashMap<String, String> HZ_WX_MAP = new HashMap<String, String>() {{
        put("0", "金");
        put("1", "木");
        put("2", "水");
        put("3", "火");
        put("4", "土");
    }};

    public static HashMap<String, String> INTERVAL_TEXT_MAP = new HashMap<String, String>() {{
        put("0", "一区");
        put("1", "二区");
        put("2", "三区");
        put("3", "四区");
        put("4", "五区");
    }};
    public static HashMap<String, String> DRAGON_ETHTX_MAP = new HashMap<String, String>() {{
        put("1", "11*");
        put("2", "22*");
        put("3", "33*");
        put("4", "44*");
        put("5", "55*");
        put("6", "66*");
    }};
    public static HashMap<String, String> DRAGON_STHSTHDX_MAP = new HashMap<String, String>() {{
        put("1", "111");
        put("2", "222");
        put("3", "333");
        put("4", "444");
        put("5", "555");
        put("6", "666");
    }};
}
