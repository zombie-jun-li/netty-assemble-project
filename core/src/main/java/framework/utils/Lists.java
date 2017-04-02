package framework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by li
 */
public abstract class Lists {

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> List<T> newArrayList(T object) {
        List<T> arrayList = new ArrayList<>(1);
        arrayList.add(object);
        return arrayList;
    }

    public static <T> List<T> newArrayList(T ... objects) {
        List<T> arrayList = new ArrayList<>(objects.length);
        Collections.addAll(arrayList, objects);
        return arrayList;
    }
}
