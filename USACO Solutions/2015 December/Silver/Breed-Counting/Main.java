import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("bcount.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("bcount.out")));
    int N = Integer.parseInt(st.nextToken());
    int Q = Integer.parseInt(st.nextToken());
    int[] holsteins = new int[N+1];
    int[] guernseys = new int[N+1];
    int[] jerseys = new int[N+1];
    for (int i = 1; i <= N; i++) {
      int cow = Integer.parseInt(br.readLine());
      if (cow == 1) {
        holsteins[i] = holsteins[i-1] + 1;
        guernseys[i] = guernseys[i-1] + 0;
        jerseys[i] = jerseys[i-1] + 0;
      } else if (cow == 2) {
        holsteins[i] = holsteins[i-1] + 0;
        guernseys[i] = guernseys[i-1] + 1;
        jerseys[i] = jerseys[i-1] + 0;
      } else {
        holsteins[i] = holsteins[i-1] + 0;
        guernseys[i] = guernseys[i-1] + 0;
        jerseys[i] = jerseys[i-1] + 1;
      }
    }
    for (int i = 0; i < Q; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());

      int h = holsteins[b] - holsteins[a-1];
      int g = guernseys[b] - guernseys[a-1];
      int j = jerseys[b] - jerseys[a-1];
      pw.println(h + " " + g + " " + j);
    }

    br.close();
    pw.close();
  }
}