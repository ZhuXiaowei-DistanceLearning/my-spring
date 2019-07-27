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

import org.smart4j.framework.context.ApplicationContext;
import org.smart4j.framework.utils.ClassUtils;
import org.smart4j.framework.utils.PropsUtils;
import org.smart4j.framework.utils.ReflectionUtils;
import org.smart4j.framework.web.context.WebApplicationContext;
import org.smart4j.framework.web.servlet.view.BeanNameViewResolver;
import org.smart4j.framework.web.servlet.view.DefaultRequestToViewNameTranslator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * HandlerMapping 可以被覆盖,控制请求路由
 * HandlerAdapter 可以被覆盖
 * 如果要定义自定义{ HandlerMappings}或{ HandlerAdapters}，则需要确保定义相应的自定义{ RequestMappingHandlerMapping}和/或{ RequestMappingHandlerAdapter}  - 前提是您打算使用{ @RequestMapping}
 */
public class DispatcherServlet extends FrameworkServlet {
    /**
     * 此命名空间的Bean工厂中的MultipartResolver对象的已知名称。
     */
    private static final String MULTIPART_RESOLVER_BEAN_NAME = "multipartResolver";
    /**
     * Bean命名空间中此处理器映射器命名空间的HandlerMapping对象的已知名称。
     */
    private static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";
    /**
     * Bean命名空间中此HandspaceAdapter对象的已知名称。
     * 仅在“detectAllHandlerAdapters”关闭时使用。
     * #setDetectAllHandlerAdapters
     */
    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";
    /**
     * Bean命名空间中此命名空间的RequestToViewNameTranslator对象的已知名称。
     */
    public static final String REQUEST_TO_VIEW_NAME_TRANSLATOR_BEAN_NAME = "viewNameTranslator";

    /**
     * bean命名空间中此ViewHesolver对象的已知名称。仅在“detectAllViewResolvers”关闭时使用
     * <p>
     * #setDetectAllViewResolvers
     */
    private static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";
    /**
     * 请求属性以保存当前Web应用程序上下文。
     * 否则，只有标签等可以获得全局Web应用程序上下文
     * org.springframework.web.servlet.support.RequestContextUtils＃findWebApplicationContext
     */
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    /**
     * 类路径资源的名称（相对于DispatcherServlet类），它定义DispatcherServlet的默认策略名称。
     */
    private static final String DEFAULT_STRATEGIES_PATH = "DispatcherServlet.properties";
    /**
     * DispatcherServlet's 的默认策略属性的常用前缀
     */
    private static final String DEFAULT_STRATEGIES_PREFIX = "org.smart4j.framework.web.servlet";


    /**
     * Detect all HandlerMappings or just expect "handlerMapping" bean?
     * 检测所有处理器映射器
     * 或者只是期望处理器映射器对象
     */
    private boolean detectAllHandlerMappings = true;

    /**
     * Detect all HandlerAdapters or just expect "handlerAdapter" bean?
     */
    private boolean detectAllHandlerAdapters = true;
    /**
     * Detect all ViewResolvers or just expect "viewResolver" bean?
     */
    private boolean detectAllViewResolvers = true;

    /**
     * Should we dispatch an HTTP OPTIONS request to { #doService}?
     * 是否发送options请求
     */
    private boolean dispatchOptionsRequest = false;
    private static final Properties defaultStrategies;

    /**
     * 此servlet使用的HandlerMapping列表
     */
    private List<HandlerMapping> handlerMappings;

    /**
     * 此servlet使用的HandlerAdapter列表
     */
    private List<HandlerAdapter> handlerAdapters;
    /**
     * 此servlet使用的RequestToViewNameTranslator
     */
    private RequestToViewNameTranslator viewNameTranslator;
    /**
     * 在包含请求后执行请求属性的清理？
     */
    private boolean cleanupAfterInclude = true;
    private List<ViewResolver> viewResolvers;

    static {
        defaultStrategies = PropsUtils.loadProperties(DEFAULT_STRATEGIES_PATH);
    }

    public DispatcherServlet() {
        super();
        setDispatchOptionsRequest(true);
    }

    /**
     * 责任链模式，由具体传入的webApplicationContext实现类去执行
     *
     * @param webApplicationContext
     */
    public DispatcherServlet(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
        setDispatchOptionsRequest(true);
    }

    @Override
    /**
     * 将DispatcherServlet特定的请求属性和委托公开给{@link #doDispatch} 进行实际调度
     */
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //在包含的情况下保留请求属性的快照，
        //能够在include之后恢复原始属性。
        Map<String, Object> attributesSnapshot = null;
        attributesSnapshot = new HashMap<>();
        Enumeration<?> parameterNames = request.getAttributeNames();
        while (parameterNames.hasMoreElements()) {
            String attrName = (String) parameterNames.nextElement();
            // 在包含请求后执行请求属性的清理 或者 该属性的开始名称以默认策略前缀为开始时
            if (this.cleanupAfterInclude || attrName.startsWith(DEFAULT_STRATEGIES_PREFIX)) {
                attributesSnapshot.put(attrName, request.getAttribute(attrName));
            }
        }
        //使框架对象可供处理程序和视图对象使用。
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());
        try {
            doDispatch(request, response);
        } finally {

        }
    }

    /**
     * 处理对处理程序的实际调度。
     * 将通过按顺序应用servlet的HandlerMappings来获取处理程序。
     * HandlerAdapter将通过查询servlet安装的HandlerAdapter来获得，以找到支持处理程序类的第一个。
     * 所有HTTP方法都由此方法处理。 由HandlerAdapters或处理程序自行决定哪些方法可以接受。
     *
     * @param request
     * @param response
     * @throws Exception
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 处理请求
        HttpServletRequest processedRequest = request;
        // 代理链
        HandlerExecutionChain mappedHandler = null;
        // 是否为multipart请求
        boolean multipartRequestParsed = false;
        // 数据模型
        ModelAndView mv = null;
        Exception dispatchException = null;
        try {
            // 确认当前处理请求
            // 根据配置或注解找到最终要指定的Handler，返回Handler
            mappedHandler = getHandler(request);
            if (mappedHandler == null) {
                return;
            }
            // 请求HandlerAdapter执行Handler，根据Handler规则执行不同类型的HandlerAdapter
            HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
            String method = request.getMethod();

            boolean isGet = "GET".equals(method);
            if (isGet || "HEAD".equals(method)) {
                long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                // 判断如果没有可用的处理器，则直接返回
            }
            if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                return;
            }
            // Handler执行完毕，返回一个ModelAndView
            mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
            // 视图名称翻译
            applyDefaultViewName(processedRequest, mv);
            // 应用注册拦截器的postHandle方法。
            mappedHandler.applyPostHandle(processedRequest, response, mv);
        } catch (Exception e) {

        } finally {
            // 而不是postHandle和afterCompletion

            //清理文件请求使用的任何资源。
        }
    }

    private void applyDefaultViewName(HttpServletRequest processedRequest, ModelAndView mv) throws Exception {
        if (mv != null && !mv.hasView()) {
            getDefaultViewName(processedRequest);
        }
    }

    protected String getDefaultViewName(HttpServletRequest request) throws Exception {
        return (this.viewNameTranslator != null ? this.viewNameTranslator.getViewName(request) : null);
    }

    /**
     * 返回此请求的HandlerExecutionChain。按顺序处理所有处理程序映射。
     *
     * @param request 请求当前HTTP请求@return HandlerExecutionChain
     * @return
     * @throws Exception
     */
    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (this.handlerMappings != null) {
            for (HandlerMapping hm : handlerMappings) {
                HandlerExecutionChain chain = hm.getHandler(request);
                if (chain != null) {
                    return chain;
                }
            }
        }
        return null;
    }

    /**
     * 返回此处理程序对象的HandlerAdapter。
     *
     * @param handler 处理程序对象以找到适配器
     * @return
     */
    protected HandlerAdapter getHandlerAdapter(Object handler) {
        if (this.handlerAdapters != null) {
            for (HandlerAdapter ha : handlerAdapters) {
                if (ha.support(handler)) {
                    return ha;
                }
            }
        }
        throw new RuntimeException();
    }

    protected void initStrategies(ApplicationContext context) {
//        initMultipartResolver(context);
//        initLocaleResolver(context);
//        initThemeResolver(context);
        // 初始化处理器映射器
        initHandlerMappings(context);
        // 初始化处理器适配器
        initHandlerAdapters(context);
//        initHandlerExceptionResolvers(context);
        // 初始化请求视图名称转化器
        // 将当前请求转化为视图名称
        initRequestToViewNameTranslator(context);
        // 初始化视图解析器
        initViewResolvers(context);
//        initFlashMapManager(context);
    }

    @Override
    protected void onRefresh(ApplicationContext context) {
        initStrategies(context);
    }

    private void initHandlerMappings(ApplicationContext context) {
        // 初始化处理器映射器
        this.handlerMappings = null;
        if (this.detectAllHandlerMappings) {
            Map<String, HandlerMapping> matchingBeans = new HashMap<>();
            // 如果不为空
            if (!matchingBeans.isEmpty()) {
                // 初始化
                this.handlerMappings = new ArrayList<>();
                // 进行排序
            }
        } else {
            HandlerMapping hm = context.getBean(HANDLER_MAPPING_BEAN_NAME, HandlerMapping.class);
            this.handlerMappings = Collections.singletonList(hm);
        }

        // 如果找不到自定义处理器映射器，则使用默认的处理器映射器
        if (this.handlerMappings == null) {
            this.handlerMappings = getDefaultStrategies(context, HandlerMapping.class);
        }

    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {
        try {
            this.viewNameTranslator = context.getBean(REQUEST_TO_VIEW_NAME_TRANSLATOR_BEAN_NAME, DefaultRequestToViewNameTranslator.class);
            if (viewNameTranslator == null) {
                RequestToViewNameTranslator a = new DefaultRequestToViewNameTranslator();
                this.viewNameTranslator = getDefaultStrategy(context, RequestToViewNameTranslator.class);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void initHandlerAdapters(ApplicationContext context) {
        // 初始化处理器映射器
        this.handlerAdapters = null;
        if (this.detectAllHandlerAdapters) {
            Map<String, HandlerAdapter> matchingBeans = new HashMap<>();
            // 如果不为空
            if (!matchingBeans.isEmpty()) {
                // 初始化
                this.handlerAdapters = new ArrayList<>();
                // 进行排序
            }
        } else {
            HandlerAdapter ha = context.getBean(HANDLER_ADAPTER_BEAN_NAME, HandlerAdapter.class);
            this.handlerAdapters = Collections.singletonList(ha);
        }

        // 如果找不到自定义处理器映射器，则使用默认的处理器映射器
        if (this.handlerAdapters == null) {
            this.handlerAdapters = getDefaultStrategies(context, HandlerAdapter.class);
        }
    }

    private void initViewResolvers(ApplicationContext context) {
        this.viewResolvers = null;
        if (this.detectAllViewResolvers) {
            Map<String, ViewResolver> matchingBeans = new HashMap<>();
            // 如果不为空
            if (!matchingBeans.isEmpty()) {
                // 初始化
                this.handlerMappings = new ArrayList<>();
                // 进行排序
            }
        } else {
            ViewResolver hm = context.getBean(VIEW_RESOLVER_BEAN_NAME, ViewResolver.class);
            this.viewResolvers = Collections.singletonList(hm);
//            this.handlerMappings.sort(this.handlerMappings);
        }

        // 如果找不到自定义处理器映射器，则使用默认的处理器映射器
        if (this.viewResolvers == null) {
            this.viewResolvers = getDefaultStrategies(context, ViewResolver.class);
        }
    }

    private void initMultipartResolver(ApplicationContext context) {
    }

    protected <T> T getDefaultStrategy(ApplicationContext context, Class<T> strategyInterface) {
        List<T> strategies = getDefaultStrategies(context, strategyInterface);
        if (strategies.size() == 1) {
            T t = strategies.get(0);
            return t;
        }
        return null;
    }

    @SuppressWarnings("uncheck")
    protected <T> List<T> getDefaultStrategies(ApplicationContext context, Class<T> strategyInterface) {
        String name = strategyInterface.getName();
        int i = name.lastIndexOf(".") + 1;
        // className
        String substring = name.substring(i);
        String value = defaultStrategies.getProperty(substring);
        List<T> strategies = new ArrayList<>();
        if (1 == 2) {

        } else {
            Class loadClass = ClassUtils.loadClass(value, false);
            strategies.add((T) ReflectionUtils.newInstance(loadClass));
        }
        return strategies;
    }


    public void setDispatchOptionsRequest(boolean dispatchOptionsRequest) {
        this.dispatchOptionsRequest = dispatchOptionsRequest;
    }

    public void setDetectAllHandlerMappings(boolean detectAllHandlerMappings) {
        this.detectAllHandlerMappings = detectAllHandlerMappings;
    }

    public void setDetectAllHandlerAdapters(boolean detectAllHandlerAdapters) {
        this.detectAllHandlerAdapters = detectAllHandlerAdapters;
    }

    public void setDetectAllViewResolvers(boolean detectAllViewResolvers) {
        this.detectAllViewResolvers = detectAllViewResolvers;
    }
}
