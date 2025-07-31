import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static int N, M, K;
    static int[][] map;
    static int[][] copy;
    static int[][] rot;
    static int[] seq;
    static boolean[] visited;
    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new int[N+1][M+1];
        copy = new int[N+1][M+1];
        rot = new int[K+1][4];
        seq = new int[K+1];
        visited = new boolean[K+1];

        for (int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=1; j<=M; j++) {
            	map[i][j] = Integer.parseInt(st.nextToken());
                copy[i][j] = map[i][j];
            }
        }

        for (int i=1; i<=K; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=1; j<=3; j++) {
                rot[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        MakeSequence(1);
        
        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static void MakeSequence(int idx) {
    	if (idx > K) {
    		RestoreMap();
    		FullRotate();
    		FindMin();
    		return;
    	}
    	
    	for (int i=1; i<=K; i++) {
    		if (!visited[i]) {
    			visited[i] = true;
    			seq[idx] = i;
    			MakeSequence(idx+1);
    			visited[i] = false;
    		}
    	}
    }
    
//    public static void PrintMap() {
//        for (int i=1; i<=N; i++) {
//            for (int j=1; j<=M; j++) {
//            	System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
    
    public static void RestoreMap() {
        for (int i=1; i<=N; i++) {
            for (int j=1; j<=M; j++) {
            	map[i][j] = copy[i][j];
            }
        }
    }
    
    public static void FindMin() {
        for (int i=1; i<=N; i++) {
        	int sum = 0;
            for (int j=1; j<=M; j++) {
            	sum += map[i][j];
            }
            
            if (ans > sum) ans = sum;
        }
    }
    
    public static void FullRotate() {
		for(int i=1; i<=K; i++) {
			int x1 = rot[seq[i]][1]-rot[seq[i]][3];
			int y1 = rot[seq[i]][2]-rot[seq[i]][3];
			int x2 = rot[seq[i]][1]+rot[seq[i]][3];
			int y2 = rot[seq[i]][2]+rot[seq[i]][3];
			
			while(x1 <= x2 && y2 <= y2 && x1 != x2 && y1 != y2) {
				Rotate(x1, y1, x2, y2);
				x1++;
				y1++;
				x2--;
				y2--;
			}
			
//        	System.out.println(seq[i]);
//			PrintMap();
		}
    }

	public static void Rotate(int x1, int y1, int x2, int y2) {
		int x1y1 = map[x1][y1];
		
		for(int i=x1; i<x2; i++) {
			map[i][y1] = map[i+1][y1];
		}
		
		for(int i=y1; i<y2; i++) {
			map[x2][i] = map[x2][i+1];
		}
		
		for(int i=x2; i>x1; i--) {
			map[i][y2] = map[i-1][y2];
		}

		for(int i=y2; i>y1; i--) {
			map[x1][i] = map[x1][i-1];
		}
		
		map[x1][y1+1] = x1y1;
	}
}