/*
ID: shivara2
LANG: JAVA
TASK: pprime
*/

import java.util.*;
import java.io.*;

class pprime {
  public static void main(String[] args) throws IOException{
    //pprime.in
    BufferedReader br = new BufferedReader(new FileReader("pprime.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int a = Integer.parseInt(st.nextToken());
    int b = Integer.parseInt(st.nextToken());
    br.close();

    ArrayList<Integer> palindromes = new ArrayList<Integer>();
    //There are 19,994 palindromes between 5 and 100000000.
    palindromes = palindrome();
    ArrayList<Integer> pprimes = new ArrayList<Integer>();
    for (int i = 0; i < palindromes.size();i++) {
      if (palindromes.get(i) >= a && palindromes.get(i) <= b && prime(palindromes.get(i))) {
        pprimes.add(palindromes.get(i));
      }
    }
    Collections.sort(pprimes);

    //pprime.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));
    for (int i = 0; i < pprimes.size(); i++) {
      pw.print(pprimes.get(i));
      pw.print("\n");
    }
    pw.close();
  }

  public static ArrayList<Integer> palindrome() {
    ArrayList<Integer> palindromes = new ArrayList<Integer>();
    //one and two digit palindromes
    for (int i = 1; i < 10; i++) {
      if (i > 4) {
        palindromes.add(i);
      }
      palindromes.add(11 * i);
    }
    //three and four digit palindromes
    for (int i = 1; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        palindromes.add(101 * i + 10 * j);
        palindromes.add(1001 * i + 110 * j);
      }
    }
    //five and six digit palindromes
    for (int i = 1; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        for (int k = 0; k < 10; k++) {
          palindromes.add(10001 * i + 1010 * j + 100 * k);
          palindromes.add(100001 * i + 10010 * j + 1100 * k);
        }
      }
    }
    //seven and eight digit palindromes
    for (int i = 1; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        for (int k = 0; k < 10; k++) {
          for (int l = 0; l < 10; l++) {
            palindromes.add(1000001 * i + 100010 * j + 10100 * k + 1000 * l);
            palindromes.add(10000001 * i + 1000010 * j + 100100 * k + 11000 * l);
          }
        }
      }
    }

    return palindromes;
  }

  public static boolean prime(int n) {
    if (n % 2 == 0) {
      return false;
    }
    for (int i = 3; i <= (int) Math.sqrt(n);i+=2) {
      if (n % i == 0) {
        return false;
      }
    }

    return true;
  }
}