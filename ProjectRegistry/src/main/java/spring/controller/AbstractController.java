package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;

import spring.service.ServiceFacade;


public abstract class AbstractController {

	@Autowired
	protected ServiceFacade serviceFacade;
}
