package framework.utils;

/**
 * Created by jun.
 */
public abstract class Assert {
    public static void notNull(Object object, String message) {
        if (null == object) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notPrimitive(Class<?> clazz, String message) {
        if (clazz.isPrimitive()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void equals(Object expect, Object actual) {
        if (!expect.equals(actual)) {
            throw new AssertionError();
        }
    }
}
