package com.leetcode.labuladong;

import com.leetcode.labuladong.entity.TreeNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ChapterTwoDp {
    // 300. 最长递增子序列
    // dp[i]表示以nums[i]这个树结尾的最长递增子序列的长度
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = 1;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // 354. 俄罗斯套娃信封问题
    public int maxEnvelopes(int[][] envelopes) {
        int len = envelopes.length;
        // 排序后转化成最长递增子序列问题
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    return b[1] - a[1];
                } else {
                    return a[0] - b[0];
                }
            }
        });
        int[] height = new int[len];
        for (int i = 0; i < len; i++) {
            height[i] = envelopes[i][1];
        }
        // 使用最长递增子序列问题求解
        return lengthOfLIS(height);
    }

    // 53. 最大子数组和
    public int maxSubArray(int[] nums) {
        // dp[i]表示以nums[i]为结尾的最大子数组和
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] <= 0) {
                dp[i] = nums[i];
            } else {
                dp[i] = dp[i - 1] + nums[i];
            }
        }
        int res = dp[0];
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // 72. 编辑距离
    public int minDistance(String word1, String word2) {
        int[][] memo = new int[word1.length()][word2.length()];
        return dp(word1, word1.length() - 1, word2, word2.length() - 1, memo);
    }

    // def dp(i, j) -> int 返回 s1[0..i] 和 s2[0..j] 的最小编辑距离
    public int dp(String word1, int i, String word2, int j, int[][] memo) {
        if (i == -1) {
            return j + 1;
        } else if (j == -1) {
            return i + 1;
        }
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        if (word1.charAt(i) == word2.charAt(j)) {
            memo[i][j] = dp(word1, i - 1, word2, j - 1, memo);
        } else {
            memo[i][j] = Math.min(
                    dp(word1, i, word2, j - 1, memo) + 1, // 插入
                    Math.min(dp(word1, i - 1, word2, j, memo) + 1, // 删除
                            dp(word1, i - 1, word2, j - 1, memo) + 1) // 替换
            );
        }
        return memo[i][j];
    }

    public int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public int minDistance2(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp[m][n];
    }

    // 516. 最长回文子序列
    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        int[][] memo = new int[len][len];
        return dpLps(s, 0, len - 1, memo);
    }
    // 在字串s[i……j]中，最长回文子序列的长度为dp[i][j]
    int dpLps(String s, int i, int j, int[][] memo) {
        if (i == j) {
            return 1;
        } else if (i > j) {
            return 0;
        }

        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        if (s.charAt(i) == s.charAt(j)) {
            memo[i][j] = dpLps(s, i + 1, j - 1, memo) + 2;
        } else {
            memo[i][j] = Math.max(dpLps(s, i, j - 1, memo), dpLps(s, i + 1, j, memo));
        }
        return memo[i][j];
    }

    // 10. 正则表达式匹配
    public boolean isMatch(String s, String p) {
        int[][] memo = new int[s.length()][p.length()];
        return dpM(s, 0, p, 0, memo);
    }
    // 若dp(s,i,p,j)为true，则表示s[i……]可以匹配p[j……]；若dp(s,i,p,j)为false，则表示s[i……]不能匹配p[j……]
    public boolean dpM(String s, int i, String p, int j, int[][] memo) {
        int m = s.length();
        int n = p.length();
        // base case
        // 模式串p已经匹配完毕，只要文本串s也匹配完了，说明匹配成功
        if (j == n) {
            return i == m;
        }
        // 文本串s已经匹配完毕，只要模式串p匹配完毕或者模式串p可以匹配空串，就可以匹配成功
        if (i == m) {
            if ((n - j) % 2 != 0) {
                return false;
            }
            for (; j + 1 < n; j += 2) {
                if (p.charAt(j + 1) != '*') {
                    return false;
                }
            }
            return true;
        }
        if (memo[i][j] != 0) {
            return memo[i][j] == 1;
        }

        boolean res = false;
        // 匹配
        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
            if (j < n - 1 && p.charAt(j + 1) == '*') {
                res = dpM(s, i, p, j + 2, memo) // 通配符*匹配0次
                || dpM(s, i + 1, p, j, memo); // 匹配1次或多次
            } else {
                res = dpM(s, i + 1, p, j + 1, memo); // 常规匹配1次
            }
            // 不匹配
        } else {
            if (j < n - 1 && p.charAt(j + 1) == '*') {
                res = dpM(s, i, p, j + 2, memo); // 通配符*匹配0次
            }
        }
        if (res) {
            memo[i][j] = 1;
        } else {
            memo[i][j] = 2;
        }
        return res;
    }

    // 651.4键键盘
    int maxA(int N) {
        return dpMaxA(N, 0, 0);
    }
    /**
     * 第一个状态是剩余的按键次数，用 n 表示；第二个状态是当前屏幕上字符 A 的数量，用 a_num 表示；第三个状态是剪切板中字符 A 的数量，用 copy 表示。
     * 如此定义「状态」，就可以知道 base case：当剩余次数 n 为 0 时，a_num 就是我们想要的答案。
     * 结合刚才说的 4 种「选择」，我们可以把这几种选择通过状态转移表示出来：
     * dp(n - 1, a_num + 1, copy),    # A
     * 解释：按下 A 键，屏幕上加一个字符
     * 同时消耗 1 个操作数
     *
     * dp(n - 1, a_num + copy, copy), # C-V
     * 解释：按下 C-V 粘贴，剪切板中的字符加入屏幕
     * 同时消耗 1 个操作数
     *
     * dp(n - 2, a_num, a_num)        # C-A C-C
     * 解释：全选和复制必然是联合使用的，
     * 剪切板中 A 的数量变为屏幕上 A 的数量
     * 同时消耗 2 个操作数
     */
    int dpMaxA(int n, int aNum, int copy) {
        // base case
        if (n <= 0) {
            return aNum;
        }
        return max(dpMaxA(n - 1, aNum + 1, copy),
                dpMaxA(n - 1, aNum + copy, copy),
                dpMaxA(n - 2, aNum, aNum));
    }
    public int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    int maxA2(int N) {
        int[] dp = new int[N + 1];
        dp[0] = 0;
        for (int i = 1; i <= N; i++) {
            // 按A键
            dp[i] = dp[i - 1] + 1;
            for (int j = 2; j < i; j++) {
                dp[i] = Math.max(dp[i], dp[j - 2] * (i - j + 1));
            }
        }
        return dp[N];
    }

    // 887. 鸡蛋掉落
    public int superEggDrop(int k, int n) {
        int memo[][] = new int[k][n];
        return dpED(k, n, memo);
    }
    public int dpED(int k, int n, int memo[][]) {
        // base case
        if (n == 0) {
            return 0;
        }
        if (k == 1) {
            return n;
        }

        if (memo[k][n] != 0) {
            return memo[k][n];
        }
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            res = Math.min(res, Math.max(dpED(k, n - i, memo), dpED(k - 1, i - 1, memo)) + 1);
        }
        memo[k][n] = res;
        return res;
    }
    public int dpED2(int k, int n, int memo[][]) {
        // base case
        if (n == 0) {
            return 0;
        }
        if (k == 1) {
            return n;
        }

        if (memo[k][n] != 0) {
            return memo[k][n];
        }
        int res = Integer.MAX_VALUE;
        int low = 1, high = n;
        while (low <= high) {
            int mid = (low + high) / 2;
            int broken = dpED2(k - 1, mid - 1, memo);
            int notBroken = dpED2(k, n - mid, memo);
            if (broken > notBroken) {
                high = mid - 1;
                res = Math.min(res, broken + 1);
            } else {
                low = mid + 1;
                res = Math.min(res, notBroken + 1);
            }
        }
        memo[k][n] = res;
        return res;
    }

    // 312. 戳气球
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // 添加两侧的虚拟气球
        int[] points = new int[n + 2];
        points[0] = points[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            points[i] = nums[i - 1];
        }

        // dp[i][j]表示，戳破气球i和气球j之间(开区间，不包括i和j)的所有气球，可以得到的最高分数为dp[i][j]
        // base case已经被初始化为0
        int[][] dp = new int[n + 2][n + 2];
        // 开始状态转移
        // i从下到上
        for (int i = n + 1; i >= 0; i--) {
            // j从左到右
            for (int j = i + 1; j < n + 2; j++) {
                // 最后戳破的气球是哪个？
                for (int k = i + 1; k < j; k++) {
                    // 择优做选择
                    dp[i][j] = Math.max(
                            dp[i][j],
                            dp[i][k] + dp[k][j] + points[i] * points[k] * points[j]
                    );
                }
            }
        }
        return dp[0][n + 1];
    }

    // 416. 分割等和子集 2.16 子集背包问题 leetcode题解有动图
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        // 和为奇数时，不可能划分成两个相等的集合
        if (sum % 2 != 0) {
            return false;
        }
        sum = sum / 2;
        boolean[][] dp = new boolean[nums.length + 1][sum + 1];
        // base case
        // dp[……][0] = true,背包没有空间了相当于装满了；dp[0][……] = false,没有物品选择了，肯定没办法装满背包了
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = true;
        }
        // 开始状态转移
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j - nums[i - 1] < 0) {
                    // 背包容量不足，装不下第i个物品
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 装入或不装入背包，看看是否存在一种情况恰好装满
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[nums.length][sum];
    }

    // 518. 零钱兑换 II 2.17 经典动态规划：完全背包问题
    public int change(int amount, int[] coins) {
        int n = coins.length;
        // dp[i][j]: 如果只使用coins中的前i个硬币的面值，像凑出金额j，有dp[i][j]种凑法。
        int[][] dp = new int[n + 1][amount + 1];
        // base case
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i - 1] < 0) {
                    dp[i][j] = dp[i - 1][j]; // coins[i-1]不装入背包
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]]; // coins[i-1]不装入背包 + coins[i-1]装入背包
                    // 如果是0-1背包的话，就是dp[i][j] = dp[i - 1][j] + dp[i - 1][j - coins[i - 1]];
                }
            }
        }
        return dp[n][amount];
    }

    // 198. 打家劫舍
    public int rob(int[] nums) {
        int len = nums.length;
        // dp[i]：打劫第1到i-1家的最大收获
        int[] dp = new int[len + 2];
        dp[0] = dp[1] = 0;
        for (int i = 2; i <= len + 1; i++) {
            dp[i] = Math.max(nums[i - 2] + dp[i - 2], dp[i - 1]);
        }
        return dp[len + 1];
    }

    // 213. 打家劫舍 II
    public int robII(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        return Math.max(robRange(nums, 1, nums.length - 1), robRange(nums, 0, nums.length - 2));
    }
    public int robRange(int[] nums, int start, int end) {
        int len = nums.length;
        int[] dp = new int[len + 2];
        for (int i = start + 2; i <= end + 2; i++) {
            dp[i] = Math.max(nums[i - 2] + dp[i - 2], dp[i - 1]);
        }
        return dp[end + 2];
    }

    // 337. 打家劫舍 III
    Map<TreeNode, Integer> memo = new HashMap<>();
    public int robIII(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (memo.containsKey(root)) {
            return memo.get(root);
        }
        int doIt = root.val
                + (root.left == null ? 0 : robIII(root.left.left) + robIII(root.left.right))
                + (root.right == null ? 0 : robIII(root.right.left) + robIII(root.right.right));
        int notDoIt = robIII(root.left) + robIII(root.right);
        int res = Math.max(doIt, notDoIt);
        memo.put(root, res);
        return res;
    }

    // 494. 目标和 可以转化成0-1背包问题
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        // 这两种情况，不可能存在合法的子集划分
        if (sum < target || (sum - target) % 2 == 1) {
            return 0;
        }
        // 转化成0-1背包问题
        return subsets(nums, (sum - target) / 2);
    }
    public int subsets(int[] nums, int sum) {
        int n = nums.length;
        // dp[i][j]=x表示，若只在前i个物品中选择，且当前背包容量为j，则最多有x中方法可以恰好装满背包。
        int[][] dp = new int[n + 1][sum + 1];
        // base case
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                if (j >= nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][sum];
    }




    public static void main(String[] args) {
//        int n = 5;
//        for (int l = 2; l <= n; l++) {
//            for (int i = 0; i <= n - l; i++) {
//                int j = l + i - 1;
//                // 计算 dp[i][j]
//                System.out.println("[" + i + "," + j + "]");
//            }
//        }
        ChapterTwoDp chapterTwoDp = new ChapterTwoDp();
//        boolean isMatch = chapterTwoDp.isMatch("aa", "a");
//        System.out.println(isMatch);
//
//        for (int i = 0; i < 20; i++) {
//            System.out.println(i);
//            int maxA1 = chapterTwoDp.maxA(i);
//            int maxA2 = chapterTwoDp.maxA2(i);
//            System.out.println("1:" + maxA1 + "; 2:" + maxA2);
//            if (maxA1 != maxA2) {
//                System.out.println("false");
//            }
//        }
        int amount = 5;
        int[] coins = {1, 2, 5, 8, 2};
        int change = chapterTwoDp.change(amount, coins);
        System.out.println(change);
        System.out.println(chapterTwoDp.rob(coins));
        System.out.println(chapterTwoDp.robRange(coins, 0, 4));

    }
}
