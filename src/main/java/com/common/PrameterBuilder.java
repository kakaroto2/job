package com.common;

import java.util.HashMap;

public class PrameterBuilder {

	private HashMap<String, Object> map; 
	
	public static PrameterBuilder instance(){
		return new PrameterBuilder();
	}
	private PrameterBuilder() {
		map=new HashMap<String, Object>();
	}
	public  PrameterBuilder  add(String key,Object value){
		this.map.put(key, value);
		return this;
	}
	public HashMap<String, Object> builder(){
		return this.map;
	}
}
