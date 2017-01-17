package io.npj.todo.items;

import io.npj.todo.mvc.Model;
import io.npj.todo.lists.ListModel;

public class ItemModel extends Model {
	private final ListModel list;
	private String name = null;
	
	public ItemModel(ListModel list) {
		this.list = list;
	}

	public ListModel getList() {
		return list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
