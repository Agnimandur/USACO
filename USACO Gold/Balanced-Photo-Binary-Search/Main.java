import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("bphoto.in"));
    int N = Integer.parseInt(br.readLine());
    int[] heights = new int[N];
    for (int i = 0; i < N; i++) {
      heights[i] = Integer.parseInt(br.readLine());
    }
    br.close();

    ArrayList<Integer> left = new ArrayList<Integer>();
    ArrayList<Integer> right = new ArrayList<Integer>();
    for (int height: heights)
      right.add(height);
    Collections.sort(right);
    int unbalanced = 0;
    for (int i = 0; i < heights.length; i++) {
      int height = heights[i];
      int Rindex = binaryFind(right,0,right.size(),height);
      right.remove(Rindex);
      int R_i = right.size() - Rindex;

      int Lindex = binaryAdd(left,0,left.size(),height);
      int L_i = left.size() - Lindex;
      left.add(Lindex,height);
      if (Math.max(L_i,R_i) > 2 * Math.min(L_i,R_i))
        unbalanced++;
    }
    //bphoto.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("bphoto.out")));
    pw.println(unbalanced);
    pw.close();
  }

  public static int binaryFind(ArrayList<Integer> arr, int l, int r, int x) {
    if (r >= l && l <= r) { 
      int mid = (l + r) / 2; 

      if (arr.get(mid) == x)
        return mid;
      else if (arr.get(mid) > x) 
        return binaryFind(arr, l, mid - 1, x); 
      else
        return binaryFind(arr, mid + 1, r, x); 
    }
    return arr.get(l);
  }

  public static int binaryAdd(ArrayList<Integer> arr, int l, int r, int x) {
    if (r == 0)
      return 0;
    if (r >= l) {
      if (x < arr.get(0))
        return 0;
      int mid = (l + r) / 2; 
      if (l >= r-1)
        return r;
      else if (arr.get(mid) > x) 
        return binaryAdd(arr, l, mid, x); 
      else
        return binaryAdd(arr, mid, r, x); 
    }
    return r;
  }
}