import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("reduce.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] cows = new int[N][2];
    int[] xs = new int[N];
    int[] ys = new int[N];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      xs[i] = Integer.parseInt(st.nextToken());
      ys[i] = Integer.parseInt(st.nextToken());
      cows[i][0] = xs[i];
      cows[i][1] = ys[i];
    }
    br.close();
    Arrays.sort(xs);
    Arrays.sort(ys);
    int[] minXs = {xs[0],xs[1],xs[2],xs[3]};
    int[] maxXs = {xs[N-1],xs[N-2],xs[N-3],xs[N-4]};
    int[] minYs = {ys[0],ys[1],ys[2],ys[3]};
    int[] maxYs = {ys[N-1],ys[N-2],ys[N-3],ys[N-4]};

    int minArea = 1600000000;
    for (int minX: minXs) {
      for (int maxX: maxXs) {
        for (int minY: minYs) {
          for (int maxY: maxYs) {
            if (maxX < minX || maxY < minY)
              continue;
            int area = (maxX - minX) * (maxY - minY);
            int numCowsOutsideRec = 0;
            for (int[] cow: cows) {
              if (cow[0] > maxX || cow[0] < minX || cow[1] > maxY || cow[1] < minY)
                numCowsOutsideRec++;
            }

            if (numCowsOutsideRec <= 3)
              minArea = Math.min(minArea,area);
          }
        }
      }
    }
    //reduce.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("reduce.out")));
    pw.println(minArea);
    pw.close();
  }
}