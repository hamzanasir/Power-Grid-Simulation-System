public class RegularAppliance extends Appliance
{
	//double percdropsmart;
	
	public RegularAppliance(int l,String apname,int o,double onoff,boolean r)
	{
		super(l,apname,o,onoff,r);
	}
	
	public void lowvoltage()
	{
		double lowvolt=(super.onwattage)-(super.onwattage*0);
		super.setonnwattage((int)lowvolt);
	}
	
	public String toString()
	{
		String out;
		out="Location ID"+super.locationID+"\nAppliance Name: "+super.appliancename+"\n ApplianceID: "+super.applianceID;
		return out;
	}
}