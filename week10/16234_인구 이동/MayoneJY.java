
import java.util.*;
import java.io.*;

public class MayoneJY {
    static int N, L, R;
    static Info[][] infos;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static class Info{
        int A;
        boolean check;
        Info(int A, boolean check){
            this.A = A;
            this.check = check;
        }
        Info(int A){
            this.A = A;
            this.check = false;
        }
        void t(){
            check = true;
        }
        void f(){
            check = false;
        }
    }
    static class Node{
        int y, x;
        Node(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
    static class BFSNode{
        int cal;
        ArrayDeque<Node> q;
        BFSNode(int cal, ArrayDeque<Node> q){
            this.cal = cal;
            this.q = q;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        infos = new Info[N][N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                infos[i][j] = new Info(Integer.parseInt(st.nextToken()));
            }
        }
        System.out.println(bfs());

    }

    static int bfs(){
        int count = 0;
        while(true){
            List<BFSNode> list = new ArrayList<>();
            boolean check = false;
            for(int n = 0; n < N; n++){
                for(int m = 0; m < N; m++){
                    if(!infos[n][m].check){
                        ArrayDeque<Node> q = new ArrayDeque<>();
                        int ANum = 0;
                        int nodeNum = 1;
                        ArrayDeque<Node> tq = new ArrayDeque<>();

                        q.add(new Node(n, m));
                        infos[n][m].t();
                        ANum = infos[n][m].A;
                        tq.add(new Node(n, m));
                        if(q.isEmpty())
                            break;

                        while (!q.isEmpty()) {
                            Node now = q.poll();
                            for(int i = 0; i < 4; i++){
                                Node next = new Node(now.y + dy[i], now.x + dx[i]);
                                if(next.y >= 0 && next.x >= 0 && next.y < N && next.x < N){
                                    int temp = Math.abs(infos[now.y][now.x].A - infos[next.y][next.x].A);
                                    if(!infos[next.y][next.x].check && 
                                        temp >= L && temp <= R){
                                        check = true;
                                        ANum += infos[next.y][next.x].A;
                                        infos[next.y][next.x].t();
                                        nodeNum++;
                                        q.add(next);
                                        tq.add(next);
                                    }
                                }
                            }
                        }
                        if(nodeNum != 1){
                            int cal = ANum / nodeNum;
                            list.add(new BFSNode(cal, tq));
                        }
                    }
                }
            }

            for(int i = 0; i < list.size(); i++){
                BFSNode node = list.get(i);
                while (!node.q.isEmpty()) {
                    Node now = node.q.poll();
                    infos[now.y][now.x].A = node.cal;
                }
            }
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    infos[i][j].f();
                    // System.out.printf("%d ", infos[i][j].A);
                }
                // System.out.println();
            }
            if (!check) {
                break;
            }
            else{
                count++;
            }
        }
        return count;
    }
}