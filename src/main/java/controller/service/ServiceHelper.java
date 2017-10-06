package controller.service;

import data.Data;
import data.ErpData;
import data.PieChartData;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by eustali on 12.04.2017.
 */
@Service
public class ServiceHelper {

    Logger log = Logger.getRootLogger();

    public List<PieChartData> calculateDataTypeDistribution(List<Data> data, Set<String> distinctDatatypes) {
        List<PieChartData> outputList = new ArrayList<>();

        for (String datatype : distinctDatatypes) {
            PieChartData datatypeNameCountPair = new PieChartData();
            datatypeNameCountPair.setKey(datatype);
            Long count = data.stream().filter(p -> p.getDatatype().equals(datatype)).count();
            datatypeNameCountPair.setValue(count.toString());

            log.info("Datatype name: " + datatypeNameCountPair.getKey());
            log.info("Total entry against: " + datatypeNameCountPair.getValue());

            outputList.add(datatypeNameCountPair);
        }

        return outputList;
    }

    public List<PieChartData> calculateErpDataTypeDistribution(List<ErpData> data, Set<String> distinctDatatypes) {
        List<PieChartData> outputList = new ArrayList<>();

        for (String erpDatatype : distinctDatatypes) {
            PieChartData erpDataTypeNameCountPair = new PieChartData();
            erpDataTypeNameCountPair.setKey(erpDatatype);
            Long count = data.stream().filter(p -> p.getDatatype().equals(erpDatatype)).count();
            erpDataTypeNameCountPair.setValue(count.toString());

            log.info("ErpDatatype name: " + erpDataTypeNameCountPair.getKey());
            log.info("Total entry against: " + erpDataTypeNameCountPair.getValue());

            outputList.add(erpDataTypeNameCountPair);
        }

        return outputList;
    }

    public List<PieChartData> calculateOperationDistribution(List<ErpData> data, Set<String> distinctOperations) {
        List<PieChartData> outputList = new ArrayList<>();

        for (String operation : distinctOperations) {
            PieChartData erpOperationCountPair = new PieChartData();
            erpOperationCountPair.setKey(operation);
            Long count = data.stream().filter(p -> p.getOperation().equals(operation)).count();
            erpOperationCountPair.setValue(count.toString());

            log.info("Erp Operation name: " + erpOperationCountPair.getKey());
            log.info("Total entry against: " + erpOperationCountPair.getValue());

            outputList.add(erpOperationCountPair);
        }

        return outputList;
    }


}
