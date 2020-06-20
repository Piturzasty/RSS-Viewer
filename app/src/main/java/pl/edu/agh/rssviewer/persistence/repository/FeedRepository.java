package pl.edu.agh.rssviewer.persistence.repository;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.rssviewer.persistence.dao.FeedDaoImpl;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.persistence.repository.base.SqLiteRepository;

public class FeedRepository extends SqLiteRepository<Feed, Long> {
    public FeedRepository(ConnectionSource connectionSource) throws SQLException {
        super(new FeedDaoImpl(connectionSource));
    }

    public List<Feed> findByCategory(String category) {
        try {
            PreparedQuery<Feed> preparedQuery = dao
                    .queryBuilder()
                    .where()
                    .eq(Feed.CATEGORY_NAME, category)
                    .prepare();
            return this.dao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
