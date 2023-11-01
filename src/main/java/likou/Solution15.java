package likou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: BYDylan
 * @date: 2024-04-28 23:56:50
 * @description: 三数之和: 给你一个整数数组 nums , 判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k , 同时还满足 nums[i] +
 *               nums[j] + nums[k] == 0 。请 你返回所有和为 0 且不重复的三元组。 注意：答案中不可以包含重复的三元组。
 */
public class Solution15 {
    /**
     * 方法一: 双指针
     * 
     * @param nums 数组
     * @return 结果
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int length = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> results = new ArrayList<>();
        // 枚举 a
        for (int first = 0; first < length; ++first) {
            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            int third = length - 1;
            int target = -nums[first];
            // 枚举 b
            for (int second = first + 1; second < length; ++second) {
                // 需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 需要保证 b 的指针在 c 的指针的左侧: 如果 b+c > 目标值, 说明太大了.要缩小范围, 则往左挪c
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                // 如果指针重合, 随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了, 可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    results.add(list);
                }
            }
        }
        return results;
    }
}
