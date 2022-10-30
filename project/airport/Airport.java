/**
 * 
 */
package project.airport;

import java.util.ArrayList;

import project.airline.Airline;
import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
import project.passenger.Passenger;

/**
 * 
 *
 */
public abstract class Airport {

	/**
	 * Unique ID of the airport.
	 */
	private final long ID;
	/**
	 * Coordinates of the airport. Used in {@link #getDistance(Airport)}
	 */
	private final double x, y;
	/**
	 * Price of fuel in this airport
	 */
	protected double fuelCost;
	/**
	 * The fee paid to this airport for certain operations.
	 */
	protected double operationFee;
	/**
	 * The maximum number of aircraft this airport can hold.
	 */
	protected int aircraftCapacity;
	/**
	 * holds passenger objects currently in this airport
	 */
	public ArrayList<Passenger> passengers;

	/**
	 * Constructor called by children of this abstract Class
	 * 
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 * @param passengers
	 */
	public Airport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity,
			ArrayList<Passenger> passengers) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		this.passengers = passengers;
	}

	/**
	 * Does the necessary departure operations
	 * 
	 * @param aircraft
	 * @return departure fee
	 */
	public abstract double departAircraft(Aircraft aircraft);

	/**
	 * Does the necessary landing operations
	 * 
	 * @param aircraft
	 * @return landing fee
	 */
	public abstract double landAircraft(Aircraft aircraft);

	/**
	 * calculates the distance to another airport
	 * 
	 * @param airport
	 * @return distance to a given airport
	 */
	public double getDistance(Airport airport) {
		double x2 = airport.getX();
		double y2 = airport.getY();
		double distance = Math.sqrt(Math.pow((x2 - this.x), 2) + Math.pow(y2 - this.y, 2));
		return distance;
	}

	/**
	 * check if you are in the same Airport
	 * 
	 * @param other
	 * @return
	 */
	public boolean equals(Airport other) {
		if (other == this) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * iterates over {@link Airline #getAircrafts()} to find out which aircrafts are
	 * currently in this airport
	 * 
	 * @return number of aircrafts are currently in this airports
	 */
	public int numberOfCurrrentAircraft() {
		int numOfCurrrentAircraft = 0;
		for (PassengerAircraft a : Airline.getAircrafts()) {
			if (a.getCurrentAirport() == this) {
				numOfCurrrentAircraft++;
			}
		}
		return numOfCurrrentAircraft;
	}

	/**
	 * check if Airport is full with Aircrafts
	 * 
	 * @return
	 */
	public boolean isFull() {

		if (this.numberOfCurrrentAircraft() == this.aircraftCapacity) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * adds passenger to Airport
	 * 
	 * @param passenger
	 */
	public void addPassenger(Passenger passenger) {
		this.passengers.add(passenger);
	}

	/**
	 * removes passengers from Airport
	 * 
	 * @param passenger
	 */
	public void removePassenger(Passenger passenger) {
		this.passengers.remove(passenger);
	}

	/**
	 * 
	 * @return fuelCost
	 */
	public double getFuelCost() {
		return this.fuelCost;
	}

	/**
	 * 
	 * @param fuelCost
	 */
	public void setFuelCost(double fuelCost) {
		this.fuelCost = fuelCost;
	}

	/**
	 * 
	 * @return operationFee
	 */
	public double getOperationFee() {
		return this.operationFee;
	}

	/**
	 * 
	 * @param operationFee
	 */
	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}

	/**
	 * 
	 * @return aircraftCapacity
	 */
	public int getAircraftCapacity() {
		return this.aircraftCapacity;
	}

	/**
	 * 
	 * @param aircraftCapacity
	 */
	public void setAircraftCapacity(int aircraftCapacity) {
		this.aircraftCapacity = aircraftCapacity;
	}

	/**
	 * 
	 * @return Airport Id
	 */
	public long getID() {
		return this.ID;
	}

	/**
	 * 
	 * @return x coordinate of airport
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * 
	 * @return y coordinate of airport
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * 
	 * @return arraylist of passengers are currently in this airport
	 */
	public ArrayList<Passenger> getPassengers() {
		return this.passengers;
	}

	/**
	 * 
	 * @param passengers
	 */
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

}
