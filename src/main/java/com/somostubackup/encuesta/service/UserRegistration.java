package com.somostubackup.encuesta.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.somostubackup.encuesta.entity.User;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserRegistration {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

//    @Inject
//    private Event<User> userEventSrc;

    public void register(User newUser) throws Exception {
        log.info("Registering " + newUser.getName());
         em.persist(newUser);
//         userEventSrc.fire(newUser);
    }
    public void updateUser(User newUser) throws Exception {
        log.info("Updating " + newUser.getName());
         em.merge(newUser);
//         userEventSrc.fire(newUser);
    }

}
