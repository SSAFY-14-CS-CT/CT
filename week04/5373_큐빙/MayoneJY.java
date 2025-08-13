
import java.util.*;
import java.io.*;

public class MayoneJY {
    static int N;
    static char[][] cubeU = new char[3][3], cubeF = new char[3][3], cubeD = new char[3][3],
        cubeB = new char[3][3], cubeL = new char[3][3], cubeR = new char[3][3];
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        for(int n = 0; n < N; n++){
            for(int i = 0; i < 3; i++){
                Arrays.fill(cubeU[i], 'w');
                Arrays.fill(cubeF[i], 'r');
                Arrays.fill(cubeD[i], 'y');
                Arrays.fill(cubeB[i], 'o');
                Arrays.fill(cubeL[i], 'g');
                Arrays.fill(cubeR[i], 'b');
            }
            String[] order = br.readLine().split(" ");
            for(String o : order){
                boolean direction = false;
                if(o.charAt(1) == '+') direction = true;
                switch (o.charAt(0)) {
                    case 'U':
                        rotate(!direction, direction, 0, 'R');
                        break;
                    case 'D':
                        rotate(direction, direction, 2, 'R');
                        break;
                    case 'F':
                        rotate(direction, direction, 2, 'F');
                        break;
                    case 'B':
                        rotate(!direction, direction, 0, 'F');
                        break;
                    case 'L':
                        rotate(direction, direction, 0, 'C');
                        break;
                    case 'R':
                        rotate(!direction, direction, 2, 'C');
                        break;
                    default:
                        break;
                }
            }

            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    System.out.print(cubeU[i][j]);
                }
                System.out.println();
            }
        }
    }

    static void rotate(boolean colDirection, boolean sideDirection, int lineNum, char metrix){
        char[] line = new char[12];
        char[][][] tempCube;
        if(metrix == 'R') tempCube = new char[][][]{rotateR(cubeL, 1), rotateR(cubeF, 1), rotateR(cubeR, 1), rotateL(cubeB, 1)};
        else if(metrix == 'C') tempCube = new char[][][]{cubeU, cubeF, cubeD, cubeB};
        else tempCube = new char[][][]{rotateR(cubeU, 1), rotateR(cubeR, 2), rotateL(cubeD, 1), cubeL};
        int idx = 0;
        for(char[][] c : tempCube){
            for(int i = 0; i < 3; i++){
                line[idx] = c[i][lineNum];
                idx++;
            }
        }
        for(int n = 0; n < 3; n++){
            if(colDirection){
                char temp = line[11];
                for(int i = 11; i >= 1; i--){
                    line[i] = line[i-1];
                }
                line[0] = temp;
            }
            else{
                char temp = line[0];
                for(int i = 1; i < 12; i++){
                    line[i-1] = line[i];
                }
                line[11] = temp;

            }
        }
        char[][] cube;
        if(metrix == 'R'){
            if(lineNum == 0)
                cube = cubeU;
            else
                cube = cubeD;
        }
        else if(metrix == 'C'){
            if(lineNum == 0)
                cube = cubeL;
            else
                cube = cubeR;
        }
        else{
            if(lineNum == 0)
                cube = cubeB;
            else
                cube = cubeF;
        }
        if(sideDirection)
            rotateL(cube, 1);
        else
            rotateR(cube, 1);
        idx = 0;
        for(char[][] c : tempCube){
            for(int i = 0; i < 3; i++){
                c[i][lineNum] = line[idx];
                idx++;
            }
        }
        if(metrix == 'R'){
            rotateL(cubeL, 1);
            rotateL(cubeF, 1);
            rotateL(cubeR, 1);
            rotateR(cubeB, 1);
        }
        else if(metrix == 'F'){
            rotateL(cubeU, 1);
            rotateL(cubeR, 2);
            rotateR(cubeD, 1);
        }
    }


    static char[][] rotateR(char[][] cube, int count){
        for(int c = 0; c < count; c++){
            for(int i = 0; i < 2; i++){
                char temp = cube[0][0];
                cube[0][0] = cube[0][1];
                cube[0][1] = cube[0][2];
                cube[0][2] = cube[1][2];
                cube[1][2] = cube[2][2];
                cube[2][2] = cube[2][1];
                cube[2][1] = cube[2][0];
                cube[2][0] = cube[1][0];
                cube[1][0] = temp;
            }
        }
        return cube;
    }

    static char[][] rotateL(char[][] cube, int count){
        for(int c = 0; c < count; c++){
            for(int i = 0; i < 2; i++){
            char temp = cube[0][0];
            cube[0][0] = cube[1][0];
            cube[1][0] = cube[2][0];
            cube[2][0] = cube[2][1];
            cube[2][1] = cube[2][2];
            cube[2][2] = cube[1][2];
            cube[1][2] = cube[0][2];
            cube[0][2] = cube[0][1];
            cube[0][1] = temp;
            }
        }
        return cube;
    }
}
