package org.smart4j.framework.web.servlet;

import org.smart4j.framework.context.ApplicationContext;
import org.smart4j.framework.http.HttpMethod;
import org.smart4j.framework.web.context.ConfigurableWebApplicationContext;
import org.smart4j.framework.web.context.WebApplicationContext;
import org.smart4j.framework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Spring的Web框架的基本servlet。
 * 在基于JavaBean的整体解决方案中提供与Spring应用程序上下文的集成。
 * 此类提供以下功能：为每个servlet管理{ org.springframework.web.context.WebApplicationContext WebApplicationContext}实例。
 * servlet的配置由servlet命名空间中的bean确定。
 * 在请求处理时发布事件，无论请求是否成功处理。
 * 子类必须实现{ #doService}来处理请求。
 * 因为这会直接扩展{ HttpServletBean}而不是HttpServlet，所以bean属性会自动映射到它上面。
 * 子类可以覆盖{ #initFrameworkServlet（）}以进行自定义初始化。
 * 在servlet init-param级别检测“contextClass”参数，然后返回到默认上下文类{ org.springframework.web.context.support。
 * XmlWebApplicationContext XmlWebApplicationContext}，如果找不到。
 * 请注意，使用默认的{ FrameworkServlet}，自定义上下文类需要实现{ org.springframework.web.context.ConfigurableWebApplicationContext ConfigurableWebApplicationContext} SPI.Accepts可选的“contextInitializerClasses”servlet init-param，它指定一个或更多{ org.springframework.context.ApplicationContextInitializer ApplicationContextInitializer}类。
 * 托管的Web应用程序上下文将被委托给这些初始化程序，从而允许进行额外的编程配置，例如，根据{ org.springframework.context.ConfigurableApplicationContext＃getEnvironment（）上下文的环境}添加属性源或激活配置文件。
 * 另请参阅{ org.springframework.web.context.ContextLoader}，它支持“contextInitializerClasses”context-param，其中“root”Web应用程序上下文具有相同的语义。
 * 将“contextConfigLocation”servlet init-param传递给上下文实例，将其解析为可能的多个文件路径，可以用任意数量的逗号和空格分隔，例如“test-servlet.xml，myServlet.xml”。
 * 如果没有明确指定，则上下文实现应该从servlet的命名空间构建一个默认位置。
 * 注意：如果有多个配置位置，以后的bean定义将覆盖先前加载的文件中定义的那些，至少在使用Spring的默认ApplicationContext时实现。
 * 这可以用于通过额外的XML文件故意覆盖某些bean定义。
 * 默认命名空间是“'servlet-name'-servlet”，例如， servlet-name“test”的“test-servlet”（通过XmlWebApplicationContext导致“/WEB-INF/test-servlet.xml”默认位置）。
 * 命名空间也可以通过“namespace”servlet init-param显式设置。
 * 从Spring 3.1开始，{ FrameworkServlet}现在可以注入Web应用程序上下文，而不是在内部创建自己的。
 * 这在Servlet 3.0+环境中很有用，它支持servlet实例的编程注册。有关详细信息，请参阅{ #FrameworkServlet（WebApplicationContext）} Javadoc。
 */

/**
 * 子类可以覆盖{ #initFrameworkServlet（）}以进行自定义初始化。
 * 初始化WebApplicationContext
 * 初始化FrameworkServlet
 */
public abstract class FrameworkServlet extends HttpServletBean {
    public static final String DEFAULT_NAMESPACE_SUFFIX = "-servlet";

    public static final String SERVLET_CONTEXT_PREFIX = FrameworkServlet.class.getName() + ".CONTEXT";


    private static final String INIT_PARAM_DELIMITERS = ",;\t\n";

    private String contextAttribute;

    private String contextId;
    private String namespace;
    private String contextConfigLocation;
    private String contextInitializerClasses;
    private WebApplicationContext webApplicationContext;

    /**
     * 用于检测是否已调用onRefresh的标志
     */
    private boolean refreshEventReceived = false;

    public FrameworkServlet() {
    }

    /**
     * 模板方法
     *
     * @throws ServletException
     */
    protected void initFrameworkServlet() throws ServletException {

    }

    protected void onRefresh(ApplicationContext context) {
        // For subclasses: do nothing by default.
    }

    @Override
    /**
     * 该类的入口
     */
    protected void initServletBean() throws ServletException {
        long startTime = System.currentTimeMillis();
        try {
            this.webApplicationContext = initWebApplicationContext();
            initFrameworkServlet();
        } catch (Exception e) {
            System.out.println("初始化FrameworkServlet失败");
            throw new RuntimeException();
        }
    }

    /**
     * 获取spring的根容器rootContext
     * 设置WebApplicationContext 并根据情况调用onRefresh方法
     * 将WebApplicationContext设置到ServletContext中
     *
     * @return
     */
    protected WebApplicationContext initWebApplicationContext() {
        WebApplicationContext wac = null;
        // 如果已经通过构造方法设置了webApplicationContext
        WebApplicationContext rootContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        if (this.webApplicationContext != null) {
            wac = this.webApplicationContext;
            if (wac instanceof ConfigurableWebApplicationContext) {
                ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
                if (cwac.getParent() == null) {
                    cwac.setParent(rootContext);
                }
            }
        }
        if (wac == null) {
            // 当webApplicationContext已经存在ServletContext中时，通过配置在Servlet中的contextAttributes参数获取
            wac = findWebApplicationContext();
        }
        if (wac == null) {
            // 如果webApplicationContext还没有创建，则创建一个
            createWebApplicationContext(wac);
        }
        return wac;
    }

    protected WebApplicationContext createWebApplicationContext(WebApplicationContext parent) {
        return createWebApplicationContext(parent);
    }

    protected WebApplicationContext createWebApplicationContext(ApplicationContext parent) {
        Class<?> contextClass = getContextClass();
        return null;
    }

    protected WebApplicationContext findWebApplicationContext() {
        String attrName = getContextAttribute();
        if (attrName == null) {
            return null;
        }
        WebApplicationContext wac =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext(), attrName);
        if (wac == null) {
            throw new IllegalStateException("找不到WebApplicationContext：无法注册");
        }
        return wac;
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpMethod httpMethod = HttpMethod.resolve(req.getMethod());
        // 判断请求方式是否为PATCH 或者 null
        if (httpMethod == HttpMethod.PATCH || httpMethod == null) {
            processRequest(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    protected final void processRequest(HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        try {
            doService(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public FrameworkServlet(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    public String getContextAttribute() {
        return contextAttribute;
    }

    public Class<?> getContextClass() {
        return null;
    }

    public void setContextAttribute(String contextAttribute) {
        this.contextAttribute = contextAttribute;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getContextConfigLocation() {
        return contextConfigLocation;
    }

    public void setContextConfigLocation(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }

    public String getContextInitializerClasses() {
        return contextInitializerClasses;
    }

    public void setContextInitializerClasses(String contextInitializerClasses) {
        this.contextInitializerClasses = contextInitializerClasses;
    }

    public WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    protected String getUsernameForRequest(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        return (userPrincipal != null ? userPrincipal.getName() : null);
    }

    protected abstract void doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception;

}
