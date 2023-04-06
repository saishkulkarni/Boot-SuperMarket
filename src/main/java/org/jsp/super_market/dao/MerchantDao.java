package org.jsp.super_market.dao;

import java.util.Optional;

import org.jsp.super_market.dto.Merchant;
import org.jsp.super_market.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MerchantDao {
	
	@Autowired
	MerchantRepository repository;

	public Merchant save(Merchant merchant) {
		return repository.save(merchant);
	}

	public Merchant find(String id) {
		Optional<Merchant> optional =repository.findById(id);
		if(optional.isEmpty())
		{
			return null;
		}
		else
			return optional.get();
	}

}
