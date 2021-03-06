package io.npj.todo.items;

import io.npj.todo.DB;
import io.npj.todo.lists.ListStore;
import io.npj.todo.mvc.Controller;
import io.npj.todo.Loop;
import io.npj.todo.TodoApp;
import io.npj.todo.lists.ListModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by pbrindisi on 12/16/16.
 */
public class ItemsController extends Controller {
	private ListStore listStore = new ListStore();
	private ItemStore itemStore = new ItemStore();

	public ItemsController() {
		super("ItemsController");
	}

	public void index(Optional<Map<String, String>> params) {
		if (params.isPresent()) {
			try {
				ListModel list = getList(params.get());
				ItemIndexView itemIndexView = new ItemIndexView(list);
				itemIndexView.render(itemStore.fetchAll(list));
			} catch (SQLException | IOException | DB.DataFileException e) {
				TodoApp.logException(e);
			}
		}
	}

	public void create(Optional<Map<String, String>> params) {
		String name = params.get().get("name");
		if (name == null) {
			name = "default item name";
		}

		try {
			ListModel list = getList(params.get());
			ItemModel item = new ItemModel(list);
			item.setName(name);
			itemStore.create(list, item);

		} catch (SQLException | DB.DataFileException e) {
			TodoApp.logException(e);
		}

		Map<String, String> indexParams = new HashMap<>();
		indexParams.put("list_id", params.get().get("list_id"));
		Loop.getInstance().onNext(this::index, Optional.of(indexParams));
	}

	public void delete(Optional<Map<String, String>> params) {
		try {
			ListModel list = getList(params.get());
			itemStore.delete(list, Integer.valueOf(params.get().get("id")));
		} catch (SQLException | DB.DataFileException e) {
			TodoApp.logException(e);
		}

		Map<String, String> indexParams = new HashMap<>();
		indexParams.put("list_id", params.get().get("list_id"));
		Loop.getInstance().onNext(this::index, Optional.of(indexParams));
	}

	private ListModel getList(Map<String, String> params) throws SQLException, DB.DataFileException {
		return listStore.fetchOne(Integer.valueOf(params.get("list_id")));
	}
}
