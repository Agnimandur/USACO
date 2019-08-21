/*
ID: shivara2
LANG: JAVA
TASK: herding
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //herding.in
    BufferedReader br = new BufferedReader(new FileReader("herding.in"));
    int N = Integer.parseInt(br.readLine());
    int[] cows = new int[N];
    for (int i = 0; i < N; i++) {
      cows[i] = Integer.parseInt(br.readLine());
    }
    Arrays.sort(cows);
    br.close();

    //MIN
    int min = 100000;
    for (int i = 0; i < N - 1; i++) {
      int cowsIn = 0;
      for (int j = i; j < N; j++) {
        if (cows[j] < cows[i] + N) {
          cowsIn++;
        } else {
          break;
        }
      }
      min = Math.min(min,N-cowsIn);
    }

    if (min == 1 && N > 3) {
      boolean edgeCase = true;
      for (int i = 2; i < N - 1; i++) {
        if (cows[i] - cows[i-1] != 1) {
          edgeCase = false;
          break;
        }
      }

      if (edgeCase && (cows[1] == cows[0] + 1 || cows[N-1] == cows[N-2] + 1)) {
        if (cows[1] > cows[0] + 2 || cows[N-1] > cows[N-2] + 2) {
          min = 2;
        }
      }
    }

    //MAX
    int max = cows[N- 1] - cows[0] - N + 1;
    max -= (Math.min(cows[1] - cows[0],cows[N - 1] - cows[N - 2]) - 1);

    //herding.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("herding.out")));
    pw.println(min + "");
    pw.println(max + "");
    pw.close();
  }
}