package com.example.demo.services.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class _BaseService {

	@Autowired
	protected EntityManager entityManager;

}