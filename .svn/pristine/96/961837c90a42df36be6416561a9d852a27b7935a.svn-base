package com.wangcai.lottery.game;

public enum Delimiter {
    /** 逗号 */
    COMMA(0, ","),
    /** 竖线 */
    VERTICAL(1, "|"),
    /** 竖线 */
    DEELIMITER_LINE(2, "\\|"),
    /** 空格 **/
    SPACE(3," ");


    private int index;
    private String symbol;

    public static final int COMMA_INDEX = 0;
    public static final int VERTICAL_LINE_INDEX = 1;
    public static final int DEELIMITER_LINE_INDEX =2;
    public static final int SPACE_INDEX = 3;

    Delimiter(int index, String symbol) {
        this.index = index;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Delimiter fromCode(int index) {
        for (Delimiter symbol: Delimiter.values()) {
            if (symbol.index == index) {
                return symbol;
            }
        }
        return COMMA;
    }
}
