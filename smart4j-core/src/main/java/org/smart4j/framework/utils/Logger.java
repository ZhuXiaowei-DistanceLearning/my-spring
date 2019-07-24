package org.smart4j.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private String name;
    private Class cls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Logger(Class class1) {
        this.cls = class1;
        Date date = new Date();
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        System.out.println("[" + time + "]:----------------");
        System.out.println("[" + time + "]:------****------");
        System.out.println("[" + time + "]:----------------");
        System.out.println("[" + time + "]:" + class1.getName() + "类初始化");
    }

    public void info(String name) {
        Date date = new Date();
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        this.name = name;
        System.out.println("[" + time + "]:" + name);
    }

    public void catchException(String name) {
        Date date = new Date();
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        this.name = name;
        System.out.println("[类" + cls.getName() + "发生了异常，异常信息为    " + time + "]:" + name);
    }

}
