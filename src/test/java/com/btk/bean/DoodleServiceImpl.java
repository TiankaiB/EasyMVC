// DoodleServiceImpl
package com.btk.bean;

import my.btk.core.annotation.Service;

@Service
public class DoodleServiceImpl implements com.btk.bean.DoodleService {
    @Override
    public String helloWord() {
        return "hello word";
    }
}