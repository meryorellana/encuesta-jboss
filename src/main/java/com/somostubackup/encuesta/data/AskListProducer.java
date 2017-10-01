package com.somostubackup.encuesta.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;

@RequestScoped
public class AskListProducer {

	private TestFillRepository testfillrep;
	
	private List<Ask> asks;
	
	
	@Produces
	@Named
	public List<Ask> getAsks(){
		return asks;
	}
	


	private void retrieveAllAskOrderedByidAsk(Test idTest){
		asks = testfillrep.findAllAskbyTest(idTest);
	}
	

}
