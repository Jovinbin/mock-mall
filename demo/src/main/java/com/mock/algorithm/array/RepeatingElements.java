package com.mock.algorithm.array;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
 *
 * @author zhao
 * @since 2022-08-19 11:42
 */
public class RepeatingElements {

    public static void main(String[] args) {
//        int[] nums = {1,3,6,7,4,6,4,3,0,1,1,1,1,1,1,1,1,1,12,1,51,5,8};
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
        long time1 = System.currentTimeMillis();
        boolean b = containsDuplicate(list);
//        list.stream().distinct().collect(Collectors.toList());
        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);
//        System.out.println("b = " + b);
    }

    public static boolean containsDuplicate(int[] nums) {
//        int[] newNums = Arrays.stream(nums).distinct().toArray();
//        if (nums.length == newNums.length){
//            return false;
//        }else {
//            return true;
//        }

        // 使用set集合去重
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        return set.size() != nums.length;
    }

    public static boolean containsDuplicate(List<Integer> nums) {
        // 使用set集合去重
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        return set.size() != nums.size();
    }

}
