package org.smart4j.framework.beans.factory;

/**
 * 标记超级接口，指示bean有资格通过回调样式方法由Spring容器通知特定框架对象。
 * 实际方法签名由各个子接口确定，但通常应该只包含一个接受单个参数的void返回方法。
 * 请注意，仅实现{ Aware}不提供默认功能。
 * 相反，必须明确地进行处理，例如在{ org.springframework.beans.factory.config.BeanPostProcessor BeanPostProcessor}中。
 * 有关处理{ * Aware}接口回调的示例，请参阅{ org.springframework.context.support.ApplicationContextAwareProcessor}和{ org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory}。
 */
public interface Aware {
}
