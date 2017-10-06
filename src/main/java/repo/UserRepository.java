package repo;

import data.User;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * Created by eustali on 02.03.2017.
 */
public interface UserRepository extends CassandraRepository<User> {

    @Query ("SELECT * FROM users WHERE userid=?0 ALLOW FILTERING")
    User findByUserName(String username);
}
