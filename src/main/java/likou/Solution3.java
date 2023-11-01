package likou;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: BYDylan
 * @date: ate:2022/10/23
 * @description: 无重复字符的最长子串 给定一个字符串 s ,请你找出其中不含有重复字符的最长子串的长度。 示例1: 输入: s = "abcabcbb" 输出: 3 解释: 因为无重复字符的最长子串是
 *               "abc",所以其长度为 3 解题思路: 滑动窗口 其实就是一个队列,比如例题中的 abcabcbb，进入这个队列(窗口)为 abc 满足题目要求,当再进入 a,队列变成了 abca,这时候不满足要求.
 *               所以,我们要移动这个队列！
 */
public class Solution3 {
    public static int lengthOfLongestSubstring(String content) {
        // 哈希集合,记录每个字符是否出现过
        Set<Character> characters = new HashSet<>();
        // 右指针,初始值为 -1,相当于我们在字符串的左边界的左侧,还没有开始移动
        int rightIndex = -1, result = 0;
        for (int leftIndex = 0; leftIndex < content.length(); ++leftIndex) {
            if (leftIndex != 0) {
                // 左指针向右移动一格,移除一个字符
                characters.remove(content.charAt(leftIndex - 1));
            }
            // 只要不重复,就一直往右找
            while (rightIndex + 1 < content.length() && !characters.contains(content.charAt(rightIndex + 1))) {
                // 不断地移动右指针
                characters.add(content.charAt(rightIndex + 1));
                ++rightIndex;
            }
            // 第 leftIndex 到 rightIndex 个字符是一个极长的无重复字符子串
            result = Math.max(result, rightIndex - leftIndex + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }
}
