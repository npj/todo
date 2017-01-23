package io.npj.todo;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Optional;

@Module
public class TestDBModule {
    @Provides @Singleton static Optional<DB> provideDB() {
        try {
            return Optional.of(new DB("jdbc:sqlite:"));
        } catch(SQLException | DB.DataFileException e) {
            return Optional.empty();
        }
    }
}

