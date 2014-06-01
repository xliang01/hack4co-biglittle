package org.bbbs.sportsbuddies.controller;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Registration;
import org.bbbs.sportsbuddies.service.RegistrationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/")
public class RegistrationController {
	
	private RegistrationService registrationService;

	public RegistrationController() {
		registrationService = new RegistrationService();
	}
	
	@RequestMapping(value = "regs/{id}", 
					method = RequestMethod.GET, 
					headers="Accept=application/json")
	public Registration GetRegistration(@PathVariable int id) {
	
		return registrationService.getRegistration(id);
	}
		
	@RequestMapping(value = "regs", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public List<Registration> GetRegistrations() {
		return registrationService.getAllRegistrations();
	}
	
	@RequestMapping(value = "regs", 
					method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void CreateRegistration(@RequestBody final Registration reg) {
		registrationService.saveRegistration(reg);
	}
	
	@RequestMapping(value = "regs/{id}", 
					method = RequestMethod.PUT, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void UpdateRegistration(@PathVariable int id, @RequestBody final Registration reg) {
		registrationService.updateRegistration(reg);
	}
	
	@RequestMapping(value = "regs/{id}", 
					method = RequestMethod.DELETE, 
					headers = "Accept=application/json")
	public void DeleteRegistration(@PathVariable int id) {
		registrationService.deleteRegistration(id);
	}
}
