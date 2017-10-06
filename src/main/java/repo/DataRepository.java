package repo;

import data.Data;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by eustali on 11.01.2017.
 */
@Repository
public interface DataRepository extends CassandraRepository<Data>/*CrudRepository<Data, Timestamp>*/ {
    //Where kosuluna yazacagin column ya primary key olarak tabloda tanımlı olmalı
    //yada uzerinde 2. indeks tanımlı olmalı

    @Query("SELECT * FROM datatable")
    Iterable<Data> findAllData();

    @Query("SELECT * FROM datatable WHERE time>=?0 AND time<=?1 ALLOW FILTERING")
    Iterable<Data> findDataByTimeRange(Date fromDate, Date toDate);

    @Query("SELECT * FROM datatable WHERE datatype =?0 AND time>=?1AND time<=?2 ALLOW FILTERING")
    Iterable<Data> findDataByTimeAndDatatype(String datatype, Date fromDate, Date toDate);

}
