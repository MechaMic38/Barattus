package com.mechamic38.barattus.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListUtils {

    public static <T> List<T> copy(List<T> list) {
        return list.stream().collect(Collectors.toList());
    }

    public static <T> Set<T> copy(Set<T> list) {
        return list.stream().collect(Collectors.toSet());
    }
}
