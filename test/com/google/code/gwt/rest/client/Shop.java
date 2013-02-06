package com.google.code.gwt.rest.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {

	String name;

	Item item;

	List<Item> lists = new ArrayList<Item>();

	public List<Item> getLists() {
		return lists;
	}

	public void setLists(List<Item> lists) {
		this.lists = lists;
	}

	Map<String, Object> map = new HashMap<String, Object>();

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
