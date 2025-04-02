package org.exp.usekeys;

public interface RepoKeys {
    default void save (Object obj){};
    default void save (Object obj1, Object obj2){};
    Object findById(Object obj);
}
