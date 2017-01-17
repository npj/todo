package io.npj.todo.lists;

import io.npj.todo.DB;
import io.npj.todo.mvc.Controller;
import io.npj.todo.Loop;
import io.npj.todo.TodoApp;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@Singleton
public class ListsController extends Controller {
	private final ListStore listStore;
	private final ListViewFactory listViewFactory;

	@Inject
	public ListsController(ListStore listStore, ListViewFactory listViewFactory) {
		super("ListsController");
		this.listStore = listStore;
		this.listViewFactory = listViewFactory;
	}

	public void index(Optional<Map<String, String>> params) {
		try {
			ListIndexView listIndexView = listViewFactory.createIndexView();
			listIndexView.render(listStore.fetchAll());
		} catch (SQLException | DB.DataFileException | IOException e) {
			TodoApp.logException(e);
		}
	}

	public void create(Optional<Map<String, String>> params) {
		String name = params.get().get("name");
		if (name.isEmpty()) {
			name = "Default List Title";
		}

		try {
			ListModel list = new ListModel();
			list.setName(name);
			listStore.create(list);
		} catch(SQLException | DB.DataFileException e) {
			TodoApp.logException(e);
		}

		Loop.getInstance().onNext(this::index, Optional.empty());
	}

	public void delete(Optional<Map<String, String>> params) {
		final int id = Integer.valueOf(params.get().get("id"));

		try {
			listStore.delete(id);
		} catch (SQLException | DB.DataFileException e) {
			TodoApp.logException(e);
		}

		Loop.getInstance().onNext(this::index, Optional.empty());
	}
}

