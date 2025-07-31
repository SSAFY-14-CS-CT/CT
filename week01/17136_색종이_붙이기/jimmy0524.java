import java.util.*;
import java.io.*;

public class Main {
    static int answer = Integer.MAX_VALUE;
    static int[][] arr;
    static int[] num = {0, 5, 5, 5, 5, 5};

    public static void main(String[] args) throws Exception {
        arr = new int[10][10];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 10; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        dfs(0,0,0);

        if (answer == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }
    }

    public static void dfs(int x, int y, int count) {
        if (x == 9 && y == 10) {
            answer = Math.min(answer, count);
            return;
        }
        if (y == 10) {
            dfs(x + 1, 0, count);
            return;
        }
        if (arr[x][y] == 1) {
            for (int i = 5; i >= 0; i--) {
                if(num[i] > 0 && isPossible(x, y ,i)) {
                    attachOrDetach(x, y, i, 0);
                    num[i]--;
                    dfs(x, y + 1, count + 1);
                    attachOrDetach(x, y, i, 1);
                    num[i]++;
                }
            }
        } else {
            dfs(x, y + 1, count);
        }
    }

    public static boolean isPossible(int x, int y, int size) {
        for (int i = x; i < x + size ; i++) {
            for (int j = y; j < y + size; j++) {
                if (i < 0 || i >= 10 || j < 0 || j >= 10) {
                    return false;
                }
                if (arr[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void attachOrDetach(int x, int y, int size, int state) {
        if (state == 0) {//붙이기
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    arr[i][j] = 0;
                }
            }
        }

        if (state == 1) {//떼기
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    arr[i][j] = 1;
                }
            }
        }
    }
}
