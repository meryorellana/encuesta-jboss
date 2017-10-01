package com.somostubackup.encuesta.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-06T23:16:11.380-0500")
@StaticMetamodel(Ask.class)
public class Ask_ {
	public static volatile SingularAttribute<Ask, Integer> idAsk;
	public static volatile SingularAttribute<Ask, String> content;
	public static volatile ListAttribute<Ask, Answer> answers;
	public static volatile SingularAttribute<Ask, Test> test;
	public static volatile SingularAttribute<Ask, Typeanswer> typeanswer;
	public static volatile ListAttribute<Ask, Usertestaskanswer> usertestaskanswers;
}
