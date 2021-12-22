package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysAndStrings {
    public List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        int sum;
        List<List<Integer>> resList = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < len-2; i++) {
            if (i != 0 && nums[i] == nums[i-1]) {
                continue;
            }
            int left = i + 1;
            int right = len - 1;
            while (left < right) {
                sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    List<Integer> subList = new ArrayList<>();
                    subList.add(nums[i]);
                    subList.add(nums[left]);
                    subList.add(nums[right]);
                    resList.add(subList);
                    while (right > left && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (right > left && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return resList;
    }

    public static void main(String[] args) {
        ArraysAndStrings arraysAndStrings = new ArraysAndStrings();
        int[] nums = new int[]{-1,0,1,2,-1,-4};
        List<List<Integer>> res = arraysAndStrings.threeSum(nums);
        System.out.println(res);
    }
}
