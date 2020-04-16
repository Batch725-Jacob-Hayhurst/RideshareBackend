package com.revature.services;

import java.util.List;

import com.revature.beans.User;

/**
 * UserService is an interface that formats the user service methods.
 * 
 * @author Timothy Mitchell
 *
 */

public interface UserService {
	
	public List<User> getUsers();
	public User getUserById(int id);
	public User getUserByUsername(String username);
	public List<User> getUserByRole(boolean isDriver);
	public List<User> getUserByRoleAndLocation(boolean isDriver, String location);
	public User addUser(User user);
	public User updateUser(User user);
	public String deleteUserById(int id);
	public List<User> getActiveDrivers();
	public Boolean isUsernameAvailable(String username);
}
