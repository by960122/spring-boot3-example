package likou;

/**
 * @author: BYDylan
 * @date: 2024-04-21 20:37:29
 * @description: 罗马数字包含以下七种字符： I, V, X, L, C, D 和 M
 */
public class Solution12 {
    private int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private String[] thousands = {"", "M", "MM", "MMM"};
    private String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    /**
     * 根据罗马数字的唯一表示法, 为了表示一个给定的整数 num\textit{num}num, 我们寻找不超过 num\textit{num}num 的最大符号值, 将 num\textit{num}num 减去该符号值,
     * 然后继续寻找不超过 num\textit{num}num 的最大符号值, 将该符号拼接在上一个找到的符号之后, 循环直至 num\textit{num}num 为 000。最后得到的字符串即为
     * num\textit{num}num 的罗马数字表示
     *
     * @param num 数字
     * @return 罗马数字
     */
    public String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();
        for (int index = 0; index < values.length; ++index) {
            int value = values[index];
            String symbol = symbols[index];
            while (num >= value) {
                num -= value;
                roman.append(symbol);
            }
            if (num == 0) {
                break;
            }
        }
        return roman.toString();
    }

    /**
     * 千位数字只能由 M {M}M 表示； 百位数字只能由 C {C}C, CD {CD}CD, D {D}D 和 CM {CM}CM 表示； 十位数字只能由 X {X}X, XL {XL}XL, L {L}L 和 XC
     * {XC}XC 表示； 个位数字只能由 I {I}I, IV {IV}IV, V {V}V 和 IX {IX}IX 表示。 这恰好把这 131313 个符号分为四组, 且组与组之间没有公共的符号。因此, 整数
     * num\textit{num}num 的十进制表示中的每一个数字都是可以单独处理的。
     *
     * @param num 数字
     * @return 罗马数字
     */
    public String intToRoman2(int num) {
        return thousands[num / 1000] + hundreds[num % 1000 / 100] + tens[num % 100 / 10] + ones[num % 10];
    }
}
