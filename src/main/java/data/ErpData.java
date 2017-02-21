package data;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by austun on 1/29/2017.
 */
@Table
public class ErpData {

    @PrimaryKeyColumn (name="time", ordinal = 1, type= PrimaryKeyType.PARTITIONED)
    private Date time;

    @Column("company_id")
    private String companyID;

    @Column("site_id")
    private int siteID;

    @Column("operation")
    private String operation;

    @Column("datatype")
    private String datatype;

    @Column("name")
    private String name;

    @Column("value")
    private String value;

    private String formattedDate;

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public Date getTime() {
        return time;
    }

    public String getCompanyID() {
        return companyID;
    }

    public int getSiteID() {
        return siteID;
    }

    public String getOperation() {
        return operation;
    }

    public String getDatatype() {
        return datatype;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}