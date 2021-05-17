/**
 * 
 */
package com.glaucus.dao;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Somendra
 *
 */
@Component
public class NumberIncrementDaoImpl implements NumberIncrementDao {

	@Autowired
	SessionFactory sessionFaactory;

	@Override
	public int numberIncrementWithLock() {

		System.out.println("*********In NumberIncrementDaoImpl*****************");
		Session session = sessionFaactory.getCurrentSession();
		int data = 0;
		
		try {
			session.beginTransaction();
			com.glaucus.entity.Number number = (com.glaucus.entity.Number) session.get(com.glaucus.entity.Number.class, 1, LockOptions.UPGRADE);
			number.setValue(number.getValue() + 1);
			number = (com.glaucus.entity.Number) session.merge(number);
			data = number.getValue();
			session.getTransaction().commit();
		}
		catch (StaleObjectStateException e) {
			session.getTransaction().rollback();
			System.out.println("Exception occured in NumberIncrementDaoImpl 1. Initiating Rollback with exception : " + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("Exception occured in NumberIncrementDaoImpl 2 : " + e.getMessage());
		}
		return data;
	}
}
