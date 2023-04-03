package org.jsp.super_market.repository;

import org.jsp.super_market.dto.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String>
{

}
