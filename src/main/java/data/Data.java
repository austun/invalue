package data;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;

/**
 * Created by eustali on 30.12.2016.
 */
@Table
public class Data {

    @PrimaryKeyColumn (name="time", ordinal = 1, type= PrimaryKeyType.PARTITIONED)
    private Date time;

    @Column("company_id")
    private String companyID;

    @Column("site_id")
    private int siteID;

    @Column("machine_id")
    private int machineID;

    @Column("device_id")
    private String deviceID;

    @Column("sensor_id")
    private int sensorID;

    @Column("wo_id")
    private String woID;

    @Column("datatype")
    private String datatype;

    @Column("name")
    private String name;

    @Column("value")
    private String value;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCompanyID() {
        return companyID;
    }

    public int getSiteID() {
        return siteID;
    }

    public int getMachineID() {
        return machineID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public int getSensorID() {
        return sensorID;
    }

    public String getWoID() {
        return woID;
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
