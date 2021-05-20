import java.util.*;
import java.io.*;

class Main {
  public static final String TASK = "tracing";
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASK+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASK+".out")));
    FastScanner sc = new FastScanner(is);

    if (TASK.equals("socdist1")) {
      //socdist1 solution
      int N = sc.ni();
      String s = sc.next();
      boolean allEmpty = true;
      //Current min D without new cows
      int cur = Integer.MAX_VALUE;
      int last = -1;
      for (int i = 0; i < N; i++) {
        if (s.charAt(i)=='1') {
          if (last >= 0)
            cur = Math.min(cur,i-last);
          last = i;
          allEmpty = false;
        }
      }
      ArrayList<Integer> open = new ArrayList<Integer>();
      int empty = 0;
      for (int i = 0; i < N; i++) {
        if (s.charAt(i)=='0') {
          empty++;
        } else {
          open.add(empty);
          empty = 0;
        }
      }
      open.add(empty);
      //Boundary regions
      int b1 = open.get(0);
      int b2 = 0;
      if (open.size() > 1) {
        b1 = Math.max(open.get(open.size()-1),open.get(0));
        b2 = Math.min(open.get(open.size()-1),open.get(0));
      }

      //Inner regions
      int i1 = 0;
      int i2 = 0;
      for (int i = 1; i < open.size()-1; i++) {
        int size = open.get(i);
        if (size > i1) {
          i2 = i1;
          i1 = size;
        } else if (size > i2) {
          i2 = size;
        }
      }

      int best = 0;

      //Case 1: put both new cows into the same region.
      //Put them both into a boundary region
      best = Math.max(best,b1/2);
      //Put them both into an inner region
      best = Math.max(best,(i1+1)/3);

      //Case 2: put both cows into different regions
      ArrayList<Integer> space = new ArrayList<Integer>();
      space.add(b1);
      space.add(b2);
      space.add((i1+1)/2);
      space.add((i2+1)/2);
      Collections.sort(space,Collections.reverseOrder());

      best = Math.max(best,space.get(1));

      int ans = Math.min(best,cur);
      if (allEmpty)
        ans = s.length()-1;
      pw.println(ans);
    } else if (TASK.equals("socdist2")) {
      int N = sc.ni();
      int[][] cows = new int[N][2];
      for (int i = 0; i < N; i++) {
        cows[i][0] = sc.ni();
        cows[i][1] = sc.ni();
      }
      Arrays.sort(cows, new Comparator<int[]>() {
        @Override
        public int compare(int[] arr1, int[] arr2) {
          return arr1[0]-arr2[0];
          //Ascending order.
        }
      });
      int R = Integer.MAX_VALUE;
      for (int i = 1; i < N; i++) {
        if (cows[i-1][1] != cows[i][1]) {
          //COWVID-19 didn't make the jump from cow i-1 to i.
          R = Math.min(R,cows[i][0]-cows[i-1][0]-1);
        }
      }

      ArrayList<Integer> infected = new ArrayList<Integer>();
      for (int i = 0; i < N; i++) {
        if (cows[i][1] == 1) {
          infected.add(cows[i][0]);
        }
      }

      int ans = 1;
      for (int i = 1; i < infected.size(); i++) {
        if (infected.get(i) - infected.get(i-1) > R) {
          //the disease couldn't have made the jump. There must be an extra "initial victim".
          ans++;
        }
      }

      pw.println(ans);
    } else if (TASK.equals("tracing")) {
      int N = sc.ni();
      int T = sc.ni();
      String s = sc.next();
      int[][] log = new int[T][3];
      for (int i = 0; i < T; i++) {
        log[i][0] = sc.ni(); //time
        log[i][1] = sc.ni()-1; //x
        log[i][2] = sc.ni()-1; //y
      }
      Arrays.sort(log, new Comparator<int[]>() {
        @Override
        public int compare(int[] arr1, int[] arr2) {
          return arr1[0]-arr2[0];
          //Ascending order.
        }
      });

      int patientZero = 0;
      int minK = Integer.MAX_VALUE;
      int maxK = 0;
      for (int p = 0; p < N; p++) {
        boolean zero = false;
        for (int k = 0; k <= T; k++) {
          int[] infected = new int[N]; //number of shakes that cow i has done after being infected. -1 means not infected.
          Arrays.fill(infected,-1);
          infected[p] = 0;
          for (int[] entry: log) {
            boolean cow1carry = false;
            if (infected[entry[1]] >= 0 && infected[entry[1]] < k)
              cow1carry = true;
            boolean cow2carry = false;
            if (infected[entry[2]] >= 0 && infected[entry[2]] < k)
              cow2carry = true;
            if (cow1carry || cow2carry) {
              //disease spreads
              infected[entry[1]] += 1;
              infected[entry[2]] += 1;
            }
          }

          boolean consistent = true;
          for (int i = 0; i < N; i++) {
            if ((infected[i] == -1 && s.charAt(i)=='1')||(infected[i] >= 0 && s.charAt(i)=='0')) {
              consistent = false;
              break;
            }
          }

          if (consistent) {
            zero = true;
            minK = Math.min(minK,k);
            maxK = Math.max(maxK,k);
          }
        }
        if (zero) {
          patientZero++;
        }
      }

      if (maxK == T) {
        pw.println(patientZero + " " + minK + " Infinity");
      } else {
        pw.println(patientZero + " " + minK + " " + maxK);
      }
    }

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
  
    public int ni() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nl() { 
      return Long.parseLong(next()); 
    } 
  
    public double nd() { 
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