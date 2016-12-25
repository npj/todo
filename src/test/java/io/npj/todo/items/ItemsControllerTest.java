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

/**
 * Created by pbrindisi on 12/24/16.
 *
 * For a still greater challenge, the ItemsController index has a hard dependency on ItemIndexView, and
 * additionally passes it a ListModel on instantiation. The provider solution we used for ListsController
 * will not work identically here. Furthermore, the dagger documentation suggests that using Provider is
 * a design smell, and a factory is a more flexible and "correct" approach where possible. Rather than use
 * a Provider, we will create an ItemViewFactory that can be injected, and write a createIndexView method.
 * We will then return to ListsController and convert the Provider usage to the same pattern.
 *
 * Steps:
 * 1) Create an ItemViewFactory class with a createIndexView method.
 * 2) Create an itemViewFactory field in ItemsController and use it to instantiate the view in the index method.
 *    The factory instance will be injected into the ItemsController constructor, so it can be mocked in tests.
 * 3) Create a parameter-less constructor in ItemViewFactory and annotate it with @Inject
 * 4) Do the same for ListViewFactory.
 */
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

	/**
	 * Verify that the method does the following:
	 * - fetches a ListModel with the supplied id.
	 * - instantiates an ItemIndexView with the fetched list.
	 * - fetches the list of items in the fetched list.
	 * - renders the ItemIndexView with the fetched items.
	 */
	public void testIndex() throws Exception {
		int listId = 123;
		ListModel list = new ListModel();
		List<ItemModel> items = new ArrayList<>();

		when(mockListService.fetchOne(listId)).thenReturn(list);
		when(mockItemViewFactory.createIndexView(list)).thenReturn(mockItemIndexView);
		when(mockItemService.fetchAll(list)).thenReturn(items);

		Map<String, String> params = new HashMap<>();
		params.put("list_id", String.valueOf(listId));
		itemsController.index(Optional.of(params));
		verify(mockItemIndexView).render(items);
	}

	public void testCreate() throws Exception {

	}

	public void testDelete() throws Exception {

	}
}