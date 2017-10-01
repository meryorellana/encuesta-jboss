package com.somostubackup.encuesta.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.somostubackup.encuesta.data.TestRepository;
import com.somostubackup.encuesta.entity.Test;

@RequestScoped
public class TestDescription {
	
	private String name;
	
	private String description;

	private String logo;
	
	private Integer countask;
	
	private Integer idTest;
	
	@Inject
	private TestRepository testrep;

	public  TestDescription(){
		
	}
	
	public  TestDescription(Test test){
		this.countask = test.getAsks().size();
		this.description = test.getDescription();
		this.logo = test.getLogo();
		this.name= test.getName();
		this.idTest = test.getIdTest();
	}
	
	public Test getTestbyTestDescription(){
		Test test = new Test();
		test = testrep.findById(this.idTest);
		return test;
	}
	
	public Integer getIdTest() {
		return idTest;
	}

	public void setIdTest(Integer idTest) {
		this.idTest = idTest;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getCountask() {
		return countask;
	}

	public void setCountask(Integer countask) {
		this.countask = countask;
	}

}
