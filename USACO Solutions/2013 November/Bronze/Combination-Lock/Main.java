import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("combo.in"));
    int N = Integer.parseInt(br.readLine());
    int[] lock = new int[3];
    StringTokenizer st = new StringTokenizer(br.readLine());
    for (int i = 0; i < 3; i++)
      lock[i] = Integer.parseInt(st.nextToken())-1;
    int[] master = new int[3];
    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < 3; i++)
      master[i] = Integer.parseInt(st.nextToken())-1;
    br.close();

    HashSet<ArrayList<Integer>> combos = new HashSet<ArrayList<Integer>>();
    for (int c1 = -2; c1 <= 2; c1++) {
      for (int c2 = -2; c2 <= 2; c2++) {
        for (int c3 = -2; c3 <= 2; c3++) {
          ArrayList<Integer> combo = new ArrayList<Integer>();
          ArrayList<Integer> combo2 = new ArrayList<Integer>();
          combo.add((N + lock[0] + c1)%N);
          combo.add((N + lock[1] + c2)%N);
          combo.add((N + lock[2] + c3)%N);
          combo2.add((N + master[0] + c1)%N);
          combo2.add((N + master[1] + c2)%N);
          combo2.add((N + master[2] + c3)%N);
          combos.add(combo);
          combos.add(combo2);
        }
      }
    }
    //combo.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));
    pw.println(combos.size());
    pw.close();
  }
}