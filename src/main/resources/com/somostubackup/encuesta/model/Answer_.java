package com.somostubackup.encuesta.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-06T23:16:10.983-0500")
@StaticMetamodel(Answer.class)
public class Answer_ {
	public static volatile SingularAttribute<Answer, Integer> idAnswer;
	public static volatile SingularAttribute<Answer, String> valuemax;
	public static volatile SingularAttribute<Answer, String> valuemin;
	public static volatile SingularAttribute<Answer, Ask> ask;
	public static volatile ListAttribute<Answer, Optionstypeanswer> optionstypeanswers;
	public static volatile ListAttribute<Answer, Usertestaskanswer> usertestaskanswers;
}
