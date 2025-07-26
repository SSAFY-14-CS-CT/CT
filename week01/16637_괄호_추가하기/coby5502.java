import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Main {
    static int N;
    static char[] arr;
    static int ans = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new java.io.OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        arr = new char[N];
        arr = br.readLine().toCharArray();
        
        solve(2, arr[0] - '0');

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static void solve(int idx, int sum) {
		if (idx >= N) {
			ans = Math.max(ans, sum);
			return;
		}
		
		// 괄호 안 친 경우
		solve(idx+2, cal(sum, arr[idx]-'0', arr[idx-1]));
		
		// 괄호 친 경우
		if (idx + 2 < N) {
			int right = cal(arr[idx]-'0', arr[idx+2]-'0', arr[idx+1]);
			int left = cal(sum, right, arr[idx-1]);
			solve(idx+4, left);
		}
	}

    private static int cal(int i, int j, char op) {
		if (op == '+')
			return i + j;
		else if (op == '-')
			return i - j;
		else
			return i * j;
	}
}