import java.io.*;
import java.util.*;

public class Main {
    static char[][] map = new char[5][5];
    static int ans = 0;
    static final int[] dr = { 1, -1, 0, 0 };
    static final int[] dc = { 0, 0, 1, -1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int r = 0; r < 5; r++) {
            String line = br.readLine();
            for (int c = 0; c < 5; c++) {
                map[r][c] = line.charAt(c);
            }
        }
        // idx: 다음에 고려할 칸(0~24), picked: 현재까지 선택 수, sCnt: 현재까지 S 수, mask: 선택된 칸 비트마스크
        dfs(0, 0, 0, 0);
        System.out.println(ans);
    }

    // 조합 DFS (idx부터 24까지 보며 7칸 선택)
    static void dfs(int idx, int picked, int sCnt, int mask) {
        // 7칸 다 뽑았으면 조건(S>=4) + 연결성 검사
        if (picked == 7) {
            if (sCnt >= 4 && isConnected(mask))
                ans++;
            return;
        }

        // 남은 칸 개수가 부족하면 컷 (idx..24 총 25-idx개)
        if ((25 - idx) < (7 - picked))
            return;

        // 남은 선택을 전부 S라고 가정해도 S>=4 불가하면 컷 (느슨하지만 안전한 가지치기)
        if (sCnt + (7 - picked) < 4)
            return;

        // 현재 상태에서 Y 수가 4 초과면 컷 (picked - sCnt = Y 개수)
        if ((picked - sCnt) > 3)
            return;

        for (int i = idx; i < 25; i++) {
            int r = i / 5, c = i % 5;
            boolean isS = (map[r][c] == 'S');

            int nextPicked = picked + 1;
            int nextSCnt = sCnt + (isS ? 1 : 0);

            // 추가로 선택했을 때 Y가 4 초과되면 패스
            int nextY = nextPicked - nextSCnt;
            if (nextY > 3)
                continue;

            // 남은 선택을 다 S로 잡아도 S>=4 불가능하면 패스
            if (nextSCnt + (7 - nextPicked) < 4)
                continue;

            dfs(i + 1, nextPicked, nextSCnt, mask | (1 << i));
        }
    }

    // 선택된 7칸(mask)이 4방향으로 모두 연결인지 BFS 검사
    static boolean isConnected(int mask) {
        // 첫 번째 선택 칸 찾기
        int start = -1;
        for (int i = 0; i < 25; i++) {
            if ((mask & (1 << i)) != 0) {
                start = i;
                break;
            }
        }
        if (start == -1)
            return false;

        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[25];
        q.offer(start);
        visited[start] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int cur = q.poll();
            int r = cur / 5, c = cur % 5;
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr < 0 || nr >= 5 || nc < 0 || nc >= 5)
                    continue;
                int ni = nr * 5 + nc;
                if (visited[ni])
                    continue;
                // 선택된 집합 안에 있는 칸만 이동
                if ((mask & (1 << ni)) == 0)
                    continue;
                visited[ni] = true;
                q.offer(ni);
                count++;
            }
        }

        return count == 7;
    }
}