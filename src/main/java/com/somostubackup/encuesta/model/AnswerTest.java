package com.somostubackup.encuesta.model;

import javax.faces.bean.RequestScoped;

import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.UserTest;

@RequestScoped
public class AnswerTest {
	
	private Ask idAsk;
	
	private String[] optionstypeanswer;
	
	
	private String value="";
	
	private Integer idOptionans;
	
	private UserTest idUserTest;
	
	public AnswerTest(){
		
	}
	public AnswerTest(Ask ask){
		this.idAsk = ask;
	}
	
	public Ask getIdAsk() {
		return idAsk;
	}
	public void setIdAsk(Ask idAsk) {
		this.idAsk = idAsk;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setIdOptionans(Integer idOptionans) {
		this.idOptionans = idOptionans;
	}
	
	public String[] getOptionstypeanswer() {
		return optionstypeanswer;
	}
	public void setOptionstypeanswer(String[] optionstypeanswer) {
		this.optionstypeanswer = optionstypeanswer;
	}
	public Integer getIdOptionans() {
		return idOptionans;
	}
	public UserTest getIdUserTest() {
		return idUserTest;
	}
	public void setIdUserTest(UserTest idUserTest) {
		this.idUserTest = idUserTest;
	}

	
}
