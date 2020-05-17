package pl.edu.agh.rssviewer.persistence.repository.base;

import java.util.List;

public interface Repository<T, I> {

    List<T> findAll();
    T findById(I id);

    Boolean create(T r);
    Boolean update(T r);
    Boolean delete(T r);

    Boolean isEmpty();
    Long count();

    Boolean doesTableExists();
}
