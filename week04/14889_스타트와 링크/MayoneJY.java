
import java.util.*;
import java.io.*;

public class MayoneJY {
    static int N;
    static int[][] map;
    static List<int[]> teams = new ArrayList<>();
    static int min = Integer.MAX_VALUE;
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
        int[] t = new int[N/2];
        Arrays.fill(t, -1);
        combination(t, 0, 0);
        System.out.println(min);
    }

    static boolean contain(int[] team, int idx){
        for(int j = 0; j < team.length; j++){
            if(team[j] == idx){
                return true;
            }
        }
        return false;
    }

    static int calcTeamScore(int[] team){
        int teamScore = 0;
        for(int i = 0; i < team.length; i++){
            for(int j = 0; j < team.length; j++){
                teamScore += map[team[i]][team[j]];
            }
        }
        return teamScore;
    }

    static void combination(int[] team, int idx, int start){
        if(idx >= N/2){
            int[] team2 = new int[N/2];
            int team2Idx = 0;
            for(int i = 0; i < N; i++){
                if(!contain(team, i)){
                    team2[team2Idx] = i;
                    team2Idx++;
                }
            }
            int teamScore = calcTeamScore(team);
            int team2Score = calcTeamScore(team2);
            min = Math.min(min, Math.abs(teamScore-team2Score));
            return;
        }

        for(int i = start; i < N; i++){
            if(idx == 0 && i >= N/2) return;
            if(contain(team, i)) continue;
            team[idx] = i;
            combination(team, idx+1, i+1);
            team[idx] = -1;
        }
    }
}
