package org.bbbs.sportsbuddies.service;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Pair;
import org.bbbs.sportsbuddies.domain.dao.PairDAO;

public class PairService extends BaseService {
	
	private PairDAO pairDAO;
	
	public PairService() 
	{
		pairDAO = (PairDAO) context.getBean("PairDAO"); 
	}
	
    public List<Pair> getAllPairs() 
    {   
        return pairDAO.getAll();
    }
    
    public Pair getPair(int id)
    {
    	return pairDAO.getById(id);
    }
    
    public void savePair(Pair pair) 
    {
    	if(pair != null && pair.getPairId() == -1)
    	{
    		pairDAO.save(pair);
    	}
    	else 
    	{
    		updatePair(pair);
    	}
    }
    
    public void updatePair(Pair pair) 
    {
    	if(pair != null && getPair(pair.getPairId()) != null) 
    	{
    		pairDAO.update(pair);
    	}
    }
    
    public void deletePair(int id) 
    {
    	if(getPair(id) != null) 
    	{
    		pairDAO.deleteById(id);
    	}
    }
    
}