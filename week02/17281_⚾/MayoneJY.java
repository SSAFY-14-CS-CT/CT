import java.io.*;
import java.util.*;

public class Main {
    public static int N;
    public static int member[][];
    public static int max = Integer.MIN_VALUE;
    public static ArrayList<int[]> combinationMember = new ArrayList<>();
    public static int ACE = 3;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        member = new int[N][9];
        combination(new int[9], 0);
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 9; j++){
                member[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i = 0; i < combinationMember.size(); i++){
            play(i, 0, 0, new boolean[3], 0, 0);
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(Integer.toString(max));
        bw.flush();
        bw.close();
    }

    /*
     * playersIdx : 조합된 선수들의 인덱스
     * idx : 이닝
     * startPlayer : N 번째 타자
     * ru : 1 ~ 3루의 주자 여부
     * score : 점수
     * out : 아웃
     */
    public static void play(int playersIdx, int idx, int startPlayer, boolean[] ru, int score, int out){
        if(idx == N){
            max = Math.max(max, score);
            return;
        }
        if(startPlayer >= 9) startPlayer = 0;

        if(member[idx][combinationMember.get(playersIdx)[startPlayer]] == 0){
            out++;
            if(out == 3){
                for(int i = 0; i < 3; i++)
                    ru[i] = false;
                play(playersIdx, idx+1, startPlayer+1, ru, score, 0);
            }
            else
                play(playersIdx, idx, startPlayer+1, ru, score, out);
        }
        else{
            Object result[] = running(member[idx][combinationMember.get(playersIdx)[startPlayer]], ru);
            play(playersIdx, idx, startPlayer+1, (boolean[])result[1], score+(int)result[0], out);
        }
    }

    public static Object[] running(int run, boolean[] ru){
        int score = 0;
        /*
         * 0 : 1루
         * 1 : 2루
         * 2 : 3루
         */
        for(int i = 2; i >= 0; i--){
            // 주자가 있다면
            if(ru[i]){
                ru[i] = false;
                // 홈까지 들어가지 않았을 때
                if(i + run < 3) ru[i+run] = true;
                else
                    score++;
            }
        }
        // 홈런일 때
        if(run == 4){
            for(int i = 0; i < 3; i++)
                ru[i] = false;
            score++;
        }
        else
            ru[run-1] = true;
        return new Object[]{score, ru};
    }

    public static void combination(int[] players, int idx){
        if(idx == 9) combinationMember.add(players);
        if(idx == 3){
            int[] temp = players.clone();
            temp[idx] = 0;
            combination(temp, idx+1);
        }
        else{
            for(int i = 1; i < 9; i++){
                if(!find(players, i)){
                    int[] temp = players.clone();
                    temp[idx] = i;
                    combination(temp, idx+1);
                }
            }
        }
    }
    
    public static boolean find(int[] arr, int value){
        for(int v: arr){
            if(v == value)
                return true;
        }
        return false;
    }
}