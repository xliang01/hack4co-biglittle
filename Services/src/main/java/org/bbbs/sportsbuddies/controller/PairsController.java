package org.bbbs.sportsbuddies.controller;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Pair;
import org.bbbs.sportsbuddies.service.PairService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class PairsController {

	private PairService pairService;
	
	public PairsController() {
		pairService = new PairService();
	}
	
	@RequestMapping(value = "pairs/{id}", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public Pair GetPair(@PathVariable int id) {
		return pairService.getPair(id);
	}
	
	@RequestMapping(value = "pairs", 
					method = RequestMethod.GET, 
					headers = "Accept=application/json")
	public List<Pair> GetPairs() {	
		return pairService.getAllPairs();
	}
	
	@RequestMapping(value = "pairs", 
					method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void AddPair(@RequestBody final Pair pair) {
		pairService.savePair(pair);
	}
	
	@RequestMapping(value = "pairs/{id}", 
					method = RequestMethod.PUT, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void UpdatePair(@PathVariable int id, @RequestBody final Pair pair) {
		pairService.updatePair(pair);
	}
	
	@RequestMapping(value = "pairs/{id}",
					method = RequestMethod.DELETE, 
					headers = "Accept=application/json")
	public void DeletePair(@PathVariable int id) {
		pairService.deletePair(id);
	}
}
