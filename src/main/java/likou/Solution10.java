package likou;

/**
 * @author: BYDylan
 * @date: 2023/11/22
 * @description: 给你一个字符串 s 和一个字符规律 p, 请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 *
 *               '.' 匹配任意单个字符 '*' 匹配零个或多个前面的那一个元素 所谓匹配, 是要涵盖 整个 字符串 s的, 而不是部分字符串。
 *
 *               示例 1： 输入：s = "aa", p = "a" 输出：false 解释："a" 无法匹配 "aa" 整个字符串。
 */
public class Solution10 {
    /**
     * https://leetcode.cn/problems/regular-expression-matching/solutions/295977/zheng-ze-biao-da-shi-pi-pei-by-leetcode-solution/
     * 
     * @param content 字符
     * @param pattern 正则
     * @return 结果
     */
    public boolean isMatch(String content, String pattern) {
        int contentLen = content.length();
        int patternLen = pattern.length();

        boolean[][] result = new boolean[contentLen + 1][patternLen + 1];
        result[0][0] = true;
        for (int index = 0; index <= contentLen; ++index) {
            for (int j = 1; j <= patternLen; ++j) {
                if (pattern.charAt(j - 1) == '*') {
                    result[index][j] = result[index][j - 2];
                    if (matches(content, pattern, index, j - 1)) {
                        result[index][j] = result[index][j] || result[index - 1][j];
                    }
                } else {
                    if (matches(content, pattern, index, j)) {
                        result[index][j] = result[index - 1][j - 1];
                    }
                }
            }
        }
        return result[contentLen][patternLen];
    }

    private boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }
}
