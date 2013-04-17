/**
 * Copyright 2013 Xdata@SIAT
 * 
 * Created:2013-4-7 8:09:20
 * 
 * email: gh.chen@siat.ac.cn
 */
package com.siatcloud.odmatrix.viz;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author mystyle
 */
class MySqlClass {
   
   private Connection conn=null;
   private Statement st=null;
   private ResultSet rs=null;
   
   public MySqlClass(String host, String port,String databaseName,String userName,String password){
       try{
           //写入驱动所在处，打开驱动
           Class.forName("com.mysql.jdbc.Driver").newInstance();
	   //数据库，用户，密码，创建与具体数据库的连接
           //conn=DriverManager.getConnection("jdbc:mysql://172.20.36.247:3306/"+databaseName,userName,password);
       conn=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+databaseName,userName,password);
           //创建执行sql语句的对象
	   st=conn.createStatement();
          
       }catch(Exception e){
           System.out.println("连接失败"+e.toString());
          
       }       
   }
   
  
   public String select(String sqlStatement){
       String result=new String();
       int size=0;
       try{
           rs=st.executeQuery(sqlStatement);
           size=st.getResultSet().getMetaData().getColumnCount();
           while(rs!=null && rs.next()){
        	   for(int i=0;i<size;i++){
        		   result=result+rs.getString(i+1)+",";
        	   }
        	   result=result+"\n";
                //result=rs.getString(n);
                //列的记数是从1开始的，这个适配器和C#的不同
           }
           
           rs.close();
           return result;
       }catch(Exception e){
           System.out.println("查询失败"+e.toString());
           
           return null;
       }
   }
   
/*   public ArrayList<String[]> getResultArray(String sqlStatement){
	   ArrayList<String[]> result=new ArrayList<String[]>();
       String[] row=null;
       int size=0;
       try{
           rs=st.executeQuery(sqlStatement);
           size=rs.getMetaData().getColumnCount();
           row=new String[size];
           while(rs!=null && rs.next()){              
              for(int i=0;i<size;i++){
            	  row[i]=rs.getString(i+1);
              }
              result.add(row);
                //列的记数是从1开始的，这个适配器和C#的不同
           }
           
           rs.close();
           return result;
       }catch(Exception e){
           System.out.println("查询失败"+e.toString());
           
           return null;
       }
   }*/
   
   public int query(String sqlStatement){
       int row=0;
       try{
           row=st.executeUpdate(sqlStatement);
           //this.close();
           return row;
       }catch(Exception e){
           System.out.println("执行sql语句失败"+e.toString());
          // this.close();
           return row;
       }
   }
   
   public void close(){
      try{
          if(rs!=null)
            this.rs.close();
          if(st!=null)
            this.st.close();
          if(conn!=null)
            this.conn.close();
          
      }catch(Exception e){
          System.out.println("关闭数据库连接失败"+e.toString());
      }       
   }
   
   public static void main(String[] args) throws SQLException{
	   
	   MySqlClass mysql=new MySqlClass("172.20.36.247","3306","realOD", "ghchen", "ghchen");
//
	   String s=mysql.select("select * from realOD.count;");
	   System.out.println(s);
//	   
//	   int b=mysql.query("insert into realOD.count(time,districtID,count) values('2013-04-09 12:00:49',11101,99 );");
//	   System.out.println(b);
//	   b=mysql.query("insert into realOD.count(time,districtID,count) values('2013-04-09 12:00:59',10101,99 );");
//	   b=mysql.query("insert into realOD.count(time,districtID,count) values('2013-04-09 12:00:59',10121,99 );");
//	   System.out.println(b);

	   
	   
	   
//	   mysql=new MySqlClass("172.20.36.247","3306","realTimeTraffic", "ghchen", "ghchen");
//	   b=mysql.query("insert into realTimeTraffic.roadSpeed(time,districtID,speed,count) values('2013-04-09 12:00:58',92111,99,4 );");
//	   System.out.println(b);
	   
	   
   }
   
   

}
