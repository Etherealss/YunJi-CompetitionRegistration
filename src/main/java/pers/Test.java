package pers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wtk
 * @date 2021-10-24
 */
public class Test {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.findWords(new String[]{"omk","iuqyeqyeioqueq","qkdqoijqld","qajlkdanc","zxcnzcn"});
    }
}

class Solution {
    public String[] findWords(String[] words) {
        Map<Character, Integer> map = new HashMap<>();
        String[] ss = {"qwertyuiop", "asdfghjkl", "zxcvbnm"};
        for (int i = 0; i < ss.length; i++) {
            char[] cs = ss[i].toCharArray();
            for (char c : cs) {
                map.put(c, i+1);
            }
        }
        List<String> res = new ArrayList<>();
        for(String s : words) {
            int len = s.length();
            char[] cs = s.toLowerCase().toCharArray();
            int flag = 0;
            for (char c : cs) {
                flag += map.get(c);
            }
            System.out.println(flag);
            if (flag == len || flag == len * 2 || flag == len * 3) {
                res.add(s);
            }
        }
        return res.toArray(new String[0]);
    }
}