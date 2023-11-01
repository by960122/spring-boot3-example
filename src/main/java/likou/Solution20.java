package likou;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: BYDylan
 * @date: 2024-05-11 23:20:51
 * @description: 括号生成: 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 
 *               输入：n = 3 输出：["((()))","(()())","(())()","()(())","()()()"]
 */
public class Solution20 {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    /**
     * 如果左括号数量不大于 nnn，我们可以放一个左括号。如果右括号数量小于左括号的数量，我们可以放一个右括号
     */
    private void backtrack(List<String> ans, StringBuilder cur, int left, int right, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur.toString());
            return;
        }
        if (left < max) {
            cur.append('(');
            backtrack(ans, cur, left + 1, right, max);
            cur.deleteCharAt(cur.length() - 1);
        }
        if (right < left) {
            cur.append(')');
            backtrack(ans, cur, left, right + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }
}
