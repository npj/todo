package io.npj.todo;

import dagger.Component;
import io.npj.todo.items.ItemsController;
import io.npj.todo.lists.ListsController;
import javax.inject.Singleton;

@Singleton
@Component(modules = { DBModule.class })
public interface TodoComponent {
    ListsController listsController();
    ItemsController itemsController();
}


