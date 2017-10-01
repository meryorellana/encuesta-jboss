package com.somostubackup.encuesta.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-06T23:16:11.382-0500")
@StaticMetamodel(Optionstypeanswer.class)
public class Optionstypeanswer_ {
	public static volatile SingularAttribute<Optionstypeanswer, Integer> idOptionsTypeAnswer;
	public static volatile SingularAttribute<Optionstypeanswer, String> value;
	public static volatile SingularAttribute<Optionstypeanswer, Answer> answer;
	public static volatile ListAttribute<Optionstypeanswer, Usertestaskanswer> usertestaskanswers;
}
