package com.exemple.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceTestImpl implements ServiceTest{
	
	@Override
	public int add(int a, int b) {
		return a+b;
	}
	
	@Override
	public String show(String tmp) {
		return tmp;
	}
	
}
