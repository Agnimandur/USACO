import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("homework.in"));
    int N = Integer.parseInt(br.readLine());
    int[] scores = new int[N];
    int[] prefixSum = new int[N+1];
    int[] minToEnd = new int[N];
    StringTokenizer st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++) {
      scores[i] = Integer.parseInt(st.nextToken());
      prefixSum[i+1] = prefixSum[i] + scores[i];
    }
    minToEnd[N-1] = scores[N-1];
    for (int i = N-2; i >= 0; i--)
      minToEnd[i] = Math.min(scores[i],minToEnd[i+1]);
    br.close();

    double[] grades = new double[N-1];
    double maxGrade = 0.0;
    for (int k = 1; k <= N-2; k++) {
      grades[k] = (prefixSum[N] - prefixSum[k] - minToEnd[k])/(double)(N-k-1);
      maxGrade = Math.max(maxGrade,grades[k]);
    }

    //homework.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("homework.out")));
    for (int k = 1; k <= N-2; k++) {
      if (grades[k] == maxGrade)
        pw.println(k);
    }
    pw.close();
  }
}