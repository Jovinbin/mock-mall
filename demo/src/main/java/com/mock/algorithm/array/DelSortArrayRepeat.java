package com.mock.algorithm.array;

import java.util.Arrays;

/**
 * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。
 *
 * 由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。更规范地说，如果在删除重复项之后有 k 个元素，那么 nums 的前 k 个元素应该保存最终结果。
 *
 * 将最终结果插入 nums 的前 k 个位置后返回 k 。
 *
 * 不要使用额外的空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 *
 * 删除排序数组中的重复项
 * @author zhao
 * @since 2022-08-18 14:04
 */
public class DelSortArrayRepeat {

    public static void main(String[] args) {
        int[] nums = {-3,1,0,1,0,3,3};
        // 使用流去重更为简单
        nums = Arrays.stream(nums).sorted().distinct().toArray();
        for (int anInt : nums) {
            System.out.println("anInt = " + anInt);
        }
//        int i = removeDuplicates(nums);
//        System.out.println("i = " + i);
//        for (int num : nums) {
//            System.out.println("num = " + num);
//        }
    }

    /**
     * 去重
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
//        HashSet<Integer> set = new HashSet<>();
//        for (int num : nums) {
//            set.add(num);
//        }
//        Object[] objects = set.toArray();
//        Arrays.sort(objects);
//        for (int i = 0; i < objects.length; i++) {
//            nums[i] = (int) objects[i];
//        }

//        Arrays.asList(nums);
//        int[] ints = Arrays.stream(nums).distinct().toArray();
//
//        for (int i = 0; i < ints.length; i++) {
//            nums[i] = ints[i];
//        }
//        return ints.length;

        // 用时短
        Arrays.sort(nums);
        int count = 0;//重复的数字个数
        for (int right = 1; right < nums.length; right++) {
            if (nums[right] == nums[right - 1]) {
                //如果有重复的，count要加1
                count++;
            } else {
                //如果没有重复，后面的就往前挪
                nums[right - count] = nums[right];
            }
        }
        //数组的长度减去重复的个数
        return nums.length - count;

    }

}
