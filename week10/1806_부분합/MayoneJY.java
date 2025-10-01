
import java.io.*;
import java.util.*;

public class MayoneJY {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());

        int[] map = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int n = 0; n < N; n++){
            map[n] = Integer.parseInt(st.nextToken());
        }

        int ans = Integer.MAX_VALUE;
        int count = 0;
        int temp = 0;
        int tempN = 0;
        outBreak:
        for(int n = 0; n < N; n++){

            while(temp < S){
                if(tempN >= N) break outBreak;
                temp += map[tempN++];
                count++;
            }
            ans = Math.min(ans, count);
            count--;
            temp -= map[n];
        }

        System.out.println(ans == Integer.MAX_VALUE ? 0 : ans);
    }    
}
