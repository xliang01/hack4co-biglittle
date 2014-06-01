package org.bbbs.sportsbuddies.controller;

import java.util.ArrayList;
import java.util.List;

import org.bbbs.sportsbuddies.domain.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserController {
	
	@RequestMapping(value = "bigs/{id}", 
					method = RequestMethod.GET, 
					headers="Accept=application/json")
	public User GetBig(@PathVariable int id) {
	
		return new User();
	}
		
	@RequestMapping(value = "bigs", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public List<User> GetBigs() {
		
		List<User> bigs = new ArrayList<User>();
		bigs.add(new User());
		bigs.add(new User());
		
		return bigs;
	}
	
	@RequestMapping(value = "bigs",
					method = RequestMethod.POST, 
					headers = "Accept=application/json")
	public void AddBig() {
	
		// Request Body Deserialize / marshal back
	}
	
	@RequestMapping(value = "bigs/{id}",
					method = RequestMethod.PUT, 
					headers = "Accept=application/json")
	public void UpdateBig(@PathVariable int id) {
	
	// Request Body Deserialize / marshal back
	}
	
	@RequestMapping(value = "bigs/{id}",
					method = RequestMethod.DELETE, 
					headers = "Accept=application/json")
	public void DeleteBig(@PathVariable int id) {
		
	}
	
	@RequestMapping(value = "littles/{id}", 
					method = RequestMethod.GET, 
					headers="Accept=application/json")
	public User GetLittle(@PathVariable int id) {
	
		return new User();
	}
		
	@RequestMapping(value = "littles", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public List<User> GetLittles() {
		
		List<User> littles = new ArrayList<User>();
		littles.add(new User());
		littles.add(new User());
		
		return littles;
	}
	
	@RequestMapping(value = "littles",
					method = RequestMethod.POST, 
					headers = "Accept=application/json")
	public void AddLittle() {
	
	// Request Body Deserialize / marshal back
	}
	
	@RequestMapping(value = "littles/{id}",
					method = RequestMethod.PUT, 
					headers = "Accept=application/json")
	public void UpdateLittle() {
	
	// Request Body Deserialize / marshal back
	}
	
	@RequestMapping(value = "littles/{id}",
					method = RequestMethod.DELETE, 
					headers = "Accept=application/json")
	public void DeleteLittle(@PathVariable int id) {
	
	}
}