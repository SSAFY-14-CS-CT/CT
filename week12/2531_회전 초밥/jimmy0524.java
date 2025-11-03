import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int answer = 0;
        int[] sushi = new int[d + 1];
        int[] arr = new int[N + k];
        for (int i = 0; i < N; i++) {
            int now = Integer.parseInt(br.readLine());
            arr[i] = now;
        }

        int index = 0;
        for (int i = N; i < N + k; i++) {
            arr[i] = arr[index];
            index++;
        }

        int left = 0;
        int right = left + k - 1;
        for (int i = left; i <= right; i++) {
            sushi[arr[i]]++;
        }
        sushi[c]++;
        int count = 0;
        for (int i = 1; i < d + 1; i++) {
            if (sushi[i] != 0) {
                count++;
            }
        }
        answer = Math.max(count, answer);
        while (left < N + k && right < N + k) {
            if (sushi[arr[left]] != 0) {
                sushi[arr[left]]--;
            }
            left += 1;
            right = left + k - 1;
            if (right >= N + k) {
                break;
            }
            sushi[arr[right]]++;

            count = 0;
            for (int i = 1; i < d + 1; i++) {
                if (sushi[i] != 0) {
                    count++;
                }
            }
            answer = Math.max(count, answer);
        }

        System.out.println(answer);
    }
}
