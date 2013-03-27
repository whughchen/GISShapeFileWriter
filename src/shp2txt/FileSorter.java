package shp2txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileSorter {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path ="C:/快盘/ourdata/prj/umn/dat/resultdata/taxinumstat1";
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		Map<Integer,String> kv = new HashMap<Integer,String>();
		String line = br.readLine();
		ArrayList<Integer> list = new ArrayList<Integer>();
		while(line!=null){
			String[] tokens = line.split(",");
			int tzone = Integer.parseInt(tokens[1]);
			kv.put(tzone, line);
			list.add(tzone);
			line = br.readLine();
		}
		Collections.sort(list);
		String opath ="C:/快盘/ourdata/prj/umn/dat/resultdata/retaxinumstat1";
		FileWriter fw = new FileWriter(opath);
		BufferedWriter bw = new BufferedWriter(fw);
		for (Integer i : list) {
			bw.write(kv.get(i)+"\n");
		}
		bw.close();
		fw.close();
	}
}
