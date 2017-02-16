public class Appliance
{
	int locationID;
	String appliancename;
	int onwattage;
	double onoffprob;
	boolean smartornot;
	static int id=0;
	int applianceID;
	
	public Appliance(int l,String apname,int o,double onoff,boolean r)
	{
		locationID=l;
		appliancename=apname;
		onwattage=o;
		onoffprob=onoff;
		smartornot=r;
		id++;
		applianceID=id;
	}
	
	//accessor methods
	public int getlocationID()
	{
		return locationID;
	}
	
	public String getappliancename()
	{
		return appliancename;
	}
	
	public int getonwattage()
	{
		return onwattage;
	}
	
	public double getonoffprobability()
	{
		return onoffprob;
	}
	
	public int getapplianceID()
	{
		return applianceID;
	}
	
	public boolean getappliancetype()
	{
		return smartornot;
	}
	//mutator methods
	
	public void setlocationID(int l)
	{
		locationID=l;
	}
	
	public void setappliancename(String str)
	{
		appliancename=str;
	}
	
	public void setonnwattage(int x)
	{
		onwattage=x;
	}
	
	public void setonoffprob(double i)
	{
		onoffprob=i;
	}
	
	public void setapplianceID(int d)
	{
		applianceID=d;
	}
	
	public String toString()
	{
		String out;
		out="Location ID"+locationID+"\nAppliance Name: "+appliancename+"\nApplianceID: "+applianceID+"\n";
		return out;
	}
}