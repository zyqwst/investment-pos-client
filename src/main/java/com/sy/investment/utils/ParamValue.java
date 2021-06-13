package com.sy.investment.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

public class ParamValue {
	
	private List<Param> list;
	
	private ParamValue() {
		list = new ArrayList<Param>();
	}
	
	public static ParamValue build() {
		return new ParamValue();
	}
	
	public ParamValue add(String key,Object value) {
		Assert.hasText(key, "参数key值不可为空");
		list.add(new Param(key, value));
		return this;
	}
	public ParamValue addLike(String key,Object value) {
		Assert.hasText(key, "参数key值不可为空");
		Assert.notNull(value, "参数value不可为空");
		list.add(new Param(key, "%"+ value+ "%"));
		return this;
	}
	public ParamValue addLikeLeft(String key,Object value) {
		Assert.hasText(key, "参数key值不可为空");
		Assert.notNull(value, "参数value不可为空");
		list.add(new Param(key, "%"+ value));
		return this;
	}
	public ParamValue addLikeRight(String key,Object value) {
		Assert.hasText(key, "参数key值不可为空");
		Assert.notNull(value, "参数value不可为空");
		list.add(new Param(key,  value+"%"));
		return this;
	}
	public List<Param> get(){
		return list;
	}
	public class Param{
		protected Param(String key,Object value) {
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public Object getValue() {
			return value;
		}
		private String key;
		private Object value;
	}
}
