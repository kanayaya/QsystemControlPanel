package ru.apertum.qsystem.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.awt.Color;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.SoftReferenceObjectPool;
import ru.apertum.qsystem.common.exceptions.ServerException;

public class GsonPool extends SoftReferenceObjectPool {
   private static GsonPool instance = null;

   private GsonPool(BasePoolableObjectFactory basePoolableObjectFactory) {
      super(basePoolableObjectFactory);
   }

   public static GsonPool getInstance() {
      if (instance == null) {
         instance = new GsonPool(new BasePoolableObjectFactory() {
            public Object makeObject() throws Exception {
               GsonBuilder gsonb = new GsonBuilder();
               GsonPool.DateSerializer ds = new GsonPool.DateSerializer();
               GsonPool.ColorSerializer cs = new GsonPool.ColorSerializer();
               gsonb.registerTypeHierarchyAdapter(Date.class, ds);
               gsonb.registerTypeHierarchyAdapter(Color.class, cs);
               return gsonb.excludeFieldsWithoutExposeAnnotation().create();
            }
         });
      }

      return instance;
   }

   public Gson borrowGson() {
      try {
         return (Gson)instance.borrowObject();
      } catch (Exception var2) {
         throw new ServerException("Проблемы с gson pool. ", var2);
      }
   }

   public void returnGson(Gson gson) {
      try {
         instance.returnObject(gson);
      } catch (Exception var3) {
         throw new ServerException("Проблемы с  gson pool. ", var3);
      }
   }

   private static class ColorSerializer implements JsonDeserializer<Color>, JsonSerializer<Color> {
      private ColorSerializer() {
      }

      public JsonElement serialize(Color arg0, Type arg1, JsonSerializationContext arg2) {
         return new JsonPrimitive(arg0.getRGB());
      }

      public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
         return new Color(json.getAsInt());
      }
   }

   private static class DateSerializer implements JsonDeserializer<Date>, JsonSerializer<Date> {
      private DateSerializer() {
      }

      public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
         return new JsonPrimitive(Uses.FORMAT_DD_MM_YYYY_TIME.format(arg0));
      }

      public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
         try {
            return Uses.FORMAT_DD_MM_YYYY_TIME.parse(json.getAsString());
         } catch (ParseException var5) {
            throw new RuntimeException("Not pars JSON by proxy!", var5);
         }
      }
   }
}
