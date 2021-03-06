package com.revature.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.User;
import com.revature.services.DistanceService;
import com.revature.services.UserService;

/**
 * LoginController takes care of handling our requests to /login.
 * It provides methods that can perform tasks like logging in and retrieving the google maps api key.
 * 
 * @author Timothy Mitchell
 *
 */

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private DistanceService ds;
	
	/**
	 * HTTP GET method (/login)
	 * 
	 * @param userName is the user's inputted username
	 * @param passWord is the user's inputted password
	 * @return A map of the user if the login is correct or an error statement if the login is incorrect.
	 */
	
	@GetMapping//("/{userName}/{passWord}")
	public Map<String, Set<String>> login(
							   @RequestParam(name="userName")String userName,
							   @RequestParam(name="passWord")String passWord) {
		
//		System.out.println(userName);
		Map<String, Set<String>> errors = new HashMap<>();
		if(userName.length() == 0) {
		       errors.computeIfAbsent("userName", key -> new HashSet<>()).add("userName required!");
		}
		/*if((userName == null || userName.equals("") || passWord.isEmpty())) {
		       errors.computeIfAbsent("passWord", key -> new HashSet<>()).add("passWord required!");
		}*/
		if (errors.isEmpty()) {
			Map<String, Set<String>> info = new HashMap<>();
			//call login service here

			User u=us.getUserByUsername(userName);
			if(u != null) {
			   info.computeIfAbsent("name", key -> new HashSet<>()).add(u.getFirstName()+" "+u.getLastName());
			   info.computeIfAbsent("userid", key -> new HashSet<>()).add(u.getUserId()+"");
			   info.computeIfAbsent("batchLoc", key -> new HashSet<>()).add(u.getBatch().getBatchLocation());

			}else {
				info.computeIfAbsent("userNotFound", key -> new HashSet<>()).add("User not found!");
			}
//			System.out.println(info);
			return info;
		}else {
			 return errors;
		}
	}
	
	/**
	 * HTTP GET method (/login/getGoogleApi)
	 * 
	 * @return The google maps api key if it is an environment variable and correct. If not, the return is an error statement.
	 */
	
	@GetMapping("/getGoogleApi")
	public Map<String, Set<String>> getGoogleApi() {
		Map<String, Set<String>> info = new HashMap<>();
		 // getting API key
		 String newkey = ds.getGoogleMAPKey();
		 info.computeIfAbsent("googleMapAPIKey", key -> new HashSet<>()).add(newkey);
		 return info;
	}
	
}
