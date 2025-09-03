import java.io.*;
import java.util.*;

public class problem3 {
    static int answer;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            answer = Integer.MAX_VALUE;
            int N = Integer.parseInt(br.readLine()); //집 개수
            ArrayList<House> houses = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) + 15;
                int y = Integer.parseInt(st.nextToken()) + 15;
                int d = Integer.parseInt(st.nextToken()); //허용 범위
                houses.add(new House(x, y, d));
            }

            //1. 하나로 커버가능한가?
            for (int i = 0; i <= 30; i++) {
                here2:
                for (int j = 0; j <= 30; j++) {
                    int temp = 0;
                    int count = 0;
                    //전체 집 순회
                    for (House house : houses) {
                        if (house.x == i && house.y == j) break here2;
                        int distance = Math.abs(house.x - i) + Math.abs(house.y - j);
                        if (distance <= house.dist) {
                            count++;
                            temp += distance;
                        } else {
                            temp = 0;
                            break;
                        }
                    }
                    if (count == N) {
                        answer = Math.min(answer, temp);
                    }
                }
            }
            //2. 두개로 커버해보기 (모든 조합) -> 얘도 없으면 -1
            if (answer == Integer.MAX_VALUE) {
                for (int i = 0; i <= 30; i++) {
                    here1 :
                    for (int j = 0; j <= 30; j++) {
                        for (int k = 0; k <= 30; k++) {
                            here2:
                            for (int l = 0; l <= 30; l++) {
                                //충전소 겹칠때
                                if (i == k && j == l) continue;
                                int temp = 0;
                                int count = 0;
                                for (House house : houses) {
                                    //집이랑 충전소 겹칠때
                                    if (house.x == i && house.y == j) {
                                        continue here1;
                                    }
                                    if (house.x == k && house.y == l) {
                                        continue here2;
                                    }
                                    int best = Math.min(Math.abs(house.x - i) + Math.abs(house.y - j), Math.abs(house.x - k) + Math.abs(house.y - l));
                                    if (best <= house.dist) {
                                        temp += best;
                                        count++;
                                    } else {
                                        temp = 0;
                                        break;
                                    }
                                }
                                if (count == N) {
                                    answer = Math.min(answer, temp);
                                }
                            }
                        }
                    }
                }
            }
            if (answer == Integer.MAX_VALUE) {
                answer = -1;
            }
            System.out.println("#" + t + " " + answer);
        }
    }

    public static class House {
        int x, y, dist;
        House (int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.dist = value;
        }
    }
}
