/**
 * 
 */
package project.airline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import project.airline.aircraft.PassengerAircraft;
import project.airline.concrete.JetPassengerAircraft;
import project.airline.concrete.PropPassengerAircraft;
import project.airline.concrete.RapidPassengerAircraft;
import project.airline.concrete.WidebodyPassengerAircraft;
import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;
import project.airport.RegionalAirport;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;

/**
 * the only class that should be imported to the main. all aircraft objects
 * should be held in this class. Also, Revenue and expenses will also be
 * calculated inside this class.
 * 
 * @author Hanaa Zaqout
 *
 */
public class Airline {
	/**
	 * fields from given input
	 */
	private Scanner in;
	/**
	 * Maximum number of aircrafts this airline can hold. Will be given as an input.
	 */
	public static int maxAircraftCount;
	/**
	 * Maximum number of airports this airline can hold. Will be given as an input.
	 */
	public static int nofAirports;
	/**
	 * Maximum number of passengers this airline can hold. Will be given as an
	 * input.
	 */
	public static int nofPassengers;
	/**
	 * Operational cost value for this airline. Will be given as an input.
	 */
	public static double airlineOperationalCost;
	/**
	 * Operational cost value for each type of aircraft. Will be given as an input.
	 */
	public static double propOperationFee, wideOperationFee, rapidOperationFee, jetOperationFee;
	/**
	 * HashMap holds airports' id as keys and airports' objects as values
	 */
	private static HashMap<Long, Airport> airports = new HashMap<Long, Airport>();
	/**
	 * ArrayList holds aircrafts of this airline the index of the aircraft in this
	 * ArrayList will be used to access it in the project
	 */
	private static ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	/**
	 * HashMap has this structure {current airport id: {destination airport id:
	 * ArrayList of passengers,...},....}
	 */
	private static HashMap<Long, HashMap<Long, ArrayList<Passenger>>> flightsTable = new HashMap<Long, HashMap<Long, ArrayList<Passenger>>>();
	/**
	 * expenses and revenue will be updated while the project is runnimg
	 */
	private static double expenses, revenue;

	/**
	 * Airline2 constructor with no parameter to allow the access of methods in this
	 * class in other classes
	 */
	public Airline() {
	}

	/**
	 * Airline2 constructor to be called in the main class with initially expenses=0
	 * and revenue=0
	 * 
	 * @param in
	 */
	public Airline(Scanner in) {
		this.in = in;
		expenses = 0;
		revenue = 0;
	}

	/**
	 * aircraftType will be used in printing results to the log
	 * 
	 * @param aircraftIndex
	 * @return aircraftType is int refers to specific aircraft type
	 */
	public int aircraftType(int aircraftIndex) {
		PassengerAircraft myAircraft = aircrafts.get(aircraftIndex);
		int aircraftType = 0;
		if (myAircraft instanceof PropPassengerAircraft) {
			aircraftType = 0;
		} else if (myAircraft instanceof WidebodyPassengerAircraft) {
			aircraftType = 1;
		} else if (myAircraft instanceof RapidPassengerAircraft) {
			aircraftType = 2;
		} else if (myAircraft instanceof JetPassengerAircraft) {
			aircraftType = 3;
		}
		return aircraftType;
	}

	static int flightnum = 0;

	/**
	 * <h3>flight is a sequence of calling methods:</h3>
	 * <li>{@link #seatSetting(int, Airport, Airport)}</li>
	 * <li>{@link #fueling(int, Airport)}</li>
	 * <li>{@link #loadPassengers(int, Airport, Airport)}</li>
	 * <li>{@link #fly(Airport, int)}</li>
	 * <li>{@link #unloadPassengers(int)}</li>
	 * <li>updates currentAirport</li>
	 * <li>update toAirport according to {@link #largestAudience(HashMap)}</li>
	 * 
	 * @param aircraftIndex
	 * @param currentAirport
	 * @param toAirprot
	 * @return
	 */
	public Airport flight(int aircraftIndex, Airport currentAirport, Airport toAirport) {
		this.seatSetting(aircraftIndex, currentAirport, toAirport);
		if (this.fueling(aircraftIndex, toAirport)) {
			this.loadPassengers(aircraftIndex, currentAirport, toAirport);
			if (this.fly(toAirport, aircraftIndex)) {
				this.unloadPassengers(aircraftIndex);
//				System.out.println("expense " + expenses);
//				System.out.println("revenue " + revenue);
				System.out.println(this.getProfit());
				flightnum++;
//				System.out.println("______________________" + flightnum);
				currentAirport = aircrafts.get(aircraftIndex).getCurrentAirport();
				return currentAirport;
			}
		}
		
		return currentAirport;
	}
	/**
	 * use for scatter input
	 */
	ArrayList <Airport> visited=new ArrayList<Airport>();
	Airport intermediate(Airport currentAirport, Airport toAirport) {
		// Iterating HashMap through for loop
		for (HashMap.Entry<Long, Airport> airport : airports.entrySet()) {
			if (currentAirport.getDistance(airport.getValue()) < 14000
					&& currentAirport.getDistance(airport.getValue()) < currentAirport.getDistance(toAirport)
					&& currentAirport != airport.getValue() && visited.contains(airport.getValue())==false) {
//				System.out.println("yessss");
				return airport.getValue();
			}

		}
		return currentAirport;
	}

	/**
	 * creates object of {@link PropPassengerAircraft} class. adds created object to
	 * {@link #aircrafts}. aircraft count should not exceed
	 * {@link #maxAircraftCount}
	 * 
	 * @param airport
	 * @param toAirport
	 */
	boolean propAircraftCreation(Airport airport) {
		double pre = this.getProfit();
		if (maxAircraftCount > aircrafts.size()) {
			PassengerAircraft myAircraft = new PropPassengerAircraft(airport);
			aircrafts.add(myAircraft);
			System.out.println(0 + " " + airport.getID() + " " + 0);
//					+ " = " + (this.getProfit() - pre));
			return true;
		}
		return false;
	}

	/**
	 * creates object of {@link JetPassengerAircraft} class. adds created object to
	 * {@link #aircrafts}. aircraft count should not exceed
	 * {@link #maxAircraftCount}
	 * 
	 * @param airport
	 * @param toAirport
	 */
	public boolean jetAircraftCreation(Airport airport) {
		double pre = this.getProfit();
		if (maxAircraftCount > aircrafts.size()) {
			PassengerAircraft myAircraft = new JetPassengerAircraft(airport);
			aircrafts.add(myAircraft);
			System.out.println(0 + " " + airport.getID() + " " + 3);
//					+ " = " + (this.getProfit() - pre));
			return true;
		}
		return false;
	}

	/**
	 * creates object of {@link RapidPassengerAircraft} class. adds created object
	 * to {@link #aircrafts}. aircraft count should not exceed
	 * {@link #maxAircraftCount}
	 * 
	 * @param airport
	 * @param toAirport
	 */
	public boolean rapidAircraftCreation(Airport airport) {
		double pre = this.getProfit();
		if (maxAircraftCount > aircrafts.size()) {
			PassengerAircraft myAircraft = new RapidPassengerAircraft(airport);
			aircrafts.add(myAircraft);
			System.out.println(0 + " " + airport.getID() + " " + 2);
//					+ " = " + (this.getProfit() - pre));
			return true;
		}
		return false;
	}

	/**
	 * creates object of {@link WidebodyPassengerAircraft} class. adds created
	 * object to {@link #aircrafts}. aircraft count should not exceed
	 * {@link #maxAircraftCount}
	 * 
	 * @param airport
	 * @param toAirport
	 */
	public boolean widebodyAircraftCreation(Airport airport) {
		double pre = this.getProfit();
		if (maxAircraftCount > aircrafts.size()) {
			PassengerAircraft myAircraft = new WidebodyPassengerAircraft(airport);
			aircrafts.add(myAircraft);
			System.out.println(0 + " " + airport.getID() + " " + 1);
//					+ " = " + (this.getProfit() - pre));
			return true;
		}
		return false;
	}

	/**
	 * reads {@link #maxAircraftCount}, {@link #nofAirports},
	 * {@link #nofPassengers}, {@link #propOperationFee} ,{@link #wideOperationFee},
	 * {@link #rapidOperationFee}, {@link #jetOperationFee}, and
	 * {@link #airlineOperationalCost} from given input in main
	 */
	private void createConstants() {

		if (in.hasNext()) {
			String[] limits = in.nextLine().split(" ");
			maxAircraftCount = Integer.valueOf(limits[0]);
			nofAirports = Integer.valueOf(limits[1]);
			nofPassengers = Integer.valueOf(limits[2]);
		}

		if (in.hasNext()) {
			String[] operations = in.nextLine().split(" ");
			propOperationFee = Double.valueOf(operations[0]);
			wideOperationFee = Double.valueOf(operations[1]);
			rapidOperationFee = Double.valueOf(operations[2]);
			jetOperationFee = Double.valueOf(operations[3]);
			airlineOperationalCost = Double.valueOf(operations[4]);
		}
	}

	/**
	 * Creates Airport objects by reading given input. adds Airport objects to
	 * {@link #airports}
	 */
	private void createAirportObjects() {
		for (int ai = 0; ai < nofAirports; ai++) {

			if (in.hasNextLine()) {
				String[] line = in.nextLine().split(", ");
				String[] beginLine = line[0].split(" : ");
				String airportType = beginLine[0];
				long ID = Long.valueOf(beginLine[1]);
				double x = Double.valueOf(line[1]);
				double y = Double.valueOf(line[2]);
				double fuelCost = Double.valueOf(line[3]);
				double airportOperationFee = Double.valueOf(line[4]);
				int aircraftCapacity = Integer.valueOf(line[5]);
				ArrayList<Passenger> passengers = new ArrayList<Passenger>();

				switch (airportType) {
				case "hub": {
					airports.put(ID,
							new HubAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity, passengers));
					break;
				}
				case "major": {
					airports.put(ID,
							new MajorAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity, passengers));
					break;
				}

				case "regional": {
					airports.put(ID,
							new RegionalAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity, passengers));
					break;
				}
				}
			}
		}
	}

	/**
	 * Create passenger objects from the given input. adds these objects to
	 * passengers' arraylist that is hold in Airports objects stored in
	 * {@link #airports}.
	 * to keep it simple at this stage. I will deal with one passenger
	 */
	private void createPassengerObjects() {
		int passengersToTakeCareOf = 0;
		for (int pi = 0; pi < nofPassengers; pi++) {

			if (in.hasNextLine()) {
				passengersToTakeCareOf++;
				String[] line = in.nextLine().split(", ");
				String[] beginLine = line[0].split(" : ");
				String passengerType = beginLine[0];
				long ID = Long.valueOf(beginLine[1]);
				int weight = Integer.valueOf(line[1]);
				int baggageCount = Integer.valueOf(line[2]);
				ArrayList<Airport> destinations = new ArrayList<Airport>();

				for (int i = 3; i < line.length; i++) {
					String val = line[i].replaceAll("[^0-9]", ""); // get red of [ ]
					destinations.add(airports.get(Long.valueOf(val)));
				}

				/*
				 * {Airport ID: Airport object} airport object: id x y passengers arraylist
				 * passengers arraylist: passenger passenger passenger passenger: id weight
				 * baggage count distenations distenations: {airport object, airport object,
				 * airport object}
				 */
				if (passengersToTakeCareOf == 1) {
					switch (passengerType) {
					case "economy": {
						Airport s = airports.get(destinations.get(0).getID());
						s.addPassenger(new EconomyPassenger(ID, weight, baggageCount, destinations));
						airports.put(destinations.get(0).getID(), s);
						break;
					}

					case "business": {
						Airport s = airports.get(destinations.get(0).getID());
						s.addPassenger(new BusinessPassenger(ID, weight, baggageCount, destinations));
						airports.put(destinations.get(0).getID(), s);
						break;
					}
					case "first": {
						Airport s = airports.get(destinations.get(0).getID());
						s.addPassenger(new FirstClassPassenger(ID, weight, baggageCount, destinations));
						airports.put(destinations.get(0).getID(), s);
						break;
					}
					case "luxury": {
						Airport s = airports.get(destinations.get(0).getID());
						s.addPassenger(new LuxuryPassenger(ID, weight, baggageCount, destinations));
						airports.put(destinations.get(0).getID(), s);
						break;
					}
					}
				}
			}
		}
	}

	/**
	 * calls {@link #createAirportObjects()} {@link #createConstants()}
	 * {@link #createPassengerObjects()} that read input and store the given data in
	 * fields and data structures.
	 */
	public void createObjects() {
		createConstants();
		createAirportObjects();
		createPassengerObjects();
	}

	/**
	 * put passengers who are in the same airport aiming to reach the same
	 * destination in groups
	 */
	public void createFlightsTable() {

		for (Long a : airports.keySet()) {
			HashMap<Long, ArrayList<Passenger>> airportsTable = new HashMap<Long, ArrayList<Passenger>>();
			for (Long da : airports.keySet()) {
				if (a != da) {
					ArrayList<Passenger> passengersGroup = new ArrayList<Passenger>();
					for (Passenger p : airports.get(a).passengers) {
						if (p.getDestinations().size() > 1) {
							if (p.getDestinations().get(1).getID() == da) {
								passengersGroup.add(p);
							}
						}
					}
					airportsTable.put(da, passengersGroup);
				}
			}
			flightsTable.put(a, airportsTable);
		}

	}

	/**
	 * aims to find the largest number of passengers who want to go into flight with
	 * same destination by iterating over value of the current airport id in the
	 * {@link #flightsTable}
	 * 
	 * @param stations
	 * @return destination's airport id that has the highest audience
	 */
	public Long largestAudience(HashMap<Long, ArrayList<Passenger>> stations) {
		int maxSize = 0;
		Long toAirportId = (long) 0;
		for (Long toAirportIds : stations.keySet()) {
			if (stations.get(toAirportIds).size() > maxSize) {
				maxSize = stations.get(toAirportIds).size();
				toAirportId = toAirportIds;
			}
		}
		if (maxSize > 0) {
			return toAirportId;
		}
		return 0L;

	}

	/**
	 * fly the aircraft from its current airport to toAirport.
	 * <code> if (fly operation completed){return true} else{return false}</code>
	 * <h3>a flight can not happen because of</h3>
	 * <li>range limitations</li>
	 * <li>the other airport is full</li>
	 * <li>aircraft is empty</li>
	 * <li>the weight this aircraft exceeds its maxWeight</li> if fly is valid:
	 * {@link #expenses} += flight cost {@link #expenses} += running cost each time
	 * this method is called
	 * <h4>running cost of an airline</h4> = the operationalCost of airline * the
	 * number of aircraft the airline has
	 * 
	 * @param toAirport
	 * @param aircraftIndex is the index at which the aircraft is stored in the
	 *                      {@link #aircrafts}
	 * @return
	 */
	boolean fly(Airport toAirport, int aircraftIndex) {
		double pre = this.getProfit();
		double runningCost = airlineOperationalCost * aircrafts.size();
		expenses += runningCost;
		if (aircrafts.get(aircraftIndex).checkFly(toAirport))
			if (aircrafts.get(aircraftIndex).getPassengers().size() > 0) {
				expenses += aircrafts.get(aircraftIndex).fly(toAirport);
				System.out.println(1 + " " + toAirport.getID() + " " + aircraftIndex);

				return true;
			}
		return false;
	}

	/**
	 * loads a passenger from a given airport to an aircraft. <code>if </code>(load
	 * operation is successful)<code>{return ture} else{return false}</code> If a
	 * passenger can be loaded: {@link #expense}+= the loading cost. Note:
	 * passengers are loaded to seats of their exact level
	 * 
	 * @param passenger     is the passenger to load
	 * @param airport       is the airport where the passenger is
	 * @param aircraftIndex is the index of the aircraft to which the passenger will
	 *                      be loaded.
	 * @return
	 */
	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		double pre = this.getProfit();
		if (aircrafts.get(aircraftIndex).checkLoadPassenger(passenger) == true) {
			expenses += aircrafts.get(aircraftIndex).loadPassenger(passenger);
			System.out.println(4 + " " + passenger.getID() + " " + aircraftIndex + " "
					+ aircrafts.get(aircraftIndex).getCurrentAirport().getID());
//					+ " "+ aircrafts.get(aircraftIndex).seatType(passenger) 
//					+ " = " + (this.getProfit() - pre));
			return true;
		}
		return false;
	}

	/**
	 * loads passengers who are interested in this flight to aircraft
	 * 
	 * @param aircraftIndex
	 * @param currentAirport
	 * @param toAirport
	 */
	void loadPassengers(int aircraftIndex, Airport currentAirport, Airport toAirport) {
		int num = 0;
		for (Passenger p : flightsTable.get(currentAirport.getID()).get(toAirport.getID())) {
			num++;
			if (num == 2) {
				break;
			}
//			if (aircrafts.get(aircraftIndex).getFuelConsumption(currentAirport.getDistance(toAirport)) > aircrafts
//					.get(aircraftIndex).getFuel()) {
//				double prerevenue = revenue;
//				Passenger lastLoadedPassenger = aircrafts.get(aircraftIndex).getPassengers()
//						.get(aircrafts.get(aircraftIndex).getPassengers().size() - 1);
//				this.unloadPassenger(lastLoadedPassenger, aircraftIndex);
//				revenue = prerevenue;
//				break;
//			}
			this.loadPassenger(p, currentAirport, aircraftIndex);
		}
	}

	/**
	 * unloads a passenger from a given aircraft. <code>if </code>(unload operation
	 * is successful)<code>{return true;} else{return false;}</code>
	 * <h3>unload operation cannot happen if</h3>
	 * <li>passenger cannot disembark at the aircraft’s airport (not a
	 * destination)</li>
	 * 
	 * if unload is successful: {@link #revenue}+= ticket price
	 * 
	 * @param passenger     is the passenger to unload
	 * @param aircraftIndex is the index of the aircraft which the passenger will be
	 *                      unloaded.
	 * @return
	 */
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		double pre = this.getProfit();
		if (passenger.isDestination(aircrafts.get(aircraftIndex).getCurrentAirport())) {
			revenue += aircrafts.get(aircraftIndex).unloadPassenger(passenger);
			System.out.println(5 + " " + passenger.getID() + " " + aircraftIndex + " "
					+ aircrafts.get(aircraftIndex).getCurrentAirport().getID());
//					+ " = " + (this.getProfit() - pre));
			return true;
		}
		return false;
	}

	/**
	 * unloads all passengers on this aircraft
	 * 
	 * @param aircraftIndex
	 */
	void unloadPassengers(int aircraftIndex) {
//		for (Passenger p : aircrafts.get(aircraftIndex).getPassengers()) {
		for (Iterator<Passenger> iterator = aircrafts.get(aircraftIndex).getPassengers().iterator(); iterator
				.hasNext();) {
			Passenger p = iterator.next();
			this.unloadPassenger(p, aircraftIndex);
			iterator.remove();
		}
	}

	/**
	 * if addedfuelCost==-1 this means we need to make connection for aircraft to
	 * refuel in the road fuels aircraft.
	 * 
	 * {@link #expenses}+=addedfuelCost
	 * 
	 * @param aircraftIndex is the index of the aircraft which will be fueled
	 * @return true if the aircraft has fuel enough to reach its destination
	 *
	 */
	boolean fueling(int aircraftIndex, Airport toAirport) {
		double pre = this.getProfit();
		double prefuel = aircrafts.get(aircraftIndex).getFuel();
		double addedfuelCost = aircrafts.get(aircraftIndex).refueling(toAirport);
		double afterfuel = aircrafts.get(aircraftIndex).getFuel();
		if (addedfuelCost >= 0) {
			if (addedfuelCost > 0) {
				expenses += addedfuelCost;
				System.out.println(3 + " " + aircraftIndex + " " + (afterfuel - prefuel));
//						+ " = " + (this.getProfit() - pre));
			}
			return true;
		} else if (addedfuelCost == -1) {
			return false;
		}
		return false;
	}

	/**
	 * this method put seats in aircraft according to passengers' level who are in
	 * this flight( check {@link #flightsTable})
	 * 
	 * @param aircraftIndex
	 * @param currentAirport
	 * @param toAirport
	 * @return
	 */
	boolean seatSetting(int aircraftIndex, Airport currentAirport, Airport toAirport) {
		int economy = 0, business = 0, firstClass = 0;
		double pre = this.getProfit();
		for (Passenger p : flightsTable.get(currentAirport.getID()).get(toAirport.getID())) {
			if (p instanceof EconomyPassenger) {
				economy++;
			} else if (p instanceof BusinessPassenger) {
				business++;
			} else if (p instanceof FirstClassPassenger || p instanceof LuxuryPassenger) {
				firstClass++;
			}
		}

		if (aircrafts.get(aircraftIndex).setSeats(economy, business, firstClass)) {
			System.out.println(2 + " " + aircraftIndex + " " + economy + " " + business + " " + firstClass);
//					+ " = "+ (this.getProfit() - pre));
			return true;
		}
		return false;

	}

	/**
	 * 
	 * @return airports
	 */
	public static HashMap<Long, Airport> getAirports() {
		return airports;
	}

	/**
	 * 
	 * @return aircrafts
	 */
	public static ArrayList<PassengerAircraft> getAircrafts() {
		return aircrafts;
	}

	/**
	 * 
	 * @return expenses
	 */
	public double getExpenses() {
		return expenses;
	}

	/**
	 * 
	 * @param expenses
	 */
	public void setExpenses(double expenses) {
		Airline.expenses = expenses;
	}

	/**
	 * 
	 * @return revenue
	 */
	public double getRevenue() {
		return revenue;
	}

	/**
	 * 
	 * @param revenue
	 */
	public void setRevenue(double revenue) {
		Airline.revenue = revenue;
	}

	/**
	 * 
	 * @return profit
	 */
	public double getProfit() {
		return revenue - expenses;
	}

	/**
	 * 
	 * @param airports
	 */
	public static void setAirports(HashMap<Long, Airport> airports) {
		Airline.airports = airports;
	}

	/**
	 * 
	 * @return flightsTable
	 */
	public HashMap<Long, HashMap<Long, ArrayList<Passenger>>> getFlightsTable() {
		return flightsTable;
	}

	/**
	 * 
	 * @param flightsTable
	 */
	public void setFlightsTable(HashMap<Long, HashMap<Long, ArrayList<Passenger>>> flightsTable) {
		Airline.flightsTable = flightsTable;
	}

}
