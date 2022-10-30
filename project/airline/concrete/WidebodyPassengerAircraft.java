/**
 * 
 */
package project.airline.concrete;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

/**
 * represents a large jet aircraft designed for very long routes and major
 * destinations.
 *
 */
public class WidebodyPassengerAircraft extends PassengerAircraft {
	/**
	 * WidebodyPassengerAircraft constructor calls the constructor of parent
	 * PassengerAircraft sets values for this type of aircraft
	 * 
	 * @param airport
	 */
	public WidebodyPassengerAircraft(Airport airport) {
		super(airport);
		this.weight = 135000;
		this.maxWeight = 250000;
		this.floorArea = 450;
		this.avaliableFloorArea = 450;
		this.fuelCapacity = 140000;
		this.fuelConsumption = 3.0;
		this.aircraftTypeMultiplier = 0.7;
	}

	/**
	 * calculates and returns the flight cost. used in {@link Aircraft
	 * #fly(Airport)}
	 * <h3>Flight cost</h3> = landing + departure+ flight operation costs
	 * 
	 * @return flight cost
	 */
	@Override
	public double getFlightCost(Airport toAirport) {

		double flightOperationCost = this.getFullness() * this.currentAirport.getDistance(toAirport) * 0.15;
		double flightCost = this.currentAirport.departAircraft(this) 
				+ flightOperationCost+toAirport.landAircraft(this);
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
		double distanceRatio = distance / 14000;
		double x = distanceRatio;
		double bathtub = 25.9324 * Math.pow(x, 4) - 50.5633 * Math.pow(x, 3) + 35.0554 * Math.pow(x, 2) - 9.90346 * x
				+ 1.97413;

		double cruisePart = this.fuelConsumption * bathtub;
		return takeoff + cruisePart;
	}

}
