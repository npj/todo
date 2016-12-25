package io.npj.todo.items;

import io.npj.todo.lists.ListModel;

import javax.inject.Inject;

/**
 * Created by pbrindisi on 12/24/16.
 */
public class ItemViewFactory {
	@Inject
	public ItemViewFactory() { }

	public ItemIndexView createIndexView(ListModel list) {
		return new ItemIndexView(list);
	}
}
