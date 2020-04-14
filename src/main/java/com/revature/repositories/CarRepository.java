package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.beans.Car;

/**
 * CarRepository which extends the JpaRepository.
 * This repository handles our various queries.
 * 
 * @author Adonis Cabreja
 *
 */

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
	
	/**
	 * Custom query that uses the @Query annotation to select a car by the user's id.
	 * 
	 * @param userId represents the user's id.
	 * @return Check {@link com.revature.services.impl.UserServiceImpl}
	 */
	
	@Query("select c from Car c where c.user.userId = ?1")
	public Car getCarByUserId(int userId);
	
	@Query("select c from Car c where c.user.isDriver = true  and c.user.isActive = true and c.user.isAcceptingRides = true  and c.user.batch.batchLocation = ?1")
	public List<Car> getUserByLocation(String location);
}
