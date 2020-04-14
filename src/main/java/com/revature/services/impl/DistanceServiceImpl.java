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
	public List<User> distanceMatrix(String[] origins, String[] destinations)
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
					System.out.println((j + 1) + "): " + t.rows[i].elements[j].distance.inMeters + " meters");
					arrlist.add((double) t.rows[i].elements[j].distance.inMeters);

					unsortMap.put((double) t.rows[i].elements[j].distance.inMeters, destinations[j]);

					System.out.println((double) t.rows[i].elements[j].distance.inMeters);

				} catch (Exception e) {
					System.out.println("invalid address");
				}
			}
		}

//		LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
//		unsortMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
//                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
//		

		System.out.println("-");

		Collections.sort(arrlist);

		System.out.println(arrlist);
		List<String> destList = new ArrayList<String>();

		arrlist.removeIf(r -> (arrlist.indexOf(r) > 4));

		Double[] arrArray = new Double[arrlist.size()];

		arrArray = arrlist.toArray(arrArray);

		System.out.println(arrArray);

		for (int c = 0; c < arrArray.length; c++) {
			String destination = unsortMap.get(arrArray[c]);
			destList.add(destination);
		}
		
		System.out.println(destList);
		
		String[] destArray = new String[destList.size()];
		
		destArray = destList.toArray(destArray);
		
		List<User> userList = new ArrayList<User>();
		
		for (int x = 0; x < destArray.length; x++) {
			User a = userDestMap.get(destArray[x]);
			System.out.println(a);
			userList.add(a);
			System.out.println(userList);
		}
		return userList;
	}
	
	/**
	 * This method receives a list of origins and a list of Car objects, determines the distance that a user would
	 * need to drive to get to their batch location, and returns the 10 closest Cars in a list
	 * 
	 * @param origins represents the users batch location
	 * @param carList a list of cars that we check for distance
	 * @return A list of cars that have to travel the least distance to their batch location
	 */
	
	@Override
	public List<Car> distanceCarMatrix(String[] origins, List<Car> carList)
			throws ApiException, InterruptedException, IOException {
		String[] destinations;
		Map<String, Car> carDestMap = new HashMap<String, Car>();

		List<String> destinationList = new ArrayList<String>();

		for (Car d : carList) {

			String add = d.getUser().gethAddress();
			String city = d.getUser().gethCity();
			String state = d.getUser().gethState();

			String fullAdd = add + ", " + city + ", " + state;

			destinationList.add(fullAdd);

			carDestMap.put(fullAdd, d);

		}

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
					arrlist.add((double) t.rows[i].elements[j].distance.inMeters);

					unsortMap.put((double) t.rows[i].elements[j].distance.inMeters, destinations[j]);

				} catch (Exception e) {
					System.out.println("invalid address: "  + destinations[j]);
				}
			}
		}

		Collections.sort(arrlist);

		List<String> destList = new ArrayList<String>();

		arrlist.removeIf(r -> (arrlist.indexOf(r) > 9));

		Double[] arrArray = new Double[arrlist.size()];

		arrArray = arrlist.toArray(arrArray);

		for (int c = 0; c < arrArray.length; c++) {
			String destination = unsortMap.get(arrArray[c]);
			destList.add(destination);
		}
		
		String[] destArray = new String[destList.size()];
		
		destArray = destList.toArray(destArray);
		
		List<Car> carFinalList = new ArrayList<Car>();
		
		for (int x = 0; x < destArray.length; x++) {
			Car a = carDestMap.get(destArray[x]);
			carFinalList.add(a);
		}
		return carFinalList;
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