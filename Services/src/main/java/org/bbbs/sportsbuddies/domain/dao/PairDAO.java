package org.bbbs.sportsbuddies.domain.dao;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Pair;

public interface PairDAO {
    // Get
    public Pair getById(int id);
    // Get All
    public List<Pair> getAll();
	// Save
    public void save(Pair pair);
    // Update
    public void update(Pair pair);
    // Delete
    public void deleteById(int id);
}
