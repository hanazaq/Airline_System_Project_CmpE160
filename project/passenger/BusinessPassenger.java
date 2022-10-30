/**
 * 
 */
package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class BusinessPassenger extends Passenger {

	/**
	 * Construtor
	 * 
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	public BusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
	}

	/**
	 * @see EconomyPassenger#calculateTicketPrice(Airport, double), but constant is
	 *      1.2
	 */
	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double ticketPrice = this.getDestinations().get(0).getDistance(toAirport) * aircraftTypeMultiplier
				* this.getConnectionMultiplier()
				* this.calculateAirportMultiplier(this.getDestinations().get(0), toAirport) * 1.2*((double)(this.getBaggageCount()*0.05+1))*this.getSeatMultiplier();

//		ticketPrice += ticketPrice * 0.05 * this.getBaggageCount();
		return ticketPrice;
	}

//	@Override
//	public double landAircraft(Aircraft aircraft) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
