package org.jsp.super_market.controller;

import java.util.List;

import org.jsp.super_market.dto.Product;
import org.jsp.super_market.exception.AllException;
import org.jsp.super_market.helper.Login;
import org.jsp.super_market.helper.ResponseStructure;
import org.jsp.super_market.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminService service;

	@PostMapping("login")
	public ResponseStructure<Login> login(@RequestBody Login login) throws AllException
	{
		return service.login(login);
	}
	
	@GetMapping("product/fetch")
	public ResponseStructure<List<Product>> fetchProducts()
	{
		return service.fetchProducts();
	}
	
	@PutMapping("product/status/{pid}")
	public ResponseStructure<Product> changeStatus(@PathVariable int pid)
	{
		return service.changeStatus(pid);
	}
}
