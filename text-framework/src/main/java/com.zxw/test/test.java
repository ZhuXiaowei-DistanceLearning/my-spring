package com.zxw.test;

import com.zxw.web.LoginServlet;
import org.smart4j.framework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Method;

public class test {
    public static void main(String[] args) {
        DefaultListableBeanFactory dl = new DefaultListableBeanFactory();
        dl.registerBean();
        try {
            Method[] methods = LoginServlet.class.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().contains("register")) {
                    Object bean = dl.getBean(LoginServlet.class);
                    method.invoke(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
