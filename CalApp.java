/******************************************************************************          
 *  Compilation:  javac Calendar.java                                                    
 *  Execution:    java Calendar [month] [year]                                               
 *                                                                                       
 *  This program takes 0, 1, or 2 command-line arguments, and do the following.          
 *  0 argument: prints the calendar for today's month and year                           
 *  1 argument: prints the calendars for all 12 months of that year                      
 *  2 arguments: prints the calendar for that month and year                             
 *                                                                                       
 *  For the 1 argument case, the 12 months are displayed in a 4x3 format,                
 *  each row consists of 3 consecutive months.        
 * Explanation: The program was originally printing null because the variable was not 
 * initalized within the loop. To fix it I just made loop that initalized all indexes of 
 * the array. For making the displayCalendar() method my goal was to reuse the oneMonth()
 * method so that the program remains readable and efficient. I had the method return a new array of a
 * String array. It accepted the year, row and collumns as parameters. The program called
 * the oneMonth() method and added the specified month in correct order into the array.
 * The main method just required adding a scanner to accept the user preferences on rows and collumns 
 * and a loop that printed out each index of String array in order. It is possible to call displayCalendar() 
 * for the args.length 0 & 2. However, for my case it would be difficult since the only input for the method
 * I made are the rows, collumns and year. My method also directly calls the oneMonth()
 * method. In order to implement this I would need to make it possible to call a month.
 * This can be done by overloading the method and adding another paramter for the month.
 * Thus, calling the method would just end up directly calling the oneMonth() method for the specified month.
 * A few bugs with the current program I had to deal with was requesting a dimension that involved more months than one
 * year. I fixed this issue by restarting the month count after december and increasing the year by 1. This
 * allowed the program to continuously display an n x m of any size and move on to the following year
 * after 12 the months were reached. 
 ******************************************************************************/
import java.util.Calendar;
import java.util.Scanner;
 
public class CalApp {
 
   /***************************************************************************          
    *  Given the month, day, and year, return which day                                  
    *  of the week it falls on according to the Gregorian calendar.                      
    *  For month, use 1 for January, 2 for February, and so forth.                       
    *  Returns 0 for Sunday, 1 for Monday, and so forth.                                 
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
		String weeks = "Su Mo Tu We Th Fr Sa "; 
		int baseLength = weeks.length();
		String header = month + " " + year;
		int headerLength = header.length();
		int goal = (headerLength+baseLength)/2;
		String spaces = "";
		for(int i = headerLength; i<goal; i++){
			spaces += " ";
		}
		return spaces;
	}
    // return a one month calendar as a string array
    public static String[] oneMonth(int month, int year) {
        String[] months = {
            "", // leave empty so that months[1] = "January"                             
            "January", "February", "March",
            "April",   "May",      "June",
            "July",    "August",   "September",
            "October", "November", "December"
        };
        // days[i] = number of days in month i                                        
        int[] days = {
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };
        // check for leap year                                                        
        if (month == 2 && isLeapYear(year))
            days[month] = 29;
 
        String[] result = new String[8];
        // fill month header                                                          
        result[0] = center(months[month], year)+ months[month] + " " + year;
        result[1] = "Su Mo Tu We Th Fr Sa ";
 
        // starting day                                                               
        int d = day(month, 1, year);
		
        int array_index = 2;
		// initalized all of the values in the array to avoid adding to null.
        for(int i = 2; i<result.length; i++)
		result[i] = "";
        // pad the first row with sufficient blanks                                   
        for (int i = 0; i < d; i++)
            result[array_index] += "   ";
        // now the visible dates                                                      
        for (int i = 1; i <= 42-d; i++) {
            // 42 is the maximum number of cells to be filled in,                     
            // and the answer to life's ultimate question.                            
            if (i < 10)
		   result[array_index] += " " + i + " ";
            else if (i > days[month])
		   result[array_index] += "   ";
            else
                result[array_index] += i + " ";
 
            if ((i + d) % 7 == 0)
                array_index++;
        }
        return result;
    }
	// Returns an n-month calendar in a specified format
	public static String[][] displayCalendar(int year, int row, int col){
		String[][] display = new String[8][row*col];
		int m = 0; // month 
		String[] months = {
            "", // leave empty so that months[1] = "January"                             
            "January", "February", "March",
            "April",   "May",      "June",
            "July",    "August",   "September",
            "October", "November", "December"
		};
		for(int i = 0; i < row*col; i++){
			if(m < 12){
				m++;
			}else{
				m = 1;
				year++;
			}
			for(int j = 0; j < 8; j++){
				if(j == 0){
					display[j][i] = oneMonth(m, year)[j] + center(months[m], year);
				}else
				display[j][i] = oneMonth(m, year)[j];
			}
		}
		return display;
		}
		
    public static void main(String[] args) {
        // note: Errors are only partially checked currently.                                        
        if (args.length > 2) {
            System.out.println("Usage: cal [month] [year]");
            System.exit(1);
        }
        if (args.length == 2 || args.length == 0) {
            int month, year;
            if (args.length == 2) {
                // should check to ensure integer command line arguments     
                month = Integer.parseInt(args[0]);    // month (Jan = 1, Dec = 12)    
                year = Integer.parseInt(args[1]);     // year                         
            }
            else {
                Calendar cal = Calendar.getInstance();
                month = cal.get(Calendar.MONTH) + 1;
                year = cal.get(Calendar.YEAR);
            }
            String[] calendar = oneMonth(month, year);
            for (int i = 0; i < 8; i++)
                System.out.println(calendar[i]);
        }
        else if (args.length == 1) {
			int year = Integer.parseInt(args[0]);
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter the amount of rows you want to display:");
			int row = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Please enter the amount of collumns you want to display:");
			int col = scanner.nextInt();
			String[][] cal = displayCalendar(year, row, col);
			int m = 0;
			for(int i = 1; i < row+1; i++){
				for(int j = 0; j < 8; j++){
					System.out.println();
					if(j > 0) m -= col;
					for(int k = 1; k < col+1; k++){
						System.out.print(cal[j][m] + " ");
						m++;
					}
				}
			}
		}
	}
}		