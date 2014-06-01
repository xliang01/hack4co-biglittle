package org.bbbs.sportsbuddies.controller;

import java.util.ArrayList;
import java.util.List;

import org.bbbs.sportsbuddies.domain.Pair;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class PairsController {

	@RequestMapping(value = "pairs/{id}", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public Pair GetPair(@PathVariable int id) {
		
		return new Pair();
	}
	
	@RequestMapping(value = "pairs", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public List<Pair> GetPairs() {
		
		List<Pair> pairs = new ArrayList<Pair>();
		pairs.add(new Pair());
		pairs.add(new Pair());
		
		return pairs;
	}
	
	@RequestMapping(value = "pairs", 
					method = RequestMethod.POST, 
					headers = "Accept=application/json")
	public void AddPair() {
		
	}
	
	@RequestMapping(value = "pairs/{id}", 
					method = RequestMethod.PUT, 
					headers = "Accept=application/json")
	public void UpdatePair(@PathVariable int id) {
		
	}
	
	@RequestMapping(value = "pairs/{id}",
					method = RequestMethod.DELETE, 
					headers = "Accept=application/json")
	public void DeletePair(@PathVariable int id) {
	
	}
}
