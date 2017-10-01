package com.somostubackup.encuesta.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.somostubackup.encuesta.entity.Test;

@RequestScoped
public class TestListProducer {
	@Inject
    private TestRepository testRepository;

    private List<Test> test;

    // @Named provides access the return value via the EL variable name "test" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Test> getTest() {
        return test;
    }

    public void onTestListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Test member) {
        retrieveAllTestOrderedByName();
    }

    @PostConstruct
    public void retrieveAllTestOrderedByName() {
    	test = testRepository.findAllOrderedByName();
    }
}
