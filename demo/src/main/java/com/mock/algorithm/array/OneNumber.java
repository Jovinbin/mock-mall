package com.mock.algorithm.array;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * 说明：
 *
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 *
 * @author zhao
 * @since 2022-08-19 13:49
 */
public class OneNumber {

    @Data
    @AllArgsConstructor
    static
    class Student{
        private Integer id;
        private String name;
    }

    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "张三"));
        list.add(new Student(2, "里斯"));
        list.add(new Student(3, "王五"));
        list.forEach(e -> {
            if (e.getId() == 1){
                e.setName("dasd");
            }
        });
        System.out.println("list = " + list);


        int[] nums = {4,1,2,1,2};
        int i = singleNumber(nums);
//        System.out.println("i = " + i);
        int a = 5;
        int b = 2;
        int flag = a ^ b;
//        System.out.println("flag = " + flag);
    }

    /**
     * a^a=0；自己和自己异或等于0
     * a^0=a；任何数字和0异或还等于他自己
     * a^b^c=a^c^b；异或运算具有交换律
     */
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];
            System.out.println("result = " + result);
        }
        return result;
    }

}
