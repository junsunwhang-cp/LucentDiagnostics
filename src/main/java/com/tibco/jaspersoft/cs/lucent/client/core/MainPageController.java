package com.tibco.jaspersoft.cs.lucent.client.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * $Id: MainPageController.java 222 2017-11-01 21:14:04Z jwhang $
 */

@Controller
@EnableAutoConfiguration
public class MainPageController {

    @RequestMapping("/mainPage")
    String home() {
        return "mainPage";
    }

}


