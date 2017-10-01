package com.somostubackup.encuesta.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the usertest database table.
 * 
 */
@Entity
@NamedQuery(name="UserTest.findAll", query="SELECT u FROM UserTest u")
public class UserTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int idUserTest;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTest;
	
	private String ipaddress;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idTest")
	private Test test;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	//bi-directional many-to-one association to UserTestAskAnswer
	@OneToMany(mappedBy="userTest")
	private List<UserTestAskAnswer> userTestAskAnswers;

	public UserTest() {
	}

	public int getIdUserTest() {
		return this.idUserTest;
	}

	public void setIdUserTest(int idUserTest) {
		this.idUserTest = idUserTest;
	}

	public Date getDateTest() {
		return this.dateTest;
	}

	public void setDateTest(Date dateTest) {
		this.dateTest = dateTest;
	}

	public Test getTest() {
		return this.test;
	}

	public void setTest(Test idTest) {
		this.test = idTest;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User idUser) {
		this.user = idUser;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public List<UserTestAskAnswer> getUsertestaskanswers() {
		return this.userTestAskAnswers;
	}

	public void setUsertestaskanswers(List<UserTestAskAnswer> userTestAskAnswers) {
		this.userTestAskAnswers = userTestAskAnswers;
	}

	public UserTestAskAnswer addUsertestaskanswer(UserTestAskAnswer userTestAskAnswer) {
		getUsertestaskanswers().add(userTestAskAnswer);
		userTestAskAnswer.setUsertest(this);

		return userTestAskAnswer;
	}

	public UserTestAskAnswer removeUsertestaskanswer(UserTestAskAnswer userTestAskAnswer) {
		getUsertestaskanswers().remove(userTestAskAnswer);
		userTestAskAnswer.setUsertest(null);

		return userTestAskAnswer;
	}

}