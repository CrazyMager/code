package com.leetcode.labuladong;

import java.util.*;

/**
 * @description:
 * @projectName:code
 * @see:com.company
 * @author:Lujw
 * @createTime:22:32 2021/12/24
 * @version:1.0
 */
public class ChapterFour {
    // 78. 子集 4.1.1
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            int all = res.size();
            for (int j = 0; j < all; j++) {
                List<Integer> tmp = new ArrayList<>(res.get(j));
                tmp.add(nums[i]);
                res.add(tmp);
            }
        }
        return res;
    }

    // 78. 子集 回溯方法
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> subsets2(int[] nums) {
        List<Integer> track = new ArrayList<>();
        backtrackSubsets(nums, 0, track);
        return res;
    }

    public void backtrackSubsets(int[] nums, int start, List<Integer> track) {
        res.add(new ArrayList<>(track));
        for (int i = start; i < nums.length; i++) {
            track.add(nums[i]);
            backtrackSubsets(nums, i + 1, track);
            track.remove(new Integer(nums[i]));
        }
    }

    // 77.组合 4.1.2
    List<List<Integer>> resCombine = new ArrayList<>(); // 记录所有组合

    public List<List<Integer>> combine(int n, int k) {
        List<Integer> track = new ArrayList<>();
        backtrackCombine(n, k, 1, track);
        return resCombine;
    }

    public void backtrackCombine(int n, int k, int start, List<Integer> track) {
        // 到达叶子节点才更新res
        if (track.size() == k) {
            resCombine.add(new ArrayList<>(track)); // 注意：要新建list存储结果
            return;
        }
        for (int i = start; i <= n; i++) {
            // 做选择
            track.add(i);
            // 递归回溯
            backtrackCombine(n, k, i + 1, track);
            // 撤销选择
            track.remove(new Integer(i));
        }
    }

    // 46 4.1.3 全排列
    List<List<Integer>> resPermute = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        List<Integer> track = new ArrayList<>();
        backtrackPermute(nums, track);
        return resPermute;
    }

    public void backtrackPermute(int[] nums, List<Integer> track) {
        if (track.size() == nums.length) {
            resPermute.add(new ArrayList<>(track));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (track.contains(nums[i])) {
                continue;
            }
            track.add(nums[i]);
            backtrackPermute(nums, track);
            track.remove(new Integer(nums[i]));
        }
    }

    // 37. 解数独 4.2
    public void solveSudoku(char[][] board) {
        backtrackSudo(board, 0, 0);
    }

    boolean backtrackSudo(char[][] board, int i, int j) {
        int m = 9, n = 9;
        if (j == n) {
            // 穷举到最后一列的话就换到下一行重新开始
            return backtrackSudo(board, i + 1, 0);
        }
        if (i == m) {
            // 找到一个可行解，触发base case
            return true;
        }

        if (board[i][j] != '.') {
            // 如果有预设数字，就不用穷举
            return backtrackSudo(board, i, j + 1);
        }

        for (char ch = '1'; ch <= '9'; ch++) {
            // 如果遇到不合法的数字，就跳过
            if (!isValid(board, i, j, ch)) {
                continue;
            }
            board[i][j] = ch;
            // 如果找到一个可行解，立即结束
            if (backtrackSudo(board, i, j)) {
                return true;
            }
            board[i][j] = '.';
        }
        // 穷举完1-9还没有找到可行解
        // 需要前面的格子换个数字穷举
        return false;
    }

    boolean isValid(char[][] board, int row, int col, char n) {
        for (int i = 0; i < 9; i++) {
            // 判断行是否重复
            if (board[row][i] == n) {
                return false;
            }
            // 判断列是否重复
            if (board[i][col] == n) {
                return false;
            }
            // 判断3*3的方框是否存在重复
            if (board[(row / 3) * 3 + i / 3][(col / 3) * 3 + i % 3] == n) {
                return false;
            }
        }
        return true;
    }


    // 22. 括号生成 4.3
    public List<String> generateParenthesis(int n) {
        List<String> resList = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        backtrack(0, 0, n, cur, resList);
        return resList;
    }
    void backtrack(int open, int close, int n, StringBuilder cur, List<String> resList) {
        if (open > n || close > n) {
            return;
        }
        if (open < close) {
            return;
        }
        if (cur.length() == n * 2) {
            resList.add(cur.toString());
            return;
        }
        cur.append('(');
        backtrack(open + 1, close, n, cur, resList);
        cur.deleteCharAt(cur.length() - 1);
        cur.append(')');
        backtrack(open, close + 1, n, cur, resList);
        cur.deleteCharAt(cur.length() - 1);
    }

    // 773. 滑动谜题 4.4
    public int slidingPuzzle(int[][] board) {
        int m = 2, n = 3;
        StringBuilder sb = new StringBuilder();
        String target = "123450";
        // 将2*3的字符数组转化成字符串
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]);
            }
        }
        String start = sb.toString();
        // 记录一维字符串的相邻索引
        int[][] neighbor= new int[][]{{1, 3}, {0, 4, 2}, {1, 5}, {0, 4}, {3, 1, 5}, {4, 2}};
        // BFS框架开始
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        q.offer(start);
        visited.add(start);

        int step = 0;
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                String cur = q.poll();
                // 判断是否达到目标局面
                if (cur.equals(target)) {
                    return step;
                }
                // 找到数字0的索引
                int idx = 0;
                for (; cur.charAt(idx) != '0'; idx++);
                // 将数字0和相邻的数字交换位置
                for (int adj : neighbor[idx]) {
                    char[] newBoard = cur.toCharArray();
                    char temp = newBoard[adj];
                    newBoard[adj] = newBoard[idx];
                    newBoard[idx] = temp;
                    String newBoardStr = new String(newBoard);
                    if (!visited.contains(newBoardStr)) {
                        q.offer(newBoardStr);
                        visited.add(newBoardStr);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    // 15.三数之和 4.6.2 3Sum问题
    public List<List<Integer>> threeSum(int[] nums) {
        return threeSumTarget(nums, 0, 0);
    }
    // 返回nums[start,……]中所有和为target的三元组
    public List<List<Integer>> threeSumTarget(int[] nums, int start, int target) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = start; i < len - 2; i++) {
            // 对每个nums[i]计算twoSum
            List<List<Integer>> tuplesRes= twoSumTarget(nums, i + 1, target - nums[i]);
            for (List<Integer> tuple : tuplesRes) {
                tuple.add(nums[i]);
                res.add(tuple);
            }
            // 跳过第一个数字重复的情况，否则可能出现重复结果
            while (i < len - 2 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return res;
    }
    // 计算nums[start,……]中两数之和，nums为有序数组
    public List<List<Integer>> twoSumTarget(int[] nums, int start, int target) {
        // nums为有序数组
        int low = start, high = nums.length - 1;
        List<List<Integer>> res = new ArrayList<>();
        while (low < high) {
            int left = nums[low], right = nums[high];
            int sum = nums[low] + nums[high];
            if (sum == target) {
                List<Integer> subList = new ArrayList<>();
                subList.add(nums[low]);
                subList.add(nums[high]);
                res.add(subList);
                while (low < high && left == nums[low]) {
                    low++;
                }
                while (low < high && right == nums[high]) {
                    high--;
                }
            }else if (sum < target) {
                while (low < high && left == nums[low]) {
                    low++;
                }
            } else {
                while (low < high && right == nums[high]) {
                    high--;
                }
            }
        }
        return res;
    }

    // 18. 四数之和 4.6.3 4Sum问题
    public List<List<Integer>> fourSum(int[] nums, int start, int target) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = start; i < len - 3; i++) {
            // 对target - nums[i]计算threeSum
            List<List<Integer>> triplesRes = threeSumTarget(nums, i + 1, target - nums[i]);
            // 如果存在满足条件的三元组，加上nums[i]就是结果四元组
            for (List<Integer> triple : triplesRes) {
                triple.add(nums[i]);
                res.add(triple);
            }
            // fourSum的第一个数不能重复
            while (i < len - 3 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return res;
    }

    // 224. 基本计算器 4.7.4
    public int calculate(String s) {

        StringBuilder sb = new StringBuilder(s);
        return calculate(sb);
    }
    public int calculate(StringBuilder sb) {
        Deque<Integer> stack = new LinkedList<>();
        // 记录num前的符号，初始化为+
        char sign = '+';
        // 记录算式中的数字
        int num = 0;

        while (sb.length() != 0){
            char c = sb.charAt(0);
            sb.deleteCharAt(0);
            // 如果是数字
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }
            // 遇到左括号开始递归计算num，将括号中的内容视为一个数
            if (c == '(') {
                num = calculate(sb);
            }
            // 遇到不是数字的字符就执行上一个sign的操作
            if ((!Character.isDigit(c) && c != ' ') || sb.length() == 0) {
                if (sign == '+') {
                    stack.push(num);
                } else if (sign == '-') {
                    stack.push(-num);
                } else if (sign == '*') {
                    stack.push(num * stack.pop());
                } else if (sign == '/') {
                    stack.push((int)(stack.pop() / num));
                }
                // 更新符号为当前符号，数字清零
                sign = c;
                num = 0;
            }
            if (c == ')') {
                break;
            }
        }
        // 将栈中所有元素求和就是答案
        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }

    // 969. 煎饼排序 4.8摊烧饼
    List<Integer> resPancakeSort = new ArrayList<>();
    public List<Integer> pancakeSort(int[] arr) {
        sort(arr, arr.length);
        return resPancakeSort;
    }
    void sort(int[] arr, int n) {
        // base case
        if (n == 1) {
            return;
        }
        // 寻找最大烧饼的索引
        int maxIndex = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        // 第一次翻转，将max反转到最上面
        reverse(arr, 0, maxIndex);
        // 记录这一次反转
        resPancakeSort.add(maxIndex + 1);
        // 第二次翻转，将max反转到最下面
        reverse(arr, 0, n - 1);
        // 记录这一次反转
        resPancakeSort.add(n);

        // 递归调用
        sort(arr, n - 1);
    }
    void reverse(int[] arr, int i, int j) {
        while (i < j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }

    // 560. 和为 K 的子数组 4.9 前缀和技巧解决子数组问题
    public int subarraySum(int[] nums, int k) {
        // 构造前缀和
        int[] preSums = new int[nums.length + 1];
        preSums[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            preSums[i + 1] = preSums[i] + nums[i];
        }
        int res = 0;
        // 因为有负数存在，不一定区间内元素越多sum越大，所以下面做法是错的
//        int i = 0, j = 0;
//        while (j < nums.length) {
//            if (preSums[j + 1] - preSums[i] == k) {
//                res++;
//                i++;j++;
//            } else if (i < j && preSums[j + 1] - preSums[i] > k) {
//                i++;
//            } else if (preSums[j + 1] - preSums[i] < k) {
//                j++;
//            } else {
//                j++;
//            }
//        }

//        HashMap<Integer, Integer> sumMap = new HashMap<>();
//        for (int i = 0; i < nums.length; i++) {
//            sumMap.put(preSums[i] + k, sumMap.getOrDefault(preSums[i] + k, 0) + 1);
//        }
//        for (int i = 0; i < nums.length; i++) {
//            res += sumMap.getOrDefault(preSums[i + 1], 0);
//        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (preSums[j + 1] - preSums[i] == k) {
                    res++;
                }
            }
        }
        return res;
    }

    // 341. 扁平化嵌套列表迭代器 4.10 -> entity/

    public static void main(String[] args) {
        ChapterFour chapterFour = new ChapterFour();
//        List<List<Integer>> res;
//        int[] nums = new int[]{1, 2, 3};
//        res = chapterFour.permute(nums);
//        System.out.println(res);
//
//        int[][] neighbor= new int[][]{{1, 3}, {0, 4, 2}, {1, 5}, {0, 4}, {3, 1, 5}, {4, 2}};
//        for (int adj : neighbor[1]) {
//            System.out.println(adj);
//        }

//        StringBuilder sb = new StringBuilder("abc");
//        sb.deleteCharAt(0);
//        sb.deleteCharAt(0);
//
//        String s = "3 * (4 - 5 / 2) - 6";
//        int a = chapterFour.calculate(s);
//        System.out.println(a);

        int[] nums = {-1,-1,1};
        int res = chapterFour.subarraySum(nums, 0);
        System.out.println(res);
    }

}
