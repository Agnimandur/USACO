/*
ID: shivara2
LANG: JAVA
TASK: sort3
*/

import java.util.*;
import java.io.*;

class sort3 {
  public static void main(String[] args) throws IOException {
    //sort3.in
    BufferedReader br = new BufferedReader(new FileReader("sort3.in"));
    int N = Integer.parseInt(br.readLine());
    int[] inputList = new int[N];
    int[] sortedList = new int[N];
    for (int i = 0; i < N; i++) {
      inputList[i] = Integer.parseInt(br.readLine());
      sortedList[i] = inputList[i];
    }
    br.close();
    Arrays.sort(sortedList);
    //'12' means that, at a given index, there is a 1 in sortedList and a 2 in inputList
    //relationList = {'12','13','21','23','31','32'}
    int[] relationList = new int[6];
    for (int i = 0; i < N; i++) {
      if (sortedList[i] == 1 && inputList[i] == 2) {
        relationList[0]++;
      } else if (sortedList[i] == 1 && inputList[i] == 3) {
        relationList[1]++;
      } else if (sortedList[i] == 2 && inputList[i] == 1) {
        relationList[2]++;
      } else if (sortedList[i] == 2 && inputList[i] == 3) {
        relationList[3]++;
      } else if (sortedList[i] == 3 && inputList[i] == 1) {
        relationList[4]++;
      } else if (sortedList[i] == 3 && inputList[i] == 2) {
        relationList[5]++;
      } else {
        //This case is either ['11','22','33'], which means that the lists are already matched.
      }
    }
    //Number of swaps that have been done
    int swap = 0;
    //Simple Swap: 1s and 2s
    swap += simpleSwap(relationList,0,2);
    //Simple Swap: 1s and 3s
    swap += simpleSwap(relationList,1,4);
    //Simple Swap: 2s and 3s
    swap += simpleSwap(relationList,3,5);

    //Complex Swap: '12','23','31' AND '21','13','32' are the two types of complex swaps in which none of the three elements are correctly matched. The type of swap is determined by the first and second indices of the list, and it requires two swaps to correctly order.
    swap += complexSwap(relationList);

    //sort3.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
    pw.println(swap);
    pw.close();
  }

  public static int simpleSwap(int[] relationList,int n1,int n2) {
    //n1 and n2 are the indices of relationList in the swap
    int minValue = Math.min(relationList[n1],relationList[n2]);
    relationList[n1] -= minValue;
    relationList[n2] -= minValue;
    return minValue;
  }

  public static int complexSwap(int[] relationList) {
    return 2 * (relationList[0] + relationList[1]);
  }
}