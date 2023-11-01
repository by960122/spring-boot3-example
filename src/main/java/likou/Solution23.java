package likou;

/**
 * @author: BYDylan
 * @date: 2024-05-13 19:49:04
 * @description: 示例 1：
 *
 *               输入：nums = [1,2,3] 输出：[1,3,2] 示例 2：
 *
 *               输入：nums = [3,2,1] 输出：[1,2,3] 示例 3：
 *
 *               输入：nums = [1,1,5] 输出：[1,5,1]
 *
 */
public class Solution23 {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}
