package eliteDangerousRestUpdater.handler.stationHandler;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StationsGson
{
	@SerializedName( "id" )
	@Expose
	public Long id;

	@SerializedName( "name" )
	@Expose
	public String name;

	@SerializedName( "system_id" )
	@Expose
	public Long systemId;

	@SerializedName( "updated_at" )
	@Expose
	public Long updatedAt;

	@SerializedName( "max_landing_pad_size" )
	@Expose
	public String maxLandingPadSize;

	@SerializedName( "distance_to_star" )
	@Expose
	public Long distanceToStar;

	@SerializedName( "government_id" )
	@Expose
	public Long governmentId;

	@SerializedName( "government" )
	@Expose
	public String government;

	@SerializedName( "allegiance_id" )
	@Expose
	public Long allegianceId;

	@SerializedName( "allegiance" )
	@Expose
	public String allegiance;

	@SerializedName( "type_id" )
	@Expose
	public Long typeId;

	@SerializedName( "type" )
	@Expose
	public String type;

	@SerializedName( "has_blackmarket" )
	@Expose
	public Boolean hasBlackmarket;

	@SerializedName( "has_market" )
	@Expose
	public Boolean hasMarket;

	@SerializedName( "has_refuel" )
	@Expose
	public Boolean hasRefuel;

	@SerializedName( "has_repair" )
	@Expose
	public Boolean hasRepair;

	@SerializedName( "has_rearm" )
	@Expose
	public Boolean hasRearm;

	@SerializedName( "has_outfitting" )
	@Expose
	public Boolean hasOutfitting;

	@SerializedName( "has_shipyard" )
	@Expose
	public Boolean hasShipyard;

	@SerializedName( "has_docking" )
	@Expose
	public Boolean hasDocking;

	@SerializedName( "has_commodities" )
	@Expose
	public Boolean hasCommodities;

	@SerializedName( "shipyard_updated_at" )
	@Expose
	public Long shipyardUpdatedAt;

	@SerializedName( "outfitting_updated_at" )
	@Expose
	public Long outfittingUpdatedAt;

	@SerializedName( "market_updated_at" )
	@Expose
	public Long marketUpdatedAt;

	@SerializedName( "is_planetary" )
	@Expose
	public Boolean isPlanetary;

	@SerializedName( "selling_ships" )
	@Expose
	public List<String> sellingShips = null;


	public Long getId()
	{
		return id;
	}


	public void setId( Long id )
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName( String name )
	{
		this.name = name;
	}


	public Long getSystemId()
	{
		return systemId;
	}


	public void setSystemId( Long systemId )
	{
		this.systemId = systemId;
	}


	public Long getUpdatedAt()
	{
		return updatedAt;
	}


	public void setUpdatedAt( Long updatedAt )
	{
		this.updatedAt = updatedAt;
	}


	public String getMaxLandingPadSize()
	{
		return maxLandingPadSize;
	}


	public void setMaxLandingPadSize( String maxLandingPadSize )
	{
		this.maxLandingPadSize = maxLandingPadSize;
	}


	public Long getDistanceToStar()
	{
		return distanceToStar;
	}


	public void setDistanceToStar( long distanceToStar )
	{
		this.distanceToStar = distanceToStar;
	}


	public Long getGovernmentId()
	{
		return governmentId;
	}


	public void setGovernmentId( Long governmentId )
	{
		this.governmentId = governmentId;
	}


	public String getGovernment()
	{
		return government;
	}


	public void setGovernment( String government )
	{
		this.government = government;
	}


	public Long getAllegianceId()
	{
		return allegianceId;
	}


	public void setAllegianceId( Long allegianceId )
	{
		this.allegianceId = allegianceId;
	}


	public String getAllegiance()
	{
		return allegiance;
	}


	public void setAllegiance( String allegiance )
	{
		this.allegiance = allegiance;
	}


	public Long getTypeId()
	{
		return typeId;
	}


	public void setTypeId( Long typeId )
	{
		this.typeId = typeId;
	}


	public String getType()
	{
		return type;
	}


	public void setType( String type )
	{
		this.type = type;
	}


	public Boolean getHasBlackmarket()
	{
		return hasBlackmarket;
	}


	public void setHasBlackmarket( Boolean hasBlackmarket )
	{
		this.hasBlackmarket = hasBlackmarket;
	}


	public Boolean getHasMarket()
	{
		return hasMarket;
	}


	public void setHasMarket( Boolean hasMarket )
	{
		this.hasMarket = hasMarket;
	}


	public Boolean getHasRefuel()
	{
		return hasRefuel;
	}


	public void setHasRefuel( Boolean hasRefuel )
	{
		this.hasRefuel = hasRefuel;
	}


	public Boolean getHasRepair()
	{
		return hasRepair;
	}


	public void setHasRepair( Boolean hasRepair )
	{
		this.hasRepair = hasRepair;
	}


	public Boolean getHasRearm()
	{
		return hasRearm;
	}


	public void setHasRearm( Boolean hasRearm )
	{
		this.hasRearm = hasRearm;
	}


	public Boolean getHasOutfitting()
	{
		return hasOutfitting;
	}


	public void setHasOutfitting( Boolean hasOutfitting )
	{
		this.hasOutfitting = hasOutfitting;
	}


	public Boolean getHasShipyard()
	{
		return hasShipyard;
	}


	public void setHasShipyard( Boolean hasShipyard )
	{
		this.hasShipyard = hasShipyard;
	}


	public Boolean getHasDocking()
	{
		return hasDocking;
	}


	public void setHasDocking( Boolean hasDocking )
	{
		this.hasDocking = hasDocking;
	}


	public Boolean getHasCommodities()
	{
		return hasCommodities;
	}


	public void setHasCommodities( Boolean hasCommodities )
	{
		this.hasCommodities = hasCommodities;
	}


	public Long getShipyardUpdatedAt()
	{
		return shipyardUpdatedAt;
	}


	public void setShipyardUpdatedAt( Long shipyardUpdatedAt )
	{
		this.shipyardUpdatedAt = shipyardUpdatedAt;
	}


	public Long getOutfittingUpdatedAt()
	{
		return outfittingUpdatedAt;
	}


	public void setOutfittingUpdatedAt( Long outfittingUpdatedAt )
	{
		this.outfittingUpdatedAt = outfittingUpdatedAt;
	}


	public Long getMarketUpdatedAt()
	{
		return marketUpdatedAt;
	}


	public void setMarketUpdatedAt( long marketUpdatedAt )
	{
		this.marketUpdatedAt = marketUpdatedAt;
	}


	public Boolean getPlanetary()
	{
		return isPlanetary;
	}


	public void setPlanetary( Boolean planetary )
	{
		isPlanetary = planetary;
	}


	public List<String> getSellingShips()
	{
		return sellingShips;
	}


	public void setSellingShips( List<String> sellingShips )
	{
		this.sellingShips = sellingShips;
	}


	public Object getSettlementSizeId()
	{
		return settlementSizeId;
	}


	public void setSettlementSizeId( Object settlementSizeId )
	{
		this.settlementSizeId = settlementSizeId;
	}


	public Object getSettlementSize()
	{
		return settlementSize;
	}


	public void setSettlementSize( Object settlementSize )
	{
		this.settlementSize = settlementSize;
	}


	public Object getSettlementSecurityId()
	{
		return settlementSecurityId;
	}


	public void setSettlementSecurityId( Object settlementSecurityId )
	{
		this.settlementSecurityId = settlementSecurityId;
	}


	public Object getSettlementSecurity()
	{
		return settlementSecurity;
	}


	public void setSettlementSecurity( Object settlementSecurity )
	{
		this.settlementSecurity = settlementSecurity;
	}


	public Long getBodyId()
	{
		return bodyId;
	}


	public void setBodyId( Long bodyId )
	{
		this.bodyId = bodyId;
	}


	public Long getControllingMinorFactionId()
	{
		return controllingMinorFactionId;
	}


	public void setControllingMinorFactionId( Long controllingMinorFactionId )
	{
		this.controllingMinorFactionId = controllingMinorFactionId;
	}


	@SerializedName( "settlement_size_id" )
	@Expose
	public Object settlementSizeId;

	@SerializedName( "settlement_size" )
	@Expose
	public Object settlementSize;

	@SerializedName( "settlement_security_id" )
	@Expose
	public Object settlementSecurityId;

	@SerializedName( "settlement_security" )
	@Expose
	public Object settlementSecurity;

	@SerializedName( "body_id" )
	@Expose
	public Long bodyId;

	@SerializedName( "controlling_minor_faction_id" )
	@Expose
	public Long controllingMinorFactionId;

}
