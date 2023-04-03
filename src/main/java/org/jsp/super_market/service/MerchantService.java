package org.jsp.super_market.service;

import org.jsp.super_market.dao.MerchantDao;
import org.jsp.super_market.dto.Merchant;
import org.jsp.super_market.helper.ResponseStructure;
import org.jsp.super_market.helper.VerificationEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

	@Autowired
	VerificationEmailSender emailSender;
	
	@Autowired
	MerchantDao dao;
	
	public ResponseStructure<Merchant> signup(Merchant merchant) {
		ResponseStructure<Merchant> structure=new ResponseStructure<>();
		
	//	emailSender.sendEmail(merchant);
		
		structure.setMessage("Verification Mail Sent verify OTP to create account");
		structure.setStatuscode(HttpStatus.PROCESSING.value());
		structure.setData(dao.save(merchant));
		
		return structure;
	}

}
