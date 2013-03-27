	<?xml version="1.0" encoding="ISO-8859-1"?>
	<StyledLayerDescriptor version="1.0.0" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
	  xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:gml="http://www.opengis.net/gml"
	  xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd">
	  <NamedLayer>
	    <Name>sects</Name>
	    <UserStyle>
	    <sld:LayerFeatureConstraints>
            <sld:FeatureTypeConstraint/>
        </sld:LayerFeatureConstraints>
	      <Name>population</Name>
	      <Title>Population in the United States</Title>
	      <Abstract>A sample filter that filters the United States into three
	        categories of population, drawn in different colors</Abstract>
	      <FeatureTypeStyle>
	        <sld:Name>group 0</sld:Name>
			                <sld:FeatureTypeName>Feature</sld:FeatureTypeName>
			                <sld:SemanticTypeIdentifier>generic:geometry</sld:SemanticTypeIdentifier>
			                <sld:SemanticTypeIdentifier>colorbrewer:quantile:orrd</sld:SemanticTypeIdentifier>
			                <sld:Rule>
			                    <sld:Name>rule01</sld:Name>
			                    <sld:Title>0..4</sld:Title>
			                    <ogc:Filter>
			                        <ogc:And>
			                            <ogc:PropertyIsGreaterThanOrEqualTo>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>0</ogc:Literal>
			                            </ogc:PropertyIsGreaterThanOrEqualTo>
			                            <ogc:PropertyIsLessThan>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>4</ogc:Literal>
			                            </ogc:PropertyIsLessThan>
			                        </ogc:And>
			                    </ogc:Filter>
			                    <sld:PolygonSymbolizer>
			                        <sld:Fill>
			                            <sld:CssParameter name="fill">#FEF0D9</sld:CssParameter>
			                        </sld:Fill>
			                        <sld:Stroke>
			                            <sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter>
			                        </sld:Stroke>
			                    </sld:PolygonSymbolizer>
			                </sld:Rule>
			                <sld:Rule>
			                    <sld:Name>rule02</sld:Name>
			                    <sld:Title>4..15</sld:Title>
			                    <ogc:Filter>
			                        <ogc:And>
			                            <ogc:PropertyIsGreaterThanOrEqualTo>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>4</ogc:Literal>
			                            </ogc:PropertyIsGreaterThanOrEqualTo>
			                            <ogc:PropertyIsLessThan>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>15</ogc:Literal>
			                            </ogc:PropertyIsLessThan>
			                        </ogc:And>
			                    </ogc:Filter>
			                    <sld:PolygonSymbolizer>
			                        <sld:Fill>
			                            <sld:CssParameter name="fill">#FDCC8A</sld:CssParameter>
			                        </sld:Fill>
			                        <sld:Stroke>
			                            <sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter>
			                        </sld:Stroke>
			                    </sld:PolygonSymbolizer>
			                </sld:Rule>
			                <sld:Rule>
			                    <sld:Name>rule03</sld:Name>
			                    <sld:Title>15..50</sld:Title>
			                    <ogc:Filter>
			                        <ogc:And>
			                            <ogc:PropertyIsGreaterThanOrEqualTo>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>15</ogc:Literal>
			                            </ogc:PropertyIsGreaterThanOrEqualTo>
			                            <ogc:PropertyIsLessThan>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>50</ogc:Literal>
			                            </ogc:PropertyIsLessThan>
			                        </ogc:And>
			                    </ogc:Filter>
			                    <sld:PolygonSymbolizer>
			                        <sld:Fill>
			                            <sld:CssParameter name="fill">#FC8D59</sld:CssParameter>
			                        </sld:Fill>
			                        <sld:Stroke>
			                            <sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter>
			                        </sld:Stroke>
			                    </sld:PolygonSymbolizer>
			                </sld:Rule>
			                <sld:Rule>
			                    <sld:Name>rule04</sld:Name>
			                    <sld:Title>50..119</sld:Title>
			                    <ogc:Filter>
			                        <ogc:And>
			                            <ogc:PropertyIsGreaterThanOrEqualTo>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>50</ogc:Literal>
			                            </ogc:PropertyIsGreaterThanOrEqualTo>
			                            <ogc:PropertyIsLessThan>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>119</ogc:Literal>
			                            </ogc:PropertyIsLessThan>
			                        </ogc:And>
			                    </ogc:Filter>
			                    <sld:PolygonSymbolizer>
			                        <sld:Fill>
			                            <sld:CssParameter name="fill">#E34A33</sld:CssParameter>
			                        </sld:Fill>
			                        <sld:Stroke>
			                            <sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter>
			                        </sld:Stroke>
			                    </sld:PolygonSymbolizer>
			                </sld:Rule>
			                <sld:Rule>
			                    <sld:Name>rule05</sld:Name>
			                    <sld:Title>119..764</sld:Title>
			                    <ogc:Filter>
			                        <ogc:And>
			                            <ogc:PropertyIsGreaterThanOrEqualTo>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>119</ogc:Literal>
			                            </ogc:PropertyIsGreaterThanOrEqualTo>
			                            <ogc:PropertyIsLessThanOrEqualTo>
			                                <ogc:PropertyName>0015</ogc:PropertyName>
			                                <ogc:Literal>764</ogc:Literal>
			                            </ogc:PropertyIsLessThanOrEqualTo>
			                        </ogc:And>
			                    </ogc:Filter>
			                    <sld:PolygonSymbolizer>
			                        <sld:Fill>
			                            <sld:CssParameter name="fill">#B30000</sld:CssParameter>
			                        </sld:Fill>
			                        <sld:Stroke>
			                            <sld:CssParameter name="stroke">#FFFFFF</sld:CssParameter>
			                        </sld:Stroke>
			                    </sld:PolygonSymbolizer>
			                </sld:Rule>
			            </sld:FeatureTypeStyle>
			            <sld:FeatureTypeStyle>
			                <sld:Name>group 1</sld:Name>
			                <sld:FeatureTypeName>Feature</sld:FeatureTypeName>
			                <sld:SemanticTypeIdentifier>generic:geometry</sld:SemanticTypeIdentifier>
			                <sld:SemanticTypeIdentifier>simple</sld:SemanticTypeIdentifier>
			                <sld:Rule>
			                    <sld:TextSymbolizer>
			                        <sld:Label>
			                            <ogc:PropertyName>0015</ogc:PropertyName>
			                        </sld:Label>
			                        <sld:Font>
			                            <sld:CssParameter name="font-family">Arial</sld:CssParameter>
			                            <sld:CssParameter name="font-size">12.0</sld:CssParameter>
			                            <sld:CssParameter name="font-style">normal</sld:CssParameter>
			                            <sld:CssParameter name="font-weight">bold</sld:CssParameter>
			                        </sld:Font>
			                        <sld:LabelPlacement>
			                            <sld:PointPlacement>
			                                <sld:AnchorPoint>
			                                    <sld:AnchorPointX>0.5</sld:AnchorPointX>
			                                    <sld:AnchorPointY>0.5</sld:AnchorPointY>
			                                </sld:AnchorPoint>
			                            </sld:PointPlacement>
			                        </sld:LabelPlacement>
			                        <sld:Fill>
			                            <sld:CssParameter name="fill">#000000</sld:CssParameter>
			                        </sld:Fill>
			                        <sld:VendorOption name="spaceAround">2</sld:VendorOption>
			                    </sld:TextSymbolizer>
			                </sld:Rule>
	     </FeatureTypeStyle>
	    </UserStyle>
	    </NamedLayer>
	</StyledLayerDescriptor>
