package com.somostubackup.encuesta.controller;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.somostubackup.encuesta.entity.Answer;
import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.service.AnswerCreate;


@Model
public class AnswerController {
	@Inject
    private FacesContext facesContext;

    @Produces
    @Named
    private Answer newAnswer;
    
    @Inject
    private AnswerCreate answerCreate;
    
    private Ask newAsk;
    
    @Inject 
    private HttpSession session;

    @PostConstruct
    public void initNewAnswer() {
    	newAsk = (Ask) facesContext.getExternalContext().getSessionMap().get("newAsk");
    	if((newAsk.getAnswers()!=null)&&(newAsk.getAnswers().size()>0)){
    		newAnswer = newAsk.getAnswers().get(0);
    	}
    	else{
    		newAnswer = new Answer();
    	}
    }
    
    public String create(){
    	if(session!=null){
        try {
        	newAsk = (Ask) facesContext.getExternalContext().getSessionMap().get("newAsk");
        	newAnswer.setAsk(newAsk);
        	if((newAsk.getAnswers()!=null)&&(newAsk.getAnswers().size()>0)){
        		answerCreate.edit(newAnswer);
        		newAsk.getAnswers().remove(0);
        		
        	}        	
        	else{
        		answerCreate.create(newAnswer);
        		newAsk.setAnswers(new ArrayList<Answer>());
        	}
			
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
	        facesContext.getExternalContext().getSessionMap().remove("newAsk");
	        facesContext.getExternalContext().getSessionMap().put("editAsk",false);
	        // actualizar newTest
	        newAsk.getAnswers().add(0, newAnswer);
	    	Test newTest = (Test) facesContext.getExternalContext().getSessionMap().get("newTest");
	    	newTest.addAsk(newAsk);
	    	facesContext.getExternalContext().getSessionMap().put("newTest", newTest);
	        return "newask";
        } catch (Exception e) {
            String errorMessage = e.getLocalizedMessage();
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
        return "";
    	}else{
    		return "index";
    	}
    }
}
