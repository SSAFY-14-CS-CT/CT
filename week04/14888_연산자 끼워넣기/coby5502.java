import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int[] nums;
    static int[] cal = new int[4];
    static int[] calNum = new int[4];
    static int[] calPerm;
    static int max = -1000000000;
    static int min = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        nums = new int[N];
        calPerm = new int[N - 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            cal[i] = Integer.parseInt(st.nextToken());
        }

        perm(0);

        System.out.println(max);
        System.out.println(min);
    }

    public static void perm(int depth) {
        if (depth == N - 1) {
            int ans = calculate();
            max = Math.max(ans, max);
            min = Math.min(ans, min);
        }

        for (int i = 0; i < 4; i++) {
            if (calNum[i] < cal[i]) {
                calNum[i]++;
                calPerm[depth] = i;
                perm(depth + 1);
                calNum[i]--;
            }
        }
    }

    public static int calculate() {
        int ans = nums[0];

        for (int i = 0; i < N - 1; i++) {
            switch (calPerm[i]) {
                case 0:
                    ans = ans + nums[i + 1];
                    break;
                case 1:
                    ans = ans - nums[i + 1];
                    break;
                case 2:
                    ans = ans * nums[i + 1];
                    break;
                case 3:
                    ans = ans / nums[i + 1];
                    break;
            }
        }

        return ans;
    }
}