package controller;

import config.CassandraConfig;
import controller.service.ServiceHelper;
import data.Data;
import data.PieChartData;
import repo.DataRepository;

import com.google.common.collect.Lists;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by eustali on 02.01.2017.
 */
@Controller
public class DataController {

    Logger log = Logger.getRootLogger();

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CassandraConfig cassandraConfig;

    @Autowired
    private ServiceHelper serviceHelper;

    @RequestMapping (value = "/data", method = RequestMethod.GET)
    public String listData(Model model) throws Exception {
        try {
            /**            List<String> companies = findDistinctCompanies();

             if (companies.size() != 0) {
             model.addAttribute("companyList", companies);
             model.addAttribute("datatypes", new String[] {"alarm", "event", "data"});
             }**/
            model.addAttribute("datatypes", new String[] {"alarm", "event", "data"});
        }
        catch (RuntimeException e) {
            log.error("Exception", e);
        }
        return "manufacture";
    }

    @RequestMapping (value = "/data", method = RequestMethod.POST)
    public String filterData(
            @RequestParam (value = "startdate")
                    String fromDate,
            @RequestParam (value = "enddate")
                    String toDate,
            @RequestParam (value = "datatype")
                    String datatype, Model model, HttpSession session) throws ParseException {
        log.info("start date: " + fromDate + "\nend date: " + toDate);
        fromDate = fromDate + ":00";
        toDate = toDate + ":00";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date       startDate  = dateFormat.parse(fromDate);
        Date       endDate    = dateFormat.parse(toDate);

        List<Data> result;

        if (datatype.equals("All")) {
            result = Lists.newArrayList(dataRepository.findDataByTimeRange(startDate, endDate));

            Set<String> distinctDataTypes = new TreeSet<>();

            //result.forEach(p -> distinctDataTypes.add(p.getDatatype().toLowerCase()));
            distinctDataTypes.add("alarm");
            distinctDataTypes.add("event");
            distinctDataTypes.add("data");

            List<PieChartData> dataTypeNameCountPairList = serviceHelper.calculateDataTypeDistribution(result,
                                                                                                       distinctDataTypes);

            model.addAttribute("piechartlist", ArrayUtils.toArray(dataTypeNameCountPairList));

        }
        else {
            result = Lists.newArrayList(dataRepository.findDataByTimeAndDatatype(datatype, startDate, endDate));
        }

        model.addAttribute("datalist", result);

        String info = String.format("Submission: startdate = %s, enddate = %s,", startDate, endDate);
        log.info(info);
        return "manufacture";
    }
}
