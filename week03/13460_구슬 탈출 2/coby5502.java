import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static char[][] map;
    static boolean[][][][] visited;
    static int holeX, holeY;
    static BeadState start;

    static final int[] dx = {-1, 0, 1, 0}; // 상우하좌
    static final int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        visited = new boolean[N][M][N][M];

        int rx = 0, ry = 0, bx = 0, by = 0;

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j);

                if (map[i][j] == 'O') {
                    holeX = i;
                    holeY = j;
                } else if (map[i][j] == 'R') {
                    rx = i;
                    ry = j;
                } else if (map[i][j] == 'B') {
                    bx = i;
                    by = j;
                }
            }
        }

        start = new BeadState(rx, ry, bx, by, 0);
        System.out.println(bfs());
    }

    static int bfs() {
        Queue<BeadState> queue = new LinkedList<>();
        queue.offer(start);
        visited[start.rx][start.ry][start.bx][start.by] = true;

        while (!queue.isEmpty()) {
            BeadState cur = queue.poll();

            if (cur.cnt >= 10) return -1;

            for (int dir = 0; dir < 4; dir++) {
                MoveResult red = move(cur.rx, cur.ry, dir);
                MoveResult blue = move(cur.bx, cur.by, dir);

                if (blue.inHole) continue; // 파란 구슬 빠지면 실패
                if (red.inHole) return cur.cnt + 1; // 빨간 구슬만 빠지면 성공

                // 위치 겹침 조정
                if (red.x == blue.x && red.y == blue.y) {
                    if (red.dist > blue.dist) {
                        red.x -= dx[dir];
                        red.y -= dy[dir];
                    } else {
                        blue.x -= dx[dir];
                        blue.y -= dy[dir];
                    }
                }

                if (!visited[red.x][red.y][blue.x][blue.y]) {
                    visited[red.x][red.y][blue.x][blue.y] = true;
                    queue.offer(new BeadState(red.x, red.y, blue.x, blue.y, cur.cnt + 1));
                }
            }
        }

        return -1;
    }

    // 구슬 한 개를 한 방향으로 끝까지 이동
    static MoveResult move(int x, int y, int dir) {
        int dist = 0;
        while (true) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (map[nx][ny] == '#') break;
            x = nx;
            y = ny;
            dist++;

            if (x == holeX && y == holeY) {
                return new MoveResult(x, y, dist, true);
            }
        }
        return new MoveResult(x, y, dist, false);
    }

    // 구슬 이동 결과
    static class MoveResult {
        int x, y, dist;
        boolean inHole;

        MoveResult(int x, int y, int dist, boolean inHole) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.inHole = inHole;
        }
    }

    // 빨간, 파란 구슬 상태
    static class BeadState {
        int rx, ry, bx, by, cnt;

        BeadState(int rx, int ry, int bx, int by, int cnt) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.cnt = cnt;
        }
    }
}