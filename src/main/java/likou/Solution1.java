package likou;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: BYDylan
 * @date: 2022/10/22
 * @description: 两数相加
 * 给定一个整数数组 nums和一个整数目标值 target,请你在该数组中找出 和为目标值 target 的那两个整数,并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是,数组中同一个元素在答案里不能重复出现。
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ,返回 [0, 1]
 */
public class Solution1 {
    public int[] twoSum(int[] nums, int target) {
        int[] indexs = new int[2];
        // 建立k-v ,一一对应的哈希表
        Map<Integer, Integer> hash = new HashMap<>();
        for (int index = 0; index < nums.length; index++) {
            if (hash.containsKey(nums[index])) {
                indexs[0] = index;
                indexs[1] = hash.get(nums[index]);
                return indexs;
            }
            // 将数据存入 key为补数 ,value为下标
            hash.put(target - nums[index], index);
        }
        // // 双重循环 循环极限为(n^2-n)/2
        // for(int i = 0; i < nums.length; i++){
        //     for(int j = nums.length - 1; j > i; j --){
        //         if(nums[i]+nums[j] == target){
        //            indexs[0] = i;
        //            indexs[1] = j;
        //            return indexs;
        //         }
        //     }
        // }
        return indexs;
    }
}
