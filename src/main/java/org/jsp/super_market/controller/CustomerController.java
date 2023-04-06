package org.jsp.super_market.controller;

import org.jsp.super_market.dto.Customer;
import org.jsp.super_market.exception.AllException;
import org.jsp.super_market.helper.Login;
import org.jsp.super_market.helper.ResponseStructure;
import org.jsp.super_market.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	CustomerService service;

	@PostMapping("signup")
	public ResponseStructure<Customer> signup(@RequestBody Customer customer) {
		return service.signup(customer);
	}

	@PutMapping("verify/{id}/{otp}")
	public ResponseStructure<Customer> verify(@PathVariable String id, @PathVariable int otp) throws AllException {
		return service.verify(id, otp);
	}

	@PostMapping("login")
	public ResponseStructure<Customer> login(@RequestBody Login login) throws AllException {
		return service.login(login);
	}
}
