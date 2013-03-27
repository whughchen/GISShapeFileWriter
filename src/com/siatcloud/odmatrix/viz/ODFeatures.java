package com.siatcloud.odmatrix.viz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.mongodb.MongoLayer.GeometryType;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;

import com.mongodb.util.Hash;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;

public class ODFeatures {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws SchemaException 
	 */
	
	private static SimpleFeatureType createFeatureType(SimpleFeatureType TYPE) {

        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("sects");
        builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system
        // add attributes in order
        builder.add("polygonProperty", MultiPolygon.class);
        builder.length(8).add("TAZID", Long.class);
		for(int i = 0 ; i < 25 ; i ++){
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
		    featureBuilder.add(15);
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
		String newFields = "/home/jags/Documents/geos/sects/neo.shp";
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
	
	public static void main(String[] args) throws IOException, SQLException, SchemaException {
		// TODO Auto-generated method stub
		//Method 1: detect a new files coming
		//Method 1: to read a key/value pairs of from latest file
		//Method 2: utilize this key/value pairs to edit shapefile field
		
		Map<String,Integer> simap = new HashMap<String, Integer>();
		simap = GetList("/home/jags/Documents/2013-03-04");
		String path = "/home/jags/Documents/geos/sects/sects.shp";
		AddColumns(path,simap);
	}

}
