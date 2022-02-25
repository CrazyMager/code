package com.leetcode.labuladong;

import com.leetcode.labuladong.entity.ListNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @description:
 * @projectName:code
 * @see:com.leetcode.labuladong.entity
 * @author:Lujw
 * @createTime:18:50 2021/12/31
 * @version:1.0
 */
public class ChapterFive {
    // 204. 计数质数 5.1 如何高效寻找素数：统计所有小于非负整数 n 的质数的数量。
    public int countPrimes(int n) {
        boolean[] isPrime = new boolean[n];
        // 初始化为true
        Arrays.fill(isPrime, true);
        // 素数从2开始算
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (isPrime[i]) {
                // i的倍数就不可能是素数了,避免重复计算（例如2*3和3*2重复），从i的i倍开始算
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }
        return count;
    }

    // 372. 超级次方 5.2 如何高效进行模幂运算：计算a的b次幂然后对1337取模
    int base = 1337;
    public int superPow(int a, int[] b) {
        return superPow(a, b, b.length - 1);
    }
    public int superPow(int a, int[] b, int end) {
        // base case
        if (end < 0) {
            return 1;
        }
        int last = b[end];
        int part1 = myPow(a, last);
        int part2 = myPow(superPow(a, b, end - 1), 10);

        return (part1 * part2) % base;
    }
    // 计算a的k次幂然后对1337取模
//    int myPow(int a, int k) {
//        // 对因子求模
//        a %= base;
//        int res = 1;
//        for (int i = 0; i < k; i++) {
//            res *= a;
//            res %= base;
//        }
//        return res;
//    }
    int myPow(int a, int k) {
        if (k == 0) {
            return 1;
        }
        a %= base;

        if (k % 2 == 1) {
            // k是奇数
            return (a * myPow(a, k - 1)) % base;
        } else {
            // k是偶数
            int sub = myPow(a, k / 2);
            return (sub * sub) % base;
        }
    }

    // 875. 爱吃香蕉的珂珂 5.3.1
    public int minEatingSpeed(int[] piles, int h) {
        int max = 0;
        for (int i = 0; i < piles.length; i++) {
            if (piles[i] > max) {
                max = piles[i];
            }
        }

        // 吃香蕉最慢每小时1个，最快max个
        int left = 1, right = max + 1;
        // 二分搜索，前闭后开[left, right)
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canFinish(piles, mid, h)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
    // 以speed速度吃n个香蕉h个小时是否可以吃完
    boolean canFinish(int[] piles, int speed, int h) {
        int time = 0;
        for(int n : piles) {
            time += timeOf(n, speed);
        }
        return time <= h;
    }
    // 以speed速度吃n个香蕉需要多久
    int timeOf(int n, int speed) {
        return n / speed + ((n % speed == 0) ? 0 : 1);
    }

    // 1011. 在 D 天内送达包裹的能力 5.3.2
    public int shipWithinDays(int[] weights, int days) {
        int left = getMax(weights), right = getSum(weights) + 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canConveyor(weights, mid, days)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
    // 货船每天运输能力capacity，days天是否能运输完货物
    boolean canConveyor (int[] weights, int capacity, int days) {
        int i = 0;
        for (int day = 0; day < days; day++) {
            int maxCap = capacity;
            while ((maxCap -= weights[i]) >= 0) {
                i++;
                if (i == weights.length) {
                    return true;
                }
            }
        }
        return false;
    }
    int getMax(int[] weights) {
        int maxWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] > maxWeight) {
                maxWeight = weights[i];
            }
        }
        return maxWeight;
    }
    int getSum(int[] weights) {
        int sumWeights = 0;
        for (int i = 0; i < weights.length; i++) {
            sumWeights += weights[i];
        }
        return sumWeights;
    }

    // 42. 接雨水 5.4 如何高效解决接雨水问题
    // 1)暴力解法，时间复杂度O(n*n)，空间复杂度O(1)
    public int trapI(int[] height) {
        int n = height.length;
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            int leftMax = 0, rightMax = 0;
            // 找右边最高的柱子
            for (int j = i; j < n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }
            // 找左边最高的柱子
            for (int j = 0; j <= i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }
            ans += Math.min(rightMax, leftMax) - height[i];
        }
        return ans;
    }
    // 1)备忘录优化：开两个数组记录leftMax和rightMax。时间复杂度O(n)，空间复杂度O(n)
    public int trapII(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        rightMax[n - 1] = height[n - 1];
        // 从左向右计算leftMax
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }
        // 从右向左计算rightMax
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }
        // 计算答案
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;
    }
    // 3)双指针解法。时间复杂度O(n)，空间复杂度O(1)
    public int trap(int[] height) {
        int n = height.length;
        int left = 0, right = n - 1;
        // leftMax代表[0, left]中最高柱子高度
        int leftMax = height[0];
        // leftMax代表[right, n - 1]中最高柱子高度
        int rightMax = height[n - 1];

        int ans = 0;
        while (left <= right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax < rightMax) {
                ans += leftMax - height[left];
                left++;
            } else {
                ans += rightMax - height[right];
                right--;
            }
        }
        return ans;
    }

    // 26. 删除有序数组中的重复项 5.5
    public int removeDuplicates(int[] nums) {
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow + 1] = nums[fast];
                slow++;
            }
        }
        return slow + 1;
    }
    // 83. 删除排序链表中的重复元素 5.5
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head, fast = head.next;
        while (fast != null) {
            if (slow.val != fast.val) {
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }
        slow.next = null;
        return head;
    }

    // 5. 最长回文子串 5.6
    public String longestPalindrome(String s) {
        int len = 0;
        int left = 0, right = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            int len1 = palindrome(s, i, i);
            int len2 = palindrome(s, i, i + 1);
            int lenMax = Math.max(len1, len2);
            if (lenMax > len) {
                len = lenMax;
                left = i - (len - 1) / 2;
                right = i + len / 2;
            }
        }
        return s.substring(left, right + 1);
    }
    int palindrome(String s, int left, int right) {
        int strLen = s.length();
        while (left >= 0 && right < strLen && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    // 55. 跳跃游戏 5.7.1
    public boolean canJump(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest <= i) {
                return false;
            }
        }
        return farthest >= nums.length - 1;
    }
    // 45. 跳跃游戏 II 5.7.2   1）dp解法
    public int jumpDp(int[] nums) {
        // dp[p]:从索引p到最后一格需要的最少步数
        int[] dp = new int[nums.length];
        Arrays.fill(dp, nums.length);
        dp[nums.length - 1] = 0;
        // 穷举每一个选择
        for (int p = nums.length - 2; p >= 0; p--) {
            // 你可以选择跳1步，2步……，nums[p]步
            for (int i = 1; i <= nums[p]; i++) {
                if (p + i < nums.length) {
                    dp[p] = Math.min(dp[p], 1 + dp[p + i]);
                }
            }
        }
        return dp[0];
    }

    // 45. 跳跃游戏 II 5.7.2   2）贪心
    public int jump(int[] nums) {
        // 站在索引i，最远能跳到索引end
        int end = 0;
        // 从索引[i……end]起跳，最远能到的距离
        int farthest = 0;
        // 记录跳跃次数
        int step = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == end) {
                // 每一次跳都选择下一次能跳最远的，因为覆盖了其他选择的跳跃区间
                step++;
                end = farthest;
            }
        }
        return step;
    }

    // 435. 无重叠区间 5.8.3
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        // 按end排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        // 至少有一个区间不相交
        int count = 1;
        // 排序后，第一个区间就是end最小的
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            if (start >= end) {
                // 找到下一个选择区间了
                count++;
                end = intervals[i][1];
            }
        }
        return intervals.length - count;
    }

    // 452. 用最少数量的箭引爆气球 5.8.3
    // 有几个不重叠区间，就需要射几支箭（end == start也算重叠）
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
//                return o1[1] - o2[1]; leetcode有一个case int溢出
                if (o1[1] >= o2[1]) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        int count = 1;
        int end = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > end) {
                count++;
                end = points[i][1];
            }
        }
        return count;
    }

    // 20. 有效的括号 5.9
    public boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
                stack.push(s.charAt(i));
            } else {
                if (stack.isEmpty() || leftOf(s.charAt(i)) != stack.pop()) {
                    return false;
                }
            }
        }
        // 是否所有的左括号都被匹配了
        return stack.isEmpty();
    }
    // 返回对应的左括号类型
    char leftOf(char c) {
        if (c == ')') {
            return '(';
        }
        if (c == ']') {
            return '[';
        }
        return '{';
    }

    // 855. 考场就座 5.10 如何调度考生的座位

    // 292. Nim 游戏 5.13.1
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    // 877. 石子游戏 5.13.2
    public boolean stoneGame(int[] piles) {
        return true;
    }

    // 319. 灯泡开关 5.13.3
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }

    public static void main(String[] args) {
        ChapterFive chapterFive = new ChapterFive();
//        int count = chapterFive.countPrimes(10);
//        System.out.println(count);
//
//        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
//        int ans = chapterFive.trap(height);

//        int[] nums = new int[]{1,1,2,2,3,3,4,5,5,5};
//        int len = chapterFive.removeDuplicates(nums);

//        String s = "aabaa";
//        String str = chapterFive.longestPalindrome(s);

//        int[] nums = new int[]{2,3,0,1,4};
//        int step = chapterFive.jump(nums);

//        int[][] points = new int[][]{{-2147483646,-2147483645},{2147483646,2147483647}};
//        int count = chapterFive.findMinArrowShots(points);

        String s = "()";
        boolean isValid = chapterFive.isValid(s);
        System.out.println("aaa");
    }
}
