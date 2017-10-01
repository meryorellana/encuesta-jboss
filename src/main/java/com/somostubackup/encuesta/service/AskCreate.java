package com.somostubackup.encuesta.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.somostubackup.encuesta.entity.Ask;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AskCreate {
	@Inject
	private EntityManager em;
	@Inject
	private Logger log;
	
//	@Inject 
//	private Event<Ask> evtask;
	
	public void create(Ask newask) throws Exception{
		log.info("New ask " + newask.getContent());
    	em.persist(newask);
//		evtask.fire(newask);
	}
	public void edit(Ask newask) throws Exception{
		log.info("Edit ask " + newask.getContent());
    	em.merge(newask);
//		evtask.fire(newask);
	}
	public void delete(Integer id) throws Exception{
		Ask newask = em.find(Ask.class, id);
		log.info("Delete ask " + newask.getContent());
		// se remueven las respuestas primero
		Query q = em.createNamedQuery("Answer.deletebyIdAsk");
		q.setParameter("ask", newask);
		q.executeUpdate();
		em.remove(newask);
//		evtask.fire(newask);
	}

}
