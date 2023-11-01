package likou;

/**
 * @author: BYDylan
 * @date: 2023/11/11
 * @description: 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2. 请你找出并返回这两个正序数组的 中位数 . 算法的时间复杂度应该为 O(log (m+n)). 示例 1：
 *               输入：nums1 = [1,3], nums2 = [2] 输出：2.00000 解释：合并数组 = [1,2,3] ,中位数 2 示例 2： 输入：nums1 = [1,2], nums2 = [3,4]
 *               输出：2.50000 解释：合并数组 = [1,2,3,4] ,中位数 (2 + 3) / 2 = 2.5 解法:
 *               https://leetcode.cn/problems/median-of-two-sorted-arrays/solutions/8999/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-2/
 */
public class Solution4 {
    public double findMedianSortedArrays(int[] aArray, int[] bArray) {
        int aLength = aArray.length;
        int bLength = bArray.length;
        int countLength = aLength + bLength;
        int left = -1, right = -1;
        int aStart = -1, bStart = -1;
        for (int index = 0; index < countLength / 2; index++) {
            left = right;
            if (aStart < aLength && (bStart >= bLength || aArray[aStart] < bArray[bStart])) {
                right = aArray[aStart++];
            } else {
                right = bArray[bStart++];
            }
        }
        if ((countLength & 1) == 0) {
            return (left + right) / 2.0;
        } else {
            return right;
        }
    }

    public double findMedianSortedArrays2(int[] aArray, int[] bArray) {
        int aLength = aArray.length;
        int bLength = bArray.length;
        // 因为数组是从索引0开始的,因此我们在这里必须+1,即索引(k+1)的数,才是第k个数
        int left = (aLength + bLength + 1) / 2;
        int right = (aLength + bLength + 2) / 2;
        // 将偶数和奇数的情况合并,如果是奇数,会求两次同样的 k
        return (getKth(aArray, 0, aLength - 1, bArray, 0, bLength - 1, left)
            + getKth(aArray, 0, aLength - 1, bArray, 0, bLength - 1, right)) * 0.5;
    }

    private double getKth(int[] aArray, int aStart, int Aend, int[] bArray, int bStart, int bEnd, int k) {
        // 因为索引和算数不同6-0=6,但是是有7个数的,因为end初始就是数组长度-1构成的.
        // 最后len代表当前数组(也可能是经过递归排除后的数组),符合当前条件的元素的个数
        int len1 = Aend - aStart + 1;
        int len2 = bEnd - bStart + 1;
        // 让 len1 的长度小于 len2,这样就能保证如果有数组空了,一定是 len1
        // 如果len1长度小于len2,把getKth()中参数互换位置,即原来的len2就变成了len1,即len1,永远比len2小
        if (len1 < len2) {
            return getKth(bArray, bStart, bEnd, aArray, aStart, Aend, k);
        }
        // 如果一个数组中没有了元素,那么即从剩余数组nums2的其实start2开始加k再-1.
        // 因为k代表个数,而不是索引,那么从nums2后再找k个数,那个就是start2 + k-1索引处就行了. 因为还包含nums2[start2]也是一个数. 因为它在上次迭代时并没有被排除
        if (len1 == 0) {
            return bArray[bStart + k - 1];
        }
        // 如果k=1,表明最接近中位数了,即两个数组中start索引处,谁的值小,中位数就是谁(start索引之前表示经过迭代已经被排出的不合格的元素,即数组没被抛弃的逻辑上的范围是nums[start]--->nums[end]).
        if (k == 1) {
            return Math.min(aArray[aStart], bArray[bStart]);
        }
        // 为了防止数组长度小于 k/2,每次比较都会从当前数组所生长度和k/2作比较,取其中的小的(如果取大的,数组就会越界)
        // 然后素组如果len1小于k / 2,表示数组经过下一次遍历就会到末尾,然后后面就会在那个剩余的数组中寻找中位数
        int i = aStart + Math.min(len1, k / 2) - 1;
        int j = bStart + Math.min(len2, k / 2) - 1;
        // 如果nums1[i] > nums2[j],表示nums2数组中包含j索引,之前的元素,逻辑上全部淘汰,即下次从J+1开始.
        // 而k则变为k - (j - start2 + 1),即减去逻辑上排出的元素的个数(要加1,因为索引相减,相对于实际排除的时要少一个的)
        if (aArray[i] > bArray[j]) {
            return getKth(aArray, aStart, Aend, bArray, j + 1, bEnd, k - (j - bStart + 1));
        } else {
            return getKth(aArray, i + 1, Aend, bArray, bStart, bEnd, k - (i - aStart + 1));
        }
    }

}
