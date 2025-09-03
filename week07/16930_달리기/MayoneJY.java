
import java.util.*;
import java.io.*;
public class MayoneJY {
	static int N, M, K;
	static boolean[][] map;
	static int[][] visited;
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new boolean[N][M];
		visited = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			String str = br.readLine();
			Arrays.fill(visited[i], Integer.MAX_VALUE);
			for(int j = 0; j < M; j++) {
				map[i][j] = (str.charAt(j) == '.')? true : false;
			}
		}


		st = new StringTokenizer(br.readLine());
		Node sNode = new Node(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
		Node eNode = new Node(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
		bfs(sNode, eNode);
		System.out.println(-1);
	}
	
	static void bfs(Node sNode, Node eNode) {
		Queue<Node> q = new ArrayDeque<>();
		visited[sNode.y][sNode.x] = 0;
		
		q.add(sNode);
		while(!q.isEmpty()) {
			Node now = q.poll();
			if(now.y == eNode.y && now.x == eNode.x) {
				System.out.println(visited[now.y][now.x]);
				System.exit(0);
			}
			
			
			for(int i = 0; i < 4; i++) {
				for(int k = 1; k <= K; k++) {
					Node next = new Node(now.y + (dy[i] * k), now.x + (dx[i] * k));
					if(isRange(next)){
						if(map[next.y][next.x] && visited[next.y][next.x] == Integer.MAX_VALUE) {
							visited[next.y][next.x] = visited[now.y][now.x] + 1;
							q.add(next);
						}
						else if(!map[next.y][next.x]){
							break;
						}
						else if(visited[now.y][now.x] + 1 > visited[next.y][next.x])
							break;
					}
				}
			}
		}
	}
	
	static boolean isRange(Node n) {
		if(n.y >= N || n.x >= M || n.y < 0 ||  n.x < 0)
			return false;
		return true;
	}
	
	
	static class Node{
		int y, x;

		public Node(int y, int x) {
			this.y = y;
			this.x = x;
		}
		
	}

}
