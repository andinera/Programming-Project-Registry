package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;

import spring.service.ServiceFacade;



/**
 * Abstract class for maintaining fields for the services.
 * 
 * @author Shane Lockwood
 *
 */
public abstract class AbstractController {

	@Autowired
	protected ServiceFacade serviceFacade;
}
