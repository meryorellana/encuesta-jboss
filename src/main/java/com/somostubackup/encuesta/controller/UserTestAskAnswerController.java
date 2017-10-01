package com.somostubackup.encuesta.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.UserTest;
import com.somostubackup.encuesta.model.AnswerTest;
import com.somostubackup.encuesta.service.UsertestaskanswerRegister;

@Model
public class UserTestAskAnswerController {
	
	@Inject
	private UsertestaskanswerRegister utestaskansreg;
	
	@Produces
	@Named
	private AnswerTest anstest;
	
	private int  countask;
    
    private Ask newAsk;
	
    private Test newTest;
    
    @Inject
    private Logger log;
    
    @Inject
    private FacesContext facesContext;
    
    @PostConstruct
	public void initUsertestaskanswer(){
    	anstest =  new AnswerTest();
    	
	}
	
	public String register() throws Exception {
		return "fintest";
	}
	public String registerOne() throws Exception {

		newAsk = (Ask) facesContext.getExternalContext().getSessionMap().get("newAsk");
		newTest = (Test) facesContext.getExternalContext().getSessionMap().get("newTest");
		anstest.setIdAsk(newAsk);
		countask = (Integer) facesContext.getExternalContext().getSessionMap().get("countask");
		try {
			UserTest userTest = (UserTest) facesContext.getExternalContext().getSessionMap().get("newUsertest");
			log.info(userTest.getUser().getName());
			anstest.setIdUserTest(userTest);
			utestaskansreg.registeransOne(anstest);
			countask = countask + 1;
			if (countask >= newTest.getAsks().size()) {
				return "fintest";
			} else {
				// Prepara para la siguiente pregunta o final de la encuesta
				newAsk = newTest.getAsks().get(countask);
					facesContext.getExternalContext().getSessionMap()
						.put("countask", countask);
				facesContext.getExternalContext().getSessionMap()
						.put("newAsk", newAsk);
				anstest.setValue("");
				initUsertestaskanswer();
				return "newusertest";
			}
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
		return "";
	}

	 private String getRootErrorMessage(Exception e) {
	        // Default to general error message that registration failed.
	        String errorMessage = "Registration failed. See server log for more information";
	        if (e == null) {
	            // This shouldn't happen, but return the default messages
	            return errorMessage;
	        }

	        // Start with the exception and recurse to find the root cause
	        Throwable t = e;
	        while (t != null) {
	            // Get the message from the Throwable class instance
	            errorMessage = t.getLocalizedMessage();
	            t = t.getCause();
	        }
	        // This is the root cause message
	        return errorMessage;
	    }
}

