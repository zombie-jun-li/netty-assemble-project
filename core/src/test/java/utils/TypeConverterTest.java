package utils;

import framework.utils.Assert;
import framework.utils.TypeConverter;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * Created by jun.
 */
public class TypeConverterTest {

    @Test
    public void testConvert() {
        Assert.equals("88", TypeConverter.convert("88", String.class));

        Assert.equals(88, TypeConverter.convert("88", Integer.class));

        short shortValue = 88;
        Assert.equals(shortValue, TypeConverter.convert("88", Short.class));

        Assert.equals(88.8f, TypeConverter.convert("88.8", Float.class));

        Assert.equals(88.88d, TypeConverter.convert("88.88", Double.class));

        Assert.equals((long)88.88, TypeConverter.convert("88.88", Long.class));

        Assert.equals(BigDecimal.valueOf(88.88), TypeConverter.convert("88.88", BigDecimal.class));

        Assert.equals(BigInteger.valueOf(88888), TypeConverter.convert("88888", BigInteger.class));

        Assert.equals(Byte.valueOf("88"), TypeConverter.convert("88", Byte.class));

        Assert.equals(Character.valueOf('8'), TypeConverter.convert("88", Character.class));

        Assert.equals(true, TypeConverter.convert("true", Boolean.class));

        Assert.equals(ConvertEnum.HELLO, TypeConverter.convert("HELLO", ConvertEnum.class));

        Map<String, Object> jsonObject = TypeConverter.convert("{\"name\":\"jun\", \"age\":18}", Map.class);
        Assert.equals("jun", jsonObject.get("name"));
        Assert.equals(18, jsonObject.get("age"));
    }

    private enum ConvertEnum {
        HELLO
    }

}
