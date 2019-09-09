/*
ID: shivara2
LANG: JAVA
TASK: helpcross
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //helpcross.in
    BufferedReader br = new BufferedReader(new FileReader("helpcross.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int C = Integer.parseInt(st.nextToken());
    int N = Integer.parseInt(st.nextToken());
    
    PriorityQueue<Animal> pq = new PriorityQueue<Animal>();
    for (int i = 0; i < C; i++) {
      int a = Integer.parseInt(br.readLine());
      Animal chick = new Animal(0,a,a);
      pq.add(chick);
    }
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      Animal cow = new Animal(1,a,b);
      pq.add(cow);
    }

    int pairs = 0;
    PriorityQueue<Integer> pending = new PriorityQueue<Integer>();
    for (int i = 0; i < C + N; i++) {
      Animal top = pq.poll();
      if (top.type == 1) {
        pending.add(top.end);
      } else {
        while (pending.size() != 0) {
          int cowEnd = pending.poll();
          if (cowEnd >= top.start) {
            pairs++;
            break;
          }
        }
      }
    }

    //helpcross.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("helpcross.out")));
    pw.println(pairs);
    pw.close();
  }
}

class Animal implements Comparable<Animal> {
  //Instance Variables (type = 0 is chicken, type = 1 is cow)
  public int type;
  public int start;
  public int end;

  //Constructor
  public Animal(int typeInput, int startInput, int endInput) {
    type = typeInput;
    start = startInput;
    end = endInput;
  }

  public int compareTo(Animal A2) {
    //Return -1 if given animal should go before A2 in the overall list, 1 if the animal should go after A2 in the priority queue.
    if (start < A2.start) {
      return -1;
    } else if (start > A2.start) {
      return 1;
    } else {
      if (type == 0 && A2.type == 1) {
        return 1;
      } else if (type == 1 && A2.type == 0) {
        return -1;
      } else {
        if (end < A2.end) {
          return -1;
        } else {
          return 1;
        }
      }
    }
  }

}