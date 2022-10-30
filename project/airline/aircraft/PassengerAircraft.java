/**
 * 
 */
package project.airline.aircraft;

import java.util.ArrayList;

import project.airline.Airline;
import project.airline.concrete.JetPassengerAircraft;
import project.airline.concrete.PropPassengerAircraft;
import project.airline.concrete.RapidPassengerAircraft;
import project.airline.concrete.WidebodyPassengerAircraft;
import project.airport.Airport;
import project.interfaces.PassengerInterface;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;

/**
 * 
 *
 */
public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	/**
	 * holds passengers who are currently in this aircraft
	 */
	protected ArrayList<Passenger> passengers = new ArrayList<Passenger>();

	/**
	 * Count of seats assigned for this aircraft for each seat type
	 */
	private int economySeats = 0, businessSeats = 0, firstClassSeats = 0;
	/*
	 * Count of seats that are occupied for each seat type.
	 */
	private int occupiedEconomySeats = 0, occupiedBusinessSeats = 0, occupiedFirstClassSeats = 0;

	/**
	 * PassengerAircraft constructor 
	 * @param airport
	 */
	public PassengerAircraft(Airport airport) {
		super(airport);
	}

	/**
	 * operationFee depends on Aircraft used now
	 * operationFees are given in the input as constants
	 */
	public double operationFee() {
		double operationFee = 0;
		if (this instanceof JetPassengerAircraft) {
			operationFee = Airline.jetOperationFee;
		} else if (this instanceof PropPassengerAircraft) {
			operationFee = Airline.propOperationFee;
		} else if (this instanceof RapidPassengerAircraft) {
			operationFee = Airline.rapidOperationFee;
		} else if (this instanceof WidebodyPassengerAircraft) {
			operationFee = Airline.wideOperationFee;
		}
		return operationFee;

	}
/**
 * gives seat type equals to level of the passenger
 * @param passenger
 * @return seatType
 */
	public int seatType(Passenger passenger) {
		int seatType = 0;
		if (passenger instanceof EconomyPassenger) {
			seatType = 0;
		} else if (passenger instanceof BusinessPassenger) {
			seatType = 1;
		} else {
			seatType = 2;
		}
		return seatType;
	}
	/**
	 * checks the validity of loading passenger to this aircraft
	 * <h3>A load operation cannot happen when</h3>
	 * <li>the aircraft and the passenger are not in the same airport</li>
	 * <li>the aircraft does not have seats for that passenger</li>
	 * <li>the aircraft exceeds the maximum weight limit with the addition of that passenger.</li>
	 * @param passenger
	 * @return
	 */
	public boolean checkLoadPassenger(Passenger passenger) {

		if (this.isFull() == false)
			if (this.isFull(this.seatType(passenger)) == false)
				if (this.getAvailableWeight() >= passenger.getWeight()) {
					return true;
				}
		return false;
	}

	/**
	 * loads the passenger to the appropriate seat if loading is complete
	 * <code>return </code> loadingFee. otherwise, <code> return </code>
	 * operationFee seats passengers at seats of their level
	 * 
	 * @return loading fee
	 */
	public double loadPassenger(Passenger passenger) {

		if (checkLoadPassenger(passenger)) {

			double passengerType = 0;
			if (passenger instanceof EconomyPassenger) {
				passengerType = 1.2;
				this.occupiedEconomySeats++;
			} else if (passenger instanceof BusinessPassenger) {
				passengerType = 1.5;
				this.occupiedBusinessSeats++;
			} else if (passenger instanceof FirstClassPassenger || passenger instanceof LuxuryPassenger) {
				passengerType = 2.5;
				this.occupiedFirstClassSeats++;
			}
			
			double loadingFee = this.operationFee() * this.aircraftTypeMultiplier * passengerType;
			passenger.board(this.seatType(passenger));
			this.passengers.add(passenger);
			this.weight+=passenger.getWeight();
			return loadingFee;
		}
		return this.operationFee();
	}

	/**
	 * unloads the passengers if they can disembark at the aircraft's airport
	 * <code>if</code> passnenger can disembark
	 * 
	 * @return ticket price which is added to revenue <code>else {return</code>
	 *         operation fee}
	 */
	public double unloadPassenger(Passenger passenger) {

		if (passenger.isDestination(currentAirport)) {
			double constant = 1;
			if (passenger instanceof EconomyPassenger) {
				constant = 1.0;
				this.occupiedEconomySeats--;
				this.deleteSeat(0);
			} else if (passenger instanceof BusinessPassenger) {
				constant = 2.8;
				this.occupiedBusinessSeats--;
				this.deleteSeat(1);
			} else if (passenger instanceof FirstClassPassenger || passenger instanceof LuxuryPassenger) {
				constant = 7.5;
				this.occupiedFirstClassSeats--;
				this.deleteSeat(2);
			}

			
			double ticketPrice = passenger.disembark(this.currentAirport, aircraftTypeMultiplier) * constant;
			this.weight-=passenger.getWeight();
			return ticketPrice;
		}
		return this.operationFee();

	}

	/*
	 * 
	 * If the transfer operation is invalid, you should return the operationFee as
	 * an expense. If the transfer operation is valid, then you should return the
	 * loading fee. This loading fee is what you get when you use the
	 * loadPassenger() method above.
	 */
	/**
	 * transfers the passenger from the current aircraft to the toAircraft
	 * <code>if</code> (passenger can not disembark) >> can use this method.
	 * <code>if</code>(operation is valid) <code>return</code> loadingFee.
	 * <code>else{return </code>operationFee}
	 *
	 * @see #loadPassenger(Passenger) but difference this is between aircrafts not
	 *      airport and aircraft.
	 *
	 * @return loading fee from {@link #loadPassenger(Passenger)}
	 */

	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		if (this.currentAirport.equals(toAircraft.currentAirport)
				&& passenger.getDestinations().get(0) == this.currentAirport) {
			this.passengers.remove(passenger);
			toAircraft.passengers.add(passenger);
			return toAircraft.loadPassenger(passenger);
		}
		return toAircraft.operationFee();

	}

	/**
	 * returns the seat's area according to its type
	 * 
	 * @param seatType
	 * @return area of the seat
	 */
	public double areaOfSeat(int seatType) {

		double areaOfSeat = 0;
		switch (seatType) {
		case 0:
			areaOfSeat = 1;
			break;
		case 1:
			areaOfSeat = 3;
			break;
		case 2:
			areaOfSeat = 8;
			break;
		}
		return areaOfSeat;
	}

	/**
	 * adds seat into this aircraft from the specified type
	 * 
	 * @param seatType
	 * @return
	 */
	public boolean addSeat(int seatType) {

		if (avaliableFloorArea - areaOfSeat(seatType) >= 0) {
			avaliableFloorArea -= areaOfSeat(seatType);

			if (seatType == 0) {
				economySeats++;
			}

			else if (seatType == 1) {
				businessSeats++;
			}

			else if (seatType == 2) {
				firstClassSeats++;
			}
			return true;
		}
		return false;

	};

	/**
	 * deletes seat from this aircraft
	 * 
	 * @param seatType
	 * @return
	 */
	public boolean deleteSeat(int seatType) {
		if (seatType == 0) {
			if (occupiedEconomySeats < economySeats && economySeats > 0
					&& avaliableFloorArea + areaOfSeat(seatType) <= floorArea) {
				/*
				 * it means we have empty seat there
				 */
				economySeats -= 1;
				avaliableFloorArea += areaOfSeat(seatType);
				return true;
			}
		} else if (seatType == 1) {
			if (occupiedBusinessSeats < businessSeats && businessSeats > 0
					&& avaliableFloorArea + areaOfSeat(seatType) <= floorArea) {
				/*
				 * it means we have empty seat there
				 */
				businessSeats -= 1;
				avaliableFloorArea += areaOfSeat(seatType);
				return true;
			}
		} else if (seatType == 2) {
			{
				if (occupiedFirstClassSeats < firstClassSeats && firstClassSeats > 0
						&& avaliableFloorArea + areaOfSeat(seatType) <= floorArea) {
					/*
					 * it means we have empty seat there
					 */
					firstClassSeats -= 1;
					avaliableFloorArea += areaOfSeat(seatType);
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * Checks whether the aircraft is full or not
	 */
	public boolean isFull() {
		if (this.economySeats + this.businessSeats + this.firstClassSeats == this.occupiedEconomySeats
				+ this.occupiedBusinessSeats + this.occupiedFirstClassSeats) {
			return true;
		}
		return false;
	}

	public boolean isFull(int seatType) {
		if (seatType == 0) {
			if (this.economySeats == this.occupiedEconomySeats) {
				return true;
			}
		}
		if (seatType == 1) {
			if (this.businessSeats == this.occupiedBusinessSeats) {
				return true;
			}
		}
		if (seatType == 2) {
			if (this.firstClassSeats == this.occupiedFirstClassSeats) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		if (this.occupiedBusinessSeats + this.occupiedEconomySeats + this.occupiedFirstClassSeats == 0) {
			return true;
		}
		return false;
	}

	public double getAvailableWeight() {
		return this.maxWeight - this.weight;
	}
/**
 * 
 */
	public double getFullness() {
		double ratio = 0;
		if (this.economySeats + this.businessSeats + this.firstClassSeats > 0) {
			ratio = 1.0*(this.occupiedBusinessSeats + this.occupiedEconomySeats + this.occupiedFirstClassSeats)
					/ 1.0*(this.economySeats + this.businessSeats + this.firstClassSeats);
		}
		return ratio;
	}

	/**
	 * manages adding seats to this aircraft by the numbers given in the parameter.
	 * priority to firstClass then business then economy
	 */
	public boolean setSeats(int economy, int business, int firstClass) {
		for (int i = 0; i < firstClass; i++) {
			addSeat(2);
		}
		for (int i = 0; i < business; i++) {
			addSeat(1);
		}
		for (int i = 0; i < economy; i++) {
			addSeat(0);
		}
		
		return true;
	}

	public boolean setAllEconomy() {
		// find the false case?
		for (int i = 0; i < this.businessSeats - this.occupiedBusinessSeats; i++) {
			this.deleteSeat(1);
		}
		for (int i = 0; i < this.firstClassSeats - this.occupiedFirstClassSeats; i++) {
			this.deleteSeat(2);
		}
		while (this.addSeat(0))
			;
		return true;
	}

	public boolean setAllBusiness() {
		for (int i = 0; i < this.economySeats - this.occupiedEconomySeats; i++) {
			this.deleteSeat(0);
		}
		for (int i = 0; i < this.firstClassSeats - this.occupiedFirstClassSeats; i++) {
			this.deleteSeat(2);
		}
		while (this.addSeat(1))
			;
		return true;
	}

	public boolean setAllFirstClass() {
		for (int i = 0; i < this.economySeats - this.occupiedEconomySeats; i++) {
			this.deleteSeat(0);
		}
		for (int i = 0; i < this.businessSeats - this.occupiedBusinessSeats; i++) {
			this.deleteSeat(1);
		}
		while (this.addSeat(2))
			;
		return true;

	}

	public boolean setRemainingEconomy() {
		while (this.addSeat(0))
			;
		return true;
	}

	public boolean setRemainingBusiness() {
		while (this.addSeat(1))
			;
		return true;
	}

	public boolean setRemainingFirstClass() {
		while (this.addSeat(2))
			;
		return true;
	}

	/**
	 * 
	 * @return businessSeats
	 */
	public int getBusinessSeats() {
		return businessSeats;
	}

	/**
	 * 
	 * @return economySeats
	 */
	public int getEconomySeats() {
		return economySeats;
	}

	/**
	 * 
	 * @param businessSeats
	 */
	public void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}

	/**
	 * 
	 * @return firstClassSeats
	 */
	public int getFirstClassSeats() {
		return firstClassSeats;
	}

	/**
	 * 
	 * @return occupiedFirstClassSeats
	 */
	public int getOccupiedFirstClassSeats() {
		return occupiedFirstClassSeats;
	}

	/**
	 * 
	 * @param firstClassSeats
	 */
	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}

	/**
	 * 
	 * @return occupiedEconomySeats
	 */
	public int getOccupiedEconomySeats() {
		return occupiedEconomySeats;
	}

	/**
	 * 
	 * @param occupiedEconomySeats
	 */
	public void setOccupiedEconomySeats(int occupiedEconomySeats) {
		this.occupiedEconomySeats = occupiedEconomySeats;
	}

	/**
	 * 
	 * @return occupiedBusinessSeats
	 */

	public int getOccupiedBusinessSeats() {
		return occupiedBusinessSeats;
	}

	/**
	 * adds passenger to this aircraft
	 * 
	 * @param passenger
	 */
	public void addPassenger(Passenger passenger) {
		this.passengers.add(passenger);
	}

	/**
	 * removes passenger from this aircraft
	 * 
	 * @param passenger
	 */
	public void removePassenger(Passenger passenger) {
		passengers.remove(passenger);
	}

	/**
	 * 
	 * @return passengers
	 */

	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}

	/**
	 * 
	 * @param passengers
	 */
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

}
