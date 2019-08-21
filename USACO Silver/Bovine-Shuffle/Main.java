import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("shuffle.in"));
    int N = Integer.parseInt(br.readLine());
    StringTokenizer st = new StringTokenizer(br.readLine());
    int[] shuffle = new int[N+1];
    for (int i = 1; i <= N; i++) {
      shuffle[i] = Integer.parseInt(st.nextToken());
    }
    int[] cows = new int[N+1];
    for (int i = 1; i <= N; i++)
      cows[i] = 1;
    for (int i = 0; i < 100000000/N; i++) {
      cows = shuffle(cows,shuffle);
    }

    int ones = 0;
    for (int i = 1; i <= N; i++) {
      if (cows[i] > 0)
        ones++;
    }
    //shuffle.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));
    pw.println(ones);
    pw.close();
  }

  public static int[] shuffle(int[] cows, int[] shuffle) {
    int[] ans = new int[cows.length];
    for (int i = 1; i < shuffle.length; i++) {
      //i goes to shuffle[i]
      ans[shuffle[i]] += cows[i];
    }
    return ans;
  }
}