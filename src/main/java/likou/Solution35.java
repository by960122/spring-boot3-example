package likou;

/**
 * @author: BYDylan
 * @date: 2024-06-13 22:47:29
 * @description: countAndSay(1) = "1" countAndSay(n) 是 countAndSay(n-1) 的行程长度编码。
 * 
 *               countAndSay(1) = "1"
 *
 *               countAndSay(2) = "1" 的行程长度编码 = "11"
 *
 *               countAndSay(3) = "11" 的行程长度编码 = "21"
 *
 *               countAndSay(4) = "21" 的行程长度编码 = "1211"
 */
public class Solution35 {
    public static void main(String[] args) {
        System.out.println(new Solution35().countAndSay(4));
    }

    public String countAndSay(int num) {
        String str = "1";
        for (int index = 2; index <= num; index++) {
            StringBuilder sb = new StringBuilder();
            // 开始的下标
            int start = 0;
            // 重复的次数: 依次统计字符串中连续相同字符的个数
            int pos = 0;

            while (pos < str.length()) {
                while (pos < str.length() && str.charAt(pos) == str.charAt(start)) {
                    pos++;
                }
                sb.append(pos - start).append(str.charAt(start));
                start = pos;
            }
            str = sb.toString();
        }
        return str;
    }
}
