package org.trj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StopStat {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String path ="E:/sln/workspace/RefuelAnalysisResearch/dat/rfstat";
		String opath = "E:/sln/workspace/RefuelAnalysisResearch/dat/rfstat_new";
		FileReader in = new FileReader(path);
		FileWriter out = new FileWriter(opath);
		BufferedReader br = new BufferedReader(in);
		BufferedWriter bw = new BufferedWriter(out);
		String line = br.readLine();
		ArrayList<int[]> stat = new ArrayList<int[]>();
		while(line!=null){
			int[] s = {Integer.parseInt(line.split("\t")[0]), Integer.parseInt(line.split("\t")[1])};
			stat.add(s);
			line = br.readLine();
		}
		int gran = 50;
		int min = stat.get(0)[0] - gran;
		int max = stat.get(stat.size()-1)[0] + gran;
		int len = (max-min)/gran;
		int[] newstat = new int[len];
		for (int[] is : stat) {
			int idx = (is[0]-min)/gran;
			newstat[idx] += is[1];
		}
		
		for (int i = 0; i < newstat.length; i++) {
			int idx = min + i*gran;
			bw.write(idx + "\t" + newstat[i] + "\n");
		}
		
		br.close();
		bw.close();
		out.close();
		in.close();
	}

}
