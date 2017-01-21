package io.npj.todo.items;

import io.npj.todo.lists.ListModel;
import io.npj.todo.lists.ListStore;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemsControllerTest extends TestCase {
	private ListStore mockListStore;
	private ItemStore mockItemStore;
	private ItemViewFactory mockItemViewFactory;
	private ItemIndexView mockItemIndexView;
	private ItemsController itemsController;

	public void setUp() throws Exception {
		super.setUp();
		mockListStore = mock(ListStore.class);
		mockItemStore = mock(ItemStore.class);
		mockItemViewFactory = mock(ItemViewFactory.class);
		mockItemIndexView = mock(ItemIndexView.class);
		itemsController = new ItemsController(mockListStore, mockItemStore, mockItemViewFactory);
	}

	public void testIndex() throws Exception {
		int listId = 123;
		ListModel list = mock(ListModel.class);
		List<ItemModel> items = new ArrayList<>();

		when(mockListStore.fetchOne(listId)).thenReturn(list);
		when(mockItemViewFactory.createIndexView(list)).thenReturn(mockItemIndexView);
		when(mockItemStore.fetchAll(list)).thenReturn(items);

		Map<String, String> params = new HashMap<>();
		params.put("list_id", String.valueOf(listId));
		itemsController.index(Optional.of(params));
		verify(mockItemIndexView).render(items);
	}
}