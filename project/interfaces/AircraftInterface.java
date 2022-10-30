/**
 * 
 */
package project.interfaces;

import project.airline.aircraft.Aircraft;
import project.airport.Airport;

/**
 * 
 * @author Hanaa implemented by implemented by {@link Aircraft}
 */
public interface AircraftInterface {

	double fly(Airport toAirport);

	/**
	 * add fuel to Airport
	 * 
	 * @param fuel
	 * @return
	 */
	boolean addFuel(double fuel);

	/**
	 * Refuels the aircraft to its full capacity.
	 * 
	 * @return
	 */
	boolean fillUp();

	/**
	 * Checks if the aircraft has the specified amount of fuel.
	 * 
	 * @param fuel
	 * @return
	 */
	boolean hasFuel(double fuel);

	/**
	 * Returns the ratio of weight to maximum weight.
	 * 
	 * @return
	 */
	double getWeightRatio();

}
