/**
 * 
 */
package com.glaucus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glaucus.service.NumberIncrementService;

/**
 * @author Somendra
 *
 */
@RestController
public class NumberIncrementController {

	@Autowired
	NumberIncrementService numberIncrementService;

	@RequestMapping(value="/")
	public String welcome(ModelMap map) {
		System.out.println("In the application****************");
		map.put("message", "Welcome to Number Increment.");
		return "mainPage";
	}

	@RequestMapping(value="/numIncrease")
	public String numIncrease(ModelMap map) {
		int result = numberIncrementService.numberIncrementId();
		map.put("result", result);
		return "incrementPage";
	}
}
