package org.jsp.super_market.service;

import java.util.List;
import java.util.Random;

import org.jsp.super_market.dao.CustomerDao;
import org.jsp.super_market.dao.ProductDao;
import org.jsp.super_market.dto.Cart;
import org.jsp.super_market.dto.Customer;
import org.jsp.super_market.dto.Item;
import org.jsp.super_market.dto.Product;
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

	@Autowired
	ProductDao productDao;

	@Autowired
	Cart cart;

	@Autowired
	Item item;

	public ResponseStructure<Customer> signup(Customer customer) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();

		customer.setOtp(new Random().nextInt(100000, 999999));

		// emailSender.sendEmail(customer);

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
				if (customer.isStatus()) {
					structure.setData(customer);
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

	public ResponseStructure<List<Product>> fetch() throws AllException {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();

		List<Product> products = productDao.fetchCustomerProducts();
		if (products.isEmpty()) {
			throw new AllException("Products Not found");
		} else {
			structure.setData(products);
			structure.setMessage("Found Succcess");
			structure.setStatuscode(HttpStatus.FOUND.value());
		}
		return structure;
	}

	public ResponseStructure<Cart> addToCart(String cid, int pid) {
		ResponseStructure<Cart> structure = new ResponseStructure<>();

		Customer customer = dao.find(cid);
		Product product = productDao.find(pid);

		Cart cart = customer.getCart();
		if (cart == null) {
			cart = this.cart;
		}

		List<Item> items = cart.getItems();

		if (items.isEmpty()) {
			item.setName(product.getName());
			item.setPrice(product.getPrice());
			item.setQuantity(1);
			items.add(item);
		} else {
			for (Item item : items) {
				if (item.getName().equals(product.getName())) {
					item.setQuantity(item.getQuantity() + 1);
					item.setPrice(item.getPrice() + product.getPrice());
					break;
				} else {
					item.setName(product.getName());
					item.setPrice(product.getPrice());
					item.setQuantity(1);
					break;
				}
			}
		}
		cart.setItems(items);
		customer.setCart(cart);

		structure.setMessage("Added to Cart");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		structure.setData(dao.save(customer).getCart());

		return structure;
	}

}
