package shp2txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.postgis.LinearRing;
import org.postgis.MultiPolygon;


public class UtilSHP2TXT {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		String path ="C:\\快盘\\ourdata\\prj\\umn\\shp\\bounds.shp";
		String opath = "C:\\快盘\\ourdata\\prj\\umn\\shp\\bounds.txt";
		
		
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
		FeatureIterator<SimpleFeature> itertor = result.features();  
		
		FileWriter fw = new FileWriter(opath);
		BufferedWriter bw = new BufferedWriter(fw);
		while(itertor.hasNext()){  
			//Data Reader
		    SimpleFeature feature = itertor.next();  
		    
		    //Fields Attributes
		    List fields = feature.getAttributes();
		    int ID = Integer.parseInt(feature.getAttribute("id").toString());
		    bw.write(ID+"\t");
		    //Geo Attributes
			String geoStr = feature.getDefaultGeometry().toString();
			LinearRing linearRing = new MultiPolygon(geoStr).getPolygon(0).getRing(0);
			for (int idx = 0; idx < linearRing.numPoints(); idx++) {
				if(idx!=linearRing.numPoints()-1)
					bw.write(linearRing.getPoint(idx).x+","+linearRing.getPoint(idx).y+";");
				else
					bw.write(linearRing.getPoint(idx).x+","+linearRing.getPoint(idx).y+"\n");
			}
		}
		bw.close();
		fw.close();
	}

}
