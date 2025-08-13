import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static int[][] arr;
    static boolean[] visited; //값 기준
    static boolean[] calVisited; //인덱스 기준
    static int sum;
    static int answer = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N + 1][N + 1];
        visited = new boolean[N + 1];
        calVisited = new boolean[N / 2];
        StringTokenizer st;
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0);
        System.out.println(answer);
    }

    public static void dfs(int count, int index) {
        if (count == N / 2) {
            calculate();
            return;
        }
        for (int i = index + 1; i <= N; i++) {
            visited[i] = true;
            dfs(count + 1, i);
            visited[i] = false;
        }
    }

    public static void calculate() {
        int startSum = 0;
        int linkSum = 0;
        for (int i = 1; i < N; i++) {
            for (int j = i + 1; j <= N; j++) {
                if (visited[i] && visited[j]) {
                    startSum += arr[i][j] + arr[j][i];
                } else if (!visited[i] && !visited[j]) {
                    linkSum += arr[i][j] + arr[j][i];
                }
            }
        }
        answer = Math.min(answer, Math.abs(startSum - linkSum));
    }
}