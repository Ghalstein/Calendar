import java.util.Calendar;
public class Cal { 

   /***************************************************************************
    *  Given the month, day, and year, return which day
    *  of the week it falls on according to the Gregorian calendar.
    *  For month, use 1 for January, 2 for February, and so forth.
    *  Returns 0 for Sunday, 1 for Monday, and so forth.
	*  
	*  Explanation: Having a separate method helps encapsulate the code. It 
	*  helps with readability because it allows the main method to not have so
	*  much code in its control flow and keeps the code more robust.
    ***************************************************************************/
    public static int day(int month, int day, int year) {
        int y = year - (14 - month) / 12;
        int x = y + y/4 - y/100 + y/400;
        int m = month + 12 * ((14 - month) / 12) - 2;
        int d = (day + x + (31*m)/12) % 7;
        return d;
    }

    // return true if the given year is a leap year
    public static boolean isLeapYear(int year) {
        if  ((year % 4 == 0) && (year % 100 != 0)) return true;
        if  (year % 400 == 0) return true;
        return false;
    }
	
	// centers the header
	public static String center(String month, int year){
		String weeks = " Su  Mo  Tu  We  Th  Fr  Sa"; 
		int baseLength = weeks.length();
		String header = month + " " + year;
		int headerLength = header.length();
		int goal = (headerLength+baseLength)/2;
		String spaces = " ";
		for(int i = headerLength; i<goal; i++){
			spaces += " ";
		}
		return spaces;
	}
	
	// prints the calendar and reduces redundant code
	public static void printCal(int month, int year){
		String[] months = {
				"",                               // leave empty so that months[1] = "January"
				"January", "February", "March",
				"April", "May", "June",
				"July", "August", "September",
				"October", "November", "December"
			};

			// days[i] = number of days in month i
			int[] days = {
				0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
			};
			
			// check for leap year
			if (month == 2 && isLeapYear(year)) days[month] = 29;
			if(month>0 && month<13 && year>0){ // checks the year and month entered is correct
				// print calendar header
				System.out.println(center(months[month], year) + months[month] + " " + year); // pritns the month and year 
				System.out.println(" Su  Mo  Tu  We  Th  Fr  Sa"); // Abbreviated each day of the week as two letters

				// starting day
				int d = day(month, 1, year);

				// print the calendar
				for (int i = 0; i < d; i++)
					System.out.print("    "); // added an extra space to compensate for the lengthier days of the week string
				for (int i = 1; i <= days[month]; i++) {
					System.out.printf(" %2d ", i); // a space added before the number to help keep the calendar more orderly.
					if (((i + d) % 7 == 0) || (i == days[month])) System.out.println();
				}

			}
			else{ // deals with the incorrectly entered year or month
					System.out.println("Sorry you have entered an incorrect year or month.");
					System.exit(1);
			}
		}
	
    public static void main(String[] args) {
		if (args.length > 2) {
            System.out.println("Usage: cal [month] [year]");
            System.exit(1);
		}
		Calendar cal = Calendar.getInstance();
		if(args.length == 0){ // when no arguments are passed 
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			printCal(month, year); // prints current month and year calendar

		}else if (args.length == 2){ // when two arguments are passed
			int month = Integer.parseInt(args[0]); 	
			// month (Jan = 1, Dec = 12)
			int year = Integer.parseInt(args[1]);     // year
			// months[i] = name of month i
			printCal(month, year); // prints specified month and year calendar
		}
	} 
}
	