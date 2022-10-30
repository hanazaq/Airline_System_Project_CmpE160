/**
 * 
 */
package project.airport;

import java.util.ArrayList;

import project.airline.aircraft.Aircraft;
import project.passenger.Passenger;

/**
 *
 *
 */
public class HubAirport extends Airport {
	/**
	 * Constructor of HubAirprot
	 * 
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 * @param passengers
	 */
	public HubAirport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity,
			ArrayList<Passenger> passengers) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity, passengers);
	}

	/**
	 * To calculate the departure fee, we need to define two values:
	 * <li>fullness coefficient= 0.6 * (e raised to the power of aircraft
	 * ratio)</li>
	 * <li>aircraft weight ratio= aircraft weight / maxWeight</li>
	 * <h3>departue fee</h3>= {@link #getOperationFee()} * fullness coefficient *
	 * aircraft weight ratio * constant(=0.7 for this type of Airport)
	 */
	@Override
	public double departAircraft(Aircraft aircraft) {
		double aircraftRatio = 1.0*this.numberOfCurrrentAircraft() / this.aircraftCapacity; 
		double fullnessCoefficient = 0.6 * Math.exp(aircraftRatio); 
		double departureFee = 0.7 * this.operationFee * aircraft.getWeightRatio() * fullnessCoefficient;
		return departureFee;
	}

	/**
	 * @see HubAirport #departAircraft(Aircraft), but constant= 0.8
	 */
	@Override
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio = 1.0*this.numberOfCurrrentAircraft() / this.aircraftCapacity; 
		double fullnessCoefficient = 0.6 * Math.exp(aircraftRatio); 
		double landingFee = 0.8 * this.operationFee * aircraft.getWeightRatio() * fullnessCoefficient;
		return landingFee;
	}

}
