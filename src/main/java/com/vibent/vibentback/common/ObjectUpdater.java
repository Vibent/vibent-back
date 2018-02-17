package com.vibent.vibentback.common;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class ObjectUpdater {

    private static final String[] EXCLUDE_FIELDS = {"ref", "id", "deleted"};

    /**
     * Copies all the non null properties, excluding specific fields ("ref" and
     * "id" for example) from the source object to the target one
     */
    public static void updateProperties(Object src, Object target) {
        // BeanUtils.copyProperties(Object source, Object target, String... ignoreProperties)
        String[] nullPropertyNames = getNullPropertyNames(src);
        String[] toIgnore = Stream.concat(
                Arrays.stream(nullPropertyNames), Arrays.stream(EXCLUDE_FIELDS))
                .toArray(String[]::new);
        BeanUtils.copyProperties(src, target, toIgnore);
    }

    /**
     * Copies all the non null properties, including normally excluded fields ("ref" and
     * "id" for example) from the source object to the target one
     */
    public static void updateAllProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
