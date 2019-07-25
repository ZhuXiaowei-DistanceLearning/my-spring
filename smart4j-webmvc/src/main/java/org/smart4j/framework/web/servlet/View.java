package org.smart4j.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * MVC查看Web交互。
 * 实现负责呈现内容并公开模型。
 * 单个视图公开了多个模型属性。
 * 查看实现可能有很大不同。 一个明显的实现是基于JSP的。
 * 其他实现可能是基于XSLT的，或使用HTML生成库。
 * 此接口旨在避免限制可能的实现范围。视图应该是bean。
 * 它们很可能被ViewResolver实例化为bean。 由于此接口是无状态的，因此视图实现应该是线程安全的。
 */
public interface View {
    String RESPONSE_STATUS_ATTRIBUTE = View.class.getName() + ".responseStatus";
    String PATH_VARIABLES = View.class.getName() + ".pathVariables";
    String SELECTED_CONTENT_TYPE = View.class.getName() + ".selectedContentType";

    /**
     * 如果已预定，则返回视图的内容类型。
     * 可用于预先检查视图的内容类型，即在实际渲染尝试之前。
     * @return
     */
    default String getContentType() {
        return null;
    }

    /**
     *在给定指定模型的情况下渲染视图。
     * 第一步是准备请求：在JSP情况下，这将意味着将模型对象设置为请求属性。
     * 第二步是视图的实际呈现，例如通过RequestDispatcher包含JSP。
     * @param model 使用名称将字符串映射为键并将相应的模型对象作为值映射（对于空模型，映射也可以是{ null}）
     * @param request
     * @param response
     * @throws Exception
     */
    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
