package repo;

import data.Data;
import data.ErpData;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * Created by eustali on 07.02.2017.
 */
@Repository
public interface ErpDataRepository extends CrudRepository<ErpData, Timestamp> {
    //Where kosuluna yazacagin column ya primary key olarak tabloda tanımlı olmalı
    //yada uzerinde 2. indeks tanımlı olmalı
    @Query("SELECT * FROM erptable")
    Iterable<ErpData> findAllErpData();
}
