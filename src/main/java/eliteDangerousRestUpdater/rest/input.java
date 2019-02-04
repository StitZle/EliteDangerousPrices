package eliteDangerousRestUpdater.rest;

import eliteDangerousRestUpdater.rest.commodity.CommodityMapping;


public class input
{
	public void input( String currentSystem, int radius, boolean needsPermit, String commodityName, int count,
			double multiplier, boolean InkPlanetray, String maxLanding, int maxAge )
	{

		//map commodity
		CommodityMapping commodityMapping = new CommodityMapping();
		int commodityID = commodityMapping.mapping( commodityName );

		//get all nearbySystems
		//GetSystems nearbySystems = new GetSystems();

		//getStations that sellID that Commodity
		//Stations stations = new Stations();
		//stations.getStations( nearbySystems.getData( currentSystem, radius, needsPermit ), planetray );
	}
}
