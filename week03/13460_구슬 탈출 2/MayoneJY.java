import java.util.*;
import java.io.*;


public class MayoneJY {
    static int N, M;
    static char[][] map;
    static int dx[] = {1, 0, -1, 0};
    static int dy[] = {0, 1, 0, -1};
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        boolean[][][][] visited = new boolean[N][M][N][M];
        int[] red = new int[2];
        int[] blue = new int[2];
        for(int i = 0; i < N; i++){
            String str = br.readLine();
            for(int j = 0; j < M; j++){
                char temp = str.charAt(j);
                // map에는 R과 B를 .으로 대체
                if(temp == 'R'){
                    red = new int[] {j, i};
                    map[i][j] = '.';
                }
                else if(temp == 'B'){
                    blue = new int[] {j, i};
                    map[i][j] = '.';
                }
                else{
                    map[i][j] = temp;
                }
            }
        }
        dfs(new int[]{-1,-1,-1} , 0, visited, red, blue);

        if(min == Integer.MAX_VALUE) min = -1;
        System.out.println(min);
    }

    /*
     * direction: {현재, 이전, 그 이전}
     * count: 횟수
     * visited: 빨간공과 파란공의 방문배열
     * red: 빨간공 좌표
     * blue: 파란공 좌표
     */
    static void dfs(int[] direction, int count, boolean[][][][] visited, int[] red, int[] blue){
        if(count >= min || count > 10) return;
        boolean[][][][] copyVisited = new boolean[N][M][N][M];
        int[] copyRed = red.clone();
        int[] copyBlue = blue.clone();

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                for(int k = 0; k < N; k++){
                    copyVisited[i][j][k] = visited[i][j][k].clone();
                }
            }
        }
        if(direction[0] != -1){
            boolean redCheck = false;
            boolean blueCheck = false;
            boolean redHole = false;
            boolean blueHole = false;

            // redCheck와 blueCheck가 true일 때 까지 반복
            while(true){
                switch (map[copyRed[1]+dy[direction[0]]][copyRed[0]+dx[direction[0]]]) {
                    case '#':
                        // 벽을 만났을 경우
                        redCheck = true;
                        break;
                    case 'O':
                        // 구멍을 만났을 경우
                        redCheck = true;
                        redHole = true;
                        break;
                    default:
                        // 벽과 구멍을 만나지 않았을 경우 방향대로 계속 +
                        // 이때 파란공과 빨간공의 위치와 관계 없음
                        if(!redCheck){
                            copyRed[0] += dx[direction[0]];
                            copyRed[1] += dy[direction[0]];
                        }
                        break;
                }

                switch (map[copyBlue[1]+dy[direction[0]]][copyBlue[0]+dx[direction[0]]]) {
                    case '#':
                        blueCheck = true;
                        break;
                    case 'O':
                        blueCheck = true;
                        blueHole = true;
                        break;
                    default:
                        if(!blueCheck){
                            copyBlue[0] += dx[direction[0]];
                            copyBlue[1] += dy[direction[0]];
                        }
                        break;
                }

                if(redCheck && blueCheck) {
                    
                    // 빨간공만 들어갔을 때
                    if(redHole && !blueHole){
                        min = Math.min(min, count);
                        return;
                    }
                    // 파란공만 들어갔을 때
                    else if(!redHole && blueHole){
                        return;
                    }
                    // 둘다 들어갔을 때
                    else if(redHole && blueHole){
                        return;
                    }

                    // 빨간공과 파란공의 위치가 같을 때
                    if(copyRed[0] == copyBlue[0] && copyRed[1] == copyBlue[1]){
                        switch (direction[0]) {
                            case 0:
                                if(red[0] > blue[0]){
                                    copyBlue[0]--;
                                }
                                else{
                                    copyRed[0]--;
                                }
                                break;
                            case 1:
                                if(red[1] > blue[1]){
                                    copyBlue[1]--;
                                }
                                else{
                                    copyRed[1]--;
                                }
                                break;
                            case 2:
                                if(red[0] > blue[0]){
                                    copyRed[0]++;
                                }
                                else{
                                    copyBlue[0]++;
                                }
                                break;
                            case 3:
                                if(red[1] > blue[1]){
                                    copyRed[1]++;
                                }
                                else{
                                    copyBlue[1]++;
                                }
                                break;
                            default:
                            break;
                        }
                        
                    }

                    if(copyVisited[copyRed[1]][copyRed[0]][copyBlue[1]][copyBlue[0]])
                        return;
                    copyVisited[copyRed[1]][copyRed[0]][copyBlue[1]][copyBlue[0]] = true;
                    break;
                }
            }
        }
        // 4방 탐색
        for(int d = 0; d < 4; d++){
            // 이전과 같은 방향은 탐색 X
            if(d != direction[0] || d != direction[1]){
                // 갈 방향에 바로 벽이 있다면
                if(map[copyRed[1]+dy[d]][copyRed[0]+dx[d]] == '#' && map[copyBlue[1]+dy[d]][copyBlue[0]+dx[d]] == '#') continue;
                // 빨간공 앞에 벽이 있고, 빨간공 앞에 파란공이 있다면
                if(map[copyRed[1]+dy[d]][copyRed[0]+dx[d]] == '#' && copyBlue[1]+dy[d] == copyRed[1] && copyBlue[0]+dx[d] == copyRed[0]) continue;
                // 파란공 앞에 벽이 있고, 파란공 앞에 빨간공이 있다면
                if(map[copyBlue[1]+dy[d]][copyBlue[0]+dx[d]] == '#' && copyRed[1]+dy[d] == copyBlue[1] && copyRed[0]+dx[d] == copyBlue[0]) continue;
                int[] dCopy = direction.clone();
                dCopy[2] = dCopy[1];
                dCopy[1] = dCopy[0];
                dCopy[0] = d;
                dfs(dCopy, count+1, copyVisited, copyRed, copyBlue);
            }
        }
    }
}
