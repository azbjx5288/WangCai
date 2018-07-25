package com.wangcai.lottery.material;

/**
 * 投注模式：元、角、分 、厘
 * Created by Alashi on 2016/4/7.
 */

public enum LucreMode {
    /** "元"模式 */
    YUAN_ONE(0, "元", 1),
    /** "元"模式 */
    YUAN_TWO(1, "元", 0.5),
    /** "角"模式 */
    JIAO_ONE(2, "角", 0.1),
    /** "角"模式 */
    JIAO_TWO(3, "角", 0.05),
    /** "分"模式 */
    FEN(4, "分", 0.01),
    /** "厘"模式 */
    LI(5, "厘", 0.001);

    private int index;
    private String name;
    private double factor;

    LucreMode(int index, String name, double factor) {
        this.index = index;
        this.name = name;
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }

    public String getName() {
        return name;
    }

    public static LucreMode fromCode(int index) {
        for (LucreMode lucreMode: LucreMode.values()) {
            if (lucreMode.index == index) {
                return lucreMode;
            }
        }
        return YUAN_ONE;
    }
}
