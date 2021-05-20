import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("nocow.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken())-1;
    st = new StringTokenizer(br.readLine());
    int numAdj = st.countTokens()-5;
    String[][] missing = new String[numAdj][N];
    for (int i = 0; i < numAdj+5; i++) {
      String adj = st.nextToken();
      if (i > 3 && i <= 3 + numAdj) {
        missing[i-4][0] = adj;
      }
    }
    for (int col = 1; col < N; col++) {
      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < numAdj+5; i++) {
        String adj = st.nextToken();
        if (i > 3 && i <= 3 + numAdj) {
          missing[i-4][col] = adj;
        }
      }
    }
    br.close();
    ArrayList<ArrayList<String>> adjectives = new ArrayList<ArrayList<String>>();
    for (String[] adjTypes: missing) {
      List<String> settings = Arrays.asList(adjTypes);
      HashSet<String> uniqueSettings = new HashSet<>(settings);
      ArrayList<String> list = new ArrayList<String>(uniqueSettings);
      adjectives.add(list);
    }
    for (int i = 0; i < numAdj; i++) {
      Collections.sort(adjectives.get(i));
    }

    int[] weights = new int[numAdj];
    weights[numAdj-1] = 1;
    for (int i = numAdj-2; i >= 0; i--)
      weights[i] = weights[i+1] * adjectives.get(i+1).size();

    int[] missingPos = new int[N+1];
    for (int col = 0; col < N; col++) {
      String[] cow = new String[numAdj];
      for (int row = 0; row < numAdj; row++) {
        cow[row] = missing[row][col];
      }
      int pos = 0;
      for (int i = 0; i < numAdj; i++) {
        pos += (weights[i] * adjectives.get(i).indexOf(cow[i]));
      }
      missingPos[col] = pos;
    }
    int totalSettings = weights[0] * adjectives.get(0).size();
    missingPos[N] = totalSettings;
    Arrays.sort(missingPos);

    int answer = -1;
    for (int i = 1; i <= N+1; i++) {
      int gap = missingPos[i-1] - i - K;
      if (gap >= 0) {
        answer = missingPos[i-1] - (gap+1);
        break;
      }
    }

    //nocow.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nocow.out")));
    int index = 0;
    for (ArrayList<String> setting: adjectives) {
      int val = answer / weights[index];
      String s = setting.get(val);
      if (index < numAdj-1)
        pw.print(s + " ");
      else
        pw.println(s);
      answer -= (val * weights[index]);
      index++;
    }
    pw.close();
  }
}