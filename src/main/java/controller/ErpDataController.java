package controller;

import data.Data;
import data.ErpData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import repo.ErpDataRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eustali on 07.02.2017.
 */
@Controller
@RequestMapping (value = "/erpdata")
public class ErpDataController {

    @Autowired
    private ErpDataRepository erpDataRepository;

    @RequestMapping (method = RequestMethod.GET)
    public String listErpData(Model model) {
        List<ErpData>     allErpData = new ArrayList<>();
        DateFormat        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Iterable<ErpData> result     = erpDataRepository.findAllErpData();
        model.addAttribute("erpdatalist", result);
        return "performance";
    }

}
