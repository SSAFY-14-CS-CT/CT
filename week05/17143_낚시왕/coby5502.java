import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int R, C, M;
    static ArrayList<Shark> sharks = new ArrayList<>();
    static int ans;

    static int[] dr = {-1, 1, 0, 0}; // 0: 위, 1: 아래
    static int[] dc = {0, 0, 1, -1}; // 2: 오른쪽, 3: 왼쪽

    static class Shark implements Comparable<Shark> {
        int r, c, s, d, z;

        Shark(int r, int c, int s, int d, int z) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.z = z;
        }

        @Override
        public int compareTo(Shark other) {
            if (this.r != other.r) {
                return Integer.compare(this.r, other.r);
            }
            if (this.c != other.c) {
                return Integer.compare(this.c, other.c);
            }
            return Integer.compare(other.z, this.z); // z 내림차순
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken()) - 1;
            int z = Integer.parseInt(st.nextToken());
            sharks.add(new Shark(r, c, s, d, z));
        }

        fishing();

        System.out.println(ans);
    }

    static void fishing() {
        for (int col = 0; col < C; col++) {
            // 1. 낚시왕이 상어를 잡는다.
            int minRow = Integer.MAX_VALUE;
            Shark caughtShark = null;

            for (Shark shark : sharks) {
                if (shark.c == col && shark.r < minRow) {
                    minRow = shark.r;
                    caughtShark = shark;
                }
            }

            if (caughtShark != null) {
                ans += caughtShark.z;
                sharks.remove(caughtShark);
            }

            // 2. 상어들이 이동한다.
            sharkMoving();

            // 3. 겹치는 상어를 제거한다.
            solveCollisions();
        }
    }

    static void sharkMoving() {
        ArrayList<Shark> movedSharks = new ArrayList<>();
        
        for (Shark shark : sharks) {
            int r = shark.r;
            int c = shark.c;
            int s = shark.s;
            int d = shark.d;

            // 상하좌우 이동에 따라 왕복 거리가 다름
            if (d == 0 || d == 1) { // 상하 이동
                s %= (R - 1) * 2;
            } else { // 좌우 이동
                s %= (C - 1) * 2;
            }

            for (int i = 0; i < s; i++) {
                r += dr[d];
                c += dc[d];

                if (d == 0 && r < 0) { // 위쪽 벽에 부딪힘
                    d = 1;
                    r = 1;
                } else if (d == 1 && r >= R) { // 아래쪽 벽에 부딪힘
                    d = 0;
                    r = R - 2;
                } else if (d == 2 && c >= C) { // 오른쪽 벽에 부딪힘
                    d = 3;
                    c = C - 2;
                } else if (d == 3 && c < 0) { // 왼쪽 벽에 부딪힘
                    d = 2;
                    c = 1;
                }
            }
            shark.r = r;
            shark.c = c;
            shark.d = d;
            movedSharks.add(shark);
        }
        sharks = movedSharks;
    }

    static void solveCollisions() {
        Collections.sort(sharks);
        ArrayList<Shark> survivedSharks = new ArrayList<>();

        if (sharks.isEmpty()) {
            return;
        }

        survivedSharks.add(sharks.get(0));
        for (int i = 1; i < sharks.size(); i++) {
            Shark currentShark = sharks.get(i);
            Shark lastSurvived = survivedSharks.get(survivedSharks.size() - 1);

            if (currentShark.r != lastSurvived.r || currentShark.c != lastSurvived.c) {
                survivedSharks.add(currentShark);
            }
        }
        sharks = survivedSharks;
    }
}