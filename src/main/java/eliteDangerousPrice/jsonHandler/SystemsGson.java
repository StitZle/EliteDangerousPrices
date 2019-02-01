package eliteDangerousPrice.jsonHandler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SystemsGson
{
	@SerializedName( "id" )
	@Expose
	private Float id;

	@SerializedName( "edsm_id" )
	@Expose
	private Float edsmId;

	@SerializedName( "name" )
	@Expose
	private String name;

	@SerializedName( "x" )
	@Expose
	private Float x;

	@SerializedName( "y" )
	@Expose
	private Float y;

	@SerializedName( "z" )
	@Expose
	private Float z;

	@SerializedName( "population" )
	@Expose
	private Long population;

	@SerializedName( "government_id" )
	@Expose
	private Integer governmentId;

	@SerializedName( "government" )
	@Expose
	private String government;

	@SerializedName( "allegiance_id" )
	@Expose
	private Integer allegianceId;

	@SerializedName( "allegiance" )
	@Expose
	private String allegiance;

	@SerializedName( "security_id" )
	@Expose
	private Integer securityId;

	@SerializedName( "security" )
	@Expose
	private String security;

	@SerializedName( "primary_economy_id" )
	@Expose
	private Integer primaryEconomyId;

	@SerializedName( "primary_economy" )
	@Expose
	private String primaryEconomy;

	@SerializedName( "power" )
	@Expose
	private String power;

	@SerializedName( "power_state" )
	@Expose
	private String powerState;

	@SerializedName( "power_state_id" )
	@Expose
	private Integer powerStateId;

	@SerializedName( "needs_permit" )
	@Expose
	private Boolean needsPermit;

	@SerializedName( "updated_at" )
	@Expose
	private Long updatedAt;


	public Float getId()
	{
		return id;
	}


	public void setId( Float id )
	{
		this.id = id;
	}


	public Float getEdsmId()
	{
		return edsmId;
	}


	public void setEdsmId( Float edsmId )
	{
		this.edsmId = edsmId;
	}


	public String getName()
	{
		return name;
	}


	public void setName( String name )
	{
		this.name = name;
	}


	public Float getX()
	{
		return x;
	}


	public void setX( Float x )
	{
		this.x = x;
	}


	public Float getY()
	{
		return y;
	}


	public void setY( Float y )
	{
		this.y = y;
	}


	public Float getZ()
	{
		return z;
	}


	public void setZ( Float z )
	{
		this.z = z;
	}


	public Long getPopulation()
	{
		return population;
	}


	public void setPopulation( Long population )
	{
		this.population = population;
	}


	public Integer getGovernmentId()
	{
		return governmentId;
	}


	public void setGovernmentId( Integer governmentId )
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


	public Integer getAllegianceId()
	{
		return allegianceId;
	}


	public void setAllegianceId( Integer allegianceId )
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


	public Integer getSecurityId()
	{
		return securityId;
	}


	public void setSecurityId( Integer securityId )
	{
		this.securityId = securityId;
	}


	public String getSecurity()
	{
		return security;
	}


	public void setSecurity( String security )
	{
		this.security = security;
	}


	public Integer getPrimaryEconomyId()
	{
		return primaryEconomyId;
	}


	public void setPrimaryEconomyId( Integer primaryEconomyId )
	{
		this.primaryEconomyId = primaryEconomyId;
	}


	public String getPrimaryEconomy()
	{
		return primaryEconomy;
	}


	public void setPrimaryEconomy( String primaryEconomy )
	{
		this.primaryEconomy = primaryEconomy;
	}


	public String getPower()
	{
		return power;
	}


	public void setPower( String power )
	{
		this.power = power;
	}


	public String getPowerState()
	{
		return powerState;
	}


	public void setPowerState( String powerState )
	{
		this.powerState = powerState;
	}


	public Integer getPowerStateId()
	{
		return powerStateId;
	}


	public void setPowerStateId( Integer powerStateId )
	{
		this.powerStateId = powerStateId;
	}


	public Boolean getNeedsPermit()
	{
		return needsPermit;
	}


	public void setNeedsPermit( Boolean needsPermit )
	{
		this.needsPermit = needsPermit;
	}


	public Long getUpdatedAt()
	{
		return updatedAt;
	}


	public void setUpdatedAt( Long updatedAt )
	{
		this.updatedAt = updatedAt;
	}
}
