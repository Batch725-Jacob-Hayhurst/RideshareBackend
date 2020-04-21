package com.revature.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.revature.beans.Car;
import com.revature.beans.User;
import com.revature.services.DistanceService;
import com.revature.services.UserService;

/**
 * DistanceServiceImpl handles any additional services that need to be made
 * before calling the repository methods.
 * 
 * @author Timothy Mitchell
 *
 */

@Service
public class DistanceServiceImpl implements DistanceService {

	@Autowired
	private UserService us;

	/**
	 * Gets the home and work locations in order to find the distance between the rider and the driver.
	 * 
	 * @param origins represents the users housing location
	 * @param destinations represents the batch location
	 * @return A list of users by isDriver and location.
	 */
	
	@Override
	public List<User> distanceMatrix(String[] origins, String[] destinations) //NOTE: This was created by a previous team and is not being used
			throws ApiException, InterruptedException, IOException {

		Map<String, User> userDestMap = new HashMap<String, User>();

		List<String> destinationList = new ArrayList<String>();

		for (User d : us.getActiveDrivers()) {

			String add = d.gethAddress();
			String city = d.gethCity();
			String state = d.gethState();

			String fullAdd = add + ", " + city + ", " + state;

			destinationList.add(fullAdd);

			userDestMap.put(fullAdd, d);

		}

		// System.out.println(destinationList);

		destinations = new String[destinationList.size()];
		
		destinations = destinationList.toArray(destinations);

		GeoApiContext context = new GeoApiContext.Builder().apiKey(getGoogleMAPKey()).build();
		List<Double> arrlist = new ArrayList<Double>();
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
		DistanceMatrix t = req.origins(origins).destinations(destinations).mode(TravelMode.DRIVING).units(Unit.IMPERIAL)
				.await();

		Map<Double, String> unsortMap = new HashMap<>();

		for (int i = 0; i < origins.length; i++) {
			for (int j = 0; j < destinations.length; j++) {
				try {
//					System.out.println((j + 1) + "): " + t.rows[i].elements[j].distance.inMeters + " meters");
					arrlist.add((double) t.rows[i].elements[j].distance.inMeters);

					unsortMap.put((double) t.rows[i].elements[j].distance.inMeters, destinations[j]);

//					System.out.println((double) t.rows[i].elements[j].distance.inMeters);

				} catch (Exception e) {
//					System.out.println("invalid address");
				}
			}
		}

//		LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
//		unsortMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
//                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
//		

//		System.out.println("-");

		Collections.sort(arrlist);

//		System.out.println(arrlist);
		List<String> destList = new ArrayList<String>();

		arrlist.removeIf(r -> (arrlist.indexOf(r) > 4));

		Double[] arrArray = new Double[arrlist.size()];

		arrArray = arrlist.toArray(arrArray);

//		System.out.println(arrArray);

		for (int c = 0; c < arrArray.length; c++) {
			String destination = unsortMap.get(arrArray[c]);
			destList.add(destination);
		}
		
//		System.out.println(destList);
		
		String[] destArray = new String[destList.size()];
		
		destArray = destList.toArray(destArray);
		
		List<User> userList = new ArrayList<User>();
		
		for (int x = 0; x < destArray.length; x++) {
			User a = userDestMap.get(destArray[x]);
//			System.out.println(a);
			userList.add(a);
//			System.out.println(userList);
		}
		return userList;
	}
	
	/**
	 * This method receives a list of origins and a list of Car objects, determines the distance that a user would
	 * need to drive to get to their batch location, and returns the n closest Cars in a list where n is numCars
	 * 
	 * @param origins represents the users batch location
	 * @param carList a list of cars that we check for distance
	 * @param numCars The max number of cars that will be returned in the list
	 * @return A list of cars that have to travel the least distance to their batch location
	 */
	
	@Override
	public List<Car> distanceCarMatrix(String[] origins, List<Car> carList, int numCars)
			throws ApiException, InterruptedException, IOException {
		
		Map<String, Car> carDestMap = new HashMap<String, Car>();
		List<String> destinationList = new ArrayList<String>();
		List<Double> distanceList = new ArrayList<Double>();
		Map<Double, String> unsortedMap = new HashMap<>();

		// Here we set up the list of our addresses, put them in data structures
		destinationAddressSetup(carList, destinationList, carDestMap); 
		
		// Here we calculate the distances for our lists using Google maps API
		calculateDistances(origins, destinationList, distanceList, unsortedMap); 
		

		Collections.sort(distanceList);
		distanceList.removeIf(r -> (distanceList.indexOf(r) > (numCars-1)));


		List<Car> carFinalList = new ArrayList<Car>();
		for (int i = 0; i < distanceList.size(); i++) {
			Car currCar = carDestMap.get(unsortedMap.get(distanceList.get(i)));
			carFinalList.add(currCar);
		}
		
		return carFinalList;
	}
	
	/**
	 * This method calculates distances from user location to batch destination using Google Maps API
	 * 
	 * @param origins The batch location
	 * @param destinationList a List of user locations
	 * @param distanceList An empty List<Double> that will be populated with the list of distances
	 * @param unsortedMap An empty Map<Double, String> that will be populated with key value pairs where the key is
	 * the distance, and the value is the destination as a String
	 */
	public void calculateDistances(String[] origins, List<String> destinationList, List<Double> distanceList, Map<Double, String> unsortedMap) throws ApiException, InterruptedException, IOException {
		
		String [] destinations = destinationList.toArray(new String[destinationList.size()]);
		GeoApiContext context = new GeoApiContext.Builder().apiKey(getGoogleMAPKey()).build();
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
		DistanceMatrix distanceMatrix = req.origins(origins).destinations(destinations).mode(TravelMode.DRIVING).units(Unit.IMPERIAL).await();
		
		for (int i = 0; i < origins.length; i++) {
			for (int j = 0; j < destinations.length; j++) {
				try {
					distanceList.add((double) distanceMatrix.rows[i].elements[j].distance.inMeters);
					unsortedMap.put((double) distanceMatrix.rows[i].elements[j].distance.inMeters, destinations[j]);
				} catch (Exception e) {
//					System.out.println("invalid address: "  + destinations[j]);
				}
			}
		}
	}
	

	/**
	 * This method populates the desinationList and carDestMap in preparation for calculating the distances
	 * 
	 * @param carList The list of cars that we will use to populate the lists
	 * @param destinationList An empty List<String> that we will populate with a list of destinations
	 * @param carDestMap An empty Map<String, Car> that we will populate with key value pairs where the key is 
	 * the address, and the value is a Car object
	 * 
	 *  Unfixed Bug: If two people share a home address then one of them gets overwritten because the home address
	 *  			 is the key value in carDestMap
	 */
	public void destinationAddressSetup(List<Car> carList, List<String> destinationList, Map<String, Car> carDestMap) {
		for (Car d : carList) {
			String add = d.getUser().gethAddress();
			String city = d.getUser().gethCity();
			String state = d.getUser().gethState();
			String fullAdd = add + ", " + city + ", " + state;
			
			destinationList.add(fullAdd);
			carDestMap.put(fullAdd, d);
		}

	}
	
	/**
	 * Gets the Google Maps API Key from the list of environment variables.
	 * 
	 * @return The Google Maps API Key
	 */

	public String getGoogleMAPKey() {
		Map<String, String> env = System.getenv();
		for (Map.Entry<String, String> entry : env.entrySet()) {
			if (entry.getKey().equals("googleMapAPIKey")) {
				return entry.getValue();
			}
		}
		return null;
	}

}