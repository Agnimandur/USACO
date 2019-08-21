/*
ID: shivara2
LANG: JAVA
TASK: planting
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //planting.in
    BufferedReader br = new BufferedReader(new FileReader("planting.in"));
    int N = Integer.parseInt(br.readLine());
    int[] degrees = new int[N];
    for (int i = 0; i < N-1; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      degrees[a-1] = degrees[a-1] + 1;
      degrees[b-1] = degrees[b-1] + 1;
    }
    br.close();

    int types = 0;
    for (int degree: degrees) {
      types = Math.max(types,degree);
    }

    //planting.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("planting.out")));
    pw.println(types + 1);
    pw.close();
  }
}