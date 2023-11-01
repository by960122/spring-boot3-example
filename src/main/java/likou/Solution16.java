package likou;

import java.util.Arrays;

/**
 * @author: BYDylan
 * @date: 2024-05-07 23:22:44
 * @description: 给你一个长度为 n 的整数数组 nums 和 一个目标值 target. 请你从 nums 中选出三个整数, 使它们的和与 target 最接近. 返回这三个数的和. 假定每组输入只存在恰好一个解.
 *               <p>
 *               示例 1： 输入：nums = [-1,2,1,-4], target = 1 输出：2 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) .
 *
 *               双指针
 */
public class Solution16 {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int length = nums.length;
        int best = 10000000;

        // 枚举 a
        for (int first = 0; first < length; ++first) {
            // 保证和上一次枚举的元素不相等
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // 使用双指针枚举 b 和 c
            int second = first + 1, third = length - 1;
            while (second < third) {
                int sum = nums[first] + nums[second] + nums[third];
                // 如果和为 target 直接返回答案
                if (sum == target) {
                    return target;
                }
                // 根据差值的绝对值来更新答案
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }
                if (sum > target) {
                    // 如果和大于 target, 移动 c 对应的指针
                    int k0 = third - 1;
                    // 移动到下一个不相等的元素
                    while (second < k0 && nums[k0] == nums[third]) {
                        --k0;
                    }
                    third = k0;
                } else {
                    // 如果和小于 target, 移动 b 对应的指针
                    int j0 = second + 1;
                    // 移动到下一个不相等的元素
                    while (j0 < third && nums[j0] == nums[second]) {
                        ++j0;
                    }
                    second = j0;
                }
            }
        }
        return best;
    }
}
