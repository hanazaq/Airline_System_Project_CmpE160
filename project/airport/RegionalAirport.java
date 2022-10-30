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
public class RegionalAirport extends Airport {
/**
 * Constructor of RegionalAirport
 * @param ID
 * @param x
 * @param y
 * @param fuelCost
 * @param operationFee
 * @param aircraftCapacity
 * @param passengers
 */
	public RegionalAirport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity,ArrayList<Passenger> passengers) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity,passengers);
	}

/**
 * @see HubAirport #departAircraft(Aircraft), but constant is 1.2
 */
	@Override
	public double departAircraft(Aircraft aircraft) {
		double aircraftRatio =1.0* this.numberOfCurrrentAircraft() / this.aircraftCapacity; 
		double fullnessCoefficient = 0.6 * Math.exp(aircraftRatio); 
		double departureFee = 1.2 * this.operationFee * aircraft.getWeightRatio() * fullnessCoefficient;
		return departureFee;
	}
/**
 * @see HubAirport #departAircraft(Aircraft), but constant is 1.3
 */
	@Override
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio = 1.0*this.numberOfCurrrentAircraft() / this.aircraftCapacity; 
		double fullnessCoefficient = 0.6 * Math.exp(aircraftRatio); 
		double landingFee = 1.3 * this.operationFee * aircraft.getWeightRatio() * fullnessCoefficient;
		return landingFee;
	}

}
