package likou;

/**
 * @author: BYDylan
 * @date: 2022/10/26
 * @description: 整数反转 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。 如果反转后整数超过 32 位的有符号整数的范围[−231, 231− 1] ，就返回 0。 假设环境不允许存储 64
 * 位整数（有符号或无符号）。 示例 1： 输入：x = 123 输出：321 示例 2： 输入：x = -123 输出：-321
 */
public class Solution7 {
    public static void main(String[] args) {
        int reverse = new Solution7().reverse(1534236469);
        System.out.println("reverse = " + reverse);
    }

    public int reverse(int content) {
        int result = 0;
        while (content != 0) {
            if (result < Integer.MIN_VALUE / 10 || result > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = content % 10;
            content /= 10;
            result = result * 10 + digit;
        }
        return result;
    }
}
