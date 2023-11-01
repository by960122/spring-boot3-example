package likou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: BYDylan
 * @date: 2024-05-08 23:14:50
 * @description: 给定一个仅包含数字 2-9 的字符串, 返回所有它能表示的字母组合. 答案可以按 任意顺序 返回. 给出数字到字母的映射如下（与电话按键相同）. 注意 1 不对应任何字母 输入：digits = "23"
 *               输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 
 */
public class Solution17 {
    /**
     * 首先使用哈希表存储每个数字对应的所有可能的字母, 然后进行回溯操作. 回溯过程中维护一个字符串, 表示已有的字母排列（如果未遍历完电话号码的所有数字, 则已有的字母排列是不完整的）. 该字符串初始为空.
     * 每次取电话号码的一位数字, 从哈希表中获得该数字对应的所有可能的字母, 并将其中的一个字母插入到已有的字母排列后面, 然后继续处理电话号码的后一位数字, 直到处理完电话号码中的所有数字, 即得到一个完整的字母排列.
     * 然后进行回退操作, 遍历其余的字母排列.
     */
    public List<String> letterCombinations(String digits) {
        List<String> results = new ArrayList<>();
        if (digits.length() == 0) {
            return results;
        }
        Map<Character, String> phoneMap = new HashMap<>() {
            {
                put('2', "abc");
                put('3', "def");
                put('4', "ghi");
                put('5', "jkl");
                put('6', "mno");
                put('7', "pqrs");
                put('8', "tuv");
                put('9', "wxyz");
            }
        };
        backtrack(results, phoneMap, digits, 0, new StringBuffer());
        return results;
    }

    private void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index,
        StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }

}
