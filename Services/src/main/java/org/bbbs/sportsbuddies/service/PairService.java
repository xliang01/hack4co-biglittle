package org.bbbs.sportsbuddies.service;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.domain.Pair;
import org.bbbs.sportsbuddies.domain.User;
import org.bbbs.sportsbuddies.domain.dao.EventDAO;
import org.bbbs.sportsbuddies.domain.dao.PairDAO;
import org.bbbs.sportsbuddies.domain.dao.UserDAO;

public class PairService extends BaseService {
	
	private PairDAO pairDAO;
	
	public PairService() 
	{
		pairDAO = (PairDAO) context.getBean("PairDAO"); 
	}
	
    public List<Pair> getAllPairs() {
       
        return pairDAO.getAll();
    }
    
    public Pair getPair(int id)
    {
    	return pairDAO.getById(id);
    }
	
}