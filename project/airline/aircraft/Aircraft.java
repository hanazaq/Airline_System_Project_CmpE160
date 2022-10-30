/**
 * 
 */
package project.airline.aircraft;

import project.airline.Airline;
import project.airport.Airport;
import project.interfaces.AircraftInterface;

/**
 * 
 *
 */
public abstract class Aircraft implements AircraftInterface {

	/**
	 * The airport which the plane is currently in.
	 */
	protected Airport currentAirport;
	/**
	 * This is the current weight of the aircraft. should not exceed
	 * {@link #maxWeight}
	 */
	protected double weight;
	/**
	 * the maximum allowable weight of the aircraft
	 */
	protected double maxWeight;
	/**
	 * The total floor area of the aircraft, this field is different for each type
	 * of aircraft
	 */
	protected double floorArea;
	/**
	 * initially has the same value as floorArea and this variable will be edited in
	 * operations
	 */
	protected double avaliableFloorArea;
	/**
	 * Fuel capacity of the airplane. It is different and fixed for every type of
	 * aircraft.
	 */
	protected double fuelCapacity;
	/**
	 * Fuel consumption of the aircraft will be used in the {@link #addFuel(double)}
	 */
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	/**
	 * the constant we use to convert fuel volume to fuel weight.
	 */
	protected final double fuelWeight = 0.7;
	/**
	 * holds the amount of fuel the aircraft has at any moment. should not exceed
	 * {@link #fuelCapacity}
	 */
	protected double fuel = 0;

	/**
	 * create Airline object
	 */
	protected Airline myAirline = new Airline();

	/**
	 * Constructor of Aircraft
	 * 
	 * @param currentAirport
	 */
	public Aircraft(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}

	/*
	 * abstract method to implement in children
	 */
	public abstract double getFlightCost(Airport toAirport);

	public abstract double getFuelConsumption(double distance);

	/**
	 * checks if the fly is manageable or not
	 * <li>enough fuel</li>
	 * <li>toAirprot has place to host this aircraft</li>
	 * <li>the weight this aircraft does not exceed its maxWeight</li>
	 * 
	 * 
	 * @param toAirport
	 * @return
	 */
	public boolean checkFly(Airport toAirport) {
		if (this.getFuelConsumption(currentAirport.getDistance(toAirport)) <= this.fuel)
			if (toAirport.isFull() == false)
				if (this.weight <= this.maxWeight) {
					return true;
				}
		return false;
	}

	/**
	 * flies the aircraft to toAirport, does fuel calculations using
	 * {@link #getFuelConsumption(double)}. changes values of {@link #fuel},
	 * {@link #weight}, {@link #currentAirport}, if this fly is valid.
	 * <h3>flight cost</h3>= operational cost of the aircraft + the departure fee +
	 * the landing fee
	 * 
	 * @return flightCost
	 */
	public double fly(Airport toAirport) {
		double flightCost = 0;
		if (checkFly(toAirport) == true) {
			double consumedFuel = this.getFuelConsumption(this.currentAirport.getDistance(toAirport));
			flightCost = this.getFlightCost(toAirport);
			this.currentAirport = toAirport;
			
			this.fuel -= consumedFuel;
			this.weight -= (consumedFuel * this.fuelWeight);
			return flightCost;

		}
		return flightCost;

	}

	/**
	 * checks if aircraft has this amount of fuel
	 * 
	 * @param fuel
	 */
	public boolean hasFuel(double fuel) {
		if (fuel == this.fuel) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public double getWeightRatio() {
		return this.weight / this.maxWeight;
	}

	/**
	 * 
	 */
	public boolean fillUp() {
		if (this.fuel < this.fuelCapacity) {
			this.addFuel(this.fuelCapacity - this.fuel);
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public boolean addFuel(double fuel) {
		if (this.fuel + fuel <= this.fuelCapacity && this.weight + (fuel * this.fuelWeight) <= this.maxWeight) {
			this.fuel += fuel;
			this.weight += fuel * this.fuelWeight;
			return true;
		}
		return false;
	}

	/**
	 * dumps fuel from aircraft
	 * 
	 * @param fuel
	 * @return
	 */
	public boolean dumpFuel(double fuel) {
		if (this.fuel <= this.fuelCapacity && this.fuel - fuel >= 0) {
			this.fuel -= fuel;
			this.weight -= fuel * this.fuelWeight;
			return true;
		}
		return false;
	}

	/**
	 * adds fuel to the aircraft. this method will be used in Airline must add the
	 * appropriate fuel weight along with the fuel.
	 * <h3>fuel cost</h3> = the fuel amount * the airport’s fuel cost which the
	 * aircraft is refueling at.
	 * <h3>refueling logic</h3>
	 * <li>if aircraft has enough fuel to do the flight. no need for refueling</li>
	 * <li>if it does not have enough fuel try to fill up fuel</li>
	 * <li>if filling up is not allowable now. try to add enough fuel for the
	 * trip</li>
	 * <li>no place for fuel in the aircraft. you may need to disembark some economy
	 * passengers to reduce weight, here method  or better make connection<code>return</code> -1</li>
	 * 
	 * @param nowAirport
	 * @param toAirport
	 * @return fuel cost
	 */
	public double refueling(Airport toAirport) {

		if (this.hasFuel(this.getFuelConsumption(this.currentAirport.getDistance(toAirport)))) {
			return 0;
		}
		else {
			double prefuel = this.fuel;

			if (this.fillUp()) {
				double afterfuel = this.fuel;
				return (afterfuel - prefuel) * this.currentAirport.getFuelCost();
			}

			else if (this.addFuel(this.getFuelConsumption(this.currentAirport.getDistance(toAirport)) - this.fuel)) {
				double afterfuel = this.fuel;
				return (afterfuel - prefuel) * this.currentAirport.getFuelCost();
			}
		}

		return -1;
	}

	/**
	 * 
	 * @return currentAirport
	 */
	public Airport getCurrentAirport() {
		return currentAirport;
	}

	/**
	 * 
	 * @param currentAirport
	 */
	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}

	/**
	 * 
	 * @return weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * 
	 * @param weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * 
	 * @return maxWeight
	 */
	public double getMaxWeight() {
		return maxWeight;
	}

	/**
	 * 
	 * @return fuelWeight
	 */

	public double getFuelWeight() {
		return fuelWeight;
	}

	/**
	 * 
	 * @return fuel
	 */
	public double getFuel() {
		return fuel;
	}

	/**
	 * 
	 * @return fuelCapacity
	 */
	public double getFuelCapacity() {
		return fuelCapacity;
	}

	/**
	 * 
	 * @return aircraftTypeMultiplier
	 */
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}

	/**
	 * 
	 * @param aircraftTypeMultiplier
	 */
	public void setAircraftTypeMultiplier(double aircraftTypeMultiplier) {
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
	}

	/**
	 * 
	 * @param fuel
	 */
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

}
