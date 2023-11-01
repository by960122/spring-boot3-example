package likou;

/**
 * @author: BYDylan
 * @date: 2022/10/24
 * @description: 最长回文子串 给你一个字符串 s,找到 s 中最长的回文子串。 示例 1： 输入：s = "babad" 输出："bab" 解释："aba" 同样是符合题意的答案
 */
public class Solution5 {
    /**
     * babad 结果: bab aacabdkacaa 结果:aca
     *
     */
    public static void main(String[] args) {
        String result = new Solution5().longestPalindrome("babad");
        System.out.println("result = " + result);
    }

    /**
     * 中心扩散法
     *
     * @param content 字符
     * @return 长度
     */
    public String longestPalindrome(String content) {
        if (content == null || content.length() < 2) {
            return content;
        }
        int strLen = content.length();
        // 最长回文串的起点
        int maxStart = 0;
        // 最长回文串的终点
        int maxEnd = 0;
        // 最长回文串的长度
        int maxLen = 1;

        boolean[][] dp = new boolean[strLen][strLen];

        for (int rightIndex = 1; rightIndex < strLen; rightIndex++) {
            for (int leftIndex = 0; leftIndex < rightIndex; leftIndex++) {
                // 首尾相等时,有2种情况
                // 情况1：s[i...j]长度不超过3,不用检查必为回文串
                // 情况2：s[i...j]长度大于3,由s[i+1...j-1]来判断
                // 回文含义, aba left+1 = b, right-1 = b
                if (content.charAt(leftIndex) == content.charAt(rightIndex)
                    && (rightIndex - leftIndex < 3 || dp[leftIndex + 1][rightIndex - 1])) {
                    dp[leftIndex][rightIndex] = true;
                    if (rightIndex - leftIndex + 1 > maxLen) {
                        maxLen = rightIndex - leftIndex + 1;
                        maxStart = leftIndex;
                        maxEnd = rightIndex;
                    }
                }
            }
        }
        return content.substring(maxStart, maxEnd + 1);
    }
}
