import java.util.*;
import java.io.*;

class Main{
    static int N = 10;
    static int[] PAPER = new int[]{5,5,5,5,5};
    static int[][] map = new int[N][N];
    static int min = Integer.MAX_VALUE;
    static public void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int[] paper = PAPER.clone();
        int[][] copyMap = new int[N][N];
        for(int m = 0; m < N; m++) {
            copyMap[m] = map[m].clone();
        }
        for(int i = 5; i > 0; i--)
            dfs(copyMap, paper, 0, 0, i);
        if(min == Integer.MAX_VALUE) min = -1;
        bw.write(Integer.toString(min));
        bw.flush();
        bw.close();
    }

    static void dfs(int[][] map, int[] paper, int X, int Y, int P){
        // 색종이를 다 썼을 경우 종료
        if(paper[P-1] == 0) return;
        
        // X 좌표가 최대 좌표를 넘어서는 경우 다음 줄로 호출
        if(X >= N){
            // for(int i = 5; i > 0; i--)
            dfs(map, paper, 0, Y+1, P);
            return;
        }
        // 탐색이 끝난 경우 저장 후 종료
        if(Y >= N){
            boolean check = false;
            outBreak:
            for(int pY = 0; pY < N; pY++){
                for(int pX = 0; pX < N; pX++){
                    // 1이 남아있을 경우 실패
                    if(map[pY][pX] == 1){
                        check = true;
                        break outBreak;
                    }
                }
            }
            if (!check) {
                int count = 0;
                for (int i : paper) {
                    count += 5-i;
                }
                min = Math.min(min, count);
            }
            return;
        }
        
        // 탐색 위치의 값이 0일 경우 x좌표 +1
        if(map[Y][X] == 0)
            dfs(map, paper, X+1, Y, P);

        // 인덱스 에러 예외처리
        if(X + P > N || Y + P > N) return;
        
        int[][] copyMap = new int[N][N];
        int[] copyPaper = paper.clone();
        for(int m = 0; m < N; m++) {
            copyMap[m] = map[m].clone();
        }
        boolean check = false;

        // 탐색 좌표의 해당 N*N색종이를 붙일 수 없는 경우 종료
        outBreak:
        for(int pY = 0; pY < P; pY++){
            for(int pX = 0; pX < P; pX++){
                if(map[Y+pY][X+pX] == 0){
                    check = true;
                    break outBreak;
                }
            }
        }
        if(check)
            return;
        
        // 탐색 좌표의 해당 N*N색종이를 붙일 수 있는 경우
        
        copyPaper[P-1]--;
        
        for(int pY = 0; pY < P; pY++){
            for(int pX = 0; pX < P; pX++){
                copyMap[Y+pY][X+pX] = 0;
            }
        }

        // 다음 좌표에 N*N색종이 호출
        for(int i = 5; i > 0; i--)
            dfs(copyMap, copyPaper, X+P, Y, i);
    }
}