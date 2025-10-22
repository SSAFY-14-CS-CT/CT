import java.util.*;
import java.io.*;

public class MayoneJY {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        for(int i = 0; i < N; i++){
            int num = Integer.parseInt(br.readLine());
            int count = 1;
            count += num/2;
            count += num/3;
            int n = 1;
            while(3*n <= num){
                count += (num-3*n)/2;
                n++;
            }
            System.out.println(count);
        }
    }    
}
