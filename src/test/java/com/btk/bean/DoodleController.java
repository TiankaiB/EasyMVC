// DoodleController
package com.btk.bean;

import my.btk.core.annotation.Controller;
import my.btk.ioc.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DoodleController {
    @Autowired
    private com.btk.bean.DoodleService doodleService;

    public void hello() {
        log.info(doodleService.helloWord());
    }

    public void helloForAspect() {
        log.info("Hello Aspectj");
    }
}



