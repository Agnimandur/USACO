import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("milktemp.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int X = Integer.parseInt(st.nextToken());
    int Y = Integer.parseInt(st.nextToken());
    int Z = Integer.parseInt(st.nextToken());
    int[][] events = new int[2*N][2];
    long milk = N * (long)X;
    long maxMilk = N * (long)X;

    for (int i = 0; i < 2*N; i+=2) {
      st = new StringTokenizer(br.readLine());
      int beginJR = Integer.parseInt(st.nextToken());
      int beginH = Integer.parseInt(st.nextToken())+1;
      events[i][0] = beginJR;
      events[i][1] = 0;
      events[i+1][0] = beginH;
      events[i+1][1] = 1;
    }
    br.close();
    Arrays.sort(events, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[0]-arr2[0];
      }
    });
    int time = 0;
    for (int[] event: events) {
      if (time < event[0]) {
        maxMilk = Math.max(maxMilk,milk);
        time = event[0];
      }
      if (event[1] == 0)
        milk += (Y-X);
      else
        milk -= (Y-Z);
    }
    //milktemp.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milktemp.out")));
    pw.println(maxMilk);
    pw.close();
  }
}