package io.github.rahulrajsonu.nativesqlhelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AutoTypeCast {

  private static final Map<Class, Function<String, ?>> parsers = new HashMap<>();

  public static void main(String[] args) {
    String val = "10";
    BigInteger bi = AutoTypeCast.parse(BigInteger.class,val);
    BigDecimal bd = AutoTypeCast.parse(BigDecimal.class, val);
    System.out.println(bi.add(BigInteger.valueOf(15)).toString());
    System.out.println(bd.multiply(BigDecimal.valueOf(15)).toString());
  }

  static {
    parsers.put(Long.class, Long::parseLong);
    parsers.put(Integer.class, Integer::parseInt);
    parsers.put(String.class, String::toString);
    parsers.put(Double.class, Double::parseDouble);
    parsers.put(Float.class, Float::parseFloat);
    parsers.put(BigInteger.class, BigInteger::new);
    parsers.put(BigDecimal.class, BigDecimal::new);
  }

  public static  <T> T parse(Class<T> klass, String value) {
    if(null != value) {
      return (T) parsers.get(klass).apply(value);
    }
    return null;
  }

}
