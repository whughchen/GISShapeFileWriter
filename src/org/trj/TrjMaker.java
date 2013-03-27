package org.trj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TrjMaker {

	/**
	 * @param args
	 */
	static double x = 113.88432;
	static double y = 22.508974;
	static double odm = 101747.961;
	static int idx = 0;
	public static void main(String[] args) throws IOException {
		for(int i = 500 ; i < 1000 ; i++){
			FileReader fr = new FileReader("E:/sln/pyworkspace/PyRAnalysis/PyRACluster/data/allidnew");
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter("E:/datasource/ra/raout/id"+i);
			BufferedWriter bw = new BufferedWriter(fw);
			String line = br.readLine();
			
			while(line!=null){
				String[] tokens = line.split(",");
				int id = Integer.parseInt(tokens[0]);
				if(id==i)
					bw.write(line+"\n");
				line = br.readLine();
			}
			
			bw.close();
			fw.close();
			br.close();
			fr.close();
			String[] arg = new String[2];
			arg[0] = String.valueOf(i);
			StopExtrTest.main(arg);
		}
	}

}
