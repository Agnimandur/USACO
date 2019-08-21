/*
ID: shivara2
LANG: JAVA
TASK: haybales
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //haybales.in
    BufferedReader br = new BufferedReader(new FileReader("haybales.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int Q = Integer.parseInt(st.nextToken());
    //Populate the List Haybales.
    int[] haybales = new int[N];
    HashSet<Integer> haySet = new HashSet<Integer>();
    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++) {
      haybales[i] = Integer.parseInt(st.nextToken());
      haySet.add(haybales[i]);
    }
    Arrays.sort(haybales);

    //haybales.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("haybales.out")));
    for (int i = 0; i < Q; i++) {
      st = new StringTokenizer(br.readLine());
      int q1 = Integer.parseInt(st.nextToken());
      int q2 = Integer.parseInt(st.nextToken());
      if (haySet.contains(q1)) {
        pw.println(returnIndex(haybales,q2) - returnIndex(haybales,q1) + 1);
      } else {
        pw.println(returnIndex(haybales,q2) - returnIndex(haybales,q1));
      }
    }
    br.close();
    pw.close();
  }

  public static int returnIndex(int[] haybales, int num) {
    int start = 0;
    int end = haybales.length - 1;
    int mid;
    while (start <= end) {
      mid = (start + end)/2;
      if (num == haybales[mid]) {
        return mid;
      } if (num < haybales[mid]) {
        end = mid-1;
      } else {
        start = mid+1;
      }
    }

    //Edge case
    if (num < haybales[0]) {
      return -1;
    } else {
      return (start+end)/2;
    }
  }
}