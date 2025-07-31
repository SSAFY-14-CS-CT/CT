import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[][] intMap;
    static char[][] charMap;
    static int[] dx = new int[]{1, 0 ,-1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};
    static char a = 'A';
    static ArrayList<char[]> bridge = new ArrayList<char[]>();
    static ArrayList<Character> bridgeChar = new ArrayList<Character>();
    static ArrayList<ArrayList<Integer>> com = new ArrayList<ArrayList<Integer>>();
    static int min = Integer.MAX_VALUE;
    static int sum = 0;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        intMap = new int[N][M];
        charMap = new char[N][M];
        for(int i = 0; i < N; i++){
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                intMap[i][j] = Integer.parseInt(st1.nextToken());
            }
        }
        covertMap(0, 0);

        createBridge();

        combination(0, new ArrayList<Integer>());

        for(int i = 0; i < com.size(); i++){
            run(com.get(i));
        }
        if(min == Integer.MAX_VALUE) min = -1;
        System.out.println(min);
    }

    // 연결 되었는지 확인하는 함수
    public static void find(boolean[] check, ArrayList<Integer> c, int idx){
        if(c.size() <= idx) return;
        char[] temp = bridge.get(c.get(idx));
        check[temp[0]-'A'] = true;
        check[temp[1]-'A'] = true;
        sum += temp[2] - '0';
        
        for(int i = 0; i < c.size(); i++){
            char[] t = bridge.get(c.get(i));
            if(check[t[0]-'A'] == true && check[t[1]-'A'] == true)
                continue;
            if(temp[0] == t[0] || temp[0] == t[1] || temp[1] == t[0] || temp[1] == t[1])
                find(check, c, i);
        }
    }

    public static void run(ArrayList<Integer> c){
        boolean[] check = new boolean[bridgeChar.size()];
        sum = 0;
        
        find(check, c, 0);

        boolean temp = false;
        for(int i = 0; i < check.length; i++){
            if(!check[i]){
                temp = true;
                break;
            }
        }
        if(!temp){
            min = Math.min(min, sum);
        }
    }

    // 연결된 다리의 조합
    public static void combination(int idx, ArrayList<Integer> c){
        if(idx == bridge.size()){
            com.add(c);
            return;
        }
        ArrayList<Integer> temp = new ArrayList<>();
        temp.addAll(c);
        temp.add(idx);
        combination(idx+1, temp);

        combination(idx+1, c);
    }

    // 다리 연결하는 함수
    public static void createBridge(){
        for(int i = 0; i < N; i++){
            char temp = '0';
            int count = 0;
            for(char m:charMap[i]){
                if(m != '0'){
                    if(temp != m){
                        if(temp != '0' && count > 1){
                            bridge.add(new char[]{temp, m, (char)(count+'0')});
                        }
                        temp = m;
                        count = 0;
                    }
                    else{
                        count = 0;
                    }
                    if(!bridgeChar.contains(temp)) bridgeChar.add(temp);
                }
                else{
                    count++;
                }
            }
        }
        
        for(int i = 0; i < M; i++){
            char temp = '0';
            int count = 0;
            for(int j = 0; j < N; j++){
                if(charMap[j][i] != '0'){
                    if(temp != charMap[j][i]){
                        if(temp != '0' && count > 1){
                            bridge.add(new char[]{temp, charMap[j][i], (char)(count+'0')});
                        }
                        temp = charMap[j][i];
                        count = 0;
                    }
                    else{
                        count = 0;
                    }
                    if(!bridgeChar.contains(temp)) bridgeChar.add(temp);
                }
                else{
                    count++;
                }
            }
        }
    }

    // 섬에 ID를 지정하는 함수
    public static void covertMap(int x, int y){
        if(x >= M){
            covertMap(0, y+1);
            return;
        }
        if(y >= N)
            return;

        if(intMap[y][x] == 0)
            charMap[y][x] = '0';

        if(intMap[y][x] == 1){
            findIsland(x, y);
            a += 1;
        }

        covertMap(x+1, y);
    }

    // 섬을 찾는 함수
    public static void findIsland(int x, int y){
        if(x >= M || y >= N || x < 0 || y < 0)
            return;
        if(intMap[y][x] == 1){
            intMap[y][x] = 2;
            charMap[y][x] = a;
            for(int i = 0; i < 4; i++){
                findIsland(x+dx[i], y+dy[i]);
            }
        }
    }
}
