package org.jsp.super_market.dto;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@Component
public class Merchant {

	@Id
	@GeneratedValue(generator = "merchantid")
	@GenericGenerator(name = "merchantid", strategy = "org.jsp.super_market.helper.MerchantIdGenerator")
	String id;
	String name;
	long mobile;
	String email;
	String password;
	boolean status;
	double wallet;
	int otp;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<Product> products;

}
