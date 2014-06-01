package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Event;
import org.bbbs.sportsbuddies.domain.Pair;

public interface PairDAO {
    
    public void save(Pair pair);
    
    public Pair getById(int id);
    //Update
    public void update(Pair pair);
    //Delete
    public void deleteById(int id);
    //Get All
    public List<Pair> getAll();
}
