import java.util.Scanner;
public class EventSimulator {
	public static void main(String[] args) {
		final int ARRIVAL_MEAN=5;
		int simulationLength=0, minute=0, nextArrivalTime, callCount=0; 
		Scanner input = new Scanner(System.in);
	
		while (simulationLength<=0)	{
			System.out.print("How many minutes long is the simulation? "); 

			while (!input.hasNextInt()) {
				input.next();
				System.out.print("Please enter an integer: ");
			}
			simulationLength = input.nextInt();
		}

		nextArrivalTime = minute + getRandInt('E', ARRIVAL_MEAN, 0);
		
		while(minute<=simulationLength) {
			while ((minute == nextArrivalTime) && (minute<=simulationLength)) {
				callCount++;
				System.out.println("Minute:"+minute+" Event#"+callCount);
				nextArrivalTime=minute+getRandInt('E', ARRIVAL_MEAN, 0);
			}
			minute++;
		} 
		System.out.println("Number of events = " + callCount);
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
}