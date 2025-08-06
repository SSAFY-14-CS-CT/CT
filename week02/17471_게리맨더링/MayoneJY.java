import java.util.*;
import java.io.*;

public class Main {
    public static int N;
    public static int[] nSize;
    public static int[][] nodeMap;
    public static ArrayList<ArrayList<Integer>> com = new ArrayList<ArrayList<Integer>>();
    public static int min = Integer.MAX_VALUE;
    public static boolean[] visited;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        nSize = new int[N];
        nodeMap = new int[N][];
        for(int i = 0; i < N; i++){
            nSize[i] = Integer.parseInt(st.nextToken());
        }
        for(int i = 0; i < N; i++){
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            int J = Integer.parseInt(st1.nextToken());
            int[] temp = new int[J];
            for(int j = 0; j < J; j++){
                temp[j] = Integer.parseInt(st1.nextToken())-1;
            }
            nodeMap[i] = temp;
        }
        combination(new ArrayList<Integer>(), 0);
        for(int i = 0; i < com.size(); i++){
            run(com.get(i));
        }
        if(min == Integer.MAX_VALUE) min = -1;
        System.out.println(min);
    }
    
    public static void find(ArrayList<Integer> com, int idx){
        if(visited[idx]) return;
        for(int i = 0; i < nodeMap[com.get(idx)].length; i++){
            if(com.contains(nodeMap[com.get(idx)][i])){
                visited[idx] = true;
                for(int j = 0; j < com.size(); j++){
                    if(com.get(j) == nodeMap[com.get(idx)][i]){
                        find(com, j);
                        break;
                    }
                }
            }
        }
    }

    public static void run(ArrayList<Integer> com){
        ArrayList<Integer> com2 = new ArrayList<Integer>();
        for(int i = 0; i < N; i++){
            if(!com.contains(i))
                com2.add(i);
        }
        if(com.size()==0 || com2.size()==0) return;

        visited = new boolean[com.size()];
        find(com, 0);
        boolean check1 = false;
        for(boolean b:visited){
            if(!b) check1 = true;
        }
        
        visited = new boolean[com2.size()];
        find(com2, 0);
        boolean check2 = false;
        for(boolean b:visited){
            if(!b) check2 = true;
        }
        

        if((!check1 || com.size() == 1) && (!check2 || com2.size() == 1)){
            int sum1 = 0;
            int sum2 = 0;
            for(int i = 0; i < com.size(); i++){
                sum1 += nSize[com.get(i)];
            }
            for(int i = 0; i < com2.size(); i++){
                sum2 += nSize[com2.get(i)];
            }
            int result = sum1 - sum2;
            if (result < 0) {
                result *= -1;
            }
            min = Math.min(min, result);
        }
    }
    public static void combination(ArrayList<Integer> c, int idx){
        if(idx==N){
            com.add(c);
            return;
        }
        
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.addAll(c);
        temp.add(idx);
        combination(temp, idx+1);

        combination(c, idx+1);
    }
}

