import java.io.*;
import java.util.*;

public class MayoneJY {

    public enum Name { FISH, SHARK; }

    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static final int N = 4;
    static int max = 0;

    static List<Fish> fishs = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                int size = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                fishs.add(new Fish(size, dir-1, Name.FISH, i, j));
            }
        }
        Collections.sort(fishs);

        Fish startFish = findFishAt(fishs, 0, 0);
        int initScore = 0;
        int sharkDir;
        if (startFish != null) {
            initScore = startFish.size;
            sharkDir = startFish.dir;
            startFish.isDead = true;
        } else {
            initScore = 0;
            sharkDir = 0;
        }
        Fish shark = new Fish(1000, sharkDir, Name.SHARK, 0, 0);
        fishs.add(shark);
        Collections.sort(fishs);

        dfs(deepCopy(fishs), initScore);
        System.out.println(max);
    }

    static void dfs(List<Fish> state, int score){
        moveFish(state);

        Fish shark = getShark(state);

        boolean moved = false;
        for (int step = 1; step <= 3; step++) {
            int ny = shark.y + dy[shark.dir] * step;
            int nx = shark.x + dx[shark.dir] * step;
            if (!inRange(ny, nx)) break;

            Fish target = findAliveFishAt(state, ny, nx);
            if (target == null) continue;
            moved = true;

            List<Fish> next = deepCopy(state);
            Fish nextShark = getShark(next);
            Fish eaten = findAliveFishAt(next, ny, nx);

            nextShark.y = ny; nextShark.x = nx;
            nextShark.dir = eaten.dir;
            eaten.isDead = true;

            dfs(next, score + eaten.size);
        }

        if (!moved) {
            max = Math.max(max, score);
        }
    }

    static void moveFish(List<Fish> state){
        Fish shark = getShark(state);
        for (Fish f : state) {
            if (f.name == Name.SHARK || f.isDead) continue;

            for (int rot = 0; rot < 8; rot++) {
                int nd = (f.dir + rot) % 8;
                int ny = f.y + dy[nd];
                int nx = f.x + dx[nd];

                if (!inRange(ny, nx)) continue;
                if (ny == shark.y && nx == shark.x) continue;

                Fish occupant = findAliveFishAt(state, ny, nx);
                if (occupant != null) {
                    int ty = f.y, tx = f.x;
                    f.y = occupant.y; f.x = occupant.x;
                    occupant.y = ty;  occupant.x = tx;
                } else {
                    f.y = ny; f.x = nx;
                }
                f.dir = nd;
                break;
            }
        }
    }

    static boolean inRange(int y, int x){
        return 0 <= y && y < N && 0 <= x && x < N;
    }

    static Fish findFishAt(List<Fish> state, int y, int x){
        for (Fish f : state) {
            if (f.y == y && f.x == x) return f;
        }
        return null;
    }

    static Fish findAliveFishAt(List<Fish> state, int y, int x){
        for (Fish f : state) {
            if (f.name == Name.FISH && !f.isDead && f.y == y && f.x == x) return f;
        }
        return null;
    }

    static Fish getShark(List<Fish> state){
        return state.get(state.size()-1);
    }

    static List<Fish> deepCopy(List<Fish> src){
        List<Fish> copy = new ArrayList<>(src.size());
        for (Fish f : src) copy.add(f.copy());
        return copy;
    }

    static class Fish implements Comparable<Fish>{
        Name name;
        int size, dir;
        int y, x;
        boolean isDead = false;

        Fish(int size, int dir, Name name, int y, int x){
            this.size = size;
            this.dir = dir;
            this.name = name;
            this.y = y;
            this.x = x;
        }

        Fish copy(){
            Fish c = new Fish(size, dir, name, y, x);
            c.isDead = isDead;
            return c;
        }

        @Override
        public int compareTo(Fish o){
            return Integer.compare(this.size, o.size);
        }
    }
}
