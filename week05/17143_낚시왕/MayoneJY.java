import java.util.*;
import java.io.*;

public class MayoneJY {
    static int R, C;
    static int locateMan = 0;
    static Shark[][] sharks;
    static int sumFishedSize = 0;
    public static void main(String[] args) throws Exception{
        init();
        moveMan();
        System.out.println(sumFishedSize);
    }
    
    static void init() throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        sharks = new Shark[R+1][C+1];
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            boolean d = false;
            switch (dir) {
                case 1: dir = 1; d = false; break;
                case 2: dir = 1; d = true; break;
                case 3: dir = 2; d = true; break;
                case 4: dir = 2; d = false; break;
                default: break;
            }
            // sharks.add(new Shark(r, c, s, z, dir, d));
            sharks[r][c] = new Shark(s, z, dir, d);
        }
    }

    static void moveMan(){
        for(int i = 1; i <= C; i++){
            fishingShark(i);
            Shark[][] temp = new Shark[R+1][C+1];
            for(int j = 1; j < R+1; j++){
                for(int k = 0; k < C+1; k++){
                    if(sharks[j][k] != null){
                        moveShark(temp, sharks[j][k], k, j);
                    }
                }
            }
            sharks = temp;
            
        }
    }

    static void fishingShark(int x){
        for(int i = 1; i < R+1; i++){
            if(sharks[i][x] != null){
                sumFishedSize += sharks[i][x].size;
                sharks[i][x] = null;
                break;
            }
        }
    }

    static void moveShark(Shark[][] temp, Shark s, int x, int y){
        int speed = s.speed;
        int direction = s.direction;
        int locate = 0;
        boolean d = s.d;
        int move = 0;
        if(direction == 1){
            locate = y;
            if(locate == R && d)
                d = !d;
            else if(locate == 1 && !d)
                d = !d;
            move = speed % ((R-1)*2);
        }
        else{
            locate = x;
            if(locate == C && d)
                d = !d;
            else if(locate == 1 && !d)
                d = !d;
            move = speed % ((C-1)*2);
        }
        for(int i = 0; i < move; i++){
            if(d) locate++;
            else locate--;

            if(direction == 1 && d && locate == R) d = !d;
            else if(direction == 1 && !d && locate == 1) d = !d;
            else if(direction == 2 && d && locate == C) d = !d;
            else if(direction == 2 && !d && locate == 1) d = !d;
        }



        s.d = d;
        if(direction == 1){
            if(temp[locate][x] != null){
                if(temp[locate][x].size < s.size){
                    temp[locate][x] = s;
                }
            }
            else{
                temp[locate][x] = s;
            }
        }
        else{
            if(temp[y][locate] != null){
                if(temp[y][locate].size < s.size){
                    temp[y][locate] = s;
                }
            }
            else{
                temp[y][locate] = s;
            }
        }
    }

    static class Shark{
        int speed, size, direction;
        boolean d;
        Shark(int speed, int size, int direction, boolean d){
            this.speed = speed;
            this.size = size;
            this.direction = direction;
            this.d = d;
        }
    }
}
