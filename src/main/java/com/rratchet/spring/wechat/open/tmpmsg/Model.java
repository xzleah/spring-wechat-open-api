package com.rratchet.spring.wechat.open.tmpmsg;

public class Model {

	private String value;
	
	private String color;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Model [value=" + value + ", color=" + color + "]";
	}
}
