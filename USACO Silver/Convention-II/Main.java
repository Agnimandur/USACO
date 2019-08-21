import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("convention2.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] cows = new int[N][3];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      cows[i][0] = Integer.parseInt(st.nextToken());
      cows[i][1] = Integer.parseInt(st.nextToken());
      cows[i][2] = N-i;
    }
    Arrays.sort(cows, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        if (arr1[0] - arr2[0] != 0) {
          return arr1[0]-arr2[0];
        } else {
          return arr2[2]-arr1[2];
        }
      }
    });
    br.close();

    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return b[2] - a[2];
      }
    });

    int maxTime = 0;
    int time = 0;
    int index = 0;
    while (true) {
      if (index < N && cows[index][0] <= time) {
        pq.add(cows[index]);
        index++;
        continue;
      }

      if (index < N && pq.isEmpty()) {
        pq.add(cows[index]);
        time = cows[index][0];
        index++;
        continue;
      }

      if (! pq.isEmpty()) {
        int[] cow = pq.poll();
        maxTime = Math.max(maxTime,time - cow[0]);
        time += cow[1];
        continue;
      }

      break;
    }

    //convention2.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("convention2.out")));
    pw.println(maxTime);
    pw.close();
  }
}