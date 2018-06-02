package ru.rshalimov.reactnative.common;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
   private final Deque <Map <String, Object>> deque = new ArrayDeque <> ();
   
   {
      deque.addLast(new HashMap <String, Object> ());
   }
   
   public MapBuilder put(String key, Object value) {
      deque.getLast().put(key, value);
      
      return this;
   }
   
   public MapBuilder put(String string) {
      return put(string, string);
   }
   
   public MapBuilder push(String key) {
      final Map <String, Object> value = new HashMap <> ();
      
      put(key, value);
      
      deque.addLast(value);
      
      return this;
   }
   
   public MapBuilder pop() {
      deque.removeLast();
      
      return this;
   }
   
   public Map <String, Object> build() {
      return deque.getFirst();
   }
}
