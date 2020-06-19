package pl.edu.agh.rssviewer.persistence.repository;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import javax.inject.Inject;

import pl.edu.agh.rssviewer.persistence.dao.FeedDaoImpl;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.persistence.repository.base.SqLiteRepository;

public class FeedRepository extends SqLiteRepository<Feed, Long> {
    @Inject
    public FeedRepository(ConnectionSource connectionSource) throws SQLException {
        super(new FeedDaoImpl(connectionSource));
    }
}
