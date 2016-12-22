package io.npj.todo.lists;

import io.npj.todo.mvc.Controller;
import io.npj.todo.Loop;
import io.npj.todo.Todo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * Created by pbrindisi on 12/16/16.
 */
public class ListsController extends Controller {
	private final ListService listService = new ListService();

	public ListsController() {
		super("ListsController");

	}

	public void index(Optional<Map<String, String>> params) {
		try {
			ListIndexView listIndexView = new ListIndexView();
			listIndexView.addCommandListener(new ListIndexCommandListener());
			listIndexView.render(listService.fetchAll());
		} catch (SQLException | IOException e) {
			Todo.logException(e);
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
			listService.create(list);
		} catch(SQLException e) {
			Todo.logException(e);
		}

		Loop.getInstance().onNext(this::index, Optional.empty());
	}

	public void delete(Optional<Map<String, String>> params) {
		final int id = Integer.valueOf(params.get().get("id"));

		try {
			listService.delete(id);
		} catch (SQLException e) {
			Todo.logException(e);
		}

		Loop.getInstance().onNext(this::index, Optional.empty());
	}
}
