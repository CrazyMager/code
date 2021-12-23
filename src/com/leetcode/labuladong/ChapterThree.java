package com.leetcode.labuladong;

import com.leetcode.labuladong.entity.TreeNode;
import sun.awt.image.ImageWatched;
import sun.reflect.generics.tree.Tree;

import java.util.*;

public class ChapterThree {
    // 98. 验证二叉搜索树 3.3.1 判断BST的合法性
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }
    public boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }
        if (min != null && root.val <= min.val) {
            return false;
        }
        if (max != null && root.val >= max.val) {
            return false;
        }
        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }
    TreeNode preNode;
    public boolean isValidBST2(TreeNode root) { // 递归中序遍历方法
        if (root == null) {
            return true;
        }
        if (!isValidBST(root.left)) {
            return false;
        }
        if (preNode != null && preNode.val >= root.val) {
            return false;
        }
        preNode = root;
        if (!isValidBST(root.right)) {
            return false;
        }
        return true;
    }

    // 700. 二叉搜索树中的搜索 3.3.2
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        } else if (root.val < val) {
            return searchBST(root.right, val);
        } else {
            return searchBST(root.left, val);
        }
    }

    // 701. 二叉搜索树中的插入操作 3.3.3 在BST中插入一个数
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (root.val < val) {
            root.right = insertIntoBST(root.right, val);
        }
        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }
    public TreeNode insertIntoBST2(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode pos = root;
        while (pos != null) {
            if (val < pos.val) {
                if (pos.left == null) {
                    pos.left = new TreeNode(val);
                    break;
                } else {
                    pos = pos.left;
                }
            } else {
                if (pos.right == null) {
                    pos.right = new TreeNode(val);
                    break;
                } else {
                    pos = pos.right;
                }
            }
        }
        return root;
    }

    // 450. 删除二叉搜索树中的节点 3.3.4
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            if (root.left == null && root.right == null) {
                return null;
            }
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            TreeNode changeNode = findLeftestNode(root.right);
            root.val = changeNode.val;
            root.right = deleteNode(root.right, changeNode.val);
            return root;
        }
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        }
        return root;
    }
    public TreeNode findLeftestNode(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // 222. 完全二叉树的节点个数 3.4
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
    public int countNodes2(TreeNode root) {
        TreeNode l = root, r = root;
        // 记录左右子树的高度
        int hl = 0, hr = 0;
        while (l != null) {
            l = l.left;
            hl++;
        }
        while (r != null) {
            r = r.right;
            hr++;
        }
        // 如果左右子树的高度相同，说明是一颗满二叉树
        if (hl == hr) {
            return (int)Math.pow(2, hl) - 1;
        }
        // 如果左右高度不同，则按普通二叉树的逻辑计算
        return 1 + countNodes2(root.left) + countNodes2(root.right);
    }

    // 297. 二叉树的序列化与反序列化 3.5
    String SEP = ",";
    String NULL = "#";
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }
    void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }
        sb.append(root.val).append(SEP);
        serialize(root.left, sb);
        serialize(root.right, sb);
    }
    public TreeNode deserialize(String data) {
        // 将字符串转化为列表
        LinkedList<String> nodes = new LinkedList<>();
        for (String s : data.split(SEP)) {
            nodes.addLast(s);
        }
        return deserialize(nodes);
    }
    public TreeNode deserialize(LinkedList<String> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        // 列表的最左侧就是根节点
        String first = nodes.removeFirst();
        if (first.equals(NULL)) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(first));
        root.left = deserialize(nodes);
        root.right = deserialize(nodes);
        return root;
    }

    // 3.5.5 层序遍历序列化二叉树
    public String serializeLevelTraverse(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        // 初始化队列，将root加入队列
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur == null) {
                sb.append(NULL).append(SEP);
                continue;
            }
            sb.append(cur.val).append(SEP);
            q.offer(cur.left);
            q.offer(cur.right);
        }
        return sb.toString();
    }

    // 236. 二叉树的最近公共祖先 3.6
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // base case
        if (root == null) {
            return null;
        }
        if (root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;
    }

    // 496. 下一个更大元素 I 3.7.1
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Deque<Integer> stack = new ArrayDeque<>();
        // 存放nums[2]中下一个更大的数
        HashMap<Integer, Integer> ansMap = new HashMap<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums2[i]) {
                stack.pop();
            }
            ansMap.put(nums2[i], stack.isEmpty() ? -1 : stack.peek());
            stack.push(nums2[i]);
        }

        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = ansMap.get(nums1[i]);
        }
        return res;
    }

    // 739. 每日温度 3.7.2
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] ans = new int[temperatures.length]; // 这里放元素索引，而不是元素
        for (int i = temperatures.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[i]) {
                stack.pop();
            }
            ans[i] = stack.isEmpty() ? 0 : stack.peek() - i; // 得到索引间距
            stack.push(i); // 加入索引，而不是元素
        }
        return ans;
    }

    // 503. 下一个更大元素 II 3.7.3
    public int[] nextGreaterElements(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] ans = new int[nums.length];
        // 假装nums这个数组翻倍了
        for (int i = 2 * nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i % nums.length]) {
                stack.pop();
            }
            ans[i % nums.length] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i % nums.length]);
        }
        return ans;
    }

}
