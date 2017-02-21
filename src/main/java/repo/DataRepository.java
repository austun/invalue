package repo;

import data.Data;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by eustali on 11.01.2017.
 */
@Repository
public interface DataRepository extends CassandraRepository<Data>/*CrudRepository<Data, Timestamp>*/ {
    //Where kosuluna yazacagin column ya primary key olarak tabloda tanımlı olmalı
    //yada uzerinde 2. indeks tanımlı olmalı

    @Query("SELECT * FROM datatable2")
    Iterable<Data> findAllData();

    @Query("SELECT * FROM datatable2 WHERE time>=?0 AND time<=?1 ALLOW FILTERING")
    Iterable<Data> findDataByTimeRange(Date fromDate, Date toDate);
}
