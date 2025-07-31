import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int[] population;
    static List<List<Integer>> adjList;
    static boolean[] selected;
    static boolean[] visited;
    static int minDiff = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        population = new int[N];
        selected = new boolean[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++)
            population[i] = Integer.parseInt(st.nextToken());

        adjList = new ArrayList<>();
        for (int i = 0; i < N; i++)
            adjList.add(new ArrayList<>());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int neighbors = Integer.parseInt(st.nextToken());
            for (int j = 0; j < neighbors; j++) {
                int neighbor = Integer.parseInt(st.nextToken()) - 1;
                adjList.get(i).add(neighbor);
            }
        }

        divideRegions(0);

        System.out.println(minDiff == Integer.MAX_VALUE ? -1 : minDiff);
    }

    private static void divideRegions(int index) {
        if (index == N) {
            List<Integer> groupA = new ArrayList<>();
            List<Integer> groupB = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                if (selected[i]) groupA.add(i);
                else groupB.add(i);
            }

            if (groupA.isEmpty() || groupB.isEmpty()) return;

            if (isConnected(groupA) && isConnected(groupB)) {
                calculatePopulationDiff();
            }
            return;
        }

        selected[index] = true;
        divideRegions(index + 1);
        selected[index] = false;
        divideRegions(index + 1);
    }

    private static boolean isConnected(List<Integer> group) {
        visited = new boolean[N];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(group.get(0));
        visited[group.get(0)] = true;

        int visitedCount = 1;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : adjList.get(current)) {
                if (group.contains(neighbor) && !visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                    visitedCount++;
                }
            }
        }
        return visitedCount == group.size();
    }

    private static void calculatePopulationDiff() {
        int groupASum = 0;
        int groupBSum = 0;

        for (int i = 0; i < N; i++) {
            if (selected[i]) groupASum += population[i];
            else groupBSum += population[i];
        }

        minDiff = Math.min(minDiff, Math.abs(groupASum - groupBSum));
    }
}