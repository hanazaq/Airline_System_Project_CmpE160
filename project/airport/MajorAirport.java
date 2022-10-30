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
public class MajorAirport extends Airport {
	/**
	 * Constructor of MajorAirport
	 * 
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 * @param passengers
	 */
	public MajorAirport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity,
			ArrayList<Passenger> passengers) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity, passengers);
	}

	/**
	 * @see HubAirport #departAircraft(Aircraft), but constant is 0.9
	 */
	@Override
	public double departAircraft(Aircraft aircraft) {
		double aircraftRatio = 1.0*this.numberOfCurrrentAircraft() / this.aircraftCapacity; 
		double fullnessCoefficient = 0.6 * Math.exp(aircraftRatio); 
		double departureFee = 0.9 * this.operationFee * aircraft.getWeightRatio() * fullnessCoefficient;

		return departureFee;
	}

	/**
	 * @see HubAirport #departAircraft(Aircraft), but constant is 1
	 */
	@Override
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio = 1.0*this.numberOfCurrrentAircraft() / this.aircraftCapacity; 
		double fullnessCoefficient = 0.6 * Math.exp(aircraftRatio); 
		double landingFee = this.operationFee * aircraft.getWeightRatio() * fullnessCoefficient;
		return landingFee;
	}

}
