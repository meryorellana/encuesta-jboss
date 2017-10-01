package com.somostubackup.encuesta.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity

@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findbyEmail", query="SELECT u FROM User u where u.email=:email ")})

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer idUser;

	private String address;

	@Pattern(regexp = "[a-zA-Z0-9_.-]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+", 
         message = "{email.invalid}")
	private String email;

	private int isAdmin;

	private String login;

	@Size(min = 1, max = 45)
    @Pattern(regexp = "[a-zA-Z0-9,. ]*", message = "{name.invalid}")
    private String name;

	@Size(min = 1, max = 45)
    @Pattern(regexp = "[A-Za-z ]*", message = "{name.invalid}")
	private String name2;

	private String password;
	
	@Pattern(regexp = "(\\+\\d{3}-\\d{7,8})|(\\+\\d{2}-\\d{10})", 
         message = "{phonenumber.invalid}")
	private String phonenumber;

	//bi-directional many-to-one association to Test
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	
	private List<Test> tests;

	public User() {
	}

	public int getIdUser() {
		return this.idUser.intValue();
	}

	public void setIdUser(int idUser) {
		this.idUser=idUser;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsAdmin() {
		return (this.isAdmin==0);
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Test> getTests() {
		return this.tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public Test addTest(Test test) {
		getTests().add(test);
		test.setUser(this);

		return test;
	}

	public Test removeTest(Test test) {
		getTests().remove(test);
		test.setUser(null);

		return test;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

}