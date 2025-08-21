import java.util.*;
import java.io.*;

public class Main {
    static final int N = 4;
    // 상어 0 빈칸 -1
    static int[][] arr;
    static Fish[] fishes;
    static int answer;
    static final int[] dx = { 0, -1, -1, 0, 1, 1, 1, 0, -1 }; // 1부터시작
    static final int[] dy = { 0, 0, -1, -1, -1, 0, 1, 1, 1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        arr = new int[4][4];
        fishes = new Fish[17];
        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                arr[i][j] = a;
                fishes[a] = new Fish(a, i, j, b, true);
            }
        }

        int firstFish = arr[0][0];
        int sharkDir = fishes[firstFish].dir;
        int sum = firstFish;
        fishes[firstFish].isAlive = false;
        arr[0][0] = -1;
        dfs(arr, fishes, 0, 0, sharkDir, sum);
        System.out.println(answer);
    }

    public static void dfs(int[][] arr, Fish[] fishes, int sx, int sy, int sdir, int sum) {
        answer = Math.max(answer, sum);

        fishMove(arr, fishes, sx, sy);
        sharkMove(arr, fishes, sx, sy, sdir, sum);
    }

    static void sharkMove(int[][] board, Fish[] fishes, int sx, int sy, int sdir, int sum) {
        for (int step = 1; step <= 3; step++) {
            int nx = sx + dx[sdir] * step;
            int ny = sy + dy[sdir] * step;

            if (0 > nx || 0 > ny || nx >= N || ny >= N)
                break;
            if (board[nx][ny] <= 0)
                continue;

            int[][] nb = copyBoard(board);
            Fish[] nf = copyFishes(fishes);

            int eatNum = nb[nx][ny];
            int ndir = nf[eatNum].dir;
            nf[eatNum].isAlive = false;

            nb[sx][sy] = 0;
            nb[nx][ny] = -1;

            dfs(nb, nf, nx, ny, ndir, sum + eatNum);
        }
    }

    public static void fishMove(int[][] arr, Fish[] fishes, int sx, int sy) {
        for (int num = 1; num <= 16; num++) {
            Fish f = fishes[num];
            if (!f.isAlive)
                continue;

            for (int rot = 0; rot < 8; rot++) {
                int ndir = rotate(f.dir, rot);
                int nx = f.x + dx[ndir];
                int ny = f.y + dy[ndir];

                if (0 > nx || 0 > ny || nx >= N || ny >= N || (nx == sx && ny == sy)) {
                    continue;
                }

                if (arr[nx][ny] == 0) {
                    arr[f.x][f.y] = 0;
                    arr[nx][ny] = f.num;
                    f.x = nx;
                    f.y = ny;
                    f.dir = ndir;
                } else {
                    // 다른 물고기와 swap
                    int otherNum = arr[nx][ny];
                    Fish other = fishes[otherNum];

                    arr[f.x][f.y] = otherNum;
                    arr[nx][ny] = f.num;

                    other.x = f.x;
                    other.y = f.y;

                    f.x = nx;
                    f.y = ny;
                    f.dir = ndir;
                }
                break;
            }
        }
    }

    static int rotate(int dir, int rot) {
        return ((dir - 1 + rot) % 8) + 1;
    }

    static int[][] copyBoard(int[][] b) {
        int[][] nb = new int[N][N];
        for (int i = 0; i < N; i++)
            nb[i] = b[i].clone();
        return nb;
    }

    static Fish[] copyFishes(Fish[] fs) {
        Fish[] nf = new Fish[17];
        for (int i = 1; i <= 16; i++)
            if (fs[i] != null)
                nf[i] = new Fish(fs[i]);
        return nf;
    }

    static class Fish {
        int num;
        int x, y;
        int dir;
        boolean isAlive = true;

        Fish(int num, int x, int y, int dir, boolean isAlive) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.isAlive = isAlive;
        }

        // 복사를 위한 생성자
        Fish(Fish other) {
            this.num = other.num;
            this.x = other.x;
            this.y = other.y;
            this.dir = other.dir;
            this.isAlive = other.isAlive;
        }
    }
}