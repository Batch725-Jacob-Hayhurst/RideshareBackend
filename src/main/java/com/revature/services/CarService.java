package com.revature.services;

import java.util.List;

import com.revature.beans.Car;

/**
 * CarService is an interface that formats the car service methods.
 * 
 * @author Timothy Mitchell
 *
 */

public interface CarService {
	
	public List<Car> getCars();
	public List<Car> getCarByLocation(String location);
	public Car getCarById(int id);
	public Car getCarByUserId(int userId);
	public Car addCar(Car car);
	public Car updateCar(Car car);
	public String deleteCarById(int id);
}
