package controller;

import config.CassandraConfig;
import controller.service.ServiceHelper;
import data.ErpData;
import data.PieChartData;
import repo.ErpDataRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.ArrayUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by eustali on 07.02.2017.
 */
@Controller
public class ErpDataController {

    Logger log = Logger.getRootLogger();

    @Autowired
    private ErpDataRepository erpDataRepository;

    @Autowired
    private ServiceHelper serviceHelper;

    @Autowired
    private CassandraConfig cassandraConfig;

    @RequestMapping (value = "/erpdata", method = RequestMethod.GET)
    public String listErpData(Model model) {
        model.addAttribute("datatypes",
                           new ArrayList<String>(Arrays.asList("inventory", "order", "stock", "workorder")));
        model.addAttribute("operations", new ArrayList<String>(Arrays.asList("insert", "update", "delete")));
        return "performance";
    }

    @RequestMapping (value = "/erpdata", method = RequestMethod.POST)
    public String filterData(
            @RequestParam (value = "startdate")
                    String fromDate,
            @RequestParam (value = "enddate")
                    String toDate,
            @RequestParam (value = "datatype")
                    String datatype,
            @RequestParam (value = "operation")
                    String operation, Model model) throws ParseException {
        try {
            fromDate = fromDate + ":00";
            toDate = toDate + ":00";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date       startDate  = dateFormat.parse(fromDate);
            Date       endDate    = dateFormat.parse(toDate);

            String cql = String.format("SELECT * FROM erptable WHERE time >= '%s' AND time <= '%s' ",
                                       fromDate,
                                       toDate);

            if (!datatype.equals("All")) {
                String datatypeClause = String.format("AND datatype = '%s' ", datatype);
                cql = cql + datatypeClause;
            }

            if (!operation.equals("All")) {
                String operationClause = String.format("AND operation = '%s' ", operation);
                cql = cql + operationClause;
            }

            cql = cql + "ALLOW FILTERING";

            log.info("Generated cql query: " + cql);

            CassandraOperations cassandraOperations = cassandraConfig.cassandraTemplate();
            List<ErpData>       result              = cassandraOperations.select(cql, ErpData.class);

            if (datatype.equals("All")) {
                Set<String> distinctDataTypes = new TreeSet<>();

                //result.forEach(p -> distinctDataTypes.add(p.getDatatype().toLowerCase()));
                distinctDataTypes.add("inventory");
                distinctDataTypes.add("order");
                distinctDataTypes.add("stock");
                distinctDataTypes.add("workorder");

                List<PieChartData> dataTypeNameCountPairList = serviceHelper.calculateErpDataTypeDistribution(result, distinctDataTypes);

                model.addAttribute("erpdatatypelist", ArrayUtils.toArray(dataTypeNameCountPairList));
            }

            if (operation.equals("All")) {
                Set<String> distinctOperations = new TreeSet<>();

                distinctOperations.add("insert");
                distinctOperations.add("update");
                distinctOperations.add("delete");

                //result.forEach(p -> distinctDataTypes.add(p.getDatatype().toLowerCase()));

                List<PieChartData> operationAndCountPairList = serviceHelper.calculateOperationDistribution(result, distinctOperations);

                model.addAttribute("erpoperationlist", ArrayUtils.toArray(operationAndCountPairList));
            }

            model.addAttribute("datalist", result);

            String info = String.format("Submission: startdate = %s, enddate = %s, datatype = %s operation = %s",
                                        startDate,
                                        endDate,
                                        datatype,
                                        operation);
            log.info(info);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "performance";
    }

}
