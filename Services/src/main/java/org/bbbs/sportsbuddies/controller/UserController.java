package org.bbbs.sportsbuddies.controller;

import java.util.List;

import org.bbbs.sportsbuddies.domain.User;
import org.bbbs.sportsbuddies.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserController {
	
	private UserService userService;
	
	public UserController() {
		userService = new UserService();
	}
	
	@RequestMapping(value = "bigs/{id}", 
					method = RequestMethod.GET, 
					headers="Accept=application/json")
	public User GetBig(@PathVariable int id) {
		return userService.getBig(id);
	}
		
	@RequestMapping(value = "bigs", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public List<User> GetBigs() {
		return userService.getBigs();
	}
	
	@RequestMapping(value = "bigs",
					method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void AddBig(@RequestBody final User user) {
		userService.saveBig(user);
	}
		
	@RequestMapping(value = "bigs/{id}",
					method = RequestMethod.PUT, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void UpdateBig(@PathVariable int id, @RequestBody final User user) {
		userService.updateBig(user);
	}
	
	@RequestMapping(value = "bigs/{id}",
					method = RequestMethod.DELETE, 
					headers = "Accept=application/json")
	public void DeleteBig(@PathVariable int id) {
		userService.deleteBig(id);
	}
	
	@RequestMapping(value = "littles/{id}", 
					method = RequestMethod.GET, 
					headers="Accept=application/json")
	public User GetLittle(@PathVariable int id) {
		return userService.getLittle(id);
	}
		
	@RequestMapping(value = "littles", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public List<User> GetLittles() {
		return userService.getLittles();
	}
	
	@RequestMapping(value = "littles",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void AddLittle(@RequestBody final User user) {
		userService.saveLittle(user);
	}
	
	@RequestMapping(value = "littles/{id}",
					method = RequestMethod.PUT, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void UpdateLittle(@PathVariable int id, @RequestBody final User user) {
		userService.updateLittle(user);
	}
	
	@RequestMapping(value = "littles/{id}",
					method = RequestMethod.DELETE, 
					headers = "Accept=application/json")
	public void DeleteLittle(@PathVariable int id) {
		userService.deleteLittle(id);
	}
}