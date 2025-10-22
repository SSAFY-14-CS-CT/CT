import java.io.*;
import java.util.*;

public class MayoneJY {
    static int N, D, K, C;
    static int[] susi;
    static List<Integer> selectSusi = new LinkedList<>();
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        susi = new int[K];

        int count = 0;
        int max = 0;
        int j = 0;
        for(int i = 0; i < K-1; i++){
            int t = Integer.parseInt(br.readLine());
            if(i < K){
                if(!selectSusi.contains(t))
                    count++;
                susi[i] = t;
                selectSusi.add(t);
            }
        }
        int i = 0;
        for(i = 0; i < N; i++){
            if(N - i + 1 <= K){
                if(!selectSusi.contains(susi[j]))
                    count++;
                selectSusi.add(susi[j++]);
            }
            else{
                int t = Integer.parseInt(br.readLine());
                if(!selectSusi.contains(t))
                    count++;
                selectSusi.add(t);
            }
            if(!selectSusi.contains(C))
                max = Math.max(max, count + 1);
            else
                max = Math.max(max, count);
            
            if(max == K + 1)
                break;

            if(!selectSusi.contains(selectSusi.remove(0)))
                count--;
        }
        System.out.println(max);
    }
}