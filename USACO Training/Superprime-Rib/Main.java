/*
ID: shivara2
LANG: JAVA
TASK: sprime
*/

import java.util.*;
import java.io.*;

class sprime {
  public static void main(String[] args) throws IOException{
    //sprime.in
    BufferedReader br = new BufferedReader(new FileReader("sprime.in"));
    int N = Integer.parseInt(br.readLine());
    br.close();

    //find superprimes
    ArrayList<Integer> sprimes = new ArrayList<Integer>();
    sprimes = superPrime(N);

    //sprime.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sprime.out")));
    for (int i = 0; i < sprimes.size(); i++) {
      pw.print(sprimes.get(i));
      pw.print("\n");
    }
    pw.close();
  }

  public static ArrayList<Integer> superPrime(int N) {
    int[] digits = {1,3,7,9};
    //N=1
    ArrayList<Integer> superprime1 = new ArrayList<Integer>();
    superprime1.add(2);
    superprime1.add(3);
    superprime1.add(5);
    superprime1.add(7);

    //N=2
    ArrayList<Integer> superprime2 = new ArrayList<Integer>();
    for (int num: superprime1) {
      for (int digit: digits) {
        if (prime(10 * num + digit)) {
          superprime2.add(10 * num + digit);
        }
      }
    }

    //N=3
    ArrayList<Integer> superprime3 = new ArrayList<Integer>();
    for (int num: superprime2) {
      for (int digit: digits) {
        if (prime(10 * num + digit)) {
          superprime3.add(10 * num + digit);
        }
      }
    }

    //N=4
    ArrayList<Integer> superprime4 = new ArrayList<Integer>();
    for (int num: superprime3) {
      for (int digit: digits) {
        if (prime(10 * num + digit)) {
          superprime4.add(10 * num + digit);
        }
      }
    }

    //N=5
    ArrayList<Integer> superprime5 = new ArrayList<Integer>();
    for (int num: superprime4) {
      for (int digit: digits) {
        if (prime(10 * num + digit)) {
          superprime5.add(10 * num + digit);
        }
      }
    }

    //N=6
    ArrayList<Integer> superprime6 = new ArrayList<Integer>();
    for (int num: superprime5) {
      for (int digit: digits) {
        if (prime(10 * num + digit)) {
          superprime6.add(10 * num + digit);
        }
      }
    }

    //N=7
    ArrayList<Integer> superprime7 = new ArrayList<Integer>();
    for (int num: superprime6) {
      for (int digit: digits) {
        if (prime(10 * num + digit)) {
          superprime7.add(10 * num + digit);
        }
      }
    }

    //N=8
    ArrayList<Integer> superprime8 = new ArrayList<Integer>();
    for (int num: superprime7) {
      for (int digit: digits) {
        if (prime(10 * num + digit)) {
          superprime8.add(10 * num + digit);
        }
      }
    }

    //return
    ArrayList[] returnValue = {superprime1,superprime2,superprime3,superprime4,superprime5,superprime6,superprime7,superprime8};
    return returnValue[N-1];
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