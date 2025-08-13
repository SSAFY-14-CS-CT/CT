
import java.util.*;
import java.io.*;
public class MayoneJY {
    static int N;
    static int[] num;
    static char[] op = {'+', '-', '*', '/'};
    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int[] opCount = new int[4];
        num = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            num[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < 4; i++){
            opCount[i] = Integer.parseInt(st.nextToken());
        }

        dfs("", opCount);
        System.out.println(max);
        System.out.println(min);
    }

    static int calc(String o){
        int sum = num[0];
        for(int i = 0; i < o.length(); i++){
            switch (o.charAt(i)) {
                case '+':
                    sum += num[i+1];
                    break;
                case '-':
                    sum -= num[i+1];
                    break;
                case '*':
                    sum *= num[i+1];
                    break;
                case '/':
                    sum /= num[i+1];
                default:
                    break;
            }
        }
        return sum;
    }

    static void dfs(String o, int[] opCount){
        boolean check = false;
        for(int i = 0; i < 4; i++){
            if(opCount[i] != 0) check = true;
        }
        if(!check){
            int sum = calc(o);
            min = Math.min(min, sum);
            max = Math.max(max, sum);
            return;
        }

        for(int i = 0; i < 4; i++){
            if(opCount[i] != 0){
                opCount[i]--;
                dfs(o+op[i], opCount);
                opCount[i]++;
            }
        }
    }
}
