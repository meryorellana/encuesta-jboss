package com.somostubackup.encuesta.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.somostubackup.encuesta.entity.UserTestAskAnswer;
import com.somostubackup.encuesta.model.AnswerTest;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UsertestaskanswerRegister {

    @Inject
    private EntityManager em;
    

//    public void register(PageTest ustaskans) throws Exception {
//        for(AnswerTest asktmp :ustaskans.getPagetest()){
//        	// registro la respuesta
//        	registeransOne(asktmp);
//        	
//        }
//    }

    public void registerOne(UserTestAskAnswer ustaskans) throws Exception {
       	   em.persist(ustaskans);
       } 
	public void registeransOne(AnswerTest anstest) throws Exception {
		UserTestAskAnswer usertestaskans;
		if ((anstest.getIdAsk().getTypeanswer().indexOf("Checkbox") > 0)||(anstest.getIdAsk().getTypeanswer().indexOf("Radio") > 0)) {
			for (String inttmp : anstest.getOptionstypeanswer()) {
				usertestaskans = initialval(anstest);
				usertestaskans.setValue(inttmp);
				//usertestaskans.setOptionstypeanswer(testfillrep.findByIdOptionstypeanswer(Integer.valueOf(inttmp)));
				registerOne(usertestaskans);
			}
			if(anstest.getIdAsk().getTypeanswer().indexOf("Other") > 0){
				usertestaskans = initialval(anstest);
				usertestaskans.setValue(anstest.getValue());
				registerOne(usertestaskans);
			}
		} 
		else {
				usertestaskans = initialval(anstest);
				usertestaskans.setValue(anstest.getValue());
				registerOne(usertestaskans);
		}


	}
	private UserTestAskAnswer initialval(AnswerTest anstest){
		UserTestAskAnswer usertestaskans = new UserTestAskAnswer ();
        usertestaskans.setAsk(anstest.getIdAsk());
        usertestaskans.setUsertest(anstest.getIdUserTest());
		usertestaskans.setDateAnswer(new Date());
		return usertestaskans;
	}
}
