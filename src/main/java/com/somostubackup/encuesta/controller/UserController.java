package com.somostubackup.encuesta.controller;



import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.jboss.tools.examples.util.Resources;

import com.somostubackup.encuesta.data.TestFillRepository;
import com.somostubackup.encuesta.data.UserRepository;
import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.User;
import com.somostubackup.encuesta.entity.UserTest;
import com.somostubackup.encuesta.service.TestCreate;
import com.somostubackup.encuesta.service.UserRegistration;

@Model
public class UserController {
	@Inject
    private FacesContext facesContext;

    @Inject
    private UserRegistration userRegistration;
    
    @Inject
    private UserRepository userRepository;    
    
    @Inject
    private TestCreate testRegistration;
    
    @Inject 
    private TestFillRepository testfillrep;
    
    @Produces
    @Named
    private User newUser;
    
    private String strlogin="listtest";
    
    @Inject
    Logger log;
    
    private User logUser;


    @PostConstruct
    public void initNewUser() {
        newUser = new User();
    }

    public String register() throws Exception {
    	
        try {
        	logUser = userRepository.findByEmail(newUser.getEmail());
        }catch(Exception e){}
        try{
        	if(logUser==null){
        		userRegistration.register(newUser);
        		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
                facesContext.addMessage(null, m);
        	}else{
        		newUser = logUser;
        	}
            
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
        if(facesContext.getExternalContext().getSessionMap().containsKey("newTest")){
//        	if(newUser == null){
//        		newUser = userRepository.findByEmail(logUser.getEmail());
//        	}
//        	facesContext.getExternalContext().getSessionMap().put("newUser", newUser);
        	Test newTest = (Test) facesContext.getExternalContext().getSessionMap().get("newTest");
        	facesContext.getExternalContext().getSessionMap().put("countask",0);
			try{
				UserTest usertesttmp = new UserTest();
		    	usertesttmp.setTest(newTest);
		    	usertesttmp.setUser(newUser);
		    	usertesttmp.setIpaddress(Resources.getRemoteAddress((HttpServletRequest) facesContext.getExternalContext().getRequest()));
		    	usertesttmp.setDateTest(new Date());
		    	testRegistration.registerUsertest(usertesttmp);
				facesContext.getExternalContext().getSessionMap().put("newUsertest", usertesttmp);
				if(facesContext.getExternalContext().getSessionMap().get("fill").toString().equalsIgnoreCase("1")){
					newTest.setAsks(testfillrep.findAllAskbyTest(newTest));
			    	Ask newAsk = newTest.getAsks().get(0);
    				//newAsk.setAnswers(testfillrep.findAllAnswerbyAsk(newAsk));
    	        	facesContext.getExternalContext().getSessionMap().put("newAsk", newAsk);
    	    		return "newusertest";
				}
				else{
					return "externalTest";
				}
			}
			catch(Exception e){
				String errorMessage = e.getLocalizedMessage();
	            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Fill Poll unsuccessful");
	            facesContext.addMessage(null, m);
			}
			
        }else{
        	facesContext.getExternalContext().getSessionMap().put("newUser", newUser);
        	List<Test> listest = userRepository.findAllTestbyUser(logUser);
    		facesContext.getExternalContext().getSessionMap().put("counttest", ((listest!=null)&&(listest.size()>0)));
        	//initNewUser();
        	 return "listtest";
        }
       return "";
    }

    public String login() throws Exception {
    	String rtstr="";
        try {
        	logUser = userRepository.findByEmail(newUser.getEmail());
        	FacesMessage m;
        	if(logUser.getPassword().equals(newUser.getPassword())){
        		log.info("Usuario " +String.valueOf(logUser.getIdUser()));
        		List<Test> listest = userRepository.findAllTestbyUser(logUser);
        		logUser.setTests(listest);
        		facesContext.getExternalContext().getSessionMap().put("newUser", logUser);
        		facesContext.getExternalContext().getSessionMap().put("counttest", ((listest!=null)&&(listest.size()>0)));
        		//newUser = logUser;
        		rtstr=strlogin;
        	}else{
        		ResourceBundle text = ResourceBundle.getBundle("com.somostubackup.encuesta.message.ApplicationResource", facesContext.getViewRoot().getLocale());
           	    m = new FacesMessage(FacesMessage.SEVERITY_INFO, text.getString("wrongpass"), "");
           	    facesContext.addMessage(null, m);
           	    return "";
        	}
            
   		 
        }
        catch (javax.persistence.NoResultException e) {
       	    facesContext.getExternalContext().getSessionMap().put("newUser", logUser);
        	return "newuserreg";
        }
        catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            ResourceBundle text = ResourceBundle.getBundle("com.somostubackup.encuesta.message.ApplicationResource", facesContext.getViewRoot().getLocale());
       	    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, text.getString("error.login"));
            facesContext.addMessage(null, m);
        }
        return rtstr;
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
    public void selectLanguage(){
    	String key = facesContext.getExternalContext().getRequestParameterMap().get("key");
    	if(key.equalsIgnoreCase("es")){
    		facesContext.getViewRoot().setLocale(Locale.forLanguageTag(key));
    	}else{
    		facesContext.getViewRoot().setLocale(Locale.getDefault());
    	}
    }
    public String salir(){
    	facesContext.getExternalContext().invalidateSession();
    	return "index";
    }
    
    public String forgotPassword(){
		try{
			User loginuser = userRepository.findByEmail(newUser.getEmail());
			facesContext.getExternalContext().getSessionMap().put("newUser",loginuser);
			return "newpassword";
		}
		catch(Exception e){
			log.info(e.getMessage());
			ResourceBundle text = ResourceBundle.getBundle("com.somostubackup.encuesta.message.ApplicationResource", facesContext.getViewRoot().getLocale());
			 FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("userunregistered"), text.getString("userunregistered"));
			 facesContext.addMessage(null, m);
		}
		return "";
	}

}
