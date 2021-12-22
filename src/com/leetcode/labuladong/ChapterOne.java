package com.leetcode.labuladong;

import com.leetcode.labuladong.entity.TreeNode;

import java.util.*;

public class ChapterOne {
    // 124. 二叉树中的最大路径和
    private int ans = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftMax = Math.max(0, maxPathSum(root.left));
        int rightMax = Math.max(0, maxPathSum(root.right));

        int maxPath = leftMax + rightMax + root.val;
        ans = Math.max(ans, maxPath);
        return Math.max(leftMax, rightMax) + root.val;
    }

    // 99. 恢复二叉搜索树
    TreeNode pre;
    TreeNode leftErr, rightErr;
    public void recoverTree(TreeNode root) {
        reverse(root);
        if (leftErr!=null && rightErr!=null) {
            int temp = leftErr.val;
            leftErr.val = rightErr.val;
            rightErr.val = temp;
        }

    }
    public void reverse(TreeNode root) {
        if (root == null) return;
        reverse(root.left);
        // BST两个结点被错误地互换，肯定中序遍历左边被互换结点被换大了，左边被互换结点被换小了
        // 即左边被互换结点大于右边相邻结点，右边被互换结点小于左边相邻结点
        if (pre != null) {
            if (root.val < pre.val) {
                if (leftErr == null)
                leftErr = pre;
            }
            if (leftErr != null && root.val < pre.val) {
                rightErr = root;
            }
        }
        pre = root;

        reverse(root.right);
    }

    HashMap<Integer, Integer> mem = new HashMap<>();
    int[] mema = new int[3];
    private int coinProcess(int[] coins, int rest, int ind, HashMap<Integer, Integer> mem) {
        if (mem.containsKey(rest)) {
            return mem.get(rest);
        }
        if (ind == coins.length) {
            return rest == 0 ? 0 : -1;
        }
        int res = -1;
        for (int k = 0; coins[ind] * k <= rest; k++) {
            int next = coinProcess(coins, rest - coins[ind] * k, ind + 1, mem);
            mem.put(rest - coins[ind] * k, next);
            if (next != -1) {
                if (res != -1) {
                    res = Math.min(res, next + k);
                } else {
                    res = next + k;
                }
            }
        }
        return res;
    }
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for (int i = 0; i <= amount; i++) {
            dp[i] = amount + 1;
        }
        dp[0] = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int coin : coins) {
                if (i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    // 300. 最长递增子序列
    // dp[i]表示以nums[i]这个树结尾的最长递增子序列的长度
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int maxLen = 0;
        for (int i = 0; i < dp.length; i++) {
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    // 46.全排列
    List<List<Integer>> res = new LinkedList<>();
    public List<List<Integer>> permute(int[] nums) {
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return res;
    }
    void backtrack(int[] nums, LinkedList<Integer> track) {
        if (track.size() == nums.length) {
            res.add(new LinkedList<>(track));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (track.contains(nums[i])) {
                continue;
            }
            track.add(nums[i]);
            backtrack(nums, track);
            track.removeLast();
        }
    }

    // 752. 打开转盘锁 剑指 Offer II 109. 开密码锁
    String plusOne(String s, int j) {
        char ch[] = s.toCharArray();
        if (ch[j] == '9') {
            ch[j] = '0';
        } else {
            ch[j] += 1;
        }
        return new String(ch);
    }
    String minusOne(String s, int j) {
        char ch[] = s.toCharArray();
        if (ch[j] == '0') {
            ch[j] = '9';
        } else {
            ch[j] -= 1;
        }
        return new String(ch);
    }
    public int openLock(String[] deadends, String target) {
        int step = 0;
        Queue<String> q = new LinkedList<>();
        Set<String> isVisited = new HashSet<>();
        Set<String> deads = new HashSet<>();
        for (String temp : deadends) {
            deads.add(temp);
        }
        q.offer("0000");
        isVisited.add("0000");
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                String cur = q.poll();
                if (deads.contains(cur)) {
                    continue;
                }
                if (cur.equals(target)) {
                    return step;
                }
                for (int j = 0; j < 4; j++) {
                    String minus = minusOne(cur, j);
                    if (!isVisited.contains(minus)) {
                        q.offer(minus);
                        isVisited.add(minus);
                    }
                    String plus = plusOne(cur, j);
                    if (!isVisited.contains(plus)) {
                        q.offer(plus);
                        isVisited.add(plus);
                    }
                }
            }
            step++;
        }
        return -1;
    }

     // 76. 最小覆盖子串
     public String minWindow(String s, String t) {
        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();
        int tLen = t.length();
        for (int i = 0; i < tLen; i++) {
            need.put(t.charAt(i), need.getOrDefault(t.charAt(i), 0) + 1);
        }
        // 窗口[left, right)，初始窗口内无数据
        int left = 0, right = 0;
        // valid表示窗口中满足need条件的字符个数
        int valid = 0;

        // 记录最小覆盖子串的起始索引及长度
        int start = 0, len = Integer.MAX_VALUE;

        while (right < s.length()) {
            // c是将要移入窗口的字符
            char c = s.charAt(right);
            // 往右移动窗口
            right++;
            // 进行窗口内数据的一系列更新
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            // 判断左侧窗口是否要收缩
            while (valid == need.size()) {
                // 在这里更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d是将要移出窗口的字符
                char d = s.charAt(left);
                // 左移窗口
                left++;
                // 进行窗口的一系列更新
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
     }

    // 567. 字符串的排列
    public boolean checkInclusion(String s1, String s2) {
        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();
        int tLen = s1.length();
        for (int i = 0; i < tLen; i++) {
            need.put(s1.charAt(i), need.getOrDefault(s1.charAt(i), 0) + 1);
        }
        // 窗口[left, right)，初始窗口内无数据
        int left = 0, right = 0;
        // valid表示窗口中满足need条件的字符个数
        int valid = 0;

        while (right < s2.length()) {
            char c = s2.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            while (right - left >= s1.length()) {
                if (valid == need.size()) {
                    return true;
                }
                char d = s2.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return false;
    }

    // 438.找到字符串中所有字母异位词(和上一题一样，不同之处在于加了res)
    public List<Integer> findAnagrams(String s2, String s1) {
        List<Integer> res = new ArrayList<>();
        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();
        int tLen = s1.length();
        for (int i = 0; i < tLen; i++) {
            need.put(s1.charAt(i), need.getOrDefault(s1.charAt(i), 0) + 1);
        }
        // 窗口[left, right)，初始窗口内无数据
        int left = 0, right = 0;
        // valid表示窗口中满足need条件的字符个数
        int valid = 0;

        while (right < s2.length()) {
            char c = s2.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            while (right - left >= s1.length()) {
                if (valid == need.size()) {
                    res.add(left);
                }
                char d = s2.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return res;
    }

    // 3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int res = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);
            while (window.get(c) > 1) {
                char d = s.charAt(left);
                left++;
                window.put(d, window.get(d) - 1 );
            }
            res = Math.max(res, right - left);
        }
        return res;
    }
    
    public static void main(String[] args) {
        ChapterOne chapterOne = new ChapterOne();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        node1.left = node3;
        node3.right = node2;
        chapterOne.recoverTree(node1);
        System.out.println(chapterOne.leftErr.val);
        System.out.println(chapterOne.rightErr.val);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        String[] demands = new String[]{"0201","0101","0102","1212","2002"};
        int step = chapterOne.openLock(demands, "0202");
        System.out.println(step);
    }
}
