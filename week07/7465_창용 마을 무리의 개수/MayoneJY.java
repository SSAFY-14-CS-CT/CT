
import java.io.*;
import java.util.*;

public class MayoneJY {
    static int T, N, M;
    static int[] parents;
    static Set<Integer> count;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            parents = new int[N];
            count = new HashSet<>();
            for(int n = 0; n < N; n++){
                parents[n] = n;
            }
            for(int m = 0; m < M; m++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;

                union(a, b);
            }
            for(int n = 0; n < N; n++){
                count.add(find(n));
            }
            System.out.printf("#%d %d\n", t, count.size());
        }
    }

    static int find(int a){
        if(parents[a] == a) return a;
        return parents[a] = find(parents[a]);
    }

    static void union(int a, int b){
        a = find(a);
        b = find(b);

        if(a != b){
            parents[b] = a;
        }
    }
}