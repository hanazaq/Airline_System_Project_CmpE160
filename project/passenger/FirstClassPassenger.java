/**
 * 
 */
package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

/**
 * 
 *
 */
public class FirstClassPassenger extends Passenger {
	/**
	 * Constructor
	 * 
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	public FirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
	}

	/**
	 * @see EconomyPassenger#calculateTicketPrice(Airport, double), but constant is
	 *      3.2
	 */
	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double ticketPrice = this.getDestinations().get(0).getDistance(toAirport) * aircraftTypeMultiplier
				* this.getConnectionMultiplier()
				* this.calculateAirportMultiplier(this.getDestinations().get(0), toAirport) * 3.2*this.getSeatMultiplier();
		ticketPrice += ticketPrice * 0.05 * this.getBaggageCount();
		return ticketPrice;
	}

//	@Override
//	public double landAircraft(Aircraft aircraft) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
}
