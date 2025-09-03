import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static int[] num;
    static int[] calNum;
    static int max = Integer.MIN_VALUE;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        num = new int[N];
        calNum = new int[4];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            calNum[i] = Integer.parseInt(st.nextToken());
        }
        dfs(0, num[0]);

        System.out.println(max);
        System.out.println(min);
    }

    public static void dfs(int index, int sum) {
        if (index >= N - 1) {
            max = Math.max(max, sum);
            min = Math.min(min, sum);
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (calNum[i] > 0) {
                calNum[i]--;
                int tempSum = calculate(i, sum, index);
                dfs(index + 1, tempSum);
                calNum[i]++;
            }
        }
    }

    public static int calculate(int cal, int sum, int index) {
        switch(cal) {
            case 0 :
                return sum + num[index + 1];
            case 1 :
                return sum - num[index + 1];
            case 2 :
                return sum * num[index + 1];
            case 3 :
                return sum / num[index + 1];
        }
        return 0;
    }
}