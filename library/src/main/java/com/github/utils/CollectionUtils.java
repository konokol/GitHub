package com.github.utils;

import java.util.Collection;
import java.util.List;

/**
 * collection tools
 *
 * @author  Ivan on 2018-11-29 00:11.
 * @version v0.1
 * @since   v0.1.0
 */
public final class CollectionUtils {

    /**
     * whether a collection is empty
     *
     * @param collection collection
     * @return 0 if the collection is empty
     */
    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * get the size of a collection
     * @param collection collection
     * @return 0 if collection is empty
     */
    public static <E> int size(Collection<E> collection) {
        return isEmpty(collection) ? 0 : collection.size();
    }

    /**
     * get the last element of a list
     * @param collection collection
     * @return last element
     */
    public static <E> E getLast(List<E> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        } else {
            return collection.get(collection.size() -1);
        }
    }

    /**
     * get the first element of a list
     * @param collection collection
     * @return the first element
     */
    public static <E> E getFirst(List<E> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        } else {
            return collection.get(0);
        }
    }

}
