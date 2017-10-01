package com.somostubackup.encuesta.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-06T23:16:11.441-0500")
@StaticMetamodel(Test.class)
public class Test_ {
	public static volatile SingularAttribute<Test, Integer> idTest;
	public static volatile SingularAttribute<Test, Date> dateFin;
	public static volatile SingularAttribute<Test, Date> dateInit;
	public static volatile SingularAttribute<Test, String> description;
	public static volatile SingularAttribute<Test, String> filecss;
	public static volatile SingularAttribute<Test, String> logo;
	public static volatile SingularAttribute<Test, String> name;
	public static volatile SingularAttribute<Test, String> pageFin;
	public static volatile SingularAttribute<Test, String> status;
	public static volatile ListAttribute<Test, Ask> asks;
	public static volatile ListAttribute<Test, Pagetest> pagetests;
	public static volatile SingularAttribute<Test, User> user;
	public static volatile ListAttribute<Test, Usertestaskanswer> usertestaskanswers;
}
