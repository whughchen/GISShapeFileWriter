package org.trj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TrjReName {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path = "E:/sln/workspace/RefuelAnalysisResearch/dat/datsrc/";
		String opath = "E:/sln/workspace/RefuelAnalysisResearch/dat/datout/";
		File inf = new File(path);
		String[] fnames  = inf.list();
			for (String s : fnames) {
			String id = s.substring(2,s.length()-6);
			FileReader in = new FileReader(path+s);
			BufferedReader br = new BufferedReader(in);
			FileWriter out = new FileWriter(opath+s);
			BufferedWriter bw = new BufferedWriter(out);
			String line = br.readLine();
			while(line!=null){
				String[] tokens = line.split("_",2);
				bw.write(id+"_"+tokens[1]+"\n");
				bw.flush();
				line = br.readLine();
			}
			br.close();
			in.close();
			bw.close();
			out.close();
		}
	}

}
