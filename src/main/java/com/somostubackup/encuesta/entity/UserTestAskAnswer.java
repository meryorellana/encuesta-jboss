package com.somostubackup.encuesta.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the usertestaskanswer database table.
 * 
 */
@Entity
@NamedQuery(name="UserTestAskAnswer.findAll", query="SELECT u FROM UserTestAskAnswer u")
public class UserTestAskAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int idusertestaskanswer;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAnswer;
	
	//bi-directional many-to-one association to Answer
	@ManyToOne
	@JoinColumn(name="idAnswer")
	private Answer answer;
	//bi-directional many-to-one association to Ask
	@ManyToOne
	@JoinColumn(name="idAsk")
	private Ask ask;

	private String value;

	//bi-directional many-to-one association to UserTest
	@ManyToOne
	@JoinColumn(name="idUserTest")
	private UserTest userTest;

	public UserTestAskAnswer() {

	}

	public int getIdusertestaskanswer() {
		return this.idusertestaskanswer;
	}

	public void setIdusertestaskanswer(int idusertestaskanswer) {
		this.idusertestaskanswer = idusertestaskanswer;
	}

	public Date getDateAnswer() {
		return this.dateAnswer;
	}

	public void setDateAnswer(Date dateAnswer) {
		this.dateAnswer = dateAnswer;
	}

	public Answer getAnswer() {
		return this.answer;
	}

	public void setAnswer(Answer idAnswer) {
		this.answer = idAnswer;
	}

	public Ask getAsk() {
		return this.ask;
	}

	public void setAsk(Ask idAsk) {
		this.ask = idAsk;
	}


	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public UserTest getUsertest() {
		return this.userTest;
	}

	public void setUsertest(UserTest userTest) {
		this.userTest = userTest;
	}

}