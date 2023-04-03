package org.jsp.super_market.helper;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomerIdGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		String randomNumber = String.valueOf((int) (Math.random() * 1000));
		return "cid" + randomNumber;
	}
}