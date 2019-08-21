/*
ID: shivara2
LANG: JAVA
TASK: frac1
*/

import java.util.*;
import java.io.*;

class frac1 {
  public static void main(String[] args) throws IOException{
    //frac1.in
    BufferedReader br = new BufferedReader(new FileReader("frac1.in"));
    int N = Integer.parseInt(br.readLine());
    br.close();
    ArrayList<Fraction> fracAL = generateFractions(N);
    Collections.sort(fracAL);
    
    //frac1.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("frac1.out")));
    for (int i = 0; i < fracAL.size(); i++) {
      pw.println(fracAL.get(i));
    }
    pw.close();
  }

  public static ArrayList<Fraction> generateFractions(int N) {
    ArrayList<Fraction> fracAL = new ArrayList<Fraction>();
    Fraction placeholderFrac = new Fraction(0,1);
    fracAL.add(placeholderFrac);
    for (int i = 1; i < N; i++) {
      for (int j = 1; j < N+1; j++) {
        placeholderFrac = new Fraction(i,j);
        if (placeholderFrac.inSimplestForm()) {
          fracAL.add(placeholderFrac);
        }
      }
    }

    //return fracAL
    if (N == 1) {
      placeholderFrac = new Fraction(1,1);
      fracAL.add(placeholderFrac);
    }
    return fracAL;
  }
}

class Fraction implements Comparable<Fraction> {
  //Instance Variables
  public int num;
  public int den;

  //Constructor Method
  public Fraction(int numInput, int denInput) {
    num = numInput;
    den = denInput;
  }

  public boolean inSimplestForm() {
    for (int i = 1; i < Math.min(num,den) + 1; i++) {
      if (i > 1 && num % i == 0 && den % i == 0 || ((double) num)/den > 1.0) {
        return false;
      }
    }
    return true;
  }

  public String toString() {
    return num + "/" + den;
  }

  public int compareTo(Fraction F2) {
    double temp = (double) num/den;
    double F2temp = (double) F2.num/F2.den;
    if (temp > F2temp) {
      return 1;
    } else if (temp < F2temp) {
      return -1;
    } else {
      return 0;
    }
  }
}