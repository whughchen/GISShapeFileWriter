/**
 * odVizDemo com.siatcloud.odmatrix.viz MysqlTOshp.java
 *
 * Copyright 2013 Xdata@SIAT
 * Created:2013-4-11 下午9:46:19
 * email: gh.chen@siat.ac.cn
 */
package com.siatcloud.odmatrix.viz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.siatcloud.odmatrix.viz.RealtimeUpdater;


/**
 * odVizDemo com.siatcloud.odmatrix.viz MysqlTOshp.java
 *
 * Copyright 2013 Xdata@SIAT
 * Created:2013-4-11 下午9:46:19
 * email: gh.chen@siat.ac.cn
 *
 */
public class MysqlTOshp {

	public static void main(String[] args) throws Exception {
		//String path = "C:\\Users\\admin\\Desktop\\sects\\news.shp";
		String path = "/home/hadoop/tomcat/webapps/geoserver/data/data/sects/news.shp";
		while(true){
		Map<String, Integer> list=getCountMap();
		RealtimeUpdater.shpEditor(path,list);	
		Thread.sleep(20000);
		}
		
	}
	
	public static Map<String, Integer> getCountMap(){
	  Map<String, Integer> iDcount=new HashMap<String, Integer>() ;
	   MySqlClass mysql=new MySqlClass("172.20.36.247","3306","realOD", "ghchen", "ghchen");
	   String s=mysql.select("select * from realOD.count;");
	   //System.out.println(s);
	   String[] line =s.split("\n");
	   for(int i=0;i<line.length;i++){
		   String[] countArray=line[i].split(",");	
		   int count=Integer.parseInt(countArray[2]);
		   if (count<0) { count=0 ;}
		   iDcount.put(countArray[1], count );
		   //System.out.print(countArray[1]+","+ Integer.parseInt(countArray[2])+"\n");
	   }
	   return iDcount;
	}
}
