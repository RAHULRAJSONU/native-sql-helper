package com.rr.opensource.nativesqlhelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Helper Library for native SQL
 * @author Rahul raj mailto:rajrahul9939@gmail.com
 * @since 1.0-SNAPSHOT (May 31, 2020)
 */
public class NativeSQLHelper {

  /**
   * API to build string for where clause (IN)
   *
   * @param attributes List<String> eg; ["param1","param2","param3"]
   * @return String eg; ('param1','param2','param3')
   */
  public static StringBuilder buildStringForInQuery(List<String> attributes) {
    StringBuilder sb = new StringBuilder("(");
    sb.append(attributes.stream().map(str->"'"+str+"'").collect(Collectors.joining(",")));
    sb.append(")");
    return sb;
  }

  /**
   * API to map native sql result list to list of pojo
   *
   * @param resultList List of object array
   * @param type type of pojo class
   * @param columnInOrder array of column used in select query in the same order
   *                      eg; "select col1, col2, col3 from table 1"
   *                      then: {"col1","col2","col3"}
   * @param <T>
   * @return List<Pojo>
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public static <T> List<T> mapToModel(
      List<Object[]> resultList,
      Class<T> type,
      String ...columnInOrder)throws IllegalAccessException, InstantiationException{
    List<T> list = new ArrayList<T>();
    for (Object[] result : resultList) {
      T t = type.newInstance();
      loadResultSetIntoObject(result, t, arrayToMap(columnInOrder));
      list.add(t);
    }
    return list;
  }

  private static void loadResultSetIntoObject(
      Object[] results,
      Object object,
      Map<String, Integer> columnMap)
      throws IllegalArgumentException, IllegalAccessException {
    Class<?> zclass = object.getClass();
    for (Field field : zclass.getDeclaredFields()) {
      field.setAccessible(true);
      String column = field.getName().replace("get","");
      Object value = results[columnMap.get(column)];
      Class<?> type = field.getType();
      if (isPrimitive(type)) {//check primitive type(Point 5)
        Class<?> boxed = boxPrimitiveClass(type);//box if primitive(Point 6)
        value = boxed.cast(value);
      }
      field.set(object, value);
    }
  }

  private static boolean isPrimitive(Class<?> type) {
    return (type == int.class || type == long.class || type == double.class || type == float.class
        || type == boolean.class || type == byte.class || type == char.class
        || type == short.class);
  }

  private static Class<?> boxPrimitiveClass(Class<?> type) {
    if (type == int.class) {
      return Integer.class;
    } else if (type == long.class) {
      return Long.class;
    } else if (type == double.class) {
      return Double.class;
    } else if (type == float.class) {
      return Float.class;
    } else if (type == boolean.class) {
      return Boolean.class;
    } else if (type == byte.class) {
      return Byte.class;
    } else if (type == char.class) {
      return Character.class;
    } else if (type == short.class) {
      return Short.class;
    } else {
      String string = "class '" + type.getName() + "' is not a primitive";
      throw new IllegalArgumentException(string);
    }
  }

  private static Map<String, Integer> arrayToMap(String[] arr){
    Map<String, Integer> result =
        IntStream.range(0, arr.length)
            .boxed()
            .collect(Collectors.toMap(i -> arr[i], i -> i, Integer::sum));
    return result;
  }
}

