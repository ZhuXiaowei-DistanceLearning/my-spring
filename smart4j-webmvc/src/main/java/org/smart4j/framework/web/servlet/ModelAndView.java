package org.smart4j.framework.web.servlet;

import org.smart4j.framework.http.HttpStatus;
import org.smart4j.framework.ui.ModelMap;
import org.smart4j.framework.utils.CollectionUtils;

import java.util.Map;

/**
 * Web MVC框架中的Model和View的持有者。
 * 请注意，这些完全不同。 这个类只是为了使控制器能够在单个返回值中返回模型和视图。
 * 表示由处理程序返回的模型和视图，由DispatcherServlet解析。
 * 视图可以采用String视图名称的形式，需要由ViewResolver对象解析;
 * 或者，可以直接指定View对象。 该模型是一个Map，允许使用按名称键入的多个对象。
 */
public class ModelAndView {
    private Object view;
    private ModelMap model;
    private HttpStatus status;
    private boolean cleared = false;

    public ModelAndView() {
    }

    /**
     * 没有要公开的模型数据时方便的构造函数。 也可以与{@code addObject}一起使用。
     * @param viewName 要呈现的视图名称
     */
    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    /**
     * 没有要公开的模型数据时方便的构造函数。 也可以与{@code addObject}一起使用。
     * @param view View object to render
     */
    public ModelAndView(View view) {
        this.view = view;
    }

    /**
     * 给定视图名称和模型，创建一个新的ModelAndView。
     * @param viewName viewName要呈现的视图的名称，由DispatcherServlet的ViewResolver@param模型解析模型名称（字符串）到模型对象（对象）的映射。
     * @param model 模型条目可能不是{@code null}，但如果没有模型数据，模型Map可能是{@code null}。
     */
    public ModelAndView(String viewName, Map<String, ?> model) {
        this.view = viewName;
        if (model != null) {
            getModelMap();
        }
    }

    public ModelAndView(View view, Map<String, ?> model) {
        this.view = view;
    }

    public ModelAndView(String viewName, HttpStatus status) {
        this.view = viewName;
        this.status = status;
    }

    public boolean hasView() {
        return (this.view != null);
    }

    public boolean isReference() {
        return this.view instanceof String;
    }

    protected Map<String, Object> getModelInternal() {
        return this.model;
    }

    public ModelMap getModelMap() {
        if (this.model == null) {
            this.model = new ModelMap();
        }
        return this.model;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        return this;
    }

    public void clear() {
        this.view = null;
        this.model = null;
        this.cleared = true;
    }

    public boolean isEmpty() {
        return (this.view == null && CollectionUtils.isEmpty(this.model));
    }


    public String getViewName() {
        // 判断该view是否为字符串类型
        return (this.view instanceof String ? (String) this.view : null);
    }

    public Object getView() {
        return this.view instanceof View ? (View) this.view : null;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public ModelMap getModel() {
        return model;
    }

    public void setModel(ModelMap model) {
        this.model = model;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }
}
