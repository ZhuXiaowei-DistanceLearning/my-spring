package org.smart4j.framework.context;

import org.smart4j.framework.beans.factory.Aware;

/**
 * 希望被通知其运行的{ ApplicationContext}的任何对象实现的接口。
 * 例如，当对象需要访问一组协作bean时，实现此接口是有意义的。请注意，通过bean引用进行配置比仅用于bean查找目的更好地实现此接口。
 * 如果对象需要访问文件资源，即想要调用{ getResource}，想要发布应用程序事件，也可以实现此接口，或者需要访问MessageSource。
 * 但是，最好在这种特定场景中实现更具体的{ ResourceLoaderAware}，{ link ApplicationEventPublisherAware}或{ MessageSourceAware}接口。
 * 请注意，文件资源依赖关系也可以作为{org.springframework.core.io.Resource}，通过字符串填充由bean工厂自动进行类型转换。
 * 这样就不需要为了访问特定的文件资源而实现任何回调接口。
 * { link org.springframework.context.support.ApplicationObjectSupport}是应用程序对象的便利基类，实现了这个接口。
 * 所有bean生命周期方法，请参阅{ org.springframework.beans.factory.BeanFactory BeanFactory javadocs}
 */
public interface ApplicationContextAware extends Aware {
}
