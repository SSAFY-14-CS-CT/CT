import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int[][] map;
    static int[] groupA;
    static int[] groupB;
    static int aIdx = 0, bIdx = 0;
    static int min = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N + 1][N + 1];
        groupA = new int[N / 2 + 1];
        groupB = new int[N / 2 + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        groupA[++aIdx] = 1;
        comb(2);
        System.out.println(min);
    }

    public static void comb(int start) {
        if (aIdx == N / 2) {
            bIdx = 0;
            for (int i = 1; i <= N; i++) {
                if (!contain(groupA, i)) {
                    groupB[++bIdx] = i;
                }
            }

            // System.out.println("-------------------------------");
            // printGroup(groupA);
            // printGroup(groupB);

            int aSum = calculate(groupA);
            int bSum = calculate(groupB);
            min = Math.min(Math.abs(aSum - bSum), min);
            return;
        }

        for (int i = start; i <= N; i++) {
            groupA[++aIdx] = i;
            comb(i + 1);
            aIdx--;
        }
    }

    public static int calculate(int[] group) {
        int sum = 0;

        for (int me = 1; me <= N / 2; me++) {
            for (int mate = 1; mate <= N / 2; mate++) {
                sum += map[group[me]][group[mate]];
            }
        }
        return sum;
    }

    public static boolean contain(int[] group, int value) {
        for (int i = 1; i <= N / 2; i++) {
            if (group[i] == value) return true;
        }
        return false;
    }

    public static void printGroup(int[] group) {
        for (int me = 1; me <= N / 2; me++) {
            System.out.print(group[me] + " ");
        }
        System.out.println("합: " + calculate(group));
    }
}