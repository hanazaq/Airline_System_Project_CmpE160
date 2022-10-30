/**
 * 
 */
package project.interfaces;

import project.airline.aircraft.PassengerAircraft;
import project.passenger.Passenger;

/**
 * @author Hanaa
 *
 */
public interface PassengerInterface {
	double transferPassenger(Passenger passenger, PassengerAircraft toAircraft);

	double loadPassenger(Passenger passenger);

	double unloadPassenger(Passenger passenger);

	/**
	 * Checks whether the aircraft is full or not
	 * 
	 * @return
	 */
	boolean isFull();

	/**
	 * Checks whether a certain seat type is full or not
	 * 
	 * @param seatType
	 * @return
	 */
	boolean isFull(int seatType);

	/**
	 * Checks whether the aircraft is empty or not.
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * Returns the leftover weight capacity of the aircraft.
	 * 
	 * @return
	 */
	public double getAvailableWeight();

	public boolean setSeats(int economy, int business, int firstClass);//

	/**
	 * Sets every seat to economy.
	 * 
	 * @return
	 */
	public boolean setAllEconomy();

	/**
	 * Sets every seat to business
	 * 
	 * @return
	 */
	public boolean setAllBusiness();

	/**
	 * Sets every seat to first class.
	 * 
	 * @return
	 */
	public boolean setAllFirstClass();

	/**
	 * Does not change previously set seats, sets the remaining to economy.
	 * 
	 * @return
	 */
	public boolean setRemainingEconomy();

	/**
	 * Does not change previously set seats, sets the remaining to business.
	 * 
	 * @return
	 */
	public boolean setRemainingBusiness();

	/**
	 * Does not change previously set seats, sets the remaining to first class.
	 * 
	 * @return
	 */

	public boolean setRemainingFirstClass();

	/**
	 * Returns the ratio of occupied seats to all seats.
	 * 
	 * @return
	 */
	public double getFullness();

}
