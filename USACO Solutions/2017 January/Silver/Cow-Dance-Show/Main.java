/*
ID: shivara2
LANG: JAVA
TASK: cowdance
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //cowdance.in
    BufferedReader br = new BufferedReader(new FileReader("cowdance.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int Tmax = Integer.parseInt(st.nextToken());
    int[] dancers = new int[N];
    for (int i = 0; i < N; i++) {
      dancers[i] = Integer.parseInt(br.readLine());
    }

    int minStageSize = N;
    for (int K = 1; K < N + 1; K++) {
      int time = dance(K,dancers,Tmax);
      if (time <= Tmax) {
        minStageSize = K;
        break;
      }
    }

    //cowdance.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowdance.out")));
    pw.println(minStageSize);
    pw.close();
  }

  public static int dance(int K, int[] dancers, int Tmax) {
    PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
    for (int i = 0; i < K; i++) {
      pq.add(dancers[i]);
    }
    for (int i = K; i < dancers.length; i++) {
      int firstDone = pq.poll() + dancers[i];
      if (firstDone <= Tmax) {
        pq.add(firstDone);
      } else {
        return Tmax+1;
      }
    }

    while (pq.size() > 1) {
      pq.poll();
    }
    //pq.poll() is now the maximum
    return pq.poll();
  }
}