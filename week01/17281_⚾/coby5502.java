import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static final int NUMBERS = 9;
    static final int INNINGS = 9;
    static int[][] players;
    static int[][] games;
    static int[] seq = new int[NUMBERS];
    static boolean[] visited = new boolean[NUMBERS];
    static int playerIdx = 0;
    static int[] base = new int[3];
    static int score = 0;
    static int ans = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        players = new int[N][NUMBERS];
        games = new int[N][NUMBERS];
        
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<NUMBERS; j++) {
                players[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        MakeSequence(0);
        
        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static void MakeSequence(int idx) {
        if (idx == NUMBERS) {
            PlayGame();
            return;
        }

        if (idx == 3) {
            seq[idx] = 0;
            MakeSequence(idx+1);
            return;
        }

        for (int i=1; i<NUMBERS; i++) {
            if (!visited[i]) {
                visited[i] = true;
                seq[idx] = i;
                MakeSequence(idx+1);
                visited[i] = false;
            }
        }
    }
    
    public static void PlayGame() {
        playerIdx = 0;
        score = 0;
        
        for (int i=0; i<N; i++) {
            PlayInning(i);
        }
        
        if (ans < score) {
            ans = score;
        }
    }
    
    public static void PlayInning(int inning) {
        int out = 0;
        Arrays.fill(base, 0);
        
        while(out < 3) {
            switch (players[inning][seq[playerIdx]]) {
            case 0:
                out++;
                break;
            case 1:
                One();
                break;
            case 2:
                Two();
                break;
            case 3:
                Three();
                break;
            case 4:
                Four();
                break;
            }

            playerIdx++;
            if (playerIdx == NUMBERS) playerIdx = 0;
        }
    }
    
    public static void One() {
        if (base[2] == 1) score++;
        base[2] = base[1];
        base[1] = base[0];
        base[0] = 1;
    }
    
    public static void Two() {
        if (base[2] == 1) score++;
        if (base[1] == 1) score++;
        base[2] = base[0];
        base[1] = 1;
        base[0] = 0;
    }
    
    public static void Three() {
        if (base[2] == 1) score++;
        if (base[1] == 1) score++;
        if (base[0] == 1) score++;
        base[2] = 1;
        base[1] = 0;
        base[0] = 0;
    }
    
    public static void Four() {
        if (base[2] == 1) score++;
        if (base[1] == 1) score++;
        if (base[0] == 1) score++;
        score++;
        Arrays.fill(base, 0);
    }
}