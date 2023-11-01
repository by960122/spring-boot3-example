package likou;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: BYDylan
 * @date: 2024-08-24 22:47:46
 * @description: 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target, 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合, 并以列表形式返回. 你可以按
 *               任意顺序 返回这些组合. candidates 中的 同一个 数字可以 无限制重复被选取 . 如果至少一个数字的被选数量不同，则两种组合是不同的. 对于给定的输入，保证和为 target 的不同组合数少于
 *               150 个
 * 
 *               示例 1：
 *
 *               输入：candidates = [2,3,6,7], target = 7 输出：[[2,2,3],[7]] 解释： 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。 7
 *               也是一个候选， 7 = 7 。 仅有这两种组合。
 */
public class Solution39 {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates, target, results, combine, 0);
        return results;
    }

    /**
     * 注意有2种选择, 因为数字可以重复使用
     * 
     * @param candidates 输入数组
     * @param target 目前数字
     * @param results 结果集
     * @param combine 已组合列表
     * @param index 下标
     */
    private static void dfs(int[] candidates, int target, List<List<Integer>> results, List<Integer> combine,
        int index) {
        if (index == candidates.length) {
            return;
        }
        if (target == 0) {
            results.add(new ArrayList<>(combine));
            return;
        }
        // 选择1: 直接跳过, index+1
        dfs(candidates, target, results, combine, index + 1);
        // 选择2: 选择当前数, 因为数字可以重复, 所以还是index
        if (target - candidates[index] >= 0) {
            combine.add(candidates[index]);
            dfs(candidates, target - candidates[index], results, combine, index);
            // 递归的目的时尝试新的途径, 如果成功会被加入到results, 所以无论递归成功或失败, 每次出来要退恢复到前一个状态
            combine.remove(combine.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(combinationSum(new int[] {2, 3, 6, 7}, 7));
    }
}
