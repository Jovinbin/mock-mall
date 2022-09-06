package com.mock.algorithm.array;

/**
 *
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 *
 * @author zhao
 * @since 2022-08-19 15:22
 */
public class MoveZero {

    public static void main(String[] args) {
        int[] nums = {1,2,0,12,0};
        moveZeroes(nums);
        for (int num : nums) {
            System.out.println("num = " + num);
        }
    }

    public static void moveZeroes(int[] nums) {
//        int i = 0;//统计前面0的个数
//        for (int j = 0; j < nums.length; j++) {
//            if (nums[j] == 0) {//如果当前数字是0就不操作
//                i++;
//            } else if (i != 0) {
//                //否则，把当前数字放到最前面那个0的位置，然后再把
//                //当前位置设为0
//                nums[j - i] = nums[j];
//                nums[j] = 0;
//            }
//        }

        // 方法二：
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            //只要不为0就往前挪
            if (nums[j] != 0) {
                //i指向的值和j指向的值交换
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;

                i++;
            }
        }
    }

}
