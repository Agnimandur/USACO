import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("diamond.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int[] diamonds = new int[N];
    for (int i = 0; i < N; i++) {
      diamonds[i] = Integer.parseInt(br.readLine());
    }
    br.close();
    Arrays.sort(diamonds);

    int[] sizeEndingAtI = new int[N];
    sizeEndingAtI[0] = 1;
    int s_i = 0;
    for (int i = 1; i < N; i++) {
      sizeEndingAtI[i] = sizeEndingAtI[i-1] + 1;
      while (diamonds[s_i] + K < diamonds[i]) {
        sizeEndingAtI[i]--;
        s_i++;
      }
    }
    int c1size = 0;
    int c1index = N-1;
    for (int i = N-1; i >= 0; i--) {
      if (sizeEndingAtI[i] > c1size) {
        c1size = sizeEndingAtI[i];
        c1index = i;
      }
    }

    int c2size = 0;
    int c2index = N-1;
    for (int i = N-1; i >= 0; i--) {
      int size = sizeEndingAtI[i];
      if (size > c2size) {
        if (i > c1index && diamonds[i] - K > diamonds[c1index]) {
          c2size = size;
          c2index = i;
        } else if (i < c1index && diamonds[i] + K < diamonds[c1index]) {
          c2size = size;
          c2index = i;
        }
      }
    }

    //diamond.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("diamond.out")));
    pw.println(c1size+c2size);
    pw.close();
  }
}