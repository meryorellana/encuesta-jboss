package com.somostubackup.encuesta.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.somostubackup.encuesta.entity.Answer;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AnswerCreate {
	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	public void create(Answer newAnswer) throws Exception{
		log.info("New Answer" + newAnswer.getValuemin());
		em.persist(newAnswer);
	}
	public void edit(Answer newAnswer) throws Exception{
		log.info("Edit Answer" + newAnswer.getValuemin());
		em.merge(newAnswer);
	}

	public int deletebyAnswer(Answer answer){
		Query q = em.createNamedQuery ("Optionstypeanswer.deletebyIdAnswer");
		q.setParameter("answer", answer);
		return q.executeUpdate();
	}

}
