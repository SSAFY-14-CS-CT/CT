
import java.util.*;
import java.io.*;
public class MayoneJY {
	static int N;
	static int[][] map;
	static int one = 0, zero = 0;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		dfs(0, 0, N);

		System.out.println(zero);
		System.out.println(one);
	}
	
	
	static void dfs(int y, int x, int size) {
		if(isRange(y, x, size)) {
			if(map[y][x] == 1) one++;
			else zero++;
			return;
		}
		
		int half = size / 2;
		dfs(y, x, half);
		dfs(y + half, x, half);
		dfs(y, x + half, half);
		dfs(y + half, x + half, half);
	}
	
	static boolean isRange(int y, int x, int size) {
		int start = map[y][x];
		for(int i = y; i < y + size; i++) {
			for(int j = x; j < x + size; j++) {
				if(i >= N || j >= N || map[i][j] != start) return false;
			}
		}
		return true;
	}
}
