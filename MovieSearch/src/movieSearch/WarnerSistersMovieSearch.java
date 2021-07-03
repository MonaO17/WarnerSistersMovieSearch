package movieSearch;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class WarnerSistersMovieSearch {
	static Scanner scan = new Scanner(System.in);

	/** Main method: greets customer, invokes method menuAsk(), says goodbye to the user and closes the program 
	 */
	public static void main (String [] args) {
		//Welcome visitor
		System.out.println("\t\t\tWelcome to \"Warner Sisters - Movie Search \"! \n\t\tThe place to search "
				+ "and find the best entertaining movies. \n\tAbout half a million movies are waiting to "
				+ "be discovered by you!!! Have fun :) ");

		while (menuAsk()); 
		System.out.println("\t\tGood Bye! Thank you for using the \"Warner Sisters Movie Search\"!");
		scan.close();
		System.exit(0);
	}

	/** Method menuAsk(): allows the customer to choose between all possible program options, 
	 * these are: find movie, add movie, close program
	 * All other methods in which the options mentioned are implemented are invoked from this menu
	 */
	private static boolean menuAsk() {
		//declare variables
		String menuAnswer, movieSearch = null, database = null;
		int menu=0;

		// Create and give out the menu to search, add a movie or close program
		System.out.println("\n\t\t\t\tWhat would you like to do? \n\tSearch movie (1)\t\tAdd movie "
				+ "(2)\t\tClose program (3)");

		try {
			menuAnswer = scan.nextLine();
			menu = Integer.valueOf(menuAnswer);

			if(menu == 1) {
				movieSearch= movieSearch();
				database= database();
				System.out.println(searchDatabase(movieSearch,database));
			}else if(menu == 2) {
				System.out.println(addMovie());
			}else if(menu == 3) {
				return false;
			}else {
				System.out.println("\nSorry - invalid input!\n");					
			}
		}catch(Exception e){
			System.out.println("Invalid input!\n");	
		}
		return true;
	}

	/**Method movieSearch(): lets customer enter search request (title or director)
	 * returns String movieSearch initialized with the search request
	 */
	private static String movieSearch(){
		//declare variables
		String movieSearch="";

		//let user type in search request
		System.out.println("\t\t\t\tWelcome to the movie-search!\n\t\t\t    Please type in a title or director: ");
		movieSearch = scan.nextLine();

		//return movieSearch
		return movieSearch;
	}

	/**Method database(): lets customer choose the database, in which he/she wants to search
	 * returns String database initialized with the chosen database
	 */
	private static String database() {
		//declare variables
		String dB, database="";
		int db=0;

		//let user pick database
		System.out.println("\t\t\t\tWhere would you like to search?\n\t\t\tIMDB-database(1) "
				+ "our customer-database(2)?");
		dB=scan.nextLine();
		db=Integer.valueOf(dB);

		if(db == 1) {
			database="C:\\OoP1\\Database_IMDB.csv";
		} else if (db == 2) {
			database="InterneDatenbank.txt";
			System.out.println("\n***Beware! The content of our customer database is added by our "
					+ "customers and is not checked for accuracy!***\n");
		} else {
			System.err.println("Error");
		}

		//return database
		return database;
	}

	/**Method searchDatabase(movieSearch,database) receives search request + desired database of the customer
	 * Searches the search request (String movieSearch) in the chosen database
	 * returns all matches 
	 */
	private static String searchDatabase(String movieSearch, String database) {
		//declare variables
		Scanner fileScan=null, movieIMDBScan;
		String movieIMDB, search="", movie="", matching="";
		String [] match = new String[15];
		int j = 0, i = 0;
		boolean found;

		// welcome text
		System.out.println("\t\t Please wait a moment! The database is being searched for: "+movieSearch);

		//open IMDB database file
		try {
			fileScan = new Scanner(new File(database));
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Sorry, something went wrong! \nYour file could not be found! \nAttention! If you "
					+ "are searching in our customer-database: there might not have been any movies added yet!");
		}

		while (fileScan.hasNext()) { 						
			movieIMDB = fileScan.nextLine(); 
			movieIMDBScan = new Scanner(movieIMDB); 		
			movieIMDBScan.useDelimiter(",");
			j=0;
			search="";
			found = false;							

			//check if search matches a movie in database
			while (movieIMDBScan.hasNext()) {				

				match[j] = movieIMDBScan.next();
				matching=match[j];
				if (matching.toLowerCase().contains(movieSearch.toLowerCase())) { 
					found = true; 							
				}

				search += match[j]+",   "; 				
				j++;											
			}

			//If match found, return match!
			if (found == true) {						
				i++;
				movieIMDBScan.close();
				movie += "\nMovie "+i+" \t"+ search;
				// prints out number, title and director from the searched term
			}

		}
		if (movie != "") {
			return ("\tWe found the following movies: "+movie);
		}
		fileScan.close();

		//If no match found, return: 
		return ("\t\t\tSorry, \""+movieSearch + "\" could not be found!"); 
	}

	/**AddMovie () method: With this method, the customer can add a movie to the customer database
	 * The customer can enter the movie title and director, check  spelling + add movie to the customer database
	 * Returns a sentence in which the customer is thanked for having added the movie title (String addMovieTitle) 
	 * and director (addMovieDirector)
	 */
	private static String addMovie() {
		//declare variables
		String fileName="InterneDatenbank.txt", addMovieTitle, addMovieDirector, answerCorrect;																					
		PrintWriter writer=null;	
		FileWriter addSomething=null;
		int answer;	

		//create file
		try {			
			addSomething= new FileWriter(fileName, true);		//append = true, write over = false
			writer= new PrintWriter (addSomething);	
		} catch (FileNotFoundException e) {						
			//e.printStackTrace();
			System.out.println("Sorry, something went wrong! \nYour file could not be found!");
		} catch (IOException e) {								
			//e.printStackTrace();
			System.out.println("Sorry, something went wrong! \nAn input or output operation has failed!");
		}

		//let user enter movie
		do{ 
			System.out.println("\t\t\t   Please type in the title of the movie: ");
			addMovieTitle = scan.nextLine();
			System.out.println("\t\t\tPlease type in the name of the movie-director: ");
			addMovieDirector = scan.nextLine();

			///let user check spelling
			System.out.println("\tPlease check your spelling, did you want to add the movie: \""+addMovieTitle+"\" from "+addMovieDirector+"?"
					+ "\n\t\tYes, that´s correct (1)\t  No, I want to type it in again!(2)");
			answerCorrect=scan.nextLine();
			answer=Integer.valueOf(answerCorrect);

			if(answer==1) {

			}else if (answer==2) {
				System.out.println("Alright!"); 
			}else {
				System.out.println("\t\tSorry, that was not a valid answer! Please enter the movie again!");
			}
		} while (answer!=1);

		//write addMovieTitle and addMovieDirector in text document
		writer.print(addMovieTitle); 
		writer.println(", " + addMovieDirector);

		//close writer
		writer.close();	
		return "\t\tGreat! Thank you for adding the movie: \""+addMovieTitle+"\" from "+addMovieDirector+"!";

	}

}
