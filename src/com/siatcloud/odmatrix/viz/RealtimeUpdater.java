package com.siatcloud.odmatrix.viz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureReader;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.mongodb.MongoLayer.GeometryType;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.filter.Filter;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.FeatureId;

import com.mongodb.util.Hash;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;

public class RealtimeUpdater {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws SchemaException 
	 * @throws InterruptedException 
	 */
	
	public static void main(String[] args) throws IOException, SQLException, SchemaException, InterruptedException {
		//Method 1: detect a new files coming
		//Method 2: read a key/value pairs of OD Matrix from latest file
		//Method 3: utilize this key/value pairs to edit shapefile's field of '2400'

		String curLat = "";//���µ��ļ���·��
		while(true){
			//Method 1:
	//		String dirPath = "/home/jags/Documents/geos/odsites/dat";
		    SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
		    SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd-HH");
		    Date date=new Date();
		    String today=sdf1.format(date);
		    String file=sdf2.format(date);
			String dirPath = "/server/real-time-OD/"+today+"/count";//paht of directory containing raw files
			curLat = fileDetector(dirPath,curLat);
			
			//When new file is under detection
			if(curLat!=null){
				//Method 2:
				Map<String,Integer> list = new HashMap<String,Integer>();
				list = getList(curLat);
				
				for(String s : list.keySet()){
					System.out.println(s + "\t" + list.get(s));
				}
				
				//Method 3:
		//		String path = "/usr/local/share/tomcat7/webapps/geoserver/data/data/sects/news.shp";
				String path = "/home/hadoop/tomcat/webapps/geoserver/data/data/sects/news.shp";//path of shapefile in geoserver
				shpEditor(path,list);
			}
			Thread.sleep(20*1000);
		}
	}
	
	
	public static String fileDetector(String dirPath, String curLat){
	    SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd-HH");
		File root = new File(dirPath);
		ArrayList<Long>	modiedTimes = new ArrayList<Long>();
		ArrayList<File> files = new ArrayList<File>();
		fetchFiles(root,files,modiedTimes);
		if(files.size()==0)
			return null;

		String thisCurLat = files.get(modiedTimes.indexOf(Collections.max(modiedTimes))).getAbsolutePath();
		if(curLat==null)return thisCurLat;
		if(thisCurLat.compareTo(curLat)==0)
			return null;
		else
			return thisCurLat;
	}
	
	public static void fetchFiles(File root,ArrayList<File> files,ArrayList<Long> modiedTimes){
		Calendar cal=Calendar.getInstance();   
		long today = cal.getTime().getTime() - (cal.getTime().getTime())%((60*60*24*1000));
		Date date = new Date(today);
		for(File file : root.listFiles()){
			if(!file.isDirectory()){
				long modiedTime = file.lastModified();
				if(modiedTime > today){
					files.add(file);
					modiedTimes.add(modiedTime);
				}
			}else
				fetchFiles(file,files,modiedTimes);
		}
	}
	
	
	/*public static Map<String,Integer> getList(String path) throws IOException{
		Map<String,Integer> list = new HashMap<String,Integer>();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while(line!=null){
//			if(line.split("#").length==2&&line.split("#")[0].split(",").length==2){
//				String[] tokens = line.split("#")[0].split(",");
//				String id = tokens[0];
			if(line.split(",").length==3){
				String[] tokens = line.split(",");
				String id = tokens[1];
				list.put(id, Integer.parseInt(tokens[2]));
				
				//list.put(id, Integer.parseInt(tokens[1]));	
			}
			line = br.readLine();
		}
		br.close();
		fr.close();
		return list;
	}*/
	
	public static Map<String,Integer> getList(String path) throws IOException{
		Map<String,Integer> list = new HashMap<String,Integer>();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		String curTimestamp = null;
		while(line!=null){
			if(line.split(",").length==3){
				curTimestamp = line.split(",")[0];
			}
			line = br.readLine();
		}
		//br.close();
		//fr.close();
		FileReader frfr = new FileReader(path);
		BufferedReader brbr = new BufferedReader(frfr);
		line = brbr.readLine();
		while(line!=null){
			if(line.split(",").length==3){
				String[] tokens = line.split(",");
				if(tokens[0].compareTo(curTimestamp)==0){
					String id = tokens[1];
					//int count=Integer.parseInt(tokens[2]);
					//if(count==-1) count=0;
					list.put(id, Integer.parseInt(tokens[2]));	
				}
			}
			line = brbr.readLine();
		}
		brbr.close();
		frfr.close();
		return list;
	}
	
	public static void shpEditor(String path, Map<String,Integer> list) throws IOException{
		
		File file = new File(path);
		FileDataStoreFactorySpi factory = FileDataStoreFinder.getDataStoreFactory("shp");
		Map params = Collections.singletonMap( "url", file.toURL() );
		ShapefileDataStore shpDataStore = null;
		shpDataStore = new ShapefileDataStore(file.toURL());
		
		
		//Feature Access
		String typeName = shpDataStore.getTypeNames()[0];  
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = null;  
		featureSource = (FeatureSource<SimpleFeatureType, SimpleFeature>)shpDataStore.getFeatureSource(typeName);  

		SimpleFeatureStore featureStore = (SimpleFeatureStore)featureSource;
		Transaction t = new DefaultTransaction();
		featureStore.setTransaction(t);
		FilterFactory2 ff = (FilterFactory2) CommonFactoryFinder.getFilterFactory2(null);
		Set<Integer> values = new HashSet<Integer>();
		
		FeatureCollection<SimpleFeatureType, SimpleFeature> result = featureStore.getFeatures();
		System.out.println(result.getSchema().getGeometryDescriptor().getLocalName());
		FeatureIterator<SimpleFeature> itertor = result.features();
		while(itertor.hasNext()){  
			//Data Reader
		    SimpleFeature feature = itertor.next();  
//		    int value = list.get(id);
		    String sectID = feature.getAttribute("TAZID").toString();
		    FeatureId fid = ff.featureId(feature.getID());
			Set<FeatureId> fids = new HashSet<FeatureId>();
			fids.add(fid);
			Filter filter = (Filter) ff.id(fids);
			int value = 0;
			if(list.get(sectID)!=null)
				value = list.get(sectID);
		 	featureStore.modifyFeatures("2400", value, filter);
		}  
		t.commit();
		t.close();
	}
	
	//abandon methods
	private static SimpleFeatureType createFeatureType(SimpleFeatureType TYPE) {

        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("sects");
        builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system
        // add attributes in order
        builder.add("polygonProperty", MultiPolygon.class);
        builder.length(8).add("TAZID", Long.class);
		for(int i = 0 ; i < 24 ; i ++){
			for(int j = 0 ; j < 60 ; j+=5){
				String hstr;
				String minstr;
				if(i<10)
					hstr = "0" + String.valueOf(i);
				else
					hstr = String.valueOf(i);
				if(j<10)
					minstr = "0" + String.valueOf(j);
				else
					minstr = String.valueOf(j);
				if(i==23&&j==50){
					builder.length(8).add(hstr+minstr, Long.class);
				}
				else{
					builder.length(8).add(hstr+minstr, Long.class);
				}
			}
		}
		builder.setDefaultGeometry("polygonProperty");
		
		// build the type
        final SimpleFeatureType SECTS = builder.buildFeatureType();
        
        return SECTS;
        
    }
	
	 private static File getNewShapeFile(File csvFile) {
	        String path = csvFile.getAbsolutePath();
	        String newPath = path.substring(0, path.length() - 4) + ".shp";

	        JFileDataStoreChooser chooser = new JFileDataStoreChooser("shp");
	        chooser.setDialogTitle("Save shapefile");
	        chooser.setSelectedFile(new File(newPath));

	        int returnVal = chooser.showSaveDialog(null);

	        if (returnVal != JFileDataStoreChooser.APPROVE_OPTION) {
	            // the user cancelled the dialog
	            System.exit(0);
	        }

	        File newFile = chooser.getSelectedFile();
	        if (newFile.equals(csvFile)) {
	            System.out.println("Error: cannot replace " + csvFile);
	            System.exit(0);
	        }

	        return newFile;
	}
	
	public static void AddColumns(String path,Map<String,Integer> simap) throws IOException, SQLException, SchemaException{
		String path2 = "/home/jags/Documents/geos/sects/newsects";
		

	
		
		
		File file = new File(path);
		FileDataStoreFactorySpi factory = FileDataStoreFinder.getDataStoreFactory("shp");
		Map params = Collections.singletonMap( "url", file.toURL() );
		ShapefileDataStore shpDataStore = null;
		shpDataStore = new ShapefileDataStore(file.toURL());
		
		
		//Feature Access
		String typeName = shpDataStore.getTypeNames()[0];  
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = null;  
		featureSource = (FeatureSource<SimpleFeatureType, SimpleFeature>)shpDataStore.getFeatureSource(typeName);  
		FeatureCollection<SimpleFeatureType, SimpleFeature> result = featureSource.getFeatures();
		System.out.println(result.getSchema().getGeometryDescriptor().getLocalName());
		FeatureIterator<SimpleFeature> itertor = result.features();
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(createFeatureType(result.getSchema()));
		SimpleFeatureCollection collection = FeatureCollections.newCollection();
		while(itertor.hasNext()){  
			//Data Reader
		    SimpleFeature feature = itertor.next();  
		    System.out.println(result.getSchema().getDescriptor(0));
		    System.out.println(feature.getAttribute("TAZID"));
//		    String geoStr = feature.getDefaultGeometry().toString();
		    MultiPolygon multiPolygon = (MultiPolygon) feature.getDefaultGeometry();
//		    Polygon polygon = multiPolygon.get
		    String id = feature.getAttribute("TAZID").toString();
		    featureBuilder.add(multiPolygon);
		    featureBuilder.add(feature.getAttribute("TAZID"));
		    for(int i = 0 ; i < 24 ; i ++){
				for(int j = 0 ; j < 60 ; j+=5){
					String hstr;
					String minstr;
					if(i<10)
						hstr = "0" + String.valueOf(i);
					else
						hstr = String.valueOf(i);
					if(j<10)
						minstr = "0" + String.valueOf(j);
					else
						minstr = String.valueOf(j);
					String ts = hstr+minstr;
					String id_ts = id +"_" + ts;
					if(i==23&&j==50){
						if(simap.get(id_ts)!=null)
							featureBuilder.add(simap.get(id_ts));
						else
							featureBuilder.add(0);
					}
					else{
						if(simap.get(id_ts)!=null)
							featureBuilder.add(simap.get(id_ts));
						else
							featureBuilder.add(0);
					}
				}
			}
		    SimpleFeature newfeature = featureBuilder.buildFeature(null);
            collection.add(newfeature);
		    //Fields Attributes
		}  
		
		File newFile = getNewShapeFile(new File(path2));
		ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

        params = new HashMap<String, Serializable>();
        params.put("url", newFile.toURI().toURL());
        params.put("create spatial index", Boolean.TRUE);
        ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
        SimpleFeatureType TYPE = createFeatureType(result.getSchema());
        newDataStore.createSchema(TYPE);
        
        
        newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);
        Transaction transaction = new DefaultTransaction("create");
        
        typeName = newDataStore.getTypeNames()[0];
        featureSource = newDataStore.getFeatureSource(typeName);

        if (featureSource instanceof SimpleFeatureStore) {
            SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;

            featureStore.setTransaction(transaction);
            try {
                featureStore.addFeatures(collection);
                transaction.commit();

            } catch (Exception problem) {
                problem.printStackTrace();
                transaction.rollback();

            } finally {
                transaction.close();
            }
            System.exit(0); // success!
        } else {
            System.out.println(typeName + " does not support read/write access");
            System.exit(1);
        }
		System.out.println(result.getSchema().getDescriptor(12));
	}
	
	public static void WriteFeature(String path) throws MalformedURLException, SchemaException{
		String newFields = "/home/jags/Documents/geos/sects/newsects.shp";
		for(int i = 0 ; i < 24 ; i ++){
			for(int j = 0 ; j < 60 ; j+=5){
				String hstr;
				String minstr;
				if(i<10)
					hstr = "0" + String.valueOf(i);
				else
					hstr = String.valueOf(i);
				if(j<10)
						minstr = "0" + String.valueOf(j);
				else
					minstr = String.valueOf(j);
				if(i==23&&j==50){
					newFields += hstr+minstr + ":Long";
					break;
				}
				else{
					newFields += hstr+minstr+":Long,";
				}
					
			}
		}
	}
	
	public static Map<String,Integer> GetList(String dir) throws IOException{
		Map<String,Integer> simap = new HashMap<String, Integer>();
		File root = new File(dir);
		for(File f : root.listFiles()){
				if(f.isDirectory()==false){
				FileReader fr = new FileReader(f.getPath());
				String ts = f.getName().split("-")[4] + f.getName().split("-")[5];
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				while(line!=null){
					if(line.split("#").length==2&&line.split("#")[0].split(",").length==2){
						String[] tokens = line.split("#")[0].split(",");
						
						String id_ts = tokens[0] + "_" + ts;
						simap.put(id_ts, Integer.parseInt(tokens[1]));	
					}
					line = br.readLine();
				}
				System.out.println(f.getName());
				br.close();
				fr.close();
			}
		}
		return simap;
	}
}
