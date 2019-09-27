import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cardgame.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cardgame.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int[] elsie = new int[N];
    boolean[] present = new boolean[2*N];
    for (int i = 0; i < N; i++) {
      int card = sc.nextInt()-1;
      elsie[i] = card;
      present[card] = true;
    }
    ArrayList<Integer> bessieCards = new ArrayList<Integer>(N);
    for (int i = 0; i < 2*N; i++) {
      if (! present[i])
        bessieCards.add(i);
    }
    Collections.sort(bessieCards);

    TreeSet<Integer> low = new TreeSet<Integer>();
    TreeSet<Integer> high = new TreeSet<Integer>();
    for (int i = 0; i < N/2; i++)
      low.add(bessieCards.get(i));
    for (int i = N/2; i < N; i++)
      high.add(bessieCards.get(i));
    int score = 0;
    for (int i = 0; i < N/2; i++) {
      Integer play = high.higher(elsie[i]);
      if (play == null) {
        high.pollFirst();
      } else {
        high.remove(play);
        score++;
      }
    }
    for (int i = N/2; i < N; i++) {
      Integer play = low.lower(elsie[i]);
      if (play == null) {
        low.pollLast();
      } else {
        low.remove(play);
        score++;
      }
    }
    //System.out.println(score);
    pw.println(score);
    pw.close();
  }

  static class FastScanner { 
    public BufferedReader br; 
    public StringTokenizer st; 
  
    public FastScanner(InputStream is) throws IOException { 
      br = new BufferedReader(new InputStreamReader(is),32768);
      st = null;
    }
  
    public String next() { 
      while (st == null || !st.hasMoreTokens()) { 
        try { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) { 
          throw new RuntimeException(e);
        }
      } 
      return st.nextToken(); 
    } 
  
    public int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    public double nextDouble() { 
      return Double.parseDouble(next()); 
    } 
  
    public String nextLine() { 
      String str = ""; 
      try { 
        str = br.readLine(); 
      } catch (IOException e) { 
        throw new RuntimeException(e);
      } 
      return str; 
    }
  }
}