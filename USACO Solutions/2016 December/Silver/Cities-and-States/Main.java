/*
ID: shivara2
LANG: JAVA
TASK: citystate
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    //citystate.in
    BufferedReader br = new BufferedReader(new FileReader("citystate.in"));
    int N = Integer.parseInt(br.readLine());
    HashMap<String,Integer> cityState = new HashMap<String,Integer>();
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      String city = st.nextToken().substring(0,2);
      String state = st.nextToken();
      if (cityState.containsKey(city + state)) {
        int n = cityState.get(city + state);
        cityState.put(city + state,n+1);
      } else {
        cityState.put(city + state,1);
      }
    }

    int cityPairs = 0;
    for (String code: cityState.keySet()) {
      String city = code.substring(0,2);
      String state = code.substring(2,4);
      if (cityState.containsKey(state + city) && !city.equals(state)) {
        int n1 = cityState.get(city+state);
        int n2 = cityState.get(state+city);
        cityPairs += n1*n2;
      }
    }

    //citystate.out
    cityPairs /= 2;
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("citystate.out")));
    pw.println(cityPairs);
    pw.close();
  }
}