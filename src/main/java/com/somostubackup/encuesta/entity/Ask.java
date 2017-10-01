package com.somostubackup.encuesta.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;


/**
 * The persistent class for the ask database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Ask.deletebyId", query="DELETE FROM Ask a WHERE a.idAsk = :id"),
	@NamedQuery(name="Ask.findAll", query="SELECT a FROM Ask a")
})
public class Ask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer idAsk;

	@Size(min = 0, max = 250)
	private String content;

	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="ask",fetch=FetchType.EAGER)
	private List<Answer> answers;

	//bi-directional many-to-one association to Test
	@ManyToOne
	@JoinColumn(name="idTest")
	private Test test;

	
	private String idTypeAnswer;

	//bi-directional many-to-one association to UserTestAskAnswer
	@OneToMany(mappedBy="ask")
	private List<UserTestAskAnswer> userTestAskAnswers;

	public Ask() {
	}

	public Ask(int idask) {
		this.idAsk = idask;
	}

	public Integer getIdAsk() {
		return this.idAsk;
	}

	public void setIdAsk(Integer idAsk) {
		this.idAsk = idAsk;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Answer addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setAsk(this);

		return answer;
	}

	public Answer removeAnswer(Answer answer) {
		getAnswers().remove(answer);
		answer.setAsk(null);

		return answer;
	}
	
	public int getCountanswer(){
		return this.answers.size();
	}

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getTypeanswer() {
		return this.idTypeAnswer;
	}

	public void setTypeanswer(String typeanswer) {
		this.idTypeAnswer = typeanswer;
	}

	public List<UserTestAskAnswer> getUsertestaskanswers() {
		return this.userTestAskAnswers;
	}

	public void setUsertestaskanswers(List<UserTestAskAnswer> userTestAskAnswers) {
		this.userTestAskAnswers = userTestAskAnswers;
	}

	public UserTestAskAnswer addUsertestaskanswer(UserTestAskAnswer userTestAskAnswer) {
		getUsertestaskanswers().add(userTestAskAnswer);
		userTestAskAnswer.setAsk(this);

		return userTestAskAnswer;
	}

	public UserTestAskAnswer removeUsertestaskanswer(UserTestAskAnswer userTestAskAnswer) {
		getUsertestaskanswers().remove(userTestAskAnswer);
		userTestAskAnswer.setAsk(null);

		return userTestAskAnswer;
	}
	public static Comparator<Ask> idAskno = new Comparator<Ask>() {

		public int compare(Ask ask1, Ask ask2) {

		   int idask1 = ask1.getIdAsk();
		   int idask2 = ask2.getIdAsk();

		   /*For ascending order*/
		   return idask1-idask2;

	   }};
	 

}