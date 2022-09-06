package com.mock.algorithm.array;

/**
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 *
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
 *
 * 返回 你能获得的 最大 利润 。
 *
 *
 * @author zhao
 * @since 2022-08-19 10:37
 */
public class SellShares {

    public static void main(String[] args) {
        int[] prices = {1,7,4,5,1};
        int sumPrice = maxProfit(prices);
        System.out.println("sumPrice = " + sumPrice);
    }

    public static int maxProfit(int[] prices) {
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]){
                sum += prices[i] - prices[i - 1];
            }
        }
        return sum;
    }

}
