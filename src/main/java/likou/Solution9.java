package likou;

/**
 * @author: BYDylan
 * @date: 2023/11/16
 * @description: 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false .
 *
 *               回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数.
 *
 *               例如，121 是回文，而 123 不是.
 *
 *               示例 1：
 *
 *               输入：x = 121 输出：true 示例 2：
 *
 *               输入：x = -121 输出：false 解释：从左向右读, 为 -121 . 从右向左读, 为 121- . 因此它不是一个回文数. 示例 3：
 *
 *               输入：x = 10 输出：false 解释：从右向左读, 为 01 . 因此它不是一个回文数.
 */
public class Solution9 {
    public boolean isPalindrome(int content) {
        // 负数,最后一位是0,都不为回文数字
        if (content < 0 || content % 10 == 0 && content != 0) {
            return false;
        }
        int revertInt = 0;
        while (content > revertInt) {
            revertInt = revertInt * 10 + content % 10;
            content /= 10;
        }
        // 偶数相等,奇数去掉最后一位,1221 12321
        return content == revertInt || content == revertInt / 10;
    }
}
