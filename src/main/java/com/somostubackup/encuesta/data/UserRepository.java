package com.somostubackup.encuesta.data;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.User;
import com.somostubackup.encuesta.entity.UserTest;
@ApplicationScoped
public class UserRepository {

    @Inject
    private EntityManager em;

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> member = criteria.from(User.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(User_.email), email));
        criteria.select(member).where(cb.equal(member.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<User> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> member = criteria.from(User.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(User_.name)));
        criteria.select(member).orderBy(cb.asc(member.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    public List<Test> findAllTestbyUser(User userid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Test> criteria = cb.createQuery(Test.class);
        Root<Test> test = criteria.from(Test.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(User_.name)));
        criteria.select(test).where(cb.equal(test.get("user"),userid));
        return em.createQuery(criteria).getResultList();
    }

    public List<UserTest> findAllUserTestbyUser(User userid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserTest> criteria = cb.createQuery(UserTest.class);
        Root<UserTest> test = criteria.from(UserTest.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(User_.name)));
        criteria.select(test).where(cb.equal(test.get("user"),userid)).orderBy(cb.asc(test.get("test")));
        return em.createQuery(criteria).getResultList();
    }
}
