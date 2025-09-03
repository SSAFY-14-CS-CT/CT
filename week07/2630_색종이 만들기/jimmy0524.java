import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] arr;
    static int blueCount;
    static int whiteCount;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        devide(0, 0, N);
        System.out.println(whiteCount);
        System.out.println(blueCount);
    }

    public static void devide(int x, int y, int size) {
        int temp = arr[x][y];
        boolean check = true;
        here: for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (arr[i][j] != temp) {
                    check = false;
                    break here;
                }
            }
        }
        if (!check) {
            devide(x, y, size / 2);
            devide(x + size / 2, y, size / 2);
            devide(x, y + size / 2, size / 2);
            devide(x + size / 2, y + size / 2, size / 2);
        } else {
            if (temp == 1) {
                blueCount++;
            } else {
                whiteCount++;
            }
        }
    }
}
