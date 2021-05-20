import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("convention.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    int C = Integer.parseInt(st.nextToken());
    int[] times = new int[N];
    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++) {
      times[i] = Integer.parseInt(st.nextToken());
    }
    br.close();
    Arrays.sort(times);
    int left = 0;
    int right = times[times.length - 1] - times[0];
    while (true) {
      if (left >= right-1)
        break;
      int mid = (left + right)/2;
      if (feasibleRange(N,M,C,mid,times))
        right = mid;
      else
        left = mid;
    }
    int ans = right;
    if (feasibleRange(N,M,C,left,times))
      ans = left;

    //convention.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("convention.out")));
    pw.println(ans);
    pw.close();
  }

  public static boolean feasibleRange(int N, int M, int C, int wait, int[] times) {
    int startIndex = 0;
    int buses = 1;
    int i = 0;
    while (i < N) {
      if ((i - startIndex) == (C - 1) && (times[i] - times[startIndex]) <= wait && i < N-1) {
        startIndex = i+1;
        buses++;
        i++;
      } else if (times[i] - times[startIndex] > wait) {
        startIndex = i;
        buses++;
      } else {
        i++;
      }
    }

    return (buses <= M);
  }
}