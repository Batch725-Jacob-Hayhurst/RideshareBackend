package com.revature.services;

import java.io.IOException;
import java.util.List;

import com.google.maps.errors.ApiException;
import com.revature.beans.User;
import com.revature.beans.Car;

/**
 * DistanceService is an interface that formats the distance service methods.
 * 
 * @author Timothy Mitchell
 *
 */

public interface DistanceService {
 
	public List<User> distanceMatrix (String[] origins, String[] destinations) throws ApiException, InterruptedException, IOException ;
	List<Car> distanceCarMatrix(String[] origins, List<Car> carList, int numCars) throws ApiException, InterruptedException, IOException;
	
	// Place key googleMapAPIKey & value apiKey (to be shared on slack) into Environment Vars.
	public  String getGoogleMAPKey();
	
	
}