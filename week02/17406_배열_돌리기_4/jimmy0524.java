import java.util.*;
import java.io.*;

public class Main {
    static int[][] arr;
    static int N;
    static int M;
    static int K;
    static boolean[][] visited;
    static boolean[] kVisited;
    static rcs[] rcsList;
    static int answer = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken()); //회전 연산 개수
        rcsList = new rcs[K];
        arr = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            rcsList[i] = new rcs(r,c,s);
        }
        kVisited = new boolean[K];
        choiceRotate(0);

        System.out.println(answer);
    }

    public static void choiceRotate(int count) {
        if (count == K) {
            checkSum();
            return;
        }

        for (int i = 0; i < K; i++) {
            if (!kVisited[i]) {
                int[][] backup = new int[N + 1][M + 1];
                for (int j = 1; j <= N; j++) {
                    backup[j] = arr[j].clone();
                }
                rcs rcs = rcsList[i];
                int r = rcs.r;
                int s = rcs.s;
                int c = rcs.c;
                visited = new boolean[N + 1][M + 1];
                Node leftNode = new Node(r - s, c - s);
                Node rightNode = new Node(r + s, c + s);
                rotation(leftNode, rightNode);
                kVisited[i] = true;
                choiceRotate(count + 1);
                kVisited[i] = false;
                for (int j = 1; j <= N; j++) {
                    arr[j] = backup[j].clone();
                }
            }
        }
    }

    public static void rotation(Node left, Node right) {
        if (left.x == right.x && left.y == right.y) {
            return;
        }
        if (visited[left.x][left.y] || visited[right.x][right.y]) {
            return;
        }
        visited[left.x][left.y] = true;
        visited[right.x][right.y] = true;

        int temp = arr[left.x][left.y];
        for (int i = left.x; i <= right.x - 1; i++) {
            arr[i][left.y] = arr[i + 1][left.y];
        }

        for (int i = left.y; i <= right.y - 1; i++) {
            arr[right.x][i] = arr[right.x][i + 1];
        }

        for (int i = right.x; i >= left.x + 1; i--) {
            arr[i][right.y] = arr[i - 1][right.y];
        }

        for (int i = right.y; i >= left.y + 1; i--) {
            arr[left.x][i] = arr[left.x][i - 1];
        }

        arr[left.x][left.y + 1] = temp;

        rotation(new Node(left.x + 1, left.y + 1), new Node(right.x - 1, right.y - 1));
    }

    public static void checkSum() {
        for (int i = 1; i <= N; i++) {
            int sum = 0;
            for (int j = 1; j <= M; j++) {
                sum += arr[i][j];
            }
            answer = Math.min(answer, sum);
        }
    }

    public static class Node {
        int x,y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class rcs {
        int r,c,s;

        public rcs(int r, int c, int s) {
            this.r = r;
            this.c = c;
            this.s = s;
        }
    }
}
