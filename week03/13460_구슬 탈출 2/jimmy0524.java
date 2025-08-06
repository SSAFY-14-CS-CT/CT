import java.util.*;
import java.io.*;

public class Main {
    static boolean[][][][] visited;
    static char[][] arr;
    static int N;
    static int M;
    static int[] dx = {0,0,1,-1};
    static int[] dy = {1,-1,0,0};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new char[N][M];

        Node red = null;
        Node blue = null;

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = s.charAt(j);
                if (arr[i][j] == 'R') {
                    red = new Node(i, j);
                } else if (arr[i][j] == 'B') {
                    blue = new Node(i, j);
                }
            }
        }

        System.out.println(bfs(red, blue));

    }

    public static int bfs(Node R, Node B) {
        Queue<State> queue = new LinkedList<>();
        visited = new boolean[N][M][N][M];
        queue.add(new State(R, B, 0));
        visited[R.x][R.y][B.x][B.y] = true;

        while (!queue.isEmpty()) {
            State now = queue.poll();

            if (now.depth >= 10) {
                return -1;
            }

            for (int i = 0; i < 4; i++) {
                MoveResult redRes = move(now.red, i);
                MoveResult blueRes = move(now.blue, i);

                if (blueRes.inHole) {
                    continue;
                }

                if (!blueRes.inHole && redRes.inHole) {
                    return now.depth + 1;
                }

                if (redRes.x == blueRes.x && redRes.y == blueRes.y) {
                    if (redRes.dist > blueRes.dist) {
                        redRes.x -= dx[i];
                        redRes.y -= dy[i];
                    } else {
                        blueRes.x -= dx[i];
                        blueRes.y -= dy[i];
                    }
                }

                if (!visited[redRes.x][redRes.y][blueRes.x][blueRes.y]) {
                    visited[redRes.x][redRes.y][blueRes.x][blueRes.y] = true;
                    queue.offer(new State(
                            new Node(redRes.x, redRes.y),
                            new Node(blueRes.x, blueRes.y),
                            now.depth + 1
                    ));
                }
            }
        }
        return -1;
    }

    public static MoveResult move(Node n, int dir) {
        int x = n.x;
        int y = n.y;
        int dist = 0;
        boolean inHole = false;

        while(true) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (arr[nx][ny] == '#') {
                break;
            }

            x = nx;
            y = ny;
            dist++;

            if (arr[nx][ny] == 'O') {
                inHole = true;
                break;
            }
        }

        return new MoveResult(x, y, dist, inHole);
    }


    public static class Node {
        int x, y;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class State {
        Node red, blue;
        int depth;
        public State(Node red, Node blue, int depth) {
            this.red = red;
            this.blue = blue;
            this.depth = depth;
        }
    }

    public static class MoveResult {
        int x, y, dist;
        boolean inHole;
        public MoveResult(int x, int y, int dist, boolean inHole) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.inHole = inHole;
        }
    }
}
