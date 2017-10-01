package com.somostubackup.encuesta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.tools.examples.util.Resources;

import com.somostubackup.encuesta.data.TestFillRepository;
import com.somostubackup.encuesta.data.TestRepository;
import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.User;
import com.somostubackup.encuesta.entity.UserTest;
import com.somostubackup.encuesta.entity.UserTestAskAnswer;
import com.somostubackup.encuesta.model.AnswerTest;
import com.somostubackup.encuesta.model.PageTest;
import com.somostubackup.encuesta.service.TestCreate;

@Model
public class TestController {
	@Inject
    private FacesContext facesContext;

    @Produces
    @Named
    private Test newTest;
    
    @Inject
    private TestCreate testRegistration;
    
 
    @Inject
    private TestRepository testrep;
    
    @Inject 
    private TestFillRepository testfillrep;
    
    private String rtnewask="newask";
    
    private String rtdeltest="listtest";
    
    @Inject
    private Logger log;
    
    @ManagedProperty(value="#{param.key}")
    private String key;
    @ManagedProperty(value="#{param.fill}")
    private String fill;
    
    @Inject
    private HttpSession session;
    
    private Ask newAsk;

    @PostConstruct
    public void initnewTest() {
        if(facesContext.getExternalContext().getSessionMap().containsKey("newTest")){
        	newTest = (Test) facesContext.getExternalContext().getSessionMap().get("newTest");
        }else{
        	newTest = new Test();
        }
    }

    
    public String create() throws Exception {
		if (session != null) {
			try {
				if (facesContext.getExternalContext().getSessionMap()
						.containsKey("newTestEdit")) {
					testRegistration.update(newTest);
					facesContext.getExternalContext().getSessionMap().remove("newTestEdit");
					return "listtest";
				} else {

					log.info("creando " + newTest.getName());
					User sessionuser = (User) facesContext.getExternalContext()
							.getSessionMap().get("newUser");
					newTest.setUser(sessionuser);
					testRegistration.create(newTest);
					FacesMessage m = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Registered!",
							"Registration successful");
					facesContext.addMessage(null, m);
					// initNewTest();
					facesContext.getExternalContext().getSessionMap()
							.put("newTest", newTest);
					facesContext.getExternalContext().getSessionMap()
							.put("editAsk", false);
					return rtnewask;
				}
			} catch (Exception e) {
				String errorMessage = e.getLocalizedMessage();
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						errorMessage, "Registration unsuccessful");
				facesContext.addMessage(null, m);
				return "";
			}
		} else {
			return "index";
		}
         
    }
    
    
    public String delTest(){
    	if(session!=null){
    	key = facesContext.getExternalContext().getRequestParameterMap().get("key");
    	log.info("key " + key);
    	newTest = testrep.findById(Integer.valueOf(key));
    	testRegistration.remove(newTest);
    	return rtdeltest;
    	}else{
    		return "index";
    	}
    }
    
    public String editTest(){
    	if(session!=null){
    	key = facesContext.getExternalContext().getRequestParameterMap().get("key");
    	fill = facesContext.getExternalContext().getRequestParameterMap().get("fill");
    	log.info("key " + key);
    	newTest = testrep.findById(Integer.valueOf(key));
    	
    	facesContext.getExternalContext().getSessionMap().put("fill",fill);
    	newTest.setAsks(testfillrep.findAllAskbyTest(newTest));
    	facesContext.getExternalContext().getSessionMap().put("newTest",newTest);
    	
    	
    	if(fill.equalsIgnoreCase("0")){
    		facesContext.getExternalContext().getSessionMap().put("editAsk", false);
    		return "newask";
    	}else{
//    		Lo maneja la página si es anómina o no 
//    		if(newTest.getIsAnonymous()==1){
    			try{
    				filltestInit(fill);
    			}
    			catch(Exception e){
    				String errorMessage = e.getLocalizedMessage();
    	            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Fill Poll unsuccessful");
    	            facesContext.addMessage(null, m);
    			}
    			if(fill.equalsIgnoreCase("1")){
    				if((newTest.getAsks()==null)||(newTest.getAsks().size()==0)){
    					ResourceBundle text = ResourceBundle.getBundle("com.somostubackup.encuesta.message.ApplicationResource", facesContext.getViewRoot().getLocale());
    	           	    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, text.getString("error.noasks"), "");
    	           	    facesContext.addMessage(null, m);
    					return "";
    				}
    				newAsk = newTest.getAsks().get(0);
    				newAsk.setAnswers(testfillrep.findAllAnswerbyAsk(newAsk));
    	        	facesContext.getExternalContext().getSessionMap().put("newAsk", newAsk);
    	    		facesContext.getExternalContext().getSessionMap().put("countask", 0);
    	    		return "newusertest";
    			}else{
    				
    				return "externalTest";
    			}
    			
//    		}else{
//    			// No es una encuesta anónima se debe registrar el usuario
//    			return "newuserreg";
//    		}
    	}
    	}else{
    		return "index";
    	}
    	
    }
    
    public String editTestDesc(){
    	if(session!=null){
    	key = facesContext.getExternalContext().getRequestParameterMap().get("key");
    	newTest = testrep.findById(Integer.valueOf(key));
    	facesContext.getExternalContext().getSessionMap().put("newTest",newTest);
    	facesContext.getExternalContext().getSessionMap().put("newTestEdit","0");
    	return "newtest";
    	}else{
    		return "index";
    	}
    	
    }
    
    public String allTest(){
    	if(session !=null){
    	key = facesContext.getExternalContext().getRequestParameterMap().get("key");
    	newTest = testrep.findById(Integer.valueOf(key));
    	newTest.setAsks(testfillrep.findAllAskbyTest(newTest));
    	facesContext.getExternalContext().getSessionMap().put("newTest",newTest);
    	facesContext.getExternalContext().getSessionMap().put("pagetest",fillpagetest());
    	return "alltest";
    	}else{
    		return "index";
    	}
    }
    public boolean bcountAnswer(Integer id){
    	newTest = testrep.findById(id);
    	if(newTest != null){
    		List<UserTest> listusertest = testrep.findUsertestByTest(newTest);
    		return ((listusertest==null)||(listusertest.size()==0));
    	}else{
    		return false;
    	}
    }
    public Integer countAnswer(Integer id){
    	newTest = testrep.findById(id);
    	if(newTest != null){
    		List<UserTest> listusertest = testrep.findUsertestByTest(newTest);
    		if(listusertest!=null){
    		return listusertest.size();
    		}
    	}
    		return 0;
    	
    }
    private List<PageTest> fillpagetest(){
    	List<PageTest> listpagetest = new ArrayList<PageTest>();
    	PageTest pagetest=null;
    	AnswerTest anstest = new AnswerTest(new Ask(0));
    	int id=0;
    	newTest.setUsertest(testrep.findUsertestByTest(newTest));
    	for(UserTest ustest:newTest.getUsertest()){
    		pagetest = new PageTest(ustest.getUser(),ustest.getTest());
    		ustest.setUsertestaskanswers(testfillrep.findUserTestAskAnswerbUsertest(ustest));
    		for(UserTestAskAnswer ustestans: ustest.getUsertestaskanswers()){
    			if(anstest.getIdAsk().getIdAsk().equals(ustestans.getAsk().getIdAsk())){
    				//anstest.getOptionstypeanswer()[id] = ustestans.getOptionstypeanswer().getValue();
    				anstest.getOptionstypeanswer()[id] = ustestans.getValue();
    				id = id +1;
    			}
    			else{
   					if(id>0){
   	    				pagetest.getListanswer().add(anstest);
   	    				id=0;
   	    			}
   					anstest = fillanstest(ustestans, ustest);
  	    			pagetest.getListanswer().add(anstest);
    			}
    		}
    		listpagetest.add(pagetest);
    	}
    	return listpagetest;
    }

    private void filltestInit(String fill) throws Exception{
		User newUser = (User) facesContext.getExternalContext().getSessionMap().get("newUser");
		UserTest usertesttmp = new UserTest();
    	usertesttmp.setTest(newTest);
    	usertesttmp.setUser(newUser);
    	usertesttmp.setIpaddress(Resources.getRemoteAddress((HttpServletRequest) facesContext.getExternalContext().getRequest()));
    	usertesttmp.setDateTest(new Date());
    	testRegistration.registerUsertest(usertesttmp);
		facesContext.getExternalContext().getSessionMap().put("newUsertest", usertesttmp);
		
    }
    
    private AnswerTest fillanstest(UserTestAskAnswer ustestans, UserTest ustest){
    	AnswerTest anstest = new AnswerTest(ustestans.getAsk());
		String[] values = new String[ustestans.getAsk().getUsertestaskanswers().size()];
		anstest.setIdUserTest(ustest);
		if((ustestans.getAsk().getTypeanswer()!=null)&&(ustestans.getAsk().getTypeanswer().indexOf("Checkbox")>0)){
			values[0] = ustestans.getValue();
			anstest.setOptionstypeanswer(values);
		}
		anstest.setValue(ustestans.getValue());
		return anstest;

    }
    
}
