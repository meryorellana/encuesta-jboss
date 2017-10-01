package com.somostubackup.encuesta.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-06T23:16:11.443-0500")
@StaticMetamodel(Typeanswer.class)
public class Typeanswer_ {
	public static volatile SingularAttribute<Typeanswer, Integer> idTypeAnswer;
	public static volatile SingularAttribute<Typeanswer, String> name;
	public static volatile ListAttribute<Typeanswer, Ask> asks;
}
