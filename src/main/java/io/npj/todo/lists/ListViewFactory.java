package io.npj.todo.lists;

import javax.inject.Inject;

public class ListViewFactory {
	@Inject
	public ListViewFactory() { }

	public ListIndexView createIndexView() {
		return new ListIndexView();
	}
}
