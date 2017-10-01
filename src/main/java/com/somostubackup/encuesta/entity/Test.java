package com.somostubackup.encuesta.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the test database table.
 * 
 */
@Entity
@NamedQuery(name="Test.findAll", query="SELECT t FROM Test t")
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer idTest;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFin;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInit;

	private String description;

	private String filecss;

	private String logo;

	private String name;

	private String pageFin;

	private String status;
	
	private Integer isAnonymous;
	
	private String langIso;

	//bi-directional many-to-one association to Ask
	@OneToMany(mappedBy="test")
	@OrderBy("idAsk")
	private List<Ask> asks;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	//bi-directional many-to-one association to UserTest
	@OneToMany(mappedBy="test")
	private List<UserTest> userTest;

	public Test() {
	}

	public Integer getIdTest() {
		return this.idTest;
	}

	public void setIdTest(int idTest) {
		this.idTest = idTest;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Date getDateInit() {
		return this.dateInit;
	}

	public void setDateInit(Date dateInit) {
		this.dateInit = dateInit;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilecss() {
		return this.filecss;
	}

	public void setFilecss(String filecss) {
		this.filecss = filecss;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPageFin() {
		return this.pageFin;
	}

	public void setPageFin(String pageFin) {
		this.pageFin = pageFin;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Integer isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	
	public int getCountAnswer(){
		return this.userTest.size();
	}

	public List<Ask> getAsks() {
		return this.asks;
	}

	public void setAsks(List<Ask> asks) {
		this.asks = asks;
	}

	public Ask addAsk(Ask ask) {
		if(this.asks==null){
			this.asks = new ArrayList<Ask>();
		}
		getAsks().add(ask);
		ask.setTest(this);
		Collections.sort(this.asks,Ask.idAskno);
		return ask;
	}
	
	public Ask removeAsk(Ask ask) {
		getAsks().remove(ask);
		ask.setTest(null);

		return ask;
	}

	public void removeAskbyId(int id){
		List<Ask> listask =  getAsks();
		int c=0;
		for(Ask asktmp:listask){
			if(asktmp.getIdAsk()==id){
				getAsks().remove(c);
				return;
			}
			c=c+1;
		}
		
	}
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<UserTest> getUsertest() {
		return userTest;
	}

	public void setUsertest(List<UserTest> userTest) {
		this.userTest = userTest;
	}


	public boolean getBlAnonymous(){
		return this.isAnonymous == 0;
	}

	public String getLangIso() {
		return langIso;
	}

	public void setLangIso(String langIso) {
		this.langIso = langIso;
	}
	
	
}