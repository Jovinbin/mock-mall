package com.mock.algorithm.array;

/**
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头
 *
 * ！！！逢9近一位
 *
 * @author zhao
 * @since 2022-08-19 14:40
 */
public class AddOne {

    public static void main(String[] args) {
        int[] digits = {1,2,9};
        int[] ints = plusOne(digits);
        for (int anInt : ints) {
            System.out.println("anInt = " + anInt);
        }
    }

    public static int[] plusOne(int[] digits) {
        int n = digits.length;
        int i = digits.length-1;
        while (true){
            if(i>=0 && digits[i]+1<10){
                digits[i] = digits[i]+1;
                break;
            }else{
                if(i>=0){
                    digits[i] = (digits[i]+1)%10;
                }else {
                    int[] newInts = new int[n+1];
                    newInts[0] = 1;
                    for (int j = 1; j < newInts.length; j++) {
                        newInts[j] = digits[j-1];
                    }
                    digits = newInts;
                    break;
                }
            }
            i--;
        }
        return digits;
    }

}
