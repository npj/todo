package io.npj.todo.items;

import io.npj.todo.lists.ListModel;
import io.npj.todo.lists.ListService;
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
	private ListService mockListService;
	private ItemService mockItemService;
	private ItemViewFactory mockItemViewFactory;
	private ItemIndexView mockItemIndexView;
	private ItemsController itemsController;

	public void setUp() throws Exception {
		super.setUp();
		mockListService = mock(ListService.class);
		mockItemService = mock(ItemService.class);
		mockItemViewFactory = mock(ItemViewFactory.class);
		mockItemIndexView = mock(ItemIndexView.class);
		itemsController = new ItemsController(mockListService, mockItemService, mockItemViewFactory);
	}

	public void testIndex() throws Exception {
		int listId = 123;
		ListModel list = mock(ListModel.class);
		List<ItemModel> items = new ArrayList<>();

		when(mockListService.fetchOne(listId)).thenReturn(list);
		when(mockItemViewFactory.createIndexView(list)).thenReturn(mockItemIndexView);
		when(mockItemService.fetchAll(list)).thenReturn(items);

		Map<String, String> params = new HashMap<>();
		params.put("list_id", String.valueOf(listId));
		itemsController.index(Optional.of(params));
		verify(mockItemIndexView).render(items);
	}
}