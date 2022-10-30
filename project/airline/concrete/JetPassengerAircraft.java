/**
 * 
 */
package project.airline.concrete;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

/**
 * represents a private jet designed for short to medium-range routes. It has a
 * small floor area. used for squeezing the maximum amount of profit from luxury
 * passengers.
 *
 */
public class JetPassengerAircraft extends PassengerAircraft {
	/**
	 * JetPassengerAircraft constructor calls the constructor of parent
	 * PassengerAircraft sets values for this type of aircraft
	 * 
	 * @param airport
	 */
	public JetPassengerAircraft(Airport airport) {
		super(airport);
		this.weight = 10000;
		this.maxWeight = 18000;
		this.floorArea = 30;
		this.avaliableFloorArea = 30;
		this.fuelCapacity = 10000;
		this.fuelConsumption = 0.7;
		this.aircraftTypeMultiplier = 5;
	}

	/**
	 * calculates and returns the flight cost. used in {@link Aircraft
	 * #fly(Airport)}
	 * <h3>Flight cost</h3> = landing + departure+ flight operation costs
	 * 
	 * @return flight cost
	 */
	public double getFlightCost(Airport toAirport) {
		double flightOperationCost = this.getFullness() * this.currentAirport.getDistance(toAirport) * 0.08;
		double flightCost = this.currentAirport.departAircraft(this) + toAirport.landAircraft(this)
				+ flightOperationCost;
		return flightCost;
	}

	/**
	 * calculates the fuel consumption of the aircraft for a given distance. To
	 * calculate fuel consumption, we must define two values:
	 * <li>distance ratio</li>
	 * <li>bathtub coefficient</li>
	 * <h3>Fuel consumption</h3> = takeoff + cruise part
	 * <h4>takeoff</h4> = weight *constant / fuelWeight
	 * <h4>cruise part</h4> = fuelConsumption * bathtub coefficient * distance.
	 * 
	 * @return total fuel needed for the given distance.
	 */
	@Override
	public double getFuelConsumption(double distance) {
		double takeoff = (this.weight * 0.1) / this.fuelWeight;
		double distanceRatio = distance / 5000;
		double x = distanceRatio;
		double bathtub = 25.9324 * Math.pow(x, 4) - 50.5633 * Math.pow(x, 3) + 35.0554 * Math.pow(x, 2) - 9.90346 * x
				+ 1.97413;
		double cruisePart = this.fuelConsumption * bathtub;
		return takeoff + cruisePart;
	}

}
