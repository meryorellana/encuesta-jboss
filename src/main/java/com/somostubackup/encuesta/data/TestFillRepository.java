package com.somostubackup.encuesta.data;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.somostubackup.encuesta.entity.Answer;
import com.somostubackup.encuesta.entity.Ask;
import com.somostubackup.encuesta.entity.Test;
import com.somostubackup.encuesta.entity.UserTest;
import com.somostubackup.encuesta.entity.UserTestAskAnswer;

@ApplicationScoped
public class TestFillRepository {

    @Inject
    private EntityManager em;

    public Ask findByIdAsk(Integer id) {
        return em.find(Ask.class, id);
    }
    public Answer findByIdAnswer(Integer id) {
        return em.find(Answer.class, id);
    }

    public UserTest findByIdUserTest(Integer id){
    	return em.find(UserTest.class, id);
    }
    
    public List<Ask> findAllAskbyTest(Test test){
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ask> criteria = cb.createQuery(Ask.class);
        Root<Ask> ask = criteria.from(Ask.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(ask).where(cb.equal(ask.get("test"),test)).orderBy(cb.asc(ask.get("idAsk")));
        return em.createQuery(criteria).getResultList();
    }
    
    public List<Answer> findAllAnswerbyAsk(Ask ask){
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Answer> criteria = cb.createQuery(Answer.class);
        Root<Answer> answer = criteria.from(Answer.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(answer).where(cb.equal(answer.get("ask"),ask)).orderBy(cb.asc(answer.get("idAnswer")));
        return em.createQuery(criteria).getResultList();
    }
    
    public List<UserTestAskAnswer> findUserTestAskAnswerbUsertest(UserTest usertest){
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserTestAskAnswer> criteria = cb.createQuery(UserTestAskAnswer.class);
        Root<UserTestAskAnswer> answer = criteria.from(UserTestAskAnswer.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(answer).where(cb.equal(answer.get("userTest"),usertest));
        return em.createQuery(criteria).getResultList();
    }
//    public List<AskDesc> findAllAskDescbyTest(Test test){
//        return findAllAskDescbyAllAsk(findAllAskbyTest(test));
//    }
//
//    public List<AskDesc> findAllAskDescbyAllAsk(List<Ask> listask){
//    	List<AskDesc> listaskdesc = new ArrayList<AskDesc>();
//    	for(Ask ask:listask){
//    		listaskdesc.add(new AskDesc(ask));
//    	}
//    	
//        return listaskdesc;
//    }


}
