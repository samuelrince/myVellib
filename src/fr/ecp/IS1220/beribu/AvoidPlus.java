package fr.ecp.IS1220.beribu;

import java.util.ArrayList;

public class AvoidPlus implements PathStrategy {
	String bicycleType;
	
	@Override
	public ArrayList<Station> findPath(Localization source, Localization destination) {
		// TODO Auto-generated method stub
		ArrayList<Station> startEnd = new ArrayList<Station>(2);
		startEnd.add(source.getClosestStationWithBicycle());
		this.bicycleType = startEnd.get(0).getOneBicycleType();
		startEnd.add(destination.getClosestAvailableStation(false));
		return startEnd;
	}
	
	@Override
	public ArrayList<Station> findPath(Localization source, Localization destination,
			String bicycleType) {
		// TODO Auto-generated method stub
		this.bicycleType = bicycleType;
		ArrayList<Station> startEnd = new ArrayList<Station>(2);
		startEnd.add(source.getClosestStationWithBicycle(bicycleType));
		startEnd.add(destination.getClosestAvailableStation(false));
		return startEnd;
		
	}

	public String getBicycleType() {
		return this.bicycleType;
	}
}

