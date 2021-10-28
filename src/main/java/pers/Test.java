package pers;

/**
 * @author wtk
 * @date 2021-10-24
 */
public class Test {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int ints = solution.findNthDigit(123);
        System.out.println(ints);
    }
}

class Solution {
    public int findNthDigit(int n) {
        int num1 = n, num2 = n, digit = 1;
        while (num1 > 10) {
            num1 /= 10;
            num2 -= (int)Math.pow(10, digit);
            digit++;
        }
        int theNumber = num2 / digit;
        int index = num2 % digit;
        String s = String.valueOf(theNumber);
        char c = s.charAt(index - 1);
        return c - '0';
    }
}