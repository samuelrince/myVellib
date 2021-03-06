package fr.ecp.IS1220.myVelib.core.ride;

import fr.ecp.IS1220.myVelib.core.bicycle.Bicycle;
import fr.ecp.IS1220.myVelib.core.station.ParkingSlot;
import fr.ecp.IS1220.myVelib.core.station.Station;
import fr.ecp.IS1220.myVelib.core.system.Date;
import fr.ecp.IS1220.myVelib.core.system.Duration;
import fr.ecp.IS1220.myVelib.core.user.User;

/**
 * This class represents a ride.
 * @author Valentin
 *
 */
public class Ride implements java.io.Serializable {
	private static long uniqId;
	private long id;
	private User user;
	private Bicycle bicycle;
	private Station startStation;
	private Date startTime;
	private Station endStation = null;
	private Date endTime = null;
	private Duration duration;
	private boolean current;
	private double price;
	private int timeCreditOperation = 0;
	
	/**
	 * Constructor of class Ride.
	 * @param user user associated to the ride
	 * @param bicycle bicycle used for the ride
	 * @param startStation station where bike rental occurred
	 */
	public Ride(User user, Bicycle bicycle, Station startStation) {
		Date startTime = new Date();
		this.user = user;
		this.bicycle = bicycle;
		this.startStation = startStation;
		this.startTime = startTime;
		this.current = true;
		this.id = uniqId++;
	}

  /**
	 * Puts an end to the ride by returning the bicycle to a given parking slot.
	 * @param parkingSlot the parking slot to attach the bicycle to
	 * @throws Exception When an error occurred could be a wrong argument, lack
	 * of space in the station or a wrong parking slot
	 */
	public void end(ParkingSlot parkingSlot) throws Exception {
		parkingSlot.attachBicycle(this.bicycle);
		this.current = false;
		Date endTime = new Date();
		this.endTime = endTime;
		this.endStation = parkingSlot.getStation();
		parkingSlot.getStation().incReturnCount();
		this.duration = new Duration(this.startTime,this.endTime);
		this.pay();
		if (this.user.getPlannedRide() != null) {
			if (this.user.getPlannedRide().isLinked()){
				this.user.discardPlannedRide();
			}
		}
	}
	
	/**
	 * This method computes the cost of the ride through the user's card
	 * and updates the user's time credit balance of the if needed. It is called
	 * automatically at the end of a ride.
	 */
	private void pay() {
		this.price = this.user.getCard().cost(this.duration, this.bicycle.getType());
		this.user.addTimeCreditBalance(0, this.user.getCard().timeCreditOperation(
				this.duration, this.bicycle.getType(),this.endStation.isPlus()));
	}

	/**
	 * 
	 * @return id of the ride
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * 
	 * @return user associated to the ride
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 
	 * @return bicycle used for the ride
	 */
	public Bicycle getBicycle() {
		return bicycle;
	}

	/**
	 * 
	 * @return station where bike rental occurred
	 */
	public Station getStartStation() {
		return startStation;
	}

	/**
	 * 
	 * @return time when bike rental occurred
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 
	 * @return station where bike return occurred
	 */
	public Station getEndStation() {
		return endStation;
	}

	/**
	 * 
	 * @return time when bike return occurred
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 
	 * @return duration of the ride
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * 
	 * @return true if the ride is currently in progress, false otherwise
	 */
	public boolean isCurrent() {
		return current;
	}

	/**
	 * 
	 * @return price of the ride
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * 
	 * @return algebraic amount of time credit earned by the user for this ride
	 */
	public int getTimeCreditOperation() {
		return timeCreditOperation;
	}

	@Override
	public String toString() {
		String endStation;
		String endDate;
		String duration;
		String cost;
		String isPlus = "";
		if (this.isCurrent()) {
			endStation = "-";
			endDate = "-";
			duration ="-";
			cost = "-";
		}
		else {
			endStation = this.endStation.getName();
			if (this.endStation.isPlus())
				isPlus = " (Plus)";
			endDate = this.endTime.toString();
			duration = this.duration.toString();
			cost = Double.toString(this.price);
		}
		return "Ride [id." + id + "] of " + user.getName() + ":\n"+
	bicycle.getType()+" bike n�" + bicycle.getId() + " rented in " + startStation.getName() + 
	" on "+startTime.toString()+", returned in " + endStation +isPlus+ " on "+endDate+"\n"+
	"Cost: "+cost+"� / Duration:"+duration;
	}
	
	/**
	 * Reset the uniqID to 0. Used in MyVelibNetwork.deleteAll().
	 */
	public static void resetUniqID() {uniqId=0;}
	
	/**
	 * Force reset of ride. Used in MyVelibNetwork.deleteAll().
	 */
	public void forceReset() {this.bicycle = null; this.endStation = null; this.endStation = null; this.user = null;}
}
