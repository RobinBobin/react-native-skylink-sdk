package ru.rshalimov.reactnative.common;

import com.facebook.react.bridge.ReadableMap;

import java.lang.reflect.Method;

public class ObjectPropertySetter <T> {
   private final T objectToSetProperties;
   private final ReadableMap readableMap;
   
   public ObjectPropertySetter(T objectToSetProperties, ReadableMap readableMap) {
      this.objectToSetProperties = objectToSetProperties;
      this.readableMap = readableMap;
   }
   
   public ObjectPropertySetter set(String propertyName, Class <?> valueClass)
      throws ReflectiveOperationException
   {
      return set(propertyName, valueClass, true);
   }
   
   public ObjectPropertySetter set(
      String propertyName,
      Class <?> valueClass,
      boolean mapToPrimitive) throws ReflectiveOperationException
   {
      final Object value = Utils.safeGet(readableMap, propertyName, valueClass);
      
      if (value != null) {
         invoke(propertyName, value, mapToPrimitive);
      }
      
      return this;
   }
   
   public <EnumType extends Enum <EnumType>> ObjectPropertySetter
      setEnum(String propertyName, Class <EnumType> enumClass)
         throws ReflectiveOperationException
   {
      final String name = Utils.safeGet(readableMap, propertyName, String.class);
      
      if (name != null) {
         invoke(propertyName, Enum.valueOf(enumClass, name), false);
      }
      
      return this;
   }
   
   public T getObject() {
      return objectToSetProperties;
   }
   
   private void invoke(String propertyName, Object value, boolean mapToPrimitive)
      throws ReflectiveOperationException
   {
      Class <?> clazz = value.getClass();
      
      if (mapToPrimitive) {
         clazz = clazz == Boolean.class ? Boolean.TYPE
            : clazz == Double.class ? Double.TYPE
            : clazz == Integer.class ? Integer.TYPE
            : clazz;
      }
      
      objectToSetProperties.getClass().getMethod(String.format("set%s%s",
         propertyName.substring(0, 1).toUpperCase(), propertyName.substring(1)),
            clazz).invoke(objectToSetProperties, value);
   }
}
