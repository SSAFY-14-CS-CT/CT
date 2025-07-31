import java.util.*;
import java.io.*;

public class Main {
    static int answer = Integer.MIN_VALUE;
    static ArrayList<Integer> num;
    static ArrayList<Character> cha;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String s = br.readLine();
        num = new ArrayList<>();
        cha = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (i % 2 != 0) {
                cha.add(s.charAt(i));
            } else {
                num.add(s.charAt(i) - '0');
            }
        }
        dfs(num.get(0), 0);

        System.out.println(answer);
    }

    private static void dfs(int value, int index) {
        if (index == cha.size()) {
            answer = Math.max(answer, value);
            return;
        }

        int temp = cal(cha.get(index), value, num.get(index + 1));
        dfs(temp, index + 1);

        if (index <= cha.size() - 2) {
            int temp2 = cal(cha.get(index + 1), num.get(index + 1), num.get(index + 2));
            temp = cal(cha.get(index), value, temp2);
            dfs(temp, index + 2);
        }


    }

    private static int cal(char c, int a, int b) {
        if (c == '*') {
            return a * b;
        } else if (c == '+') {
            return a + b;
        }
        return a - b;
    }
}
