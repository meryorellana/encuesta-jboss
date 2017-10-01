package com.somostubackup.encuesta.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.somostubackup.encuesta.data.TestFillRepository;
import com.somostubackup.encuesta.data.UserRepository;
import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.User;
import com.somostubackup.encuesta.service.AskCreate;


@Model
public class AskController {
	@Inject
    private FacesContext facesContext;

    @Produces
    @Named
    private Ask newAsk;
    
    @Inject
    private AskCreate askCreate;
    
    @Inject
    private TestFillRepository askrep;
    
    private boolean editAsk = false;
    
    private Test sessionTest;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private HttpSession session;

    @PostConstruct
    public void initNewAsk() {
    	if(!((Boolean) facesContext.getExternalContext().getSessionMap().get("editAsk"))){
    		newAsk = new Ask();
    	}
    	else{
    		newAsk = (Ask) facesContext.getExternalContext().getSessionMap().get("newAsk");
    	}
    }
    
    public String create() throws Exception {
    	if(session!= null){
        try {
        		sessionTest = (Test) facesContext.getExternalContext().getSessionMap().get("newTest"); 
	        	if((Boolean) facesContext.getExternalContext().getSessionMap().get("editAsk")){
	        		facesContext.getExternalContext().getSessionMap().put("editAsk", false);
	        		askCreate.edit(newAsk);
	        	}else{
		        	newAsk.setTest(sessionTest);
		        	askCreate.create(newAsk);
	        	}
	        	FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
	            facesContext.addMessage(null, m);

    	    	if((newAsk.getTypeanswer().indexOf("Checkbox")>0)||(newAsk.getTypeanswer().indexOf("Radio")>0)){
	            	newAsk.setAnswers(askrep.findAllAnswerbyAsk(newAsk));
	            	facesContext.getExternalContext().getSessionMap().put("newAsk", newAsk);
	            	return "newanswer";
	            }
   	         // actualizar newTest
            	sessionTest.addAsk(newAsk);
    	    	facesContext.getExternalContext().getSessionMap().remove("newAsk");
    	    	facesContext.getExternalContext().getSessionMap().put("newTest", sessionTest);
            	initNewAsk();
            	
        } catch (Exception e) {
        	e.printStackTrace();
            String errorMessage = e.getLocalizedMessage();
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
        return "";
    	}else{
    		return "index";
    	}
    }
    public String finish(){
    	if(session!=null){
    	// TODO redireccionar a la lista de encuestas
    	User newUser = (User) facesContext.getExternalContext().getSessionMap().get("newUser");
    	List<Test> listest = userRepository.findAllTestbyUser(newUser);
		newUser.setTests(listest);
    	facesContext.getExternalContext().getSessionMap().put("newUser", newUser);
		facesContext.getExternalContext().getSessionMap().put("counttest", ((listest!=null)&&(listest.size()>0)));
		return "listtest";
    }else{
		return "index";
	}
    }
    public String editAsk(){
    	if(session !=null){
    	String key = facesContext.getExternalContext().getRequestParameterMap().get("key");
    	
    	newAsk = askrep.findByIdAsk(Integer.valueOf(key));
    	
    		facesContext.getExternalContext().getSessionMap().put("newAsk", newAsk);
        	facesContext.getExternalContext().getSessionMap().put("editAsk", true);
        	
    	sessionTest = (Test) facesContext.getExternalContext().getSessionMap().get("newTest");
    	sessionTest.removeAskbyId(Integer.valueOf(key));
    	facesContext.getExternalContext().getSessionMap().put("newTest", sessionTest);
    	return "";
    	}else{
    		return "index";
    	}
    }
    public String deleteAsk(){
    	if(session!=null){
    	try{
			String key = facesContext.getExternalContext()
					.getRequestParameterMap().get("key");
			//newAsk = askrep.findByIdAsk(Integer.valueOf(key));
			editAsk = false;
			facesContext.getExternalContext().getSessionMap().put("editAsk", editAsk);
	    	sessionTest = (Test) facesContext.getExternalContext().getSessionMap().get("newTest");
	    	//askCreate.delete(newAsk);
	    	askCreate.delete(Integer.valueOf(key));
	    	sessionTest.removeAskbyId(Integer.valueOf(key));
	    	facesContext.getExternalContext().getSessionMap().put("newTest", sessionTest);
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
			//log.severe(e.getMessage());
		}
		return "";
    	}
    	else{
			return "index";
		}
    }
//	public void onAskListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Ask ask) {
//		sessionTest = ask.getTest();
//		//sessionTest.setAsks(askrep.findAllAskbyTest(ask.getTest()));
//		facesContext.getExternalContext().getSessionMap().replace("newTest", sessionTest);
//    }

}
