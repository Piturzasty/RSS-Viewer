package pl.edu.agh.rssviewer.persistence.repository;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import pl.edu.agh.rssviewer.persistence.dao.FeedSourceDaoImpl;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;
import pl.edu.agh.rssviewer.persistence.repository.base.SqLiteRepository;

public class FeedSourceRepository extends SqLiteRepository<FeedSource, Long> {
    public FeedSourceRepository(ConnectionSource connectionSource) throws SQLException {
        super(new FeedSourceDaoImpl(connectionSource));
    }

    public FeedSource findByUrl(String url) {
        try {
            PreparedQuery<FeedSource> preparedQuery = dao
                    .queryBuilder()
                    .where()
                    .eq(FeedSource.URL_NAME, url)
                    .prepare();
            return this.dao.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
