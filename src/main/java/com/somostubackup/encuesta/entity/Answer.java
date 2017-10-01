package com.somostubackup.encuesta.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.List;


/**
 * The persistent class for the answer database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Answer.findAll", query="SELECT a FROM Answer a"),
@NamedQuery(name="Answer.deletebyIdAsk", query="DELETE FROM Answer a WHERE a.ask=:ask")
})
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int idAnswer;

	@Size(min = 0, max = 1024)
	private String valuemax;

	private String valuemin;

	//bi-directional many-to-one association to Ask
	@ManyToOne
	private Ask ask;

	@Size(min = 0, max = 1024)
	private String valuex;

	//bi-directional many-to-one association to UserTestAskAnswer
	@OneToMany(mappedBy="answer")
	private List<UserTestAskAnswer> userTestAskAnswers;

	public Answer() {
	}

	public int getIdAnswer() {
		return this.idAnswer;
	}

	public void setIdAnswer(int idAnswer) {
		this.idAnswer = idAnswer;
	}

	public String getValuemax() {
		return this.valuemax;
	}

	public void setValuemax(String valuemax) {
		this.valuemax = valuemax;
	}

	public String getValuemin() {
		return this.valuemin;
	}

	public void setValuemin(String valuemin) {
		this.valuemin = valuemin;
	}

	public Ask getAsk() {
		return this.ask;
	}

	public void setAsk(Ask ask) {
		this.ask = ask;
	}
	


	public String getValuex() {
		return valuex;
	}

	public void setValuex(String valuex) {
		this.valuex = valuex;
	}

	public List<UserTestAskAnswer> getUserTestAskAnswers() {
		return userTestAskAnswers;
	}

	public void setUserTestAskAnswers(List<UserTestAskAnswer> userTestAskAnswers) {
		this.userTestAskAnswers = userTestAskAnswers;
	}

	public List<UserTestAskAnswer> getUsertestaskanswers() {
		return this.userTestAskAnswers;
	}

	public void setUsertestaskanswers(List<UserTestAskAnswer> userTestAskAnswers) {
		this.userTestAskAnswers = userTestAskAnswers;
	}

	public UserTestAskAnswer addUsertestaskanswer(UserTestAskAnswer userTestAskAnswer) {
		getUsertestaskanswers().add(userTestAskAnswer);
		userTestAskAnswer.setAnswer(this);

		return userTestAskAnswer;
	}

	public UserTestAskAnswer removeUsertestaskanswer(UserTestAskAnswer userTestAskAnswer) {
		getUsertestaskanswers().remove(userTestAskAnswer);
		userTestAskAnswer.setAnswer(null);

		return userTestAskAnswer;
	}
	
	public String[] getSplitvaluemax(){
		return this.valuemax.split(",");
	}

	public String[] getSplitvaluex(){
		return this.valuex.split(",");
	}

}