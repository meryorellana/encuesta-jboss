package com.somostubackup.encuesta.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.User;

@SessionScoped
public class PageTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<AnswerTest> listanswer;
	
	private User userTest;
	private Test newTest;
	
	public PageTest(){
		
	}
	
	public PageTest(User utest, Test newTest){
		this.userTest= utest;
		this.newTest = newTest;
		listanswer = new ArrayList<AnswerTest>();
	}
	public User getUserTest() {
		return userTest;
	}

	public void setUserTest(User user,Test newTest) {
		this.userTest = user;
		if(newTest!=null){
			listanswer = new ArrayList<AnswerTest>();
			int id= 0;
			for(Ask asktmp : newTest.getAsks()){
				listanswer.add(id, new AnswerTest(asktmp));
				id= id + 1;
			}
		}
	}

	public List<AnswerTest> getListanswer() {
		return listanswer;
	}

	public void setListanswer(List<AnswerTest> listanswer) {
		this.listanswer = listanswer;
	}

	public AnswerTest getAnswerTest(int id){
		return this.listanswer.get(id);
	}
	
	public void setAnswerTest(int id, AnswerTest anstest){
		if(this.listanswer ==null){
			this.listanswer = new ArrayList<AnswerTest>();
		}
		this.listanswer.add(id, anstest);
	}

	public Test getNewTest() {
		return newTest;
	}

	public void setNewTest(Test newTest) {
		this.newTest = newTest;
	}

	public void setUserTest(User userTest) {
		this.userTest = userTest;
	}


	
}
