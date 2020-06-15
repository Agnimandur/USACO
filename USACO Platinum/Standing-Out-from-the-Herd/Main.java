import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "standingout";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();

    //read in the strings as numbers (makes working with the suffix array easier)
    String[] input = new String[N];
    ArrayList<Integer> nums = new ArrayList<Integer>();
    for (int i = 0; i < N; i++) {
      input[i] = sc.next();
      for (char c: input[i].toCharArray()) {
        nums.add(c-'a');
      }
      nums.add(-1-i);
    }

    //suffix array work
    SuffixArray sa = new SuffixArray(nums);
    sa.kasai();

    int M = nums.size();
    //the word we are in at a given prefix.
    int[] words = new int[M];
    int[] sizes = new int[M];
    int index = 0;
    for (int i = 0; i < N; i++) {
      for (int j = index; j < index+input[i].length(); j++) {
        words[j] = i;
      }
      index += (input[i].length()+1);
      words[index-1] = -1;
    }

    //size of each suffix
    for (int i = M-1; i >= 0; i--) {
      if (words[i] == -1)
        sizes[i] = 0;
      else
        sizes[i] = sizes[i+1]+1;
    }

    //answer for each word
    long[] ans = new long[N];

    MinQueue mq = new MinQueue();
    for (int i = N; i < M; i++) {
      int ind = sa.order[i];
      if (words[ind] != words[sa.order[i-1]]) {
        //new batch of suffixes belonging to the same word
        while (mq.size() > 0)
          mq.remove();
        int j = i;
        while (j < M && words[sa.order[j]]==words[ind]) {
          mq.add(sa.lcp[j]);
          j++;
        }
      } else {
        mq.remove();
      }
      ans[words[ind]] += sizes[ind];
      ans[words[ind]] -= Math.max(sa.lcp[i-1],mq.queryMin());
    }

    //print out the answer for each word
    for (long a: ans)
      pw.println(a);
    
    //System.out.println(Arrays.toString(ans));
    //System.out.println(Arrays.toString(baseline(input)));
    pw.close();
  }

  static class MinQueue {
    private ArrayDeque<Integer> s1Num;
    private ArrayDeque<Integer> s1Min;
    private ArrayDeque<Integer> s2Num;
    private ArrayDeque<Integer> s2Min;
    private int size;
    
    public MinQueue() {
      s1Num = new ArrayDeque<Integer>();
      s1Min = new ArrayDeque<Integer>();
      s2Num = new ArrayDeque<Integer>();
      s2Min = new ArrayDeque<Integer>();
      size = 0;
    }
    
    public int queryMin() {
      int min;
      if (s1Num.isEmpty()) {
          min = s2Min.peek();
      } else if (s2Num.isEmpty()) {
          min = s1Min.peek();
      } else {
          min = Math.min(s1Min.peek(),s2Min.peek()); 
      }
      return min;
    }
    
    public void add(int n) {
      int min = s1Num.isEmpty()? n : Math.min(n,s1Min.peek());
      s1Num.push(n);
      s1Min.push(min);
      size++;
    }
    
    public void remove() {
      if (s2Num.isEmpty()) {
        while (!s1Num.isEmpty()) {
          int n = s1Num.pop();
          s1Min.pop();
          int min = s2Num.isEmpty() ? n : Math.min(n, s2Min.peek());
          s2Num.push(n);
          s2Min.push(min);
        }
      }
      s2Num.pop();
      s2Min.pop();
      size--;
    }

    public int size() {
      return size;
    }
  }

  static class SuffixArray {
    private Suffix[] sorted; //suffixes in lexigraphical order
    private Suffix[] suffixes; //the suffixes in the order provided by the string.
    private int[] nums;
    private int p;
    private int N;

    //Longest common prefix
    public int[] lcp;

    //Suffixes in Lexographical order
    public int[] order;

    public SuffixArray(String s) {
      nums = new int[s.length()];
      for (int i = 0; i < nums.length; i++)
        nums[i] = s.charAt(i)-'a';
      initSuffixArray();
    }

    public SuffixArray(ArrayList<Integer> vals) {
      nums = new int[vals.size()];
      for (int i = 0; i < nums.length; i++)
        nums[i] = vals.get(i);
      initSuffixArray();
    }

    public void initSuffixArray() {
      N = nums.length;
      sorted = new Suffix[N];
      suffixes = new Suffix[N];

      //Normalize the suffixes on their first letter
      int[][] firstNorm = new int[N][2];
      for (int i = 0; i < N; i++) {
        firstNorm[i][0] = nums[i];
        firstNorm[i][1] = i;
      }
      Arrays.sort(firstNorm, new Comparator<int[]>() {
        @Override
        public int compare(int[] arr1, int[] arr2) {
          return arr1[0]-arr2[0];
        }
      });
      int norm = 0;
      for (int i = 0; i < N; i++) {
        if (i > 0 && firstNorm[i][0]>firstNorm[i-1][0]) {
          norm++;
        }
        Suffix suf = new Suffix(firstNorm[i][1],norm,0);
        sorted[i] = suf;
        suffixes[firstNorm[i][1]] = suf;
      }

      //size of the string (log)
      p = (int)(Math.log(N)/Math.log(2));
      
      //sort the suffixes lexocagraphically!
      for (int i = 0; i < p; i++) {
        //give each suffix its second number
        for (int j = 0; j < N; j++) {
          if (j < N-(1<<i)) {
            suffixes[j].num2 = suffixes[j+(1<<i)].num1;
          } else {
            suffixes[j].num2 = 0;
          }
        }

        //sort the suffixes
        Arrays.sort(sorted);

        //normalize the suffixes
        norm = 0;
        int[] newNorm = new int[N];
        for (int j = 0; j < N; j++) {
          if (j > 0 && sorted[j].compareTo(sorted[j-1]) > 0) {
            norm++;
          }
          newNorm[j] = norm;
        }
        for (int j = 0; j < N; j++) {
          suffixes[sorted[j].index].num1 = newNorm[j];
        }
      }

      //Public output values
      order = new int[N];
      for (int i = 0; i < N; i++)
        order[i] = sorted[i].index;
      lcp = new int[N];
    }

    public void kasai() {
      //Run Kasai's LCP algorithm
      int[] sufInv = new int[N];
      for (int i = 0; i < N; i++) {
        sufInv[order[i]] = i;
      }
      int k = 0;
      for (int i = 0; i < N; i++) {
        if (sufInv[i] == N-1) {
          k = 0;
          continue;
        }

        int j = order[sufInv[i]+1];
        while (i+k<N && j+k < N && nums[i+k]==nums[j+k]) {
          k++;
        }
        lcp[sufInv[i]] = k;
        if (k>0)
          k--;
      }
    }

    static class Suffix implements Comparable<Suffix> {
      public int index;
      public int num1;
      public int num2;

      public Suffix(int i, int n1, int n2) {
        index = i;
        num1 = n1;
        num2 = n2;
      }

      public String toString() {
        //return ("("+index + ","+num1+","+num2+")");
        return Integer.toString(index);
      }

      public int compareTo(Suffix s) {
        if (num1 != s.num1) {
          return (num1-s.num1);
        } else {
          return (num2-s.num2);
        }
      }
    }
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