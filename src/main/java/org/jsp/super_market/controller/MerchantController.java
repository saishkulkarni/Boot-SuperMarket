package org.jsp.super_market.controller;

import org.jsp.super_market.dto.Merchant;
import org.jsp.super_market.helper.ResponseStructure;
import org.jsp.super_market.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("merchant")
public class MerchantController {

	@Autowired
	MerchantService service;

	@PostMapping("signup")
	public ResponseStructure<Merchant> signup(@RequestBody Merchant merchant) {
		return service.signup(merchant);
	}
}
