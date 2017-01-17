package io.npj.todo.items;

import io.npj.todo.lists.ListModel;

import javax.inject.Inject;

public class ItemViewFactory {
	@Inject
	public ItemViewFactory() { }

	public ItemIndexView createIndexView(ListModel list) {
		return new ItemIndexView(list);
	}
}
