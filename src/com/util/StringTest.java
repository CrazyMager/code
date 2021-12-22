package com.util;

public class StringTest {
    /**
     *API中String的常用方法
     */

    public static void main(String[] args) {
        // 查找指定字符串是否存在
        String str1 = "abcde:fghijklmnabc";
        // 从头开始查找是否存在指定的字符
        System.out.println(str1.indexOf("c"));
        // 从第四个字符位置开始往后继续查找
        System.out.println(str1.indexOf("c", 3));
        //若指定字符串中没有该字符则系统返回-1
        System.out.println(str1.indexOf("x"));

        // Java截取（提取）子字符串（substring()）
        String subStr = str1.substring(0, str1.indexOf(":"));
        System.out.println(subStr);
        String subStrRight = str1.substring(str1.indexOf(":") + 1);
        System.out.println(subStrRight);

        // Java分割字符串（spilt()）
        String Colors = "Red,Black,White,Yellow,Blue";
        String[] arr1 = Colors.split(","); // 不限制元素个数
        String[] arr2 = Colors.split(",", 3); // 限制元素个数为3
        System.out.println("所有颜色为：");
        for (int i = 0; i < arr1.length; i++) {
            System.out.println(arr1[i]);
        }
        System.out.println("前三个颜色为：");
        for (int j = 0; j < arr2.length; j++) {
            System.out.println(arr2[j]);
        }
    }

}
