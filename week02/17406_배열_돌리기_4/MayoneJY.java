import java.io.*;
import java.util.*;

public class Main {
    public static int X;
    public static int Y;
    public static int N;
    public static int[][] move;
    public static ArrayList<int[]> orders = new ArrayList<int[]>();
    public static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Y = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        int[][] map = new int[Y][X];
        for(int i = 0; i < Y; i++){
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            for(int j = 0; j < X; j++){
                map[i][j] = Integer.parseInt(st1.nextToken());
            }
        }
        move = new int[N][3];
        for(int i = 0; i < N; i++){
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            for(int j = 0; j < 3; j++){
                move[i][j] = Integer.parseInt(st1.nextToken());
            }
        }
        int[] temp = new int[N];
        for(int i = 0; i < N; i++){
            temp[i] = -1;
        }
        combination(0, temp);
        for(int i = 0; i < orders.size(); i++){
            int[][] copyMap = new int[Y][X];
            for(int k = 0; k < Y; k++){
                copyMap[k] = map[k].clone();
            }
            for(int k = 0; k < N; k++){
                int tempMin = Math.min(
                    (int)(((move[orders.get(i)[k]][0]+move[orders.get(i)[k]][2])-(move[orders.get(i)[k]][0]-move[orders.get(i)[k]][2])+1)/2),
                    (int)(((move[orders.get(i)[k]][1]+move[orders.get(i)[k]][2])-(move[orders.get(i)[k]][1]-move[orders.get(i)[k]][2])+1)/2)
                    );
                for(int j = 0; j < tempMin; j++){
                    run(j, k, orders.get(i), copyMap);
                }
            }
            for(int j = 0; j < Y; j++){
                int sum = 0;
                for(int x = 0; x < X; x++){
                    sum += copyMap[j][x];
                }
                min = Math.min(min, sum);
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(Integer.toString(min));
        bw.flush();
        bw.close();
    }

    /*
     * idx : 바깥쪽부터 몇 번째 라인
     * orderIdx : 회전 범위 인덱스
     * order : 회전 범위
     * copyMap : 맵
     */
    public static void run(int idx, int orderIdx, int[] order, int[][] copyMap){
        int y1 = move[order[orderIdx]][0]-move[order[orderIdx]][2]-1;
        int x1 = move[order[orderIdx]][1]-move[order[orderIdx]][2]-1;
        int y2 = move[order[orderIdx]][0]+move[order[orderIdx]][2]-1;
        int x2 = move[order[orderIdx]][1]+move[order[orderIdx]][2]-1;

        int temp1 = copyMap[idx+y1][idx+x1];
        for(int i = idx+y1; i < y2-idx; i++){
            if(i+1 > y2-idx) break;
            copyMap[i][idx+x1] = copyMap[i+1][idx+x1];
        }
        for(int i = idx+x1; i < x2-idx; i++){
            if(i+1 > x2-idx) break;
            copyMap[y2-idx][i] = copyMap[y2-idx][i+1];
        }
        for(int i = y2-idx; i >= idx+y1; i--){
            if(i-1 < idx+y1) break;
            copyMap[i][x2-idx] = copyMap[i-1][x2-idx];
        }
        for(int i = x2-idx; i >= idx+x1; i--){
            if(i-1 < idx+x1) break;
            copyMap[idx+y1][i] = copyMap[idx+y1][i-1];
        }
        copyMap[idx+y1][idx+x1+1] = temp1;
    }

    public static void combination(int idx, int[] com){
        if(idx == N){
            orders.add(com);
            return;
        }

        for(int i = 0; i < N; i++){
            if(!find(com, i)){
                int[] temp = com.clone();
                temp[idx] = i;
                combination(idx+1, temp);
            }
        }

    }

    public static boolean find(int[] com, int value){
        for(int c:com){
            if(c==value) return true;
        }
        return false;
    }
}
