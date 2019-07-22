package org.smart4j.framework.aop.aspect;

public interface AspectJPrecedenceInformation {
    /**
     * Return the name of the aspect (bean) in which the advice was declared.
     */
    String getAspectName();

    /**
     * Return the declaration order of the advice member within the aspect.
     */
    int getDeclarationOrder();

    /**
     * Return whether this is a before advice.
     */
    boolean isBeforeAdvice();

    /**
     * Return whether this is an after advice.
     */
    boolean isAfterAdvice();
}
