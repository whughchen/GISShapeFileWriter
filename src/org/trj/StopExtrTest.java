package org.trj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class StopExtrTest {

	/**
	 * @param args
	 */
	static int id = 2;
	static double sx = 113.88432;
	static double sy = 22.508974;
	static double sodm = 101747.961;
	static int idx = 0;
	static int MAX = 20;
	static	double disthr = 0.05; //km
	static	long tthr = 120; //sec
	static	int velthr = 2 ; //m/s
	public static void main(String[] args) throws IOException {
		
		FileReader fr = new FileReader("E:/datasource/ra/raout/id"+args[0]);
		BufferedReader br = new BufferedReader(fr);
		FileWriter out = new FileWriter("E:/datasource/ra/raout/id"+args[0]+"output");
		BufferedWriter bw = new BufferedWriter(out);
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		ArrayList<Long> ts = new ArrayList<Long>();
		ArrayList<Double> odm = new ArrayList<Double>();
		ArrayList<Integer> psidx = new ArrayList<Integer>();		
		String line = br.readLine();
		
		while(line!=null){
			String[] tokens = line.split(",");
			ts.add(Long.parseLong(tokens[1]));
			x.add(Double.parseDouble(tokens[2]));
			y.add(Double.parseDouble(tokens[3]));
			odm.add(Double.parseDouble(tokens[4]));
			line = br.readLine();
		}
		
		int M = ts.size();
		int i = 0;
		int j = 0;
		
		
		System.out.println(id);
		for(i = 0 ; i <M-1; i++){
			i = i+1;
			i = i-1;
			for(j = i + 1 ; j < M ; j++){
				if(x.get(i)==x.get(j)&&y.get(i)==y.get(j)){
					if(!psidx.contains(j))
						psidx.add(j);
				}else{
					double dis = d(x.get(i),y.get(i),x.get(j),y.get(j));
					if(dis<disthr){
						if(!psidx.contains(j))
							psidx.add(j);						
					}else{
						if(psidx.size()!=0&&i+1==j){
							long ti = ts.get(psidx.get(psidx.size()-1)) - ts.get(psidx.get(0));
							if(ti>tthr){
								double xmean = 0;
								double ymean = 0;
								double odmmean = 0;
								double xmax = Double.MIN_VALUE;
								double xmin = Double.MAX_VALUE;
								double ymax = Double.MIN_VALUE;
								double ymin = Double.MAX_VALUE;
								for (Integer idx : psidx) {
									xmean+=x.get(idx);
									ymean+=y.get(idx);
									if (x.get(idx)>xmax)
										xmax = x.get(idx);
									if (x.get(idx)<xmin)
										xmin = x.get(idx);
									if (y.get(idx)>ymax)
										ymax = y.get(idx);
									if (y.get(idx)<ymin)
										ymin = y.get(idx);
									odmmean+=odm.get(idx);
								}
								xmean /= (double)psidx.size();
								ymean /= (double)psidx.size();
								odmmean /= (double)psidx.size();
								String newkey = args[0]+"_"+String.valueOf(ts.get(psidx.get(0)))+"_"+String.valueOf(psidx.size());
								String newvalue = String.valueOf(xmean)+","+String.valueOf(ymean)+","+String.valueOf(odmmean)+","+String.valueOf(ti) + "," + String.valueOf(xmin) + "_" + String.valueOf(xmax) + "_" + String.valueOf(ymin) + "_" + String.valueOf(ymax);
								System.out.println(newkey+"\t"+newvalue);
								bw.write(newkey+"\t"+newvalue+"\n");
								bw.flush();
							}
							psidx.clear();
							System.gc();
						}
						break;
					}
				}
			}
		}
		x.clear();
		y.clear();
		odm.clear();
		ts.clear();
		System.gc();
		bw.close();
		out.close();
	}

	public static	double d(double lng1,double lat1,double lng2, double lat2) {
//		double	lat1	=	l1[1];
//		double	lat2	=	l2[1];
//		double	lng1	=	l1[0];
//		double	lng2	=	l2[0];
		double radLat1 = lat1*Math.PI / 180.0;
		double radLat2 = lat2*Math.PI / 180.0;
		double a = radLat1 - radLat2;
		double b = lng1*Math.PI / 180.0 - lng2*Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s *  6378.137;//km
		//s = Math.round(s * 10000) / 10000;
		return s;
	}
}	
				
				
