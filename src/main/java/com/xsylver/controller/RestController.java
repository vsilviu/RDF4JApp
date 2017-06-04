package com.xsylver.controller;

import org.eclipse.rdf4j.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by vlasceanusilviu on 6/3/17.
 */
@Controller
@RequestMapping("/test")
public class RestController {

    @Autowired
    private Model bobModel;

    @RequestMapping(method = RequestMethod.GET, value = "/1")
    public Object testing() {
        bobModel.forEach(System.out::println);
        return new Object();
    }

}
