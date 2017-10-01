package com.somostubackup.encuesta.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-06T23:16:11.444-0500")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> idUser;
	public static volatile SingularAttribute<User, String> address;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Integer> isAdmin;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> name2;
	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Test> tests;
}
