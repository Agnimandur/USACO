import java.io.*;
import java.util.*;
import java.math.*;
import java.awt.Point;
 
public class Main {
	//static final long MOD = 998244353L;
	//static final long INF = -1000000000000000007L;
	static final long MOD = 1000000007L;
	//static final int INF = 1000000007;
	
	static long[] factorial;
	
	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		PrintWriter pw = new PrintWriter(System.out);
		int N = sc.ni();
		long K = sc.nl();
		//The lists we use
		ArrayList<Long> pos = new ArrayList<Long>();
		ArrayList<Long> neg = new ArrayList<Long>();
		for (int i = 0; i < N; i++) {
			long n = sc.nl();
			if (n >= 0)
				pos.add(n);
			else
				neg.add(n);
		}
		Collections.sort(pos);
		Collections.sort(neg);
		
		
		//The overall binary search
		long min = -1000000000000000000L;
		long max = 1000000000000000000L;
		while (min < max) {
			long med = (min+max)/2;
			if (min+max < 0)
				med = (min+max-1)/2;
			
			//val stores the number of pairs of products that are less than or equal to "med".
			long val = 0;
			
			//Do the complex iterations over the negatives and positives
			for (int i = 0; i < neg.size(); i++) {
				if (med < 0) {
					if (neg.get(i)*pos.get(pos.size()-1) > med)
						continue;
					int low = 0;
					int high = pos.size()-1;
					while (low < high) {
						int ind = (low+high)/2;
						if (neg.get(i)*pos.get(ind) <= med) {
							high = ind;
						} else {
							low = ind+1;
						}
					}
					val += (pos.size()-low);
				} else {
					val += pos.size();
					if (i==neg.size()-1 || neg.get(i)*neg.get(neg.size()-1) > med) {
						continue;
					}
					int low = i+1;
					int high = neg.size()-1;
					while (low < high) {
						int ind = (low+high)/2;
						if (neg.get(i)*neg.get(ind) <= med) {
							high = ind;
						} else {
							low = ind+1;
						}
					}
					val += (neg.size() - low);
				}
			}
 
			for (int i = 0; i < pos.size(); i++) {
				if (med < 0) break;
				if (i==pos.size()-1 || pos.get(i)*pos.get(i+1) > med) {
					continue;
				}
				
				int low = i+1;
				int high = pos.size()-1;
				while (low < high) {
					int ind = (low+high+1)/2;
					if (pos.get(i)*pos.get(ind) <= med) {
						low = ind;
					} else {
						high = ind-1;
					}
				}
				val += (low - i);
			}
			
			if (val >= K) {
				max = med;
			} else {
				min = med+1;
			}
			//pw.println(min + " " + max + " " + val);
		}
		pw.println(min);
		pw.close();
	}
    
    static class FastScanner { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastScanner() { 
            br = new BufferedReader(new InputStreamReader(System.in)); 
        } 
  
        String next() { 
            while (st == null || !st.hasMoreElements()) { 
                try { 
                    st = new StringTokenizer(br.readLine());
                } catch (IOException  e) { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int ni() { 
            return Integer.parseInt(next()); 
        } 
  
        long nl() { 
            return Long.parseLong(next()); 
        } 
  
        double nd() { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() { 
            String str = ""; 
            try { 
                str = br.readLine(); 
            } catch (IOException e) {
                e.printStackTrace(); 
            } 
            return str;
        }
    }
}