package com.somostubackup.encuesta.controller;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.somostubackup.encuesta.data.UserRepository;
import com.somostubackup.encuesta.entity.User;
import com.somostubackup.encuesta.model.ChangePassword;
import com.somostubackup.encuesta.service.UserRegistration;

@Model
public class PasswordController {

	@Named
	@Produces
	private ChangePassword changePassword;
	
	@Inject
	private UserRepository userRep;
	
	@Inject
	private UserRegistration userDB;
	
	@Inject
	private Logger log;
	
	@Inject
	private FacesContext facesContext;
	
	@PostConstruct
	public void initchangepass(){
		changePassword = new ChangePassword();
		if(facesContext.getExternalContext().getSessionMap().containsKey("newUser")){
			User newUser = (User) facesContext.getExternalContext().getSessionMap().get("newUser");
			changePassword.setEmail(newUser.getEmail());
		}
	}
	
	public String changePassword(){
		try{
			ResourceBundle text = ResourceBundle.getBundle("com.somostubackup.encuesta.message.ApplicationResource", facesContext.getViewRoot().getLocale());
			User loginuser = userRep.findByEmail(changePassword.getEmail());
			if(changePassword.getPhonenumber().equals(loginuser.getPhonenumber())){
				userDB.updateUser(loginuser);
           	    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, text.getString("passwordchanged"), text.getString("passwordchanged"));
           	    facesContext.addMessage(null, m);
			}
			else{
           	    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, text.getString("identnotmatch"), "");
           	    facesContext.addMessage(null, m);
			}
		}
		catch(Exception e){
			log.info(e.getMessage());
			ResourceBundle text = ResourceBundle.getBundle("com.somostubackup.encuesta.message.ApplicationResource", facesContext.getViewRoot().getLocale());
			 FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, text.getString("userunregistered"), text.getString("userunregistered"));
			 facesContext.addMessage(null, m);
		}
		return "";
	}
	
    public void validateEmail(FacesContext context, UIComponent comp,
			Object value) {
		String regexp = "[a-zA-Z0-9_.-]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
		String message = "email.invalid";
		ResourceBundle resourceBundleVal = ResourceBundle.getBundle("ValidationMessages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		if (!value.toString().matches(regexp)) {
			
			FacesMessage m = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					resourceBundleVal.getString(message),
					resourceBundleVal.getString(message));
			facesContext.addMessage(null, m);
			facesContext.addMessage(null, m);
			 m.setSeverity(FacesMessage.SEVERITY_ERROR);
	         throw new ValidatorException(m);

		} 
    }

	public void validatePassword2(FacesContext context, UIComponent comp,
			Object value) {
//		 UIInput uiInputPassword = (UIInput) comp.getAttributes()
//					.get("password");
//		String password = uiInputConfirmPassword.getSubmittedValue()
//					.toString();
		UIInput uiInputPassword = (UIInput) context.getViewRoot().findComponent("user:password");
		String password;
		if(uiInputPassword.getSubmittedValue() == null) {
		    password = uiInputPassword.getLocalValue().toString();
		}else{
			password = uiInputPassword.getSubmittedValue().toString();
		}
//		log.info(password);
		if (!value.toString().equals(password)) {
			ResourceBundle resourceBundleVal = ResourceBundle.getBundle("ValidationMessages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					resourceBundleVal.getString("passnotmatch"),
					resourceBundleVal.getString("passnotmatch"));
			facesContext.addMessage(null, m);
//			log.info("validatePassword2");
//			log.info(password);
//			log.info(value.toString());
			throw new ValidatorException(m);
		}

	}
	

}
