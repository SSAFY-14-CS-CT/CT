import java.util.*;
import java.io.*;

public class Main {
    static char[][] front = { { 'r', 'r', 'r' }, { 'r', 'r', 'r' }, { 'r', 'r', 'r' } };
    static char[][] back = { { 'o', 'o', 'o' }, { 'o', 'o', 'o' }, { 'o', 'o', 'o' } };
    static char[][] top = { { 'w', 'w', 'w' }, { 'w', 'w', 'w' }, { 'w', 'w', 'w' } };
    static char[][] bottom = { { 'y', 'y', 'y' }, { 'y', 'y', 'y' }, { 'y', 'y', 'y' } };
    static char[][] left = { { 'g', 'g', 'g' }, { 'g', 'g', 'g' }, { 'g', 'g', 'g' } };
    static char[][] right = { { 'b', 'b', 'b' }, { 'b', 'b', 'b' }, { 'b', 'b', 'b' } };
    static char[][] copyFront;
    static char[][] copyBack;
    static char[][] copyTop;
    static char[][] copyBottom;
    static char[][] copyLeft;
    static char[][] copyRight;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());
        for (int t = 0; t < n; t++) {
            copy();
            int num = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < num; j++) {
                String str = st.nextToken();
                move(str);
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(copyTop[i][j]);
                }
                sb.append('\n');
            }
        }
        System.out.print(sb);
    }

    public static void copy() {
        copyFront = new char[3][3];
        copyBack = new char[3][3];
        copyTop = new char[3][3];
        copyBottom = new char[3][3];
        copyLeft = new char[3][3];
        copyRight = new char[3][3];
        for (int i = 0; i < 3; i++) {
            copyFront[i] = front[i].clone();
            copyBack[i] = back[i].clone();
            copyTop[i] = top[i].clone();
            copyBottom[i] = bottom[i].clone();
            copyLeft[i] = left[i].clone();
            copyRight[i] = right[i].clone();
        }
    }

    public static void move(String s) {
        char where = s.charAt(0);
        char dir = s.charAt(1);

        switch (where) {
            case 'U': { // U+: F->R->B->L
                char[] temp = copyFront[0].clone();
                if (dir == '+') {
                    copyFront[0] = copyRight[0].clone();
                    copyRight[0] = copyBack[0].clone();
                    copyBack[0] = copyLeft[0].clone();
                    copyLeft[0] = temp;
                    copyTop = rotateClock(copyTop);
                } else {
                    copyFront[0] = copyLeft[0].clone();
                    copyLeft[0] = copyBack[0].clone();
                    copyBack[0] = copyRight[0].clone();
                    copyRight[0] = temp;
                    copyTop = rotateReClock(copyTop);
                }
            }
            break;
            case 'D': { // D+: F->L->B->R
                char[] temp = copyFront[2].clone();
                if (dir == '+') {
                    copyFront[2] = copyLeft[2].clone();
                    copyLeft[2] = copyBack[2].clone();
                    copyBack[2] = copyRight[2].clone();
                    copyRight[2] = temp;
                    copyBottom = rotateClock(copyBottom);
                } else {
                    copyFront[2] = copyRight[2].clone();
                    copyRight[2] = copyBack[2].clone();
                    copyBack[2] = copyLeft[2].clone();
                    copyLeft[2] = temp;
                    copyBottom = rotateReClock(copyBottom);
                }
            }
            break;
            case 'F': { // F+: U->R->D->L (시계방향)
                char[] temp = copyTop[2].clone();
                if (dir == '+') {
                    for (int i = 0; i < 3; i++) copyTop[2][i] = copyLeft[2 - i][2];
                    for (int i = 0; i < 3; i++) copyLeft[i][2] = copyBottom[0][i];
                    for (int i = 0; i < 3; i++) copyBottom[0][i] = copyRight[2 - i][0];
                    for (int i = 0; i < 3; i++) copyRight[i][0] = temp[i];
                    copyFront = rotateClock(copyFront);
                } else {
                    for (int i = 0; i < 3; i++) copyTop[2][i] = copyRight[i][0];
                    for (int i = 0; i < 3; i++) copyRight[i][0] = copyBottom[0][2 - i];
                    for (int i = 0; i < 3; i++) copyBottom[0][i] = copyLeft[i][2];
                    for (int i = 0; i < 3; i++) copyLeft[i][2] = temp[2 - i];
                    copyFront = rotateReClock(copyFront);
                }
            }
            break;
            case 'B': { // B+: U->L->D->R (시계방향)
                char[] temp = copyTop[0].clone();
                if (dir == '+') {
                    for (int i = 0; i < 3; i++) copyTop[0][i] = copyRight[i][2];
                    for (int i = 0; i < 3; i++) copyRight[i][2] = copyBottom[2][2 - i];
                    for (int i = 0; i < 3; i++) copyBottom[2][i] = copyLeft[i][0];
                    for (int i = 0; i < 3; i++) copyLeft[i][0] = temp[2 - i];
                    copyBack = rotateClock(copyBack);
                } else {
                    for (int i = 0; i < 3; i++) copyTop[0][i] = copyLeft[2 - i][0];
                    for (int i = 0; i < 3; i++) copyLeft[i][0] = copyBottom[2][i];
                    for (int i = 0; i < 3; i++) copyBottom[2][i] = copyRight[2 - i][2];
                    for (int i = 0; i < 3; i++) copyRight[i][2] = temp[i];
                    copyBack = rotateReClock(copyBack);
                }
            }
            break;
            case 'L': { // L+: U->F->D->B
                char[] temp = new char[3];
                for (int i = 0; i < 3; i++) temp[i] = copyTop[i][0];

                if (dir == '+') {
                    for (int i = 0; i < 3; i++) copyTop[i][0] = copyBack[2 - i][2];
                    for (int i = 0; i < 3; i++) copyBack[2 - i][2] = copyBottom[i][0];
                    for (int i = 0; i < 3; i++) copyBottom[i][0] = copyFront[i][0];
                    for (int i = 0; i < 3; i++) copyFront[i][0] = temp[i];
                    copyLeft = rotateClock(copyLeft);
                } else {
                    for (int i = 0; i < 3; i++) copyTop[i][0] = copyFront[i][0];
                    for (int i = 0; i < 3; i++) copyFront[i][0] = copyBottom[i][0];
                    for (int i = 0; i < 3; i++) copyBottom[i][0] = copyBack[2 - i][2];
                    for (int i = 0; i < 3; i++) copyBack[2 - i][2] = temp[i];
                    copyLeft = rotateReClock(copyLeft);
                }
            }
            break;
            case 'R': { // R+: U->B->D->F
                char[] temp = new char[3];
                for(int i=0; i<3; i++) temp[i] = copyTop[i][2];

                if (dir == '+') {
                    for(int i=0; i<3; i++) copyTop[i][2] = copyFront[i][2];
                    for(int i=0; i<3; i++) copyFront[i][2] = copyBottom[i][2];
                    for(int i=0; i<3; i++) copyBottom[i][2] = copyBack[2-i][0];
                    for(int i=0; i<3; i++) copyBack[2-i][0] = temp[i];
                    copyRight = rotateClock(copyRight);
                } else {
                    for (int i = 0; i < 3; i++) copyTop[i][2] = copyBack[2-i][0];
                    for (int i = 0; i < 3; i++) copyBack[2-i][0] = copyBottom[i][2];
                    for (int i = 0; i < 3; i++) copyBottom[i][2] = copyFront[i][2];
                    for (int i = 0; i < 3; i++) copyFront[i][2] = temp[i];
                    copyRight = rotateReClock(copyRight);
                }
            }
            break;
        }
    }

    public static char[][] rotateClock(char[][] face) {
        char[][] res = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                res[j][2 - i] = face[i][j];
        return res;
    }

    public static char[][] rotateReClock(char[][] face) {
        char[][] res = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                res[2 - j][i] = face[i][j];
        return res;
    }
}