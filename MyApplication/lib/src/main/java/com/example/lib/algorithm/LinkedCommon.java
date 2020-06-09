package com.example.lib.algorithm;


public class LinkedCommon {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public static void main(String[] args) {
//        reverseTest();
        addTwoNumbersTest();

    }

    private static void addTwoNumbersTest() {
        int n = 10;
        ListNode l1 = new ListNode(0);
        ListNode curr1 = l1;
        for (int i = 1; i < n; i++) {
            ListNode newL = new ListNode(i);
            curr1.next = newL;
            curr1 = newL;
        }
        ListNode l2 = new ListNode(0);
        ListNode curr2 = l2;
        for (int i = 1; i < n; i++) {
            ListNode newL = new ListNode(i);
            curr2.next = newL;
            curr2 = newL;
        }
        printLinked(l1);
        printLinked(l2);
        ListNode result = addTwoNumbers(l1, l2);
        printLinked(result);

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode rL1 = reverse(l1);
        ListNode rL2 = reverse(l2);
        int index = 0;

        ListNode newL = null;
        ListNode head = null;
        while (rL1 != null || rL2 != null) {
            int sum = 0;
            if (rL1 != null && rL2 != null) {
                sum = rL1.val + rL2.val + index;
                rL1 = rL1.next;
                rL2 = rL2.next;
            } else if (rL1 != null) {
                sum = rL1.val + index;
                rL1 = rL1.next;
            } else if (rL2 != null) {
                sum = rL2.val + index;
                rL2 = rL2.next;
            }
            if (newL == null) {
                newL = new ListNode(sum % 10);
                head = newL;
            } else {
                newL.next = new ListNode(sum % 10);
                newL = newL.next;
            }

            if (sum > 9) {
                index = 1;
            } else {
                index = 0;
            }
        }
        printLinked(head);
        return reverse(head);

    }


    private static void reverseTest() {
        int n = 10;
        ListNode head = new ListNode(0);
        ListNode curr = head;
        for (int i = 1; i < 10; i++) {
            ListNode newL = new ListNode(i);
            curr.next = newL;
            curr = newL;
        }
        printLinked(head);
        ListNode reverseL = reverse(head);
        printLinked(reverseL);

    }

    public static void printLinked(ListNode l1) {
        StringBuilder sb = new StringBuilder();
        while (l1.next != null) {
            sb.append(" i = " + l1.val);
            l1 = l1.next;
        }
        sb.append(" i = " + l1.val);
        System.out.println(sb.toString());
    }

    private static ListNode reverse(ListNode l1) {
        ListNode curr = l1;
        ListNode next = curr.next;
        curr.next = null;
        while (next != null) {
            ListNode temp = next;
            next = next.next;
            temp.next = curr;
            curr = temp;
        }


        return curr;
    }
}
