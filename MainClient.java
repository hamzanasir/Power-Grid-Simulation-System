import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
public class MainClient
{
	public static void makeObjects(String textfilename, Vector v)
	{
		Scanner scan;
		String mainstr;
		int locid;
		String appname;
		int onwatts;
		double prob;
		boolean smartornot;
		double drop;
		SmartAppliance smart1;
		RegularAppliance regular;
		try
		{
			File myfile=new File(textfilename);
			scan=new Scanner(myfile);
			while(scan.hasNextLine())
			{
				mainstr=scan.nextLine();
				String[] tok=mainstr.split(",");
				locid=Integer.parseInt(tok[0]);
				appname=tok[1];
				onwatts=Integer.parseInt(tok[2]);
				prob=Double.parseDouble(tok[3]);
				smartornot=Boolean.parseBoolean(tok[4]);
				drop=Double.parseDouble(tok[5]);
				if(smartornot==true)
				{
					smart1=new SmartAppliance(locid,appname,onwatts,prob,drop,smartornot);
					v.addElement(smart1);
				}
				else if(smartornot==false)
				{
					regular=new RegularAppliance(locid,appname,onwatts,prob,smartornot);
					v.addElement(regular);
				}
				else{
					System.out.println("Appliance type not specified. Error in file");
				}
			}
		}
		catch(IOException ioe)
		{
			System.out.println("Error in reading file.");
		}
	}
		
	public static double totalWattage(Vector v)
	{
		Appliance temp;
		double totalwattage=0;
		for(int i=0;i<v.size();i++)
		{
			temp=(Appliance)v.elementAt(i);
			totalwattage+=temp.getonwattage();
		}
		return totalwattage;
	}
	
	public static void BrownOut(Appliance a, Vector v){
		
		int aLocid=a.getlocationID();
		Appliance a1;
		
		for(int i=0;i<v.size();i++)
		{
			a1=(Appliance)v.elementAt(i);
			if(aLocid==a1.getlocationID())
			{
				a1.setonnwattage(0);
				v.set(i,a1);
			}
		}
		
	}
	
	public static void BrownOutAppsTurnedOff(Appliance a, Vector v,Vector toadd){
		
		int aLocid=a.getlocationID();
		Appliance a1;
		
		for(int i=0;i<v.size();i++)
		{
			a1=(Appliance)v.elementAt(i);
			if(aLocid==a1.getlocationID())
			{
				toadd.addElement(a1);
			}
		}
	}
	
	public static int getRandInt(char type, int x, int y) {
		int num;
		switch (type) { 
		case 'U': case 'u':		// Uniform Distribution
			num = (int)(x + (Math.random()*(y+1-x))); 
			break;
		case 'E': case 'e':		// Exponential Distribution
			num = (int)(-1*x*Math.log(Math.random()));  
			break;	
		case 'N': case 'n':		// Normal Distribution
			num = (int)( x +
                (y * Math.cos(2 * Math.PI * Math.random()) *
                Math.sqrt(-2 * Math.log(Math.random()))));
			break;			
		default:				// Uniform Distribution
			num = (int)(x + (Math.random()*(y+1-x))); 
		}
		return num;
	}
	
	public static void addToReport(Vector v,String filename,int simNumber)
	{
		int[] locationarray=locations(v);
		Appliance tempapp;
		try{
		FileOutputStream fos=new FileOutputStream(filename,true);
		PrintWriter pw=new PrintWriter(fos);
		pw.println("-------------------- Simulation Number: "+simNumber+" --------------------");
		for(int i=0;i<locationarray.length;i++)
		{
			pw.println("::::::Brown Outted Location ID: "+locationarray[i]+"::::::\n");
			for(int x=0;x<v.size();x++)
			{
				tempapp=(Appliance)v.elementAt(x);
				if(locationarray[i]==tempapp.getlocationID())
				{
					pw.println(tempapp.toString());
				}
			}
		}
		pw.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Unable to find file.");
		}
	}
	
	public static void sortLocationID(Vector v)
	{
		int temp;
		int index=0;
		int subarraylength;
		Appliance temp2;
		Appliance temp1;
		Appliance tempmax;
		Appliance templast;
		
		for(int j=0;j<v.size();j++)
		{
			subarraylength=v.size()-j;
			index=0;
			for(int k=1;k<subarraylength;k++)
			{
				temp2=(Appliance)v.elementAt(index);
				temp1=(Appliance)v.elementAt(k);
				if(temp1.getlocationID()>temp2.getlocationID())
				{
					index=k;
				}
			}
			tempmax=(Appliance)v.elementAt(index);
			templast=(Appliance)v.elementAt((v.size())-j-1);
			v.set(index,templast);
			v.set(v.size()-j-1,tempmax);
		}
	}
	
	public static void sortbyApplianceRating(Vector v)
	{
		int temp;
		int index=0;
		int subarraylength;
		Appliance temp2;
		Appliance temp1;
		Appliance tempmax;
		Appliance templast;
		
		for(int j=0;j<v.size();j++)
		{
			subarraylength=v.size()-j;
			index=0;
			for(int k=1;k<subarraylength;k++)
			{
				temp2=(Appliance)v.elementAt(index);
				temp1=(Appliance)v.elementAt(k);
				if(temp1.getonwattage()>temp2.getonwattage())
				{
					index=k;
				}
			}
			tempmax=(Appliance)v.elementAt(index);
			templast=(Appliance)v.elementAt((v.size())-j-1);
			v.set(index,templast);
			v.set(v.size()-j-1,tempmax);
		}
	}
	
	public static int[] locations(Vector v)
	{
		Appliance tempapp;
		Appliance tempapp1;
		sortLocationID(v);
		int counter=0;
		Random rand=new Random();
		
		for(int i=0;i<v.size()-1;i++)
		{
			tempapp=(Appliance)v.elementAt(i);
			tempapp1=(Appliance)v.elementAt(i+1);
			if(tempapp.getlocationID()!=tempapp1.getlocationID())
			{
				counter++;
			}
		}
		counter++;
		int[] locationids=new int[counter];
		counter=0;
		for(int x=0;x<v.size()-1;x++)
		{
			tempapp=(Appliance)v.elementAt(x);
			tempapp1=(Appliance)v.elementAt(x+1);
			if(tempapp.getlocationID()!=tempapp1.getlocationID())
			{
				locationids[counter]=tempapp.getlocationID();
				counter++;
			}
		}
		tempapp=(Appliance)v.elementAt(v.size()-1);
		locationids[counter]=tempapp.getlocationID();
		return locationids;
	}
	
	
	
	public static void main(String[]args)
	{
		Vector appliances=new Vector();
		makeObjects("output.txt",appliances);
		sortLocationID(appliances);
		Scanner scan=new Scanner(System.in);
		boolean exitflag=true;
		while(exitflag==true)
		{
		System.out.println("Enter \"1\" to add or delete appliances: ");
		System.out.println("Enter \"2\" to use an input file to add appliances: ");
		System.out.println("Enter \"3\" to view all appliances in a specific location: ");
		System.out.println("Enter \"4\" to view all appliances of a particular type: ");
		System.out.println("Enter \"5\" to run the simulation. ");
		System.out.println("Enter \"6\" to Quit the program: ");
		int menu=scan.nextInt();
		
		
		if(menu==1)
		{
			System.out.println("To add appliances please enter 1 again. For deleting appliances please enter 2.");
			int menu1=scan.nextInt();
			
			if(menu1==1)
			{
				SmartAppliance smartadd;
				RegularAppliance regadd;
				String yesorno;
				String name;
				boolean flag=true;
				double pdrop=0.0;
				while(flag==true)
				{
				System.out.print("What's the appliance type? Type true for smart and false for regular: ");
				boolean smartoreg=Boolean.parseBoolean(scan.next());
				
				if(smartoreg==true)
				{
					System.out.println("Please enter the low voltage drop for this smart appliance: ");
					pdrop=scan.nextDouble();
				}
				System.out.print("Please enter the LocationID of the appliance as an Integer: ");
				int lid=scan.nextInt();
				scan.nextLine();
				System.out.print("Please enter the name of the appliance: ");
				name=scan.nextLine();
				System.out.print("Please enter the power rating of the appliance as an Integer: ");
				int power=scan.nextInt();
				System.out.print("Please enter the probability that the appliance will be on as a decimal: ");
				double probwork=scan.nextDouble();
				if(smartoreg==true)
				{
					smartadd=new SmartAppliance(lid,name,power,probwork,pdrop,smartoreg);
					appliances.addElement(smartadd);
				}
				else if(smartoreg==false)
				{
					regadd=new RegularAppliance(lid,name,power,probwork,smartoreg);
					appliances.addElement(regadd);
				}
				else{
					System.out.println("Incorrect object. Not adding to vector.");
				}
				System.out.println("Do you want to add more appliances?Please answer in Yes or No. ");
				yesorno=scan.next();
				if(yesorno.equalsIgnoreCase("yes"))
				{
					flag=true;
				}
				else if(yesorno.equalsIgnoreCase("no"))
				{
					flag=false;
				}
				else{
					flag=false;
				}
				}
			}
			
			else if(menu1==2)
			{
				System.out.println("Please enter the appliance ID of the appliance you want to remove: ");
				int appid=scan.nextInt();
				Appliance tempdelapp;
				boolean flag1=false;
				for(int j=0;j<appliances.size();j++)
				{
					tempdelapp=(Appliance)appliances.elementAt(j);
					if(tempdelapp.getapplianceID()==appid)
					{
						System.out.println("Object found! Removing appliance: "+tempdelapp.toString());
						appliances.remove(j);
						flag1=true;
					}
				}
				if(flag1=true)
				{
					System.out.println("Appliance not found.");
				}
			}
			else{
				System.out.println("Incorrect menu option.");
			}
			
		}
		
		else if(menu==2)
		{
			System.out.println("Be advised that the text file formatting should be the same as output.txt");
			System.out.print("Please enter the name of the text file followed by its respective extension: ");
			String filename=scan.next();
			makeObjects(filename,appliances);
			System.out.println("Appliances successfully added!");
		}
		
		else if(menu==3)
		{
			System.out.println("The locations are: ");
			int[] locs=locations(appliances);
			for(int y=0;y<locs.length;y++)
			{
				System.out.println("Location: "+locs[y]);
			}
			
			System.out.print("Please enter a location from the list above: ");
			int selectedloc=scan.nextInt();
			System.out.println(":::::Locations in locationID "+selectedloc+" ::::::");
			Appliance temploc;
			for(int p=0;p<appliances.size();p++)
			{
				temploc=(Appliance)appliances.elementAt(p);
				if(selectedloc==temploc.getlocationID())
				{
					System.out.println(temploc.toString());
				}
			}
		}
		
		else if(menu==4)
		{
			System.out.println("Please enter the type of appliances you want to display. Smart or Regular?");
			String strsmartreg=scan.next();
			
			if(strsmartreg.equalsIgnoreCase("smart"))
			{
				System.out.println(":::::Displaying Smart Appliances:::::");
				Appliance temptype;
				for(int z=0;z<appliances.size();z++)
				{
					temptype=(Appliance)appliances.elementAt(z);
					if(temptype.getappliancetype()==true)
					{
						System.out.println(temptype.toString());
					}
				}
			}
			else if(strsmartreg.equalsIgnoreCase("regular"))
			{
				System.out.println(":::::Displaying Regular Appliances:::::");
				Appliance temptype1;
				for(int z=0;z<appliances.size();z++)
				{
					temptype1=(Appliance)appliances.elementAt(z);
					if(temptype1.getappliancetype()==false)
					{
						System.out.println(temptype1.toString());
					}
				}
			}
			else{
				System.out.println("Incorrect option.");
			}
		}
		
		
		
		
		
		
		else if(menu==5)
		{
		int simulationleng;
		System.out.println("Do you want to set the simulation length as the default 24 hrs?Yes or No?");
		String strvolts=scan.next();
		if(strvolts.equalsIgnoreCase("yes"))
		{
			simulationleng=24;
		}
		else if(strvolts.equalsIgnoreCase("no"))
		{
			System.out.print("Please enter the time length for the simulation as an integer: ");
			simulationleng=scan.nextInt();
		}
		else
		{
			System.out.println("Bad input. Setting simulation length to 24hrs.");
			simulationleng=24;
		}
		System.out.print("Please enter the maximum wattage of the grid as an integer: ");
		boolean flagnotzero=true;
		int maxwolts=scan.nextInt();
		while(flagnotzero)
		{
		if(maxwolts==0)
		{
			System.out.print("Please enter a non-zero wattage: ");
			maxwolts=scan.nextInt();
		}
		else{
			flagnotzero=false;
		}
		}
		Appliance tempapp;
		SmartAppliance smarttemp;
		RegularAppliance regtemp;
		Vector totalappefectees=new Vector();
		Random rand=new Random();
		try{
			FileOutputStream fos1=new FileOutputStream("Report.txt",false);
			PrintWriter pw1=new PrintWriter(fos1);
			pw1.println("@@@@@@@@@@@@@@@@ Detailed Simulation Report @@@@@@@@@@@@@@@@");
			pw1.close();
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("Unable to create file.");
		}
		
		final int ARRIVAL_MEAN=5;
		int minute=0, nextArrivalTime, callCount=0; 


		nextArrivalTime = minute + getRandInt('E', ARRIVAL_MEAN, 0);
		
		while(minute<=simulationleng) {
			while ((minute == nextArrivalTime) && (minute<=simulationleng)) {
				
				callCount++;
				System.out.println(":::::::::::::::::::: Hour:"+minute+"Simulation: "+callCount+"::::::::::::::::::");
				nextArrivalTime=minute+getRandInt('E', ARRIVAL_MEAN, 0);
				Vector newappliances=new Vector();
				makeObjects("output.txt",newappliances);
				double randnum=rand.nextFloat();
				double totalvoltage=0;
				Vector appliancesturnedoff=new Vector();
				int[] effectlocs;
				for(int i=0;i<newappliances.size();i++)
				{
					tempapp=(Appliance)newappliances.elementAt(i);
					if(randnum>=tempapp.getonoffprobability())
					{
						tempapp.setonnwattage(0);
						newappliances.set(i,tempapp);
					}
				}
				
				totalvoltage=totalWattage(newappliances);
				
				System.out.println("Total voltage initially: "+totalvoltage+"\n");
				
				if(totalvoltage>maxwolts)
				{
					totalvoltage=0;
					System.out.println("Grid overloaded. Shifting all smart appliances to low voltage.\n");
					for(int x=0;x<newappliances.size();x++)
					{
						tempapp=(Appliance)newappliances.elementAt(x);
						if(tempapp.getappliancetype()==true && tempapp.getonwattage()!=0)
						{
						smarttemp=(SmartAppliance)tempapp;
						smarttemp.lowvoltage();
						newappliances.set(x,smarttemp);
						}
					}
					
					totalvoltage=totalWattage(newappliances);
					
					System.out.println("Total voltage after shifting: "+totalvoltage+"\n");
					if(totalvoltage>maxwolts)
					{
						int counter=newappliances.size()-1;
						System.out.println("Grid is still overloaded. Brown outing locations.\n");
						sortbyApplianceRating(newappliances);
						while(totalvoltage>maxwolts)
						{
							tempapp=(Appliance)newappliances.elementAt(counter);
							if(tempapp.getonwattage()!=0){
							BrownOut(tempapp,newappliances);
							BrownOutAppsTurnedOff(tempapp,newappliances,appliancesturnedoff);
							BrownOutAppsTurnedOff(tempapp,newappliances,totalappefectees);
							}
							totalvoltage=totalWattage(newappliances);
							counter=counter-1;
						}
					}
					else{
						System.out.println("Shifting smart appliances to low worked! Grid is now okay!\n");
					}
					System.out.println("Grid is finally okay. Total voltage: "+totalvoltage+"\n");
				}
				else{
					System.out.println("Grid is okay!\n");
				}
				
				if(appliancesturnedoff.size()!=0)
				{
				effectlocs=locations(appliancesturnedoff);
				System.out.println("Number of Locations Brown outted excluding the smart appliances high to low changeover: "+effectlocs.length+"\n");
				System.out.println("Number of appliances switched off: "+appliancesturnedoff.size()+"\n");
				}
				
				if(appliancesturnedoff.size()!=0)
				{
					addToReport(appliancesturnedoff,"Report.txt",callCount);
				}
				
				//callCount++;
				//System.out.println(":::::::::::::::::::: Hour:"+minute+"Simultation: "+callCount+"::::::::::::::::::");
				//nextArrivalTime=minute+getRandInt('E', ARRIVAL_MEAN, 0);
			}
			minute++;
		}

		System.out.println("Total Number of Appliances affected: "+totalappefectees.size());
		System.out.println("Printing out detailed report in text file Report.txt\n");
		
		//System.out.println("Number of events = " + callCount);
	}
	else if(menu==6)
	{
		exitflag=false;
	}
	else{
		System.out.println("Incorrect option.");
	}
    }
  }
}