/**
 * 
 */
package com.glaucus.service;

import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glaucus.dao.NumberIncrementDao;

/**
 * @author Somendra
 *
 */
@Service
public class NumberIncrementServiceImpl implements NumberIncrementService {

	@Autowired
	NumberIncrementDao numberIncrementDao;

	public int numberIncrementId() {

		System.out.println("*******************Inside NumberIncrementServiceImpl**************");
		int result = 0;

		try {
			result = numberIncrementDao.numberIncrementWithLock();
			System.out.println("Result Succesfully Saved");
		}
		catch (StaleObjectStateException e) {
			System.out.println("Exception occured in NumberIncrementServiceImpl 3 : " + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("Exception occured in NumberIncrementServiceImpl 2 : " + e.getMessage());
		}
		return result;
	}
}
