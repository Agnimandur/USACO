/*
ID: shivara2
LANG: JAVA
TASK: cowcode
*/

import java.math.BigInteger;
import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //cowcode.in
    BufferedReader br = new BufferedReader(new FileReader("cowcode.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    String code = st.nextToken();
    long N = Long.parseLong(st.nextToken());
    long K = Long.parseLong(code.length() + "");
    while (2 * K < N) {
      K *= 2;
    }
    br.close();
    if (N <= 2 * code.length()) {
      K = code.length();
    } else {
      while (K != code.length()) {
        N = reduce(N,K);
        K = shrink(N,K,code);
      }
    }
    if (N > K) {
      N = reduce(N,K);
    }

    //cowcode.out
    int ans = (int) N;
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowcode.out")));
    pw.println(code.substring(ans-1,ans));
    pw.close();
  }

  public static long reduce(long N, long K) {
    long newN = N - 1;
    if (newN <= K) {
      return newN;
    } else {
      return newN - K;
    }
  }

  public static long shrink(long N, long K, String code) {
    long newK = K/2;
    while (newK > N) {
      newK /= 2;
    }
    if (newK < (long) code.length()) {
      newK = (long) code.length();
    }
    return newK;
  }
}