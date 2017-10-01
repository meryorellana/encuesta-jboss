package com.somostubackup.encuesta.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-06T23:16:11.445-0500")
@StaticMetamodel(Usertestaskanswer.class)
public class Usertestaskanswer_ {
	public static volatile SingularAttribute<Usertestaskanswer, Integer> idusertestaskanswer;
	public static volatile SingularAttribute<Usertestaskanswer, Date> dateAnswer;
	public static volatile SingularAttribute<Usertestaskanswer, Answer> answer;
	public static volatile SingularAttribute<Usertestaskanswer, Ask> ask;
	public static volatile SingularAttribute<Usertestaskanswer, Optionstypeanswer> optionstypeanswer;
	public static volatile SingularAttribute<Usertestaskanswer, Test> test;
	public static volatile SingularAttribute<Usertestaskanswer, User> user;
	public static volatile SingularAttribute<Usertestaskanswer, String> value;
}
