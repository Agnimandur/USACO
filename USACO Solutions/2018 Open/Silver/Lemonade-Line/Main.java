import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("lemonade.in"));
    int N = Integer.parseInt(br.readLine());
    StringTokenizer st = new StringTokenizer(br.readLine());
    Integer[] waits = new Integer[N];
    for (int i = 0; i < N; i++) {
      waits[i] = Integer.parseInt(st.nextToken());
    }
    br.close();
    Arrays.sort(waits, Collections.reverseOrder());
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lemonade.out")));
	int ans = N;
    for (int i = 0; i < N; i++) {
      if (waits[i] < i) {
		ans = i;
        break;
      }
    }
	pw.println(ans);
    pw.close();
  }
}