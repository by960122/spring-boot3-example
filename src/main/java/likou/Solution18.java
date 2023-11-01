package likou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: BYDylan
 * @date: 2024-05-09 22:26:55
 * @description: 四数之和 示例 1: 输入: nums = [1,0,-1,0,-2,2], target = 0 输出: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 */
public class Solution18 {
    /**
     * 排序+双指针
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> quadruplets = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return quadruplets;
        }
        Arrays.sort(nums);
        int length = nums.length;
        for (int first = 0; first < length - 3; first++) {
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            if ((long)nums[first] + nums[first + 1] + nums[first + 2] + nums[first + 3] > target) {
                break;
            }
            if ((long)nums[first] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
                continue;
            }
            for (int second = first + 1; second < length - 2; second++) {
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                if ((long)nums[first] + nums[second] + nums[second + 1] + nums[second + 2] > target) {
                    break;
                }
                if ((long)nums[first] + nums[second] + nums[length - 2] + nums[length - 1] < target) {
                    continue;
                }
                int third = second + 1, four = length - 1;
                while (third < four) {
                    long sum = (long)nums[first] + nums[second] + nums[third] + nums[four];
                    if (sum == target) {
                        quadruplets.add(Arrays.asList(nums[first], nums[second], nums[third], nums[four]));
                        while (third < four && nums[third] == nums[third + 1]) {
                            third++;
                        }
                        third++;
                        while (third < four && nums[four] == nums[four - 1]) {
                            four--;
                        }
                        four--;
                    } else if (sum < target) {
                        third++;
                    } else {
                        four--;
                    }
                }
            }
        }
        return quadruplets;
    }
}
