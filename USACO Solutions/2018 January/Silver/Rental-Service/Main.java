import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("rental.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    int R = Integer.parseInt(st.nextToken());
    Integer[] milkOutput = new Integer[N];
    int[][] stores = new int[M][2];
    int[] rents = new int[R];
    for (int i = 0; i < N; i++)
      milkOutput[i] = Integer.parseInt(br.readLine());
    Arrays.sort(milkOutput,Collections.reverseOrder());
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      stores[i][0] = Integer.parseInt(st.nextToken());
      stores[i][1] = Integer.parseInt(st.nextToken());
    }
    Arrays.sort(stores, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr2[1]-arr1[1];
      }
    });
    for (int i = 0; i < R; i++)
      rents[i] = Integer.parseInt(br.readLine());
    Arrays.sort(rents);

    long[] nRents = new long[N+1];
    nRents[0] = 0;
    for (int i = 1; i <= N; i++) {
      if (i <= R)
        nRents[i] = nRents[i-1] + (long)rents[R-i];
      else
        nRents[i] = nRents[i-1];
    }

    long[] nMilks = new long[N+1];
    nMilks[0] = 0;
    long milkLeft = (long)milkOutput[0];
    long milkWanted = (long)stores[0][0];
    int leftIndex = 0;
    int wantedIndex = 0;
    while (true) {
      if (milkLeft > milkWanted) {
        milkLeft -= milkWanted;
        nMilks[leftIndex+1] += (milkWanted * stores[wantedIndex][1]);
        wantedIndex++;
        if (wantedIndex == M) break;
        milkWanted = (long)stores[wantedIndex][0];
      } else if (milkLeft < milkWanted) {
        milkWanted -= milkLeft;
        nMilks[leftIndex+1] += (milkLeft * stores[wantedIndex][1]);
        leftIndex++;
        if (leftIndex == N) break;
        milkLeft = (long)milkOutput[leftIndex];
      } else {
        nMilks[leftIndex+1] += (milkLeft * stores[wantedIndex][1]);
        leftIndex++;
        wantedIndex++;
        if (leftIndex == N || wantedIndex == M) break;
        milkLeft = (long)milkOutput[leftIndex];
        milkWanted = (long)stores[wantedIndex][0];
      }
    }
    for (int i = 1; i <= N; i++)
      nMilks[i] += nMilks[i-1];
    
    
    //Calculate Max Profit
    long maxProfit = 0;
    for (int i = 0; i <= N; i++) {
      long profit = nRents[i] + nMilks[N-i];
      maxProfit = Math.max(maxProfit,profit);
    }
    
    //rental.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("rental.out")));
    pw.println(maxProfit);
    pw.close();
  }
}