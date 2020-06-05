public class SuffixArray {
  public Suffix[] sorted; //suffixes in lexigraphical order
  public Suffix[] suffixes; //the suffixes in the order provided by the string.
  public int[] nums;
  public int p;
  public int N;

  //Longest common prefix
  public int[] lcp;

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
    lcp = new int[N];

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
  }

  public void kasai() {
    //Run Kasai's LCP algorithm
    int[] sufInv = new int[N];
    for (int i = 0; i < N; i++) {
      sufInv[sorted[i].index] = i;
    }
    int k = 0;
    for (int i = 0; i < N; i++) {
      if (sufInv[i] == N-1) {
        k = 0;
        continue;
      }

      int j = sorted[sufInv[i]+1].index;
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