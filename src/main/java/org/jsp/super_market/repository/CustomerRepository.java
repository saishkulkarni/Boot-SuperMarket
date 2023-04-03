 package org.jsp.super_market.repository;

import org.jsp.super_market.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String>
{

}
