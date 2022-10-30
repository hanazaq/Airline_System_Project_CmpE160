/**
 * 
 */
package project.airline.concrete;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

/**
 * 
 * represents a small propeller aircraft designed for short routes and minor
 * destinations.
 *
 */
public class PropPassengerAircraft extends PassengerAircraft {

	/**
	 * PropPassengerAircraft constructor calls the constructor of parent
	 * PassengerAircraft sets values for this type of aircraft
	 * 
	 * @param airport
	 */
	public PropPassengerAircraft(Airport airport) {
		super(airport);
		this.weight = 14000;
		this.maxWeight = 23000;
		this.floorArea = 60;
		this.avaliableFloorArea = 60;
		this.fuelCapacity = 6000;
		this.fuelConsumption = 0.6;
		this.aircraftTypeMultiplier = 0.9;
	}

	/**
	 * calculates and returns the flight cost. used in {@link Aircraft
	 * #fly(Airport)}
	 * <h3>Flight cost</h3> = landing + departure+ flight operation costs
	 * 
	 * @return flight cost
	 */
	public double getFlightCost(Airport toAirport) {
		double flightOperationCost = this.getFullness() * this.currentAirport.getDistance(toAirport) * 0.1;
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
		double takeoff = (this.weight * 0.08) / this.fuelWeight;
		double distanceRatio = distance / 2000;
		double x = distanceRatio;
		double bathtub = 25.9324 * Math.pow(x, 4) - 50.5633 * Math.pow(x, 3) + 35.0554 * Math.pow(x, 2) - 9.90346 * x
				+ 1.97413;
		double cruisePart = this.fuelConsumption * bathtub;
		return takeoff + cruisePart;
	}

}
