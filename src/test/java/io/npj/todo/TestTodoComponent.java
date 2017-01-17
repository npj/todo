package io.npj.todo;

import dagger.Component;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@Component(modules = { TestDBModule.class })
public interface TestTodoComponent extends TodoComponent {
    Optional<DB> db();
}
