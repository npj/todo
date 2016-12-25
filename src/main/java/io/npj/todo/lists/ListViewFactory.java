package io.npj.todo.lists;

import javax.inject.Inject;

/**
 * Created by pbrindisi on 12/24/16.
 */
public class ListViewFactory {
	@Inject
	public ListViewFactory() { }

	public ListIndexView createIndexView() {
		return new ListIndexView();
	}
}
