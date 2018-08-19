package com.capgemini.dao.impl;

import org.springframework.stereotype.Repository;

import com.capgemini.dao.OfficeDao;
import com.capgemini.domain.OfficeEntity;

@Repository
public class OfficeDaoImpl extends AbstractDao<OfficeEntity,Long> implements OfficeDao {
}
