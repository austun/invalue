package controller;

import data.Data;
import repo.DataRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eustali on 02.01.2017.
 */
@Controller
public class DataController {

    Logger log = Logger.getRootLogger();

    @Autowired
    private DataRepository dataRepository;

    @RequestMapping (value = "/data", method = RequestMethod.GET)
    public String listData(Model model) {
        return "timesubmit";
    }

    @RequestMapping (value = "/data", method = RequestMethod.POST)
    public String listDataByTimeRange(
            @RequestParam (value = "startdate")
                    String fromDate,
            @RequestParam (value = "enddate")
                    String toDate, Model model) throws ParseException {
        fromDate = fromDate + " 00:00:00";
        toDate = toDate + " 00:00:00";
        DateFormat     dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date           startDate  = dateFormat.parse(fromDate);
        Date           endDate    = dateFormat.parse(toDate);
        Iterable<Data> result     = dataRepository.findDataByTimeRange(startDate, endDate);
        model.addAttribute("datalist", result);

        String info = String.format("Submission: startdate = %s, enddate = %s,", startDate, endDate);
        log.info(info);
        return "timesubmit";
    }

}
