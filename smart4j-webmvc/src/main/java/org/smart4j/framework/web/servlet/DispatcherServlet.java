package org.smart4j.framework.web.servlet;

/**
 * HTTP请求处理程序/控制器的中央调度程序，例如用于Web UI控制器或基于HTTP的远程服务导出器。
 * 调度到已注册的处理程序以处理Web请求，提供方便的映射和异常处理工具。
 * 此servlet非常灵活：它可以与几乎任何工作流一起使用，并安装适当的适配器类。
 * 它提供了以下功能，使其与其他请求驱动的Web MVC框架区别开来：它基于JavaBeans配置机制。
 * 它可以使用任何{ HandlerMapping}实现 - 预构建或作为应用程序的一部分提供 - 来控制将请求路由到处理程序对象。
 * 默认值为{ org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping}和{ org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping}。
 * HandlerMapping对象可以在servlet的应用程序上下文中定义为bean，实现HandlerMapping接口，覆盖默认的HandlerMapping（如果存在）。
 * HandlerMappings可以被赋予任何bean名称（它们按类型进行测试）。它可以使用任何{ HandlerAdapter};这允许使用任何处理程序接口。
 * 默认适配器是{ org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter}，{ link org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter}，适用于Spring的{ org.springframework.web.HttpRequestHandler}和{  org.springframework.web.servlet.mvc.Controller}接口。
 * 默认的{ org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter}也将被注册。
 * HandlerAdapter对象可以作为bean添加到应用程序上下文中，覆盖默认的HandlerAdapter。与HandlerMappings一样，HandlerAdapters可以被赋予任何bean名称（它们按类型进行测试）。
 * 可以通过{ HandlerExceptionResolver}指定调度程序的异常解析策略，例如将某些异常映射到错误页面。
 * 默认为{ org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver}，{ link org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver}和{ org.springframework.web。 servlet.mvc.support.DefaultHandlerExceptionResolver}。
 * 可以通过应用程序上下文覆盖这些HandlerExceptionResolvers。
 * HandlerExceptionResolver可以被赋予任何bean名称（它们按类型进行测试）。
 * 可以通过{ ViewResolver}实现指定其视图解析策略，将符号视图名称解析为View对象。默认值为{ org.springframework.web.servlet.view.InternalResourceViewResolver}。
 * ViewResolver对象可以作为bean添加到应用程序上下文中，覆盖默认的ViewResolver。
 * ViewResolvers可以被赋予任何bean名称（它们按类型进行测试）。
 * 如果用户未提供{ View}或视图名称，则配置的{ RequestToViewNameTranslator}会将当前请求转换为视图名称。
 * 相应的bean名称是“viewNameTranslator”;默认值为{ org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator}。
 * 调度程序解决多部分请求的策略由{ org.springframework.web.multipart.MultipartResolver}实现确定。
 * 包括Apache Commons FileUpload和Servlet 3的实现;典型的选择是{ org.springframework.web.multipart.commons.CommonsMultipartResolver}。
 * MultipartResolver bean名称是“multipartResolver”;默认为none。
 * 其区域设置解析策略由{ LocaleResolver}确定。开箱即用的实现通过HTTP接受标头，cookie或会话工作。
 * LocaleResolver bean名称是“localeResolver”;默认值为{ org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver}。
 * 其主题解析策略由{ ThemeResolver}决定。
 * 包括固定主题以及cookie和会话存储的实现。
 * ThemeResolver bean名称是“themeResolver”;默认值为{ org.springframework.web.servlet.theme.FixedThemeResolver}。
 * 注意：{ @RequestMapping}注释仅在相应的{ HandlerMapping}（用于类型级别注释）和/或处理时才会被处理{ HandlerAdapter}（用于方法级注释）存在于调度程序中。
 * 默认情况下就是这种情况。但是，如果要定义自定义{ HandlerMappings}或{ HandlerAdapters}，则需要确保定义相应的自定义{ RequestMappingHandlerMapping}和/或{ RequestMappingHandlerAdapter}  - 前提是您打算使用{ @RequestMapping}
 * Web应用程序可以定义任意数量的DispatcherServlet。
 * 每个servlet将在其自己的命名空间中运行，使用映射，处理程序等加载其自己的应用程序上下文。
 * 只有{ org.springframework.web.context.ContextLoaderListener}加载的根应用程序上下文（如果有）将被共享。
 * 从Spring 3.1开始，{ DispatcherServlet}现在可以注入Web应用程序上下文，而不是在内部创建自己的上下文。
 * 这在Servlet 3.0+环境中很有用，它支持servlet实例的编程注册。 有关详细信息，请参阅{ #DispatcherServlet（WebApplicationContext）} javadoc。
 */

import com.sun.istack.internal.Nullable;
import org.smart4j.framework.context.ApplicationContext;
import org.smart4j.framework.utils.PropsUtils;
import org.smart4j.framework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Properties;

/**
 * HandlerMapping 可以被覆盖,控制请求路由
 * HandlerAdapter 可以被覆盖
 * 如果要定义自定义{ HandlerMappings}或{ HandlerAdapters}，则需要确保定义相应的自定义{ RequestMappingHandlerMapping}和/或{ RequestMappingHandlerAdapter}  - 前提是您打算使用{ @RequestMapping}
 */
public class DispatcherServlet extends FrameworkServlet {
    /**
     * Well-known name for the MultipartResolver object in the bean factory for this namespace.
     */
    private static final String MULTIPART_RESOLVER_BEAN_NAME = "multipartResolver";
    /**
     * Well-known name for the ThemeResolver object in the bean factory for this namespace.
     */
    private static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";
    /**
     * Well-known name for the HandlerAdapter object in the bean factory for this namespace.
     * Only used when "detectAllHandlerAdapters" is turned off.
     * #setDetectAllHandlerAdapters
     */
    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";
    /**
     * Well-known name for the ViewResolver object in the bean factory for this namespace.
     * Only used when "detectAllViewResolvers" is turned off.
     * <p>
     * #setDetectAllViewResolvers
     */
    private static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";
    /**
     * Request attribute to hold the current web application context.
     * Otherwise only the global web app context is obtainable by tags etc.
     * org.springframework.web.servlet.support.RequestContextUtils#findWebApplicationContext
     */
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    /**
     * Name of the class path resource (relative to the DispatcherServlet class)
     * that defines DispatcherServlet's default strategy names.
     */
    private static final String DEFAULT_STRATEGIES_PATH = "DispatcherServlet.properties";
    /**
     * Common prefix that DispatcherServlet's default strategy attributes start with.
     */
    private static final String DEFAULT_STRATEGIES_PREFIX = "org.springframework.web.servlet";

    /**
     * Should we dispatch an HTTP OPTIONS request to { #doService}?
     * 是否发送options请求
     */
    private boolean dispatchOptionsRequest = false;
    private static final Properties defaultStrategies;
    /**
     * List of HandlerMappings used by this servlet
     */
    private List<HandlerMapping> handlerMappings;

    /**
     * List of HandlerAdapters used by this servlet
     */
    private List<HandlerAdapter> handlerAdapters;
    /**
     * RequestToViewNameTranslator used by this servlet
     */
    private RequestToViewNameTranslator viewNameTranslator;
    private List<ViewResolver> viewResolvers;

    static {
        defaultStrategies = PropsUtils.loadProperties(DEFAULT_STRATEGIES_PATH);
    }

    public DispatcherServlet() {
        super();
        setDispatchOptionsRequest(true);
    }

    public DispatcherServlet(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
        setDispatchOptionsRequest(true);
    }

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    protected void initStrategies(ApplicationContext context) {
        initMultipartResolver(context);
//        initLocaleResolver(context);
//        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
//        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
//        initFlashMapManager(context);
    }

    private void initViewResolvers(ApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {
    }

    private void initHandlerAdapters(ApplicationContext context) {
    }

    private void initHandlerMappings(ApplicationContext context) {
    }

    private void initMultipartResolver(ApplicationContext context) {
    }

    public void setDispatchOptionsRequest(boolean dispatchOptionsRequest) {
        this.dispatchOptionsRequest = dispatchOptionsRequest;
    }

}
