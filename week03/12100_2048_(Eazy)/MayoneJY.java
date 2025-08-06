
import java.util.*;
import java.io.*;

public class MayoneJY {
    static int N;
    static int[][] map;
    static int MAX_COUNT = 5;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(map, 1);
        System.out.println(max);
    }

    static void dfs(int[][] map, int count){
        if(count > MAX_COUNT){
            checkMaxValue(map);
            return;
        }

        for(int dir = 0; dir < 4; dir++){
            int[][] copyMap = new int[N][N];
            for(int i = 0; i < N; i++)
                copyMap[i] = map[i].clone();
            move(copyMap, dir);
            dfs(copyMap, count+1);
        }
    }

    static void checkMaxValue(int[][] map){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                max = Math.max(map[i][j], max);
            }
        }
    }

    static void move(int[][] copyMap, int direction){
        switch (direction) {
            case 0: // 오른쪽
                for(int i = 0; i < N; i++){
                    // 해당 위치에 값이 더해진 값인지 확인하는 배열
                    boolean[] check = new boolean[N];
                    for(int j = N-1; j >= 0; j--){
                        if(copyMap[i][j] == 0) continue;
                        int count = 0;
                        // 벽이나 박스까지 반복하기 위한 while문
                        while(true){
                            if(j+1+count >= N) break;
                            // 현재 위치의 오른쪽의 값이 숫자(박스)일 때
                            if(copyMap[i][j+1+count] != 0){
                                // 오른쪽의 값이 더해진 값인지 확인, 현재 값과 오른쪽 값이 같을 때
                                if(!check[j+1+count] && copyMap[i][j+1+count] == copyMap[i][j+count]){
                                    check[j+1+count] = true;
                                    copyMap[i][j+1+count] += copyMap[i][j+count];
                                    copyMap[i][j+count] = 0;
                                }
                                break;
                            }
                            // 현재 위치의 오른쪽 값이 0(빈공간)일 때
                            copyMap[i][j+1+count] = copyMap[i][j+count];
                            copyMap[i][j+count] = 0;
                            count++;
                        }
                    }
                }
                break;
        
            case 1: // 아래
                for(int j = 0; j < N; j++){
                    boolean[] check = new boolean[N];
                    for(int i = N-1; i >= 0; i--){
                        if(copyMap[i][j] == 0) continue;
                        int count = 0;
                        while(true){
                            if(i+1+count >= N) break;
                            if(copyMap[i+1+count][j] != 0){
                                if(!check[i+1+count] && copyMap[i+1+count][j] == copyMap[i+count][j]){
                                    check[i+1+count] = true;
                                    copyMap[i+1+count][j] += copyMap[i+count][j];
                                    copyMap[i+count][j] = 0;
                                }
                                break;
                            }
                            copyMap[i+1+count][j] = copyMap[i+count][j];
                            copyMap[i+count][j] = 0;
                            count++;
                        }
                    }
                }
                break;
        
            case 2: // 왼쪽
                for(int i = 0; i < N; i++){
                    boolean[] check = new boolean[N];
                    for(int j = 0; j < N; j++){
                        if(copyMap[i][j] == 0) continue;
                        int count = 0;
                        while(true){
                            if(j-1-count < 0) break;
                            if(copyMap[i][j-1-count] != 0){
                                if(!check[j-1-count] && copyMap[i][j-1-count] == copyMap[i][j-count]){
                                    check[j-1-count] = true;
                                    copyMap[i][j-1-count] += copyMap[i][j-count];
                                    copyMap[i][j-count] = 0;
                                }
                                break;
                            }
                            copyMap[i][j-1-count] = copyMap[i][j-count];
                            copyMap[i][j-count] = 0;
                            count++;
                        }
                    }
                }
                break;
        
            case 3: // 위
                for(int j = 0; j < N; j++){
                    boolean[] check = new boolean[N];
                    for(int i = 0; i < N; i++){
                        if(copyMap[i][j] == 0) continue;
                        int count = 0;
                        while(true){
                            if(i-1-count < 0) break;
                            if(copyMap[i-1-count][j] != 0){
                                if(!check[i-1-count] && copyMap[i-1-count][j] == copyMap[i-count][j]){
                                    check[i-1-count] = true;
                                    copyMap[i-1-count][j] += copyMap[i-count][j];
                                    copyMap[i-count][j] = 0;
                                }
                                break;
                            }
                            copyMap[i-1-count][j] = copyMap[i-count][j];
                            copyMap[i-count][j] = 0;
                            count++;
                        }
                    }
                }
                break;
        
            default:
                break;
        }
    }
}
