import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("snowboots.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());

    st = new StringTokenizer(br.readLine());
		int[] snow = new int[N];
		for (int i = 0; i < N; i++)
			snow[i] = Integer.parseInt(st.nextToken());
		int[][] boots = new int[B][2];
		for (int i = 0; i < B; i++) {
      st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 2; j++)
				boots[i][j] = Integer.parseInt(st.nextToken());
		}
		boolean[] visited = new boolean[N];
		visited[0] = true;
		int[][] dp = new int[B][N+1];
		for (int i = 0; i < B; i++) {
			dp[i][1] = 1;
		}

		for (int i = 0; i < B; i++) {
			int[] boot = boots[i];
			for (int j = 2; j <= N; j++) {
				if (snow[j-1] > boot[0]) {
					dp[i][j] = dp[i][j-1];
				} else if (dp[i][j-1] - dp[i][Math.max(0,j-boot[1]-1)] > 0) {
					dp[i][j] = dp[i][j-1] + 1;
					visited[j-1] = true;
				} else if (visited[j-1]) {
					dp[i][j] = dp[i][j-1] + 1;
				} else {
					dp[i][j] = dp[i][j-1];
				}
			}
		}

    //snowboots.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
		for (int i = 0; i < B; i++) {
			if (dp[i][N] > dp[i][N-1]) {
				pw.println(i);
				break;
			}
		}
    pw.close();
	}
}