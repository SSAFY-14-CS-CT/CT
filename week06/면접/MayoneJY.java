package week06.면접;

import java.io.*;
import java.util.*;
public class MayoneJY {
	static int T;
	static int N, M, K;
	static int min;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int t = 1; t <= T; t++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			min = Integer.MAX_VALUE;
			List<Integer> count = new LinkedList<>();
			for(int i = 1; i <= N; i++) {
				int temp = i % K;
				if(temp == 0) temp = K;
				count.add(temp);
			}
			int zeroCount = N-M;
			for(int i = 0; i < N-M; i++) {
				int a = (((N / K) * K) - (i * K))-1;
				if (a < 0) break;
				count.set(a, 0);
				zeroCount--;
			}
			int tempK = 1;
			breakWhile:
			while(tempK > 0) {
				for(int i = 0; i < N; i++) {
					if(zeroCount <= 0) {
						break breakWhile;
					}
					int idx = 0;
					if(count.get(i) == K-tempK) {
						count.set(i, 0);
						if(i+1 < count.size() && count.get(i+1) != 0){ // 새로 작성 한 부분.
							for(int j = 1; j < K-tempK; j++) {
								count.add(i+j, j);
								idx++;
							}
							for(int j = N+idx-1; j >= 0; j--) {
								if(idx == 0) break;
								count.remove(j);
								idx--;
							}
						}
						zeroCount--;
					}
				}
				tempK++;
			}
			
			int result = 0;
			for(int i = 0; i < N; i++) {
				if(count.get(i) == 0) continue;
				if(count.get(i) != 0) result++;
				if(count.get(i) == K) result *= 2;
			}
			
			System.out.printf("#%d %d\n", t, result);
			
		}
	}
}
