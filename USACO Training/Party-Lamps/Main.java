/*
ID: shivara2
LANG: JAVA
TASK: lamps
*/

import java.util.*;
import java.io.*;

class lamps {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("lamps.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lamps.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int C = sc.ni();
    int[] state = new int[N]; //0 is unknown, 1 is off, 2 is on
    for (int i = 2; i >= 1; i--) {
      int num = sc.ni()-1;
      while (num != -2) {
        state[num] = i;
        num = sc.ni()-1;
      }
    }
    ArrayList<String> possible = new ArrayList<String>();
    for (int mask = 0; mask < 16; mask++) {
      int presses = Integer.bitCount(mask);
      if (C >= presses && (C-presses)%2 == 0) {
        boolean[] lamps = new boolean[N];
        Arrays.fill(lamps,true);
        if ((mask&1)>0) {
          //press all the buttons
          for (int i = 0; i < N; i++)
            lamps[i] = !lamps[i];
        }
        if ((mask&2)>0) {
          //press buttons indexed 0,2,4,6,...
          for (int i = 0; i < N; i += 2)
            lamps[i] = !lamps[i];
        }
        if ((mask&4)>0) {
          //press buttons indexed 1,3,5,7,...
          for (int i = 1; i < N; i += 2)
            lamps[i] = !lamps[i];
        }
        if ((mask&8)>0) {
          //press buttons indexed 0,3,6,9,...
          for (int i = 0; i < N; i += 3)
            lamps[i] = !lamps[i];
        }

        boolean good = true; //check if this matches with the input information
        for (int i = 0; i < N; i++) {
          if ((state[i] == 1&&lamps[i]==true)||(state[i] == 2&&lamps[i]==false)) {
            //this case doesn't work
            good = false;
            break;
          }
        }

        if (good) {
          //this is a valid final arrangement of the lamps
          StringBuilder build = new StringBuilder();
          for (boolean lamp: lamps) {
            if (lamp)
              build.append('1');
            else
              build.append('0');
          }
          String ans = build.toString();
          possible.add(ans);
        }
      }
    }

    //print solution
    if (possible.size() == 0) {
      pw.println("IMPOSSIBLE");
    } else {
      Collections.sort(possible);
      for (String str: possible)
        pw.println(str);
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