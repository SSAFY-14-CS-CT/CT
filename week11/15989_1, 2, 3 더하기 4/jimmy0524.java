import java.io.BufferedReader;
import java.io.InputStreamReader;

public class boj_15989 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int[][] arr = new int[10001][4];
        arr[1][1] = 1;
        arr[2][1] = 1;
        arr[2][2] = 1;
        arr[3][1] = 1;
        arr[3][2] = 1;
        arr[3][3] = 1;
        for (int i = 4; i <= 10000; i++) {
            arr[i][1] = 1;
            arr[i][2] = arr[i - 2][1] + arr[i - 2][2];
            arr[i][3] = arr[i - 3][1] + arr[i - 3][2] + arr[i - 3][3];
        }
        for (int t = 0; t < T; t++) {
            int n = Integer.parseInt(br.readLine());
            System.out.println(arr[n][1] + arr[n][2] + arr[n][3]);
        }
    }
}