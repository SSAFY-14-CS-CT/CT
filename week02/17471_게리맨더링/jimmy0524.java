import java.util.*;
import java.io.*;

public class Main {
    static int[] ward;
    static int N;
    static boolean[] visited;
    static int answer = Integer.MAX_VALUE;
    static int[] population;
    static ArrayList<Integer>[] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine()); //6
        StringTokenizer st = new StringTokenizer(br.readLine());
        population = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            population[i] = Integer.parseInt(st.nextToken());
        }
        arr = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            arr[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 1; j <= num; j++) {
                arr[i].add(Integer.parseInt(st.nextToken()));
            }
        }
        ward = new int[N + 1];
        combine(1);
        if (answer == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }
    }

    public static void combine(int index) {
        if (index == N + 1) {
            visited = new boolean[N + 1];
            int count = 0;
            int sum = 0;
            for (int i = 1; i <= N; i++) {
                if (!visited[i]) {
                    count++;
                    bfs(i);
                }
            }

            if (count == 2) {
                for (int j = 1; j <= N; j++) {
                    if (ward[j] == 1) {
                        sum += population[j];
                    } else {
                        sum -= population[j];
                    }
                }
                sum = Math.abs(sum);
                answer = Math.min(answer, sum);
            }
            return;
        }
        ward[index] = 1;
        combine(index + 1);
        ward[index] = 2;
        combine(index + 1);
    }

    public static void bfs(int index) {
        Queue<Integer> q = new LinkedList<>();
        q.add(index);
        visited[index] = true;

        while(!q.isEmpty()) {
            int now = q.poll();
            for (int next : arr[now]) {
                if (!visited[next] && ward[next] == ward[now]) {
                    visited[next] = true;
                    q.add(next);
                }
            }
        }
    }
}
