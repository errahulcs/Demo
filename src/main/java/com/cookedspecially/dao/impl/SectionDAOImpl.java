/**
 * 
 */
package com.cookedspecially.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.SectionDAO;
import com.cookedspecially.domain.Section;

/**
 * @author shashank
 *
 */
@Repository
public class SectionDAOImpl implements SectionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addSection(Section section) {
		sessionFactory.getCurrentSession().saveOrUpdate(section);

	}

}
