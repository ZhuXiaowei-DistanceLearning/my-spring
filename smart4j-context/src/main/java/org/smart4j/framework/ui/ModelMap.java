package org.smart4j.framework.ui;

import org.smart4j.framework.core.Conventions;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * 实现{ java.util.Map}，以便在构建用于UI工具的模型数据时使用。
 * 支持链式调用和模型属性名称的生成。此类充当Servlet MVC的通用模型持有者，但不依赖于它。
 * 查看{ Model}界面以获取接口变体。
 */
public class ModelMap extends LinkedHashMap<String, Object> {
    public ModelMap() {

    }

    public ModelMap(String attributeName, Object attributeValue) {

    }

    public ModelMap(Object attributeValue) {

    }

    public ModelMap addAttribute(String attributeName, Object attributeValue) {
        put(attributeName, attributeValue);
        return this;
    }

    public ModelMap addAttribute(Object attributeValue) {
        if (attributeValue instanceof Collection && ((Collection<?>) attributeValue).isEmpty()) {
            return this;
        }
        return addAttribute(Conventions.getVariableName(attributeValue), attributeValue);
    }

    public boolean containsAttribute(String attributeName) {
        return containsKey(attributeName);
    }
}
