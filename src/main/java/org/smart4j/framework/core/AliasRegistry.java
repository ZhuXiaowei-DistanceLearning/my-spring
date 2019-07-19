package org.smart4j.framework.core;

/**
 * 用于管理别名
 */
public interface AliasRegistry {

    void registerAlias(String var1, String var2);

    void removeAlias(String var1);

    boolean isAlias(String var1);

    String[] getAlias(String var1);
}
