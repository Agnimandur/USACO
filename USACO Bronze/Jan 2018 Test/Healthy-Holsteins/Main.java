/*
ID: shivara2
LANG: JAVA
TASK: holstein
*/

import java.util.*;
import java.io.*;

class holstein {
  public static void main(String[] args) throws IOException{
    //holstein.in
    BufferedReader br = new BufferedReader(new FileReader("holstein.in"));
    int V = Integer.parseInt(br.readLine());
    int[] nutrition = new int[V];
    StringTokenizer st = new StringTokenizer(br.readLine());
    for (int i = 0; i < V; i++) {
      nutrition[i] = Integer.parseInt(st.nextToken());
    }

    int G = Integer.parseInt(br.readLine());
    int[][] feedTypes = new int[G][V];
    for (int i = 0; i < G; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < V; j++) {
        feedTypes[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    //All successful diets (held by allDiets)
    int[] checkCombo = {};
    ArrayList<int[]> allDiets = new ArrayList<int[]>();
    checkNutrition(G,nutrition,feedTypes,checkCombo,allDiets);
    int min = G;
    for (int i = 0; i < allDiets.size(); i++) {
      min = Math.min(min,allDiets.get(i).length);
    }
    ArrayList<int[]> minDiets = new ArrayList<int[]>();
    for (int i = 0; i < allDiets.size(); i++) {
      if (allDiets.get(i).length == min) {
        minDiets.add(allDiets.get(i));
      }
    }
    //"If more than one set of feedtypes yield a minimum of scoops, choose the set with the smallest feedtype numbers" 
    int[] sumArray = new int[minDiets.size()];
    int smallestSum = 10000;
    for (int i = 0; i < sumArray.length; i++) {
      int sum = 0;
      for (int j = 0; j < min; j++) {
        sum += minDiets.get(i)[j];
      }
      sumArray[i] = sum;
      smallestSum = Math.min(smallestSum,sum);
    }
    int[] theDiet = new int[min];
    for (int i = 0; i < sumArray.length; i++) {
      if (smallestSum == sumArray[i]) {
        theDiet = minDiets.get(i);
        break;
      }
    }
    //holstein.out (return theDiet)
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("holstein.out")));
    pw.print(min + " ");
    for (int item: theDiet) {
      if (item == theDiet[theDiet.length - 1]) {
        pw.println(item);
      } else {
        pw.print(item + " ");
      }
    }
    pw.close();
  }

  public static void checkNutrition(int G, int[] nutrition, int[][] feedTypes, int[] checkCombo,ArrayList<int[]> allDiets) {
    if (nutritionMet(nutrition,feedTypes,checkCombo)) {
      allDiets.add(checkCombo);
    } else if (checkCombo.length == 0) {
      for (int i = 1; i <= G; i++) {
        int[] check = {i};
        checkNutrition(G,nutrition,feedTypes,check,allDiets);
      }
    } else {
      for (int i = checkCombo[checkCombo.length - 1] + 1; i <= G; i++) {
        int[] check = new int[checkCombo.length+1];
        for (int j = 0; j < checkCombo.length; j++) {
          check[j] = checkCombo[j];
        }
        check[check.length - 1] = i;
        checkNutrition(G,nutrition,feedTypes,check,allDiets);
      }
    }
  }

  public static boolean nutritionMet(int[] nutrition, int[][] feedTypes, int[] checkCombo) {
    int[] diet = new int[nutrition.length];
    for (int i = 0; i < nutrition.length; i++) {
      for (int element: checkCombo) {
        diet[i] += feedTypes[element-1][i];
      }
    }

    for (int i = 0; i < diet.length; i++) {
      if (diet[i] < nutrition[i]) {
        return false;
      }
    }
    return true;
  }
}