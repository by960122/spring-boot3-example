package likou;

/**
 * @author: BYDylan
 * @date: 2024-04-20 21:04:25
 * @description: 盛最多水的容器: 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。找出其中的两条线，使得它们与 x
 *               轴共同构成的容器可以容纳最多的水。返回容器可以储存的最大水量。
 */
public class Solution11 {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, res = 0;
        while (left < right) {
            res = height[left] < height[right] ? Math.max(res, (right - left) * height[left++])
                : Math.max(res, (right - left) * height[right--]);
        }
        return res;
    }
}