/**
 * 
 */
package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

/**
 * 
 * @author Hanaa
 *
 */
public class EconomyPassenger extends Passenger {
	/**
	 * Constructor of EconomyPassenger Class
	 * 
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	public EconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);

	}

	/**
	 * This method calculates and returns the ticket price. It is calculated with
	 * the help of a few values:
	 * {@link #calculateAirportMultiplier(Airport, Airport)}
	 * {@link #getConnectionMultiplier()} {@link #aircraftTypeMultiplier}
	 * {@link #getBaggageCount()} passenger multiplier= 0.6
	 * <h3>Ticket price is the product of</h3>
	 * <li>distance between the airport of previous disembarkation and the
	 * toAirport</li>
	 * <li>aircraftTypeMultiplier</li>
	 * <li>passenger multiplier constant</li>
	 * <li>airport multiplier</li>
	 * <li>connectionMultiplier</li>
	 */
	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
	
		double ticketPrice = this.getDestinations().get(0).getDistance(toAirport) * aircraftTypeMultiplier
				* this.getConnectionMultiplier()
				* this.calculateAirportMultiplier(this.getDestinations().get(0), toAirport) * 0.6*this.getSeatMultiplier();
		ticketPrice += ticketPrice * 0.05 * this.getBaggageCount();
		return ticketPrice;
	}

//	@Override
//	public double landAircraft(Aircraft aircraft) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
