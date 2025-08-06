import java.util.*;
import java.io.*;

public class Main {
    static int[][] arr;
    static int answer = 0;
    static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        dfs(0, arr);
        System.out.println(answer);

    }

    public static void dfs(int count, int[][] ar) {
        if (count == 5) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    answer = Math.max(answer, ar[i][j]);
                }
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            int[][] temp = move(i, ar);
//			for (int k = 0; k < N; k++) {
//				for (int j = 0; j < N; j++) {
//					System.out.print(temp[k][j]);
//				}
//				System.out.println();
//			}
            dfs(count + 1, temp);
        }
    }

    public static int[][] move(int dir, int[][] now) {
        boolean isMove = true;
        int[][] temp = new int[N][N];
        boolean[][] visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            temp[i] = now[i].clone();
        }

        while (isMove) {
            isMove = false;
            switch (dir) {
                case 0: { // 우
//					System.out.println("우");
                    for (int i = 0; i < N; i++) {
                        for (int j = N - 1; j >= 1; j--) {
                            if (!visited[i][j] && !visited[i][j - 1] && temp[i][j] != 0 && temp[i][j - 1] != 0) {
                                if (temp[i][j] == temp[i][j - 1]) {
                                    temp[i][j] += temp[i][j - 1];
                                    temp[i][j - 1] = 0;
                                    visited[i][j] = true;
                                    isMove = true;
                                    continue;
                                }
                            } else if (temp[i][j] == 0 && temp[i][j - 1] != 0) {
                                temp[i][j] = temp[i][j - 1];
                                temp[i][j - 1] = 0;
                                visited[i][j] = visited[i][j - 1];
                                visited[i][j - 1] = false;
                                isMove = true;
                                continue;
                            }

                        }
                    }
                }
                break;
                case 1: { // 좌
//					System.out.println("좌");
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N - 1; j++) {
                            if (!visited[i][j] && !visited[i][j + 1] && temp[i][j] != 0 && temp[i][j + 1] != 0) {
                                if (temp[i][j] == temp[i][j + 1]) {
                                    temp[i][j] += temp[i][j + 1];
                                    temp[i][j + 1] = 0;
                                    visited[i][j] = true;
                                    isMove = true;
                                    continue;
                                }
                            } else if (temp[i][j] == 0 && temp[i][j + 1] != 0) {
                                temp[i][j] = temp[i][j + 1];
                                temp[i][j + 1] = 0;
                                visited[i][j] = visited[i][j + 1];
                                visited[i][j + 1] = false;
                                isMove = true;
                                continue;
                            }
                        }
                    }
                }
                break;
                case 2: { // 상
//					System.out.println("상");
                    for (int i = 0; i < N - 1; i++) {
                        for (int j = 0; j < N; j++) {
                            if (!visited[i][j] && !visited[i + 1][j] && temp[i][j] != 0 && temp[i + 1][j] != 0) {
                                if (temp[i][j] == temp[i + 1][j]) {
                                    temp[i][j] += temp[i + 1][j];
                                    temp[i + 1][j] = 0;
                                    visited[i][j] = true;
                                    isMove = true;
                                    continue;
                                }
                            } else if (temp[i][j] == 0 && temp[i + 1][j] != 0) {
                                temp[i][j] = temp[i + 1][j];
                                temp[i + 1][j] = 0;
                                visited[i][j] = visited[i + 1][j];
                                visited[i + 1][j] = false;
                                isMove = true;
                                continue;
                            }
                        }
                    }
                }
                break;
                case 3: { // 하
//					System.out.println("하");
                    for (int i = N - 1; i >= 1; i--) {
                        for (int j = 0; j < N; j++) {
                            if (!visited[i][j] && !visited[i - 1][j] && temp[i][j] != 0 && temp[i - 1][j] != 0) {
                                if (temp[i][j] == temp[i - 1][j]) {
                                    temp[i][j] += temp[i - 1][j];
                                    temp[i - 1][j] = 0;
                                    visited[i][j] = true;
                                    isMove = true;
                                    continue;
                                }
                            } else if (temp[i][j] == 0 && temp[i - 1][j] != 0) {
                                temp[i][j] = temp[i - 1][j];
                                temp[i - 1][j] = 0;
                                visited[i][j] = visited[i - 1][j];
                                visited[i - 1][j] = false;
                                isMove = true;
                                continue;
                            }

                        }
                    }
                }
            }
        }

        return temp;
    }
}
