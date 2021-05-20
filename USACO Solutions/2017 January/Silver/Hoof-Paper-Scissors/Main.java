/*
ID: shivara2
LANG: JAVA
TASK: hps
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //hps.in
    BufferedReader br = new BufferedReader(new FileReader("hps.in"));
    int N = Integer.parseInt(br.readLine());
    int[] cumHoof = new int[N];
    int[] cumPaper = new int[N];
    int[] cumScissor = new int[N];
    for (int i = 0; i < N; i++) {
      String play = br.readLine();
      if (i > 0) {
        cumHoof[i] = cumHoof[i-1];
        cumPaper[i] = cumPaper[i-1];
        cumScissor[i] = cumScissor[i-1];
      }

      if (play.equals("H")) {
        cumHoof[i] += 1;
      } else if (play.equals("P")) {
        cumPaper[i] += 1;
      } else {
        cumScissor[i] += 1;
      }
    }
    br.close();

    //Iterate through every break point
    int totalMax = 0;
    for (int i = 1; i < N; i++) {
      int hoofBefore = cumHoof[i-1];
      int paperBefore = cumPaper[i-1];
      int scissorBefore = cumScissor[i-1];
      int maxBefore = Math.max(hoofBefore,Math.max(paperBefore,scissorBefore));

      int hoofAfter = cumHoof[N-1] - cumHoof[i-1];
      int paperAfter = cumPaper[N-1] - cumPaper[i-1];
      int scissorAfter = cumScissor[N-1] - cumScissor[i-1];
      int maxAfter = Math.max(hoofAfter,Math.max(paperAfter,scissorAfter));

      totalMax = Math.max(totalMax,maxBefore+maxAfter);
    }

    //hps.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
    pw.println(totalMax);
    pw.close();
  }
}