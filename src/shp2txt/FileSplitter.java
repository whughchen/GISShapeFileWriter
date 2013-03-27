package shp2txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileSplitter {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path ="C:/快盘/ourdata/prj/umn/dat/resultdata/taxinumstat";
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		path ="C:/快盘/ourdata/prj/umn/dat/resultdata/taxinumstat0";
		FileWriter fr0 = new FileWriter(path);
		BufferedWriter br0 = new BufferedWriter(fr0);
		path ="C:/快盘/ourdata/prj/umn/dat/resultdata/taxinumstat1";
		FileWriter fr1 = new FileWriter(path);
		BufferedWriter br1 = new BufferedWriter(fr1);
		String line = br.readLine();
		while(line!=null){
			String[] tokens = line.split(",");
			if(Integer.parseInt(tokens[0])==1)
				fr1.write(line+"\n");
			else
				fr0.write(line+"\n");
			line = br.readLine();
		}
		br1.close();
		br0.close();
		fr1.close();
		fr0.close();
		br.close();
		fr.close();
	}

}
