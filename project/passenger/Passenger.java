
package project.passenger;

import java.util.ArrayList;
import java.util.Iterator;

import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;
import project.airport.RegionalAirport;

/**
 * 
 * @author Hanaa Zaqout
 *
 */
public abstract class Passenger {
	/**
	 * Unique ID of the passenger.
	 */
	private final long ID;
	/**
	 * Weight of the passenger.
	 */
	private final double weight;
	/**
	 * The number of pieces of baggage the passenger has used in
	 * {@link #calculateTicketPrice(Airport, double)}
	 */
	private final int baggageCount;
	/**
	 * used in {@link #calculateTicketPrice(Airport, double)}
	 */
	private double connectionMultiplier = 1;
	/**
	 * used in {@link #calculateTicketPrice(Airport, double)} value determines in
	 * {@link #board(int)}
	 */
	private double seatMultiplier;

	/**
	 * Arraylist of destinations. Used in {@link #disembark(Airport, double)}
	 * shrinks when passenger disembarks. Hence, always the element at index 0 is
	 * the current location of passenger
	 */
	private ArrayList<Airport> destinations;

	/**
	 * Constructor forces children of this abstract class
	 * ({@link BusinessPassenger},{@link EconomyPassenger},{@link FirstClassPassenger},{@link LuxuryPassenger})
	 * to specify these values
	 * 
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	Passenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;

	}

	/**
	 * must be implemented in the child classes. it is used in the
	 * {@link #disembark(Airport, double)}
	 * 
	 * @param airport
	 * @param aircraftTypeMultiplier
	 * @return ticket price
	 */
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);

//	/**
//	 * Does the necessary landing operations
//	 * 
//	 * @param aircraft
//	 * @return landing fee
//	 */
//	public abstract double landAircraft(Aircraft aircraft);

	/**
	 * checks if the given Airport is a destination of this passenger
	 * 
	 * @param airport
	 * @return
	 */
	public boolean isDestination(Airport airport) {
		if (this.destinations.contains(airport)) {
			if (this.destinations.indexOf(airport) > 0) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Loads the passenger to an aircraft. specifies the value of
	 * {@link #seatMultiplier}
	 * 
	 * @param seatType
	 * @return
	 */
	public boolean board(int seatType) {
		// expect the passanger state to change
		// remove the passenger from airport object
		this.destinations.get(0).removePassenger(this);

		if (seatType == 0) {
			seatMultiplier = 0.6;
		}

		else if (seatType == 1) {
			seatMultiplier = 1.2;
		}

		else if (seatType == 2) {
			seatMultiplier = 3.2;
		}
		return true;
	}

	/**
	 * Disembarks the passenger to the airport. <code>if</code> (airport is not a
	 * future destination) <code>{return 0;}</code> <code>else</code>
	 * {@link #calculateTicketPrice(Airport, double)} Also, it should set the
	 * airport and reset the necessary multipliers.
	 * 
	 * @param airport
	 * @param aircraftTypeMultiplier
	 * @return the ticket price
	 */
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (this.isDestination(airport) == true) {
			double ticketPrice = calculateTicketPrice(airport, aircraftTypeMultiplier);

			Iterator<Airport> itr = this.destinations.iterator();

			/*
			 * shrink destinations' arrayList
			 */
			while (itr.hasNext()) {
				int x = (int) itr.next().getID();
				if (x == airport.getID()) {
					break;
				}
				if (x != airport.getID())
					itr.remove();
			}

			this.connectionMultiplier=1;
			this.seatMultiplier=1; 
			
			if(this.destinations.size()>1)
			{	//move to airport
			airport.addPassenger(this);}
			
			return ticketPrice;
		}
		return 0;
	}

	/**
	 * Transfers the passenger to another plane. Connects the flight. updates values
	 * of {@link #connectionMultiplier} and {@link #seatMultiplier}
	 * 
	 * @param seatType
	 * @return
	 */
	public boolean connection(int seatType) {
		this.connectionMultiplier *= 0.8;
		if (seatType == 0) {
			seatMultiplier *= 0.6;
		} else if (seatType == 1) {
			seatMultiplier *= 1.2;
		} else if (seatType == 2) {
			seatMultiplier *= 3.2;
		}
		return true;
	}

	/**
	 * calculates airportMulitplier depends on the airport at which the passenger
	 * disembarked previously and to toAirport
	 * 
	 * @param airport
	 * @param toAirport
	 * @return airportMultiplier
	 */
	protected double calculateAirportMultiplier(Airport airport, Airport toAirport) {
		if (airport instanceof HubAirport) {
			if (toAirport instanceof HubAirport) {
				return 0.5;
			}
			if (toAirport instanceof MajorAirport) {
				return 0.7;
			}
			if (toAirport instanceof RegionalAirport) {
				return 1.0;
			}
		}
		if (airport instanceof MajorAirport) {
			if (toAirport instanceof HubAirport) {
				return 0.6;
			}
			if (toAirport instanceof MajorAirport) {
				return 0.8;
			}
			if (toAirport instanceof RegionalAirport) {
				return 1.8;
			}
		}
		if (airport instanceof RegionalAirport) {
			if (toAirport instanceof HubAirport) {
				return 0.9;
			}
			if (toAirport instanceof MajorAirport) {
				return 1.6;
			}
			if (toAirport instanceof RegionalAirport) {
				return 3.0;
			}
		}
		return 0;
	}

	/**
	 * 
	 * @return seatMultiplier
	 */
	public double getSeatMultiplier() {
		return seatMultiplier;
	}

	/**
	 * 
	 * @param seatMultiplier
	 */
	public void setSeatMultiplier(double seatMultiplier) {
		this.seatMultiplier = seatMultiplier;
	}

	/**
	 * 
	 * @return passenger's ID
	 */
	public long getID() {
		return ID;
	}

	/**
	 * 
	 * @return passenger's weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * 
	 * @param destinations
	 */
	public void setDestinations(ArrayList<Airport> destinations) {
		this.destinations = destinations;
	}

	/**
	 * 
	 * @return passenger's destinations
	 */
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}

	/**
	 * 
	 * @return connectionMultiplier
	 */
	public double getConnectionMultiplier() {
		return connectionMultiplier;
	}

	/**
	 * 
	 * @param connectionMultiplier
	 */
	public void setConnectionMultiplier(double connectionMultiplier) {
		this.connectionMultiplier = connectionMultiplier;
	}

	/**
	 * 
	 * @return passenger's baggageCount
	 */
	public int getBaggageCount() {
		return baggageCount;
	}

}
