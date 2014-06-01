package org.bbbs.sportsbuddies.controller;

import java.util.ArrayList;
import java.util.List;

import org.bbbs.sportsbuddies.domain.Registration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/")
public class RegistrationController {

	@RequestMapping(value = "regs/{id}", 
					method = RequestMethod.GET, 
					headers="Accept=application/json")
	public Registration GetRegistration(@PathVariable int id) {
	
		return new Registration();
	}
		
	@RequestMapping(value = "regs", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public List<Registration> GetRegistrations() {
		
		List<Registration> registrations = new ArrayList<Registration>();
		registrations.add(new Registration());
		registrations.add(new Registration());
		
		return registrations;
	}
	
	@RequestMapping(value = "regs", 
					method = RequestMethod.POST, 
					headers = "Accept=application/json")
	public void CreateRegistration() {
		
	}
	
	@RequestMapping(value = "regs/{id}", 
					method = RequestMethod.PUT, 
					headers = "Accept=application/json")
	public void UpdateRegistration(@PathVariable int id) {
		
	}
	
	@RequestMapping(value = "regs/{id}", 
					method = RequestMethod.DELETE, 
					headers = "Accept=application/json")
	public void DeleteRegistration(@PathVariable int id) {
		
	}
}
