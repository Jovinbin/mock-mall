package com.mock.algorithm.array;

import java.util.HashSet;
import java.util.Set;

/**
 * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
 *
 * @author zhao
 * @since 2022-08-19 11:42
 */
public class RepeatingElements {

    public static void main(String[] args) {
        int[] nums = {1,1,1,3,3,4,3,2,4,2};
        boolean b = containsDuplicate(nums);
        System.out.println("b = " + b);
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

}
