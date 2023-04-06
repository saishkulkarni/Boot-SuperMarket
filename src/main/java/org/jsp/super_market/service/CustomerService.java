package org.jsp.super_market.service;

import java.util.Random;

import org.jsp.super_market.dao.CustomerDao;
import org.jsp.super_market.dto.Customer;
import org.jsp.super_market.exception.AllException;
import org.jsp.super_market.helper.Login;
import org.jsp.super_market.helper.ResponseStructure;
import org.jsp.super_market.helper.VerificationEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	VerificationEmailSender emailSender;

	@Autowired
	CustomerDao dao;

	public ResponseStructure<Customer> signup(Customer customer) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();

		customer.setOtp(new Random().nextInt(100000, 999999));

		//emailSender.sendEmail(customer);

		structure.setMessage("Verification Mail Sent verify OTP to create account");
		structure.setStatuscode(HttpStatus.PROCESSING.value());
		structure.setData(dao.save(customer));

		return structure;
	}

	public ResponseStructure<Customer> verify(String id, int otp) throws AllException {
		ResponseStructure<Customer> structure = new ResponseStructure<>();

		Customer customer = dao.find(id);
		if (customer.getOtp() == otp) {
			customer.setStatus(true);
			structure.setMessage("Account Created Successfully");
			structure.setStatuscode(HttpStatus.CREATED.value());
			structure.setData(dao.save(customer));
		} else {
			throw new AllException("OTP Miss Match");
		}

		return structure;
	}

	public ResponseStructure<Customer> login(Login login) throws AllException {
		ResponseStructure<Customer> structure = new ResponseStructure<>();

		Customer customer = dao.find(login.getId());
		if (customer == null) {
			throw new AllException("Invalid Id");
		} else {
			if (customer.getPassword().equals(login.getPassword())) {
				if(customer.isStatus())
				{
				structure.setData(customer);
				structure.setMessage("Login Succcess");
				structure.setStatuscode(HttpStatus.ACCEPTED.value());
				}
				else {
					throw new AllException("Verify OTP First");
				}
			} else {
				throw new AllException("Invalid Password");
			}
		}
		return structure;
	}

}
