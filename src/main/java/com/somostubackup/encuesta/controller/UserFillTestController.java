package com.somostubackup.encuesta.controller;

import java.util.Date;
import java.util.Locale;
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

import org.jboss.tools.examples.util.Resources;

import com.somostubackup.encuesta.data.TestFillRepository;
import com.somostubackup.encuesta.data.TestRepository;
import com.somostubackup.encuesta.data.UserRepository;
import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.User;
import com.somostubackup.encuesta.entity.UserTest;
import com.somostubackup.encuesta.model.AnswerTest;
import com.somostubackup.encuesta.model.PageTest;
import com.somostubackup.encuesta.service.TestCreate;
import com.somostubackup.encuesta.service.UserRegistration;
import com.somostubackup.encuesta.service.UsertestaskanswerRegister;

@Model
public class UserFillTestController {
	@Inject 
	private FacesContext facesContext;
	
	@Produces
	@Named
	private PageTest pagetest = new PageTest();
	
	@Inject
	 private UsertestaskanswerRegister testreg;
	 
	@Inject
	 private Logger log;
	
	@Inject
	private TestFillRepository testfillrep;
	
	@Inject
	private TestCreate testcreate;
	
	@Inject
	 private TestRepository testrep;
	
	@Inject 
	private UserRegistration userreg;
	
	@Inject
	private UserRepository userrep;
	 
	 @ManagedProperty(value="#{param.id}")
	 private String id;
	 
	 private Test newTest;
	 
	 
	@PostConstruct
	private void initUserFillTest(){
		id = facesContext.getExternalContext().getRequestParameterMap()
				.get("id");
		if (pagetest.getUserTest() == null) {
			if (id != null) {
				Integer idtest = Integer.valueOf(id);
			//	log.info(String.valueOf(idtest));
				newTest = testrep.findById(idtest);
				
				newTest.setAsks(testfillrep.findAllAskbyTest(newTest));
				facesContext.getExternalContext().getSessionMap().put("newTest", newTest);

			} else {
				newTest = (Test) facesContext.getExternalContext()
						.getSessionMap().get("newTest");
				newTest.setAsks(testfillrep.findAllAskbyTest(newTest));
				pagetest.setNewTest(newTest);
				pagetest.setUserTest(new User());
			}
			// Lista de preguntas
			int c = 0;
			if (pagetest.getListanswer() == null) {
				for (Ask asktmp : newTest.getAsks()) {
					pagetest.setAnswerTest(c, new AnswerTest(asktmp));
					c = c + 1;
				}
			}
		}
	}
	 public String filltest() throws Exception {
		 log.info("llenado encuesta");
//		 UserTest userTest = (UserTest) facesContext.getExternalContext().getSessionMap().get("newUsertest");
		 UserTest userTest = new UserTest();
		 User newUser=null; 
		 if(pagetest.getNewTest().getIsAnonymous()==0){
			 try{
			 newUser = userrep.findByEmail(pagetest.getUserTest().getEmail());
			 }catch(Exception e){}
			 if(newUser==null){
				 userreg.register(pagetest.getUserTest());
				 newUser = pagetest.getUserTest();
			 }
		 }else{
			 newUser = pagetest.getNewTest().getUser();
		 }
		 userTest.setUser(newUser);
		 userTest.setTest(pagetest.getNewTest());
		 userTest.setIpaddress(Resources.getRemoteAddress((HttpServletRequest) facesContext.getExternalContext().getRequest()));
		 userTest.setDateTest(new Date());
		 testcreate.registerUsertest(userTest);

		 for(AnswerTest asktest : pagetest.getListanswer()){
			 try{
				 asktest.setIdUserTest(userTest);
				 testreg.registeransOne(asktest);
			 }catch(Exception e){
				 String errorMessage = e.getLocalizedMessage();
		            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
		            facesContext.addMessage(null, m);
				 return "";
			 }
			 
		 }
		 return "fintest";
	 }
	 public Locale getLocale(){
		 facesContext.getViewRoot().setLocale(Locale.forLanguageTag(newTest.getLangIso()));
		// log.info(newTest.getLangIso());
		 return Locale.forLanguageTag(newTest.getLangIso());
	 }

}
