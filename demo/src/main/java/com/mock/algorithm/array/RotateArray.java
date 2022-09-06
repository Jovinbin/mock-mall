package com.mock.algorithm.array;

import java.util.Arrays;

/**
 * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 *
 * @author zhao
 * @since 2022-08-19 10:56
 */
public class RotateArray {

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        int k = 4;
        rotate(nums, k);
    }

    public static void rotate(int[] nums, int k) {
//        int[] copyNums = Arrays.stream(nums).toArray();
        k = k % nums.length;
//        if (k == 0){
//            return;
//        }
//        for (int num = 0; num < nums.length; num++) {
//            if (num + k < nums.length){
//                nums[num + k] = copyNums[num];
//            }else {
//                nums[num + k - nums.length] = copyNums[num];
//            }
//        }
        // 优化
        int[] rightPart = Arrays.copyOfRange(nums, nums.length - k, nums.length);
        System.arraycopy(nums, 0, nums, k, nums.length - k);
        System.arraycopy(rightPart, 0, nums, 0, k);
    }

}
