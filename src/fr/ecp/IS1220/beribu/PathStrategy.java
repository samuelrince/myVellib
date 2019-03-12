package fr.ecp.IS1220.beribu;

import java.util.ArrayList;

public interface PathStrategy {

	public ArrayList<Station> findPath(Localization source, 
			Localization destination);
	public ArrayList<Station> findPath(Localization source, 
			Localization destination, String bicycleType);
	public String getBicycleType();
}
