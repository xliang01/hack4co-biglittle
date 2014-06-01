package org.bbbs.sportsbuddies.service;

import java.util.List;

import org.bbbs.sportsbuddies.domain.Registration;
import org.bbbs.sportsbuddies.domain.dao.RegistrationDAO;

public class RegistrationService extends BaseService {

	private RegistrationDAO registrationDAO;
	
	public RegistrationService()
	{
		registrationDAO = (RegistrationDAO) context.getBean("RegistrationDAO");
	}
	
    public List<Registration> getAllRegistrations() 
    {   
        return registrationDAO.getAll();
    }
    
    public Registration getRegistration(int id)
    {
    	return registrationDAO.getById(id);
    }
    
    public void saveRegistration(Registration reg) 
    {
    	if(reg != null && reg.getRegistrationId() == -1)
    	{
    		registrationDAO.save(reg);
    	}
    	else
    	{
    		updateRegistration(reg);
    	}
    }
    
    public void updateRegistration(Registration reg) 
    {
    	if(reg != null && getRegistration(reg.getRegistrationId()) != null) 
    	{
    		registrationDAO.update(reg);
    	}
    }
    
    public void deleteRegistration(int id) 
    {
    	if(getRegistration(id) != null)
    	{
    		registrationDAO.deleteById(id);
    	}
    }
}