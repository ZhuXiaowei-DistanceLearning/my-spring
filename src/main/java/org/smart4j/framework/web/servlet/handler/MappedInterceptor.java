package org.smart4j.framework.web.servlet.handler;

import com.sun.istack.internal.Nullable;
import org.aopalliance.interceptor.MethodInterceptor;
import org.aopalliance.interceptor.MethodInvocation;
import org.smart4j.framework.web.servlet.HandlerInterceptor;

import java.nio.file.PathMatcher;

/**
 *   *包含并委托调用{ HandlerInterceptor}
 *   *包括（并且可选地排除）拦截器应该应用的路径模式。
 *   *还提供匹配逻辑来测试拦截器是否适用于给定的请求路径。
 *   * <p> MappedInterceptor可以直接在任何地方注册
 *   * { org.springframework.web.servlet.handler.AbstractHandlerMethodMapping}。
 *   *此外，{ MappedInterceptor}类型的bean会被自动检测到
 *   * { AbstractHandlerMethodMapping}（包括祖先的ApplicationContext的）
 *   *实际上意味着拦截器使用所有处理程序映射“全局”注册。
 *  
 */
public final class MappedInterceptor implements HandlerInterceptor {
    private final String[] includePatterns;

    private final String[] excludePatterns;

    private final HandlerInterceptor interceptor;

    private PathMatcher pathMatcher;

    public MappedInterceptor(@Nullable String[] includePatterns, HandlerInterceptor interceptor) {
        this(includePatterns, null, interceptor);
    }

    public MappedInterceptor(@Nullable String[] includePatterns, @Nullable String[] excludePatterns,
                             HandlerInterceptor interceptor) {

        this.includePatterns = includePatterns;
        this.excludePatterns = excludePatterns;
        this.interceptor = interceptor;
    }

    public String[] getIncludePatterns() {
        return includePatterns;
    }

    public String[] getExcludePatterns() {
        return excludePatterns;
    }

    public HandlerInterceptor getInterceptor() {
        return interceptor;
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }
}
