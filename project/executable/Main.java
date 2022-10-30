/**
 * 
 */
package project.executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import project.airline.Airline;
import project.airport.Airport;

/**
 * @author HanaaZaqout
 *
 */
public class Main {

	static Airport currentAirport;
	static Airport toAirport;
	Airline palestinianAirlines;

	public static void main(String[] args) {

		// read input
		Scanner in = null;
		String inputFile = args[0];
		String outputFile = args[1];
//		File file = new File("myinput.txt");
		File file = new File(inputFile);

		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		PrintStream out = null;
		try {
//			out = new PrintStream(new FileOutputStream("myoutput.txt"));
			out = new PrintStream(new FileOutputStream(outputFile));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		System.setOut(out);
//		Scanner in= new Scanner(System.in);

		// create Airline object
		Airline palestinianAirlines = new Airline(in);
		// create objects from given input
		palestinianAirlines.createObjects();
		// created to be used later for larger scale operations and many passengers
		palestinianAirlines.createFlightsTable();
		// constants to keep track of flights
		int currentAircraftIndex;
		int num = 0;
		// loop over flights table
		for (var idair : palestinianAirlines.getFlightsTable().entrySet()) {
			Long curId = idair.getKey();
			// it should find destination with the higher audience. this would be beneficial
			// later to develop projects for more passengers
			Long fuId = palestinianAirlines.largestAudience(palestinianAirlines.getFlightsTable().get(curId));
			if (fuId > 0L) {
				// variables to keep track of current and future airport
				Airport currentAirport = Airline.getAirports().get(curId);
				Airport toAirport = Airline.getAirports().get(fuId);
				// to develop: according to distance choose the aircraft.
				if (palestinianAirlines.widebodyAircraftCreation(currentAirport)) {
					currentAircraftIndex = Airline.getAircrafts().size() - 1;

					// this while loop would do all possible flights that a single aircraft can
					// perform.
					// just by traveling from current airport to future airport till it reaches to
					// an empty airport
//					while (palestinianAirlines.largestAudience(palestinianAirlines.getFlightsTable().get(toAirport.getID())) > 0L) {
					num++;

					// does the flight by calling flight method. and updating the currentAirport
					currentAirport = palestinianAirlines.flight(currentAircraftIndex, currentAirport, toAirport);
					// update future airport
					toAirport = Airline.getAirports().get(palestinianAirlines
							.largestAudience(palestinianAirlines.getFlightsTable().get(currentAirport.getID())));
					// flights table should be updated as passengers' and aircrafts' location has
					// been changed
					palestinianAirlines.createFlightsTable();
//						
					// I will consider one flight right now
					if (num == 1) {
						break;
					}
				}

			}
			
//			}

		}

	}

}
