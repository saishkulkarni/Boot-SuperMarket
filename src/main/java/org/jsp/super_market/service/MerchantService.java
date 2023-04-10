package org.jsp.super_market.service;

import java.util.List;
import java.util.Random;

import org.jsp.super_market.dao.MerchantDao;
import org.jsp.super_market.dao.ProductDao;
import org.jsp.super_market.dto.Merchant;
import org.jsp.super_market.dto.Product;
import org.jsp.super_market.exception.AllException;
import org.jsp.super_market.helper.Login;
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

	@Autowired
	ProductDao dao2;

	public ResponseStructure<Merchant> signup(Merchant merchant) {
		ResponseStructure<Merchant> structure = new ResponseStructure<>();

		merchant.setOtp(new Random().nextInt(100000, 999999));

		// emailSender.sendEmail(merchant);

		structure.setMessage("Verification Mail Sent verify OTP to create account");
		structure.setStatuscode(HttpStatus.PROCESSING.value());
		structure.setData(dao.save(merchant));

		return structure;
	}

	public ResponseStructure<Merchant> verify(String id, int otp) throws AllException {
		ResponseStructure<Merchant> structure = new ResponseStructure<>();

		Merchant merchant = dao.find(id);
		if (merchant.getOtp() == otp) {
			merchant.setStatus(true);
			structure.setMessage("Account Created Successfully");
			structure.setStatuscode(HttpStatus.CREATED.value());
			structure.setData(dao.save(merchant));
		} else {
			throw new AllException("OTP Miss Match");
		}

		return structure;
	}

	public ResponseStructure<Merchant> login(Login login) throws AllException {
		ResponseStructure<Merchant> structure = new ResponseStructure<>();

		Merchant merchant = dao.find(login.getId());
		if (merchant == null) {
			throw new AllException("Invalid Id");
		} else {
			if (merchant.getPassword().equals(login.getPassword())) {
				if (merchant.isStatus()) {
					structure.setData(merchant);
					structure.setMessage("Login Succcess");
					structure.setStatuscode(HttpStatus.ACCEPTED.value());
				} else {
					throw new AllException("Verify OTP First");
				}
			} else {
				throw new AllException("Invalid Password");
			}
		}
		return structure;
	}

	public ResponseStructure<Merchant> addProduct(String mid, Product product) {
		ResponseStructure<Merchant> structure = new ResponseStructure<>();

		Merchant merchant = dao.find(mid);

		product.setMerchant(merchant);

		List<Product> list = merchant.getProducts();
		list.add(product);

		structure.setMessage("Product added successfully");
		structure.setData(dao.save(merchant));
		structure.setStatuscode(HttpStatus.CREATED.value());
		return structure;
	}

	public ResponseStructure<List<Product>> fetchProducts(String mid) throws AllException {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();

		Merchant merchant = dao.find(mid);

		if (merchant.getProducts().isEmpty()) {
			throw new AllException("No Products Found");
		} else {
			structure.setMessage("Data Found");
			structure.setStatuscode(HttpStatus.FOUND.value());
			structure.setData(merchant.getProducts());
			return structure;
		}
	}

	public ResponseStructure<Product> updateProduct(Product product, String mid) {
		ResponseStructure<Product> structure = new ResponseStructure<>();
		Merchant merchant = dao.find(mid);
		product.setMerchant(merchant);

		structure.setData(dao2.save(product));
		structure.setMessage("Updated Successfully");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());

		return structure;
	}

	public ResponseStructure<Product> deleteProduct(String mid, int pid) {
		Merchant merchant=dao.find(mid);
		Product product = dao2.find(pid);
		merchant.getProducts().remove(product);
		dao.save(merchant);
		dao2.deleteProduct(pid);

		ResponseStructure<Product> structure = new ResponseStructure<>();
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		structure.setMessage("Deleted Successfully");
		structure.setData(product);

		return structure;

	}

}
