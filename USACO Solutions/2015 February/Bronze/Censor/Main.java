import java.util.*;
import java.io.*;
import java.lang.*;

class Main {
  public static void main(String []args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("censor.in"));
    String s = br.readLine();
    String t = br.readLine();
	br.close();
	StringBuilder finalStr = new StringBuilder();
	for (int i = 0; i < s.length(); i++) {
		finalStr.append(s.charAt(i));
		if (finalStr.length() >= t.length() && finalStr.substring(finalStr.length()-t.length()).equals(t)) {
			finalStr.setLength(finalStr.length()-t.length());
		}
	}
	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
	pw.println(finalStr);
	pw.close();
  }
}