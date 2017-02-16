public class SmartAppliance extends Appliance
{
	double percdropsmart;
	
	public SmartAppliance(int l,String apname,int o,double onoff,double drop,boolean r)
	{
		super(l,apname,o,onoff,r);
		percdropsmart=drop;
	}
	
	public void lowvoltage()
	{
		double lowvolt=(super.onwattage)-(super.onwattage*percdropsmart);
		super.setonnwattage((int)lowvolt);
	}
	
	public String toString()
	{
		String out;
		out="Location ID"+super.locationID+"\n Appliance Name: "+super.appliancename+"\n ApplianceID: "+super.applianceID;
		return out;
	}
}