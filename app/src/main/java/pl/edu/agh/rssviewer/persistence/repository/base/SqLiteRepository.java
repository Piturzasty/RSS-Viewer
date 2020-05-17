package pl.edu.agh.rssviewer.persistence.repository.base;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public abstract class SqLiteRepository<T, I> implements Repository<T, I> {
    private Dao<T, I> dao;

    protected SqLiteRepository(Dao<T, I> dao) {
        this.dao = dao;
    }

    @Override
    public List<T> findAll() {
        return safeDaoOperation(() -> dao.queryForAll(), Collections.emptyList());
    }

    @Override
    public T findById(I id) {
        return safeDaoOperation(() -> id != null ? dao.queryForId(id) : null, null);
    }

    @Override
    public Boolean create(T r) {
        return safeDaoOperation(() -> dao.create(r) > 0, false);
    }

    @Override
    public Boolean update(T r) {
        return safeDaoOperation(() -> dao.update(r) > 0, false);
    }

    @Override
    public Boolean delete(T r) {
        return safeDaoOperation(() -> dao.delete(r) > 0, false);
    }

    @Override
    public Boolean isEmpty() {
        return safeDaoOperation(() -> dao.queryForAll().size() == 0, false);
    }

    @Override
    public Long count() {
        return safeDaoOperation(() -> dao.countOf(), 0L);
    }

    @Override
    public Boolean doesTableExists() {
        return safeDaoOperation(() -> dao.isTableExists(), false);
    }

    private interface DaoOperation<K> {
        K invoke() throws SQLException;
    }

    private <K> K safeDaoOperation(DaoOperation<K> operation, K defaultValue) {
        try {
            return operation.invoke();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }
}
