package com.somostubackup.encuesta.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.UserTest;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TestCreate {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;
    
    
    public void create(Test newtest) throws Exception {
    	log.info("Create " + newtest.getDescription());
         em.persist(newtest);
    }
    
    public void update(Test newtest) throws Exception {
    	log.info("Merge " + newtest.getDescription());
         em.merge(newtest);
    }
    
    public void registerUsertest(UserTest usertesttmp) throws Exception{
    	// guardar la encuesta
    	log.info("Address:" + usertesttmp.getIpaddress());
    	em.persist(usertesttmp);
    }

    public void remove(Test newTest){
    	log.info("Delete test " + newTest.getName());
   		em.remove(newTest);
    	    	
    }
   
}
