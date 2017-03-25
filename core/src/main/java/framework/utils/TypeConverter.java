package framework.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by jun.
 */
public abstract class TypeConverter {
    public static <T> T convert(String sourceText, Class<T> targetType) {
        Assert.notNull(sourceText, "sourceText should not be null.");
        Assert.notPrimitive(targetType, "targetType can not be a primitive type.");
        return (T) doConvert(sourceText, targetType);
    }

    private static Object doConvert(String sourceText, Class<?> targetType) {
        if (String.class.equals(targetType)) {
            return sourceText;
        }
        if (Number.class.equals(targetType)) {
            return convertNumber(sourceText, targetType);
        }
        if (Byte.class.equals(targetType)) {
            return Byte.valueOf(sourceText);
        }
        if (Character.class.equals(targetType)) {
            return Character.valueOf(sourceText.charAt(0));
        }
        if (Boolean.class.equals(targetType)) {
            return Boolean.valueOf(sourceText);
        }
        if (Enum.class.isAssignableFrom(targetType)) {
            return Enum.valueOf((Class<Enum>) targetType, sourceText);
        }
        return Json.fromJson(sourceText, targetType);
    }

    private static Object convertNumber(String sourceText, Class<?> targetType) {
        if (Integer.class.equals(targetType)) {
            return Integer.parseInt(sourceText);
        }
        if (Short.class.equals(targetType)) {
            return Short.parseShort(sourceText);
        }
        if (Float.class.equals(targetType)) {
            return Float.parseFloat(sourceText);
        }
        if (Double.class.equals(targetType)) {
            return Double.parseDouble(sourceText);
        }
        if (Long.class.equals(targetType)) {
            return new BigDecimal(sourceText);
        }
        if (BigDecimal.class.equals(targetType)) {
            return new BigDecimal(sourceText);
        }
        if (BigInteger.class.equals(targetType)) {
            return new BigInteger(sourceText);
        }
        return null;
    }
}
