var slds, format;
var format = new OpenLayers.Format.SLD();
function changeText()
{
	document.getElementById("t1").innerHTML = "OD Density Monitoring System Demo 2013-03-04 00:10";
}	

//function complete(req) {
	//format = new OpenLayers.Format.SLD();
  //  slds = format.read(req.responseXML || req.responseText);
//}   

function init()
{	
	//OpenLayers.Request.GET({
        //url: "./default.sld",
      //  success: complete
    //});
	//document.getElementById("t1").innerHTML = slds;
//	var styles = 0;
	//var style = styles[0];
	format = new OpenLayers.Format.SLD();
	var sld = format.read("file:///home/jags/Documents/geos/odsites/default.sld");
	var count = 0;
	for (var l in sld.namedLayers){	
		count++;	
	}
	document.getElementById("t1").innerHTML=count;
	
	//document.getElementById("t1").innerHTML = "OD Density Monitoring System Demo 2013-03-04 00:05";
	var bounds = new OpenLayers.Bounds(
                    113.91426144, 22.509802716,
                    114.62205808, 22.8536891
		);
    var format = new OpenLayers.Format.SLD();
	var fields = new Array();
	for(var i = 0 ; i < 24 ; i++)
	{
		for(var j = 0; j < 12 ; j++)
		{
			var fstr=i;
			var lstr=j*5;
			if(i<10)
			  fstr+='0'+fstr;
			if(j<10)
			  lstr+='0'+lstr;	
			fields=[fields,fstr+lstr];
		}
	}
	  	
	var startTime = new Date().getTime(); // get the current time
	while (new Date().getTime() < startTime + 5000); // hog cpu
	var field = '2400';
	var sld = newsld(field);
	var map = document.getElementById('map');
	map = new OpenLayers.Map('map');
	var wms = new OpenLayers.Layer.WMS( "sects:news - Tiled", "http://localhost:8080/geoserver/sects/wms",
	{
		LAYERS: 'sects:sects',
		STYLES: '',
		sld_body: sld,
		tiled: true
	} );
	//map.getLayersByName("sects:sects")[0].styleMap.styles["default"] = style;
	map.addLayer(wms);
	map.zoomToExtent(bounds);
}

function refreshing()
{
	changeText();
}

function newsld(field)
{
	var sld = '<?xml version="1.0" encoding="UTF-8"?><sld:StyledLayerDescriptor xmlns="http://www.opengis.net/sld" xmlns:sld="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc" xmlns:gml="http://www.opengis.net/gml" version="1.0.0"><sld:NamedLayer><sld:Name>sects:sects</sld:Name><sld:UserStyle><sld:Name>news</sld:Name><sld:Title/><sld:IsDefault>1</sld:IsDefault><sld:FeatureTypeStyle><sld:Name>group 0</sld:Name><sld:FeatureTypeName>Feature</sld:FeatureTypeName><sld:SemanticTypeIdentifier>generic:geometry</sld:SemanticTypeIdentifier><sld:SemanticTypeIdentifier>colorbrewer:quantile:orrd</sld:SemanticTypeIdentifier><sld:Rule><sld:Name>rule01</sld:Name><sld:Title>0..4</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>0</ogc:Literal></ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyIsLessThan><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>4</ogc:Literal></ogc:PropertyIsLessThan></ogc:And></ogc:Filter><sld:PolygonSymbolizer><sld:Fill><sld:CssParameter name="fill">#FEF0D9</sld:CssParameter></sld:Fill><sld:Stroke><sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter></sld:Stroke></sld:PolygonSymbolizer></sld:Rule><sld:Rule><sld:Name>rule02</sld:Name><sld:Title>4..15</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>4</ogc:Literal></ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyIsLessThan><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>15</ogc:Literal></ogc:PropertyIsLessThan></ogc:And></ogc:Filter><sld:PolygonSymbolizer><sld:Fill><sld:CssParameter name="fill">#FDCC8A</sld:CssParameter></sld:Fill><sld:Stroke><sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter></sld:Stroke></sld:PolygonSymbolizer></sld:Rule><sld:Rule><sld:Name>rule03</sld:Name><sld:Title>15..50</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>15</ogc:Literal></ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyIsLessThan><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>50</ogc:Literal></ogc:PropertyIsLessThan></ogc:And></ogc:Filter><sld:PolygonSymbolizer><sld:Fill><sld:CssParameter name="fill">#FC8D59</sld:CssParameter></sld:Fill><sld:Stroke><sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter></sld:Stroke></sld:PolygonSymbolizer></sld:Rule><sld:Rule><sld:Name>rule04</sld:Name><sld:Title>50..119</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>50</ogc:Literal></ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyIsLessThan><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>119</ogc:Literal></ogc:PropertyIsLessThan></ogc:And></ogc:Filter><sld:PolygonSymbolizer><sld:Fill><sld:CssParameter name="fill">#E34A33</sld:CssParameter></sld:Fill><sld:Stroke><sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter></sld:Stroke></sld:PolygonSymbolizer></sld:Rule><sld:Rule><sld:Name>rule05</sld:Name><sld:Title>119..764</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>119</ogc:Literal></ogc:PropertyIsGreaterThanOrEqualTo><ogc:PropertyIsLessThanOrEqualTo><ogc:PropertyName>'+field+'</ogc:PropertyName><ogc:Literal>764</ogc:Literal></ogc:PropertyIsLessThanOrEqualTo></ogc:And></ogc:Filter><sld:PolygonSymbolizer><sld:Fill><sld:CssParameter name="fill">#B30000</sld:CssParameter></sld:Fill><sld:Stroke><sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter></sld:Stroke></sld:PolygonSymbolizer></sld:Rule></sld:FeatureTypeStyle><sld:FeatureTypeStyle><sld:Name>group 1</sld:Name><sld:FeatureTypeName>Feature</sld:FeatureTypeName><sld:SemanticTypeIdentifier>generic:geometry</sld:SemanticTypeIdentifier><sld:SemanticTypeIdentifier>simple</sld:SemanticTypeIdentifier><sld:Rule><sld:TextSymbolizer><sld:Label><ogc:PropertyName>'+field+'</ogc:PropertyName></sld:Label><sld:Font><sld:CssParameter name="font-family">Arial</sld:CssParameter><sld:CssParameter name="font-size">12.0</sld:CssParameter><sld:CssParameter name="font-style">normal</sld:CssParameter><sld:CssParameter name="font-weight">bold</sld:CssParameter></sld:Font><sld:LabelPlacement><sld:PointPlacement><sld:AnchorPoint><sld:AnchorPointX>0.5</sld:AnchorPointX><sld:AnchorPointY>0.5</sld:AnchorPointY></sld:AnchorPoint></sld:PointPlacement></sld:LabelPlacement><sld:Fill><sld:CssParameter name="fill">#000000</sld:CssParameter></sld:Fill><sld:VendorOption name="spaceAround">2</sld:VendorOption></sld:TextSymbolizer></sld:Rule></sld:FeatureTypeStyle></sld:UserStyle></sld:NamedLayer></sld:StyledLayerDescriptor>';
	return sld;
}

	
