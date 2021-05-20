import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("lifeguards.in"));
    int N = Integer.parseInt(br.readLine());
    int[][] events = new int[2*N][3];
    for (int i = 0; i < 2*N; i+=2) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int begin = Integer.parseInt(st.nextToken());
      int end = Integer.parseInt(st.nextToken());
      events[i][0] = begin;
      events[i][1] = 1;
      events[i][2] = i/2;
      events[i+1][0] = end;
      events[i+1][1] = -1;
      events[i+1][2] = i/2;
    }
    Arrays.sort(events, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        // one way
        return arr1[0]-arr2[0];
      }
    });
    int[] timeLostIfFired = new int[N];
    HashSet<Integer> guards = new HashSet<Integer>();
    int timeWorking = 0;
    int time = 0;
    for (int[] event: events) {
      int newTime = event[0];
      if (guards.size() == 1) {
        for (int num: guards) {
          timeLostIfFired[num] += (newTime - time);
        }
      }
      if (! guards.isEmpty())
        timeWorking += (newTime - time);

      if (event[1] == 1) {
        guards.add(event[2]);
      } else {
        guards.remove(event[2]);
      }
      time = newTime;
    }
    Arrays.sort(timeLostIfFired);
    int fireTime = timeWorking - timeLostIfFired[0];

    //lifeguards.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lifeguards.out")));
    pw.println(fireTime);
    pw.close();
  }
}