package com.somostubackup.encuesta.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.User;
import com.somostubackup.encuesta.entity.UserTest;

@ApplicationScoped
public class TestRepository {
    @Inject
    private EntityManager em;

    public Test findById(Integer id) {
        return em.find(Test.class, id);
    }

    public Test findByUser(User newuser) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Test> criteria = cb.createQuery(Test.class);
        Root<Test> member = criteria.from(Test.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(User_.email), email));
        criteria.select(member).where(cb.equal(member.get("user"), newuser));
        return em.createQuery(criteria).getSingleResult();
    }

        
    public List<Test> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Test> criteria = cb.createQuery(Test.class);
        Root<Test> member = criteria.from(Test.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(User_.name)));
        criteria.select(member).orderBy(cb.asc(member.get("name")));
        return em.createQuery(criteria).getResultList();
    }
    
    public List<UserTest> findUsertestByTest(Test  test) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserTest> criteria = cb.createQuery(UserTest.class);
        Root<UserTest> member = criteria.from(UserTest.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(User_.email), email));
        criteria.select(member).where(cb.equal(member.get("test"), test));
        return em.createQuery(criteria).getResultList();
    }

}
