package likou;

/**
 * @author: BYDylan
 * @date: 2022/10/22
 * @description:
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}