var map, sld, waterBodies;
var format = new OpenLayers.Format.SLD();
function init(){
	var bounds = new OpenLayers.Bounds(
                    113.91426144, 22.509802716,
                    114.62205808, 22.8536891
		);
    map = new OpenLayers.Map('map', {allOverlays: true});
	var layer = new OpenLayers.Layer.WMS( "sects:news - Tiled", "http://localhost:8080/geoserver/sects/wms",
	{
		LAYERS: 'sects:sects',
		STYLES: '',
		sld_body: sld,
		tiled: true
	} );    
	map.addLayer(layer);
	map.zoomToExtent(bounds);
	var count = 0;
	var str ="";	
	var sldformat = format.read("./sld-tasmania.xml");
	
	for(var l in sldformat)
	{
		str+=l+",";
	}
	
	document.getElementById("t1").innerHTML=str;
	
    OpenLayers.Request.GET({
        url: "./default.xml",
        success: complete
    });
}

function complete(req){
	sld = format.read(req.responseXML || req.responseText);
	count = -1;
	for(var l in sld.namedLayers){
		count++;
	}
	

//    buildStyleChooser();
//    setLayerStyles();
    
//    map.zoomToExtent(new OpenLayers.Bounds(143,-39,150,-45));
}
