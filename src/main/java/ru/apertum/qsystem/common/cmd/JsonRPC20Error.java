package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

public class JsonRPC20Error extends AJsonRPC20 {
   @Expose
   @SerializedName("error")
   private JsonRPC20Error.ErrorRPC error;

   public JsonRPC20Error() {
   }

   public JsonRPC20Error(Integer code, Object data) {
      this.error = new JsonRPC20Error.ErrorRPC(code, data);
   }

   public void setError(JsonRPC20Error.ErrorRPC error) {
      this.error = error;
   }

   public JsonRPC20Error.ErrorRPC getError() {
      return this.error;
   }

   public static class ErrorRPC {
      public static final Integer UNKNOWN_ERROR = -1;
      public static final Integer RESPONCE_NOT_SAVE = 2;
      public static final Integer POSTPONED_NOT_FOUND = 3;
      public static final Integer ADVANCED_NOT_FOUND = 4;
      public static final Integer REQUIRED_CUSTOMER_NOT_FOUND = 5;
      @Expose
      @SerializedName("code")
      private Integer code;
      @Expose
      @SerializedName("message")
      private String message;
      @Expose
      @SerializedName("data")
      private Object data;

      public ErrorRPC() {
      }

      public ErrorRPC(Integer code, Object data) {
         this.code = code;
         this.message = JsonRPC20Error.ErrorRPC.ErrorCode.getMessage(code);
         this.data = data;
      }

      public Integer getCode() {
         return this.code;
      }

      public Object getData() {
         return this.data;
      }

      public String getMessage() {
         return this.message;
      }

      public static final class ErrorCode {
         private static final HashMap<Integer, String> MESSAGE = new HashMap<>();

         public static String getMessage(Integer code) {
            return MESSAGE.get(code);
         }

         static {
            MESSAGE.put(JsonRPC20Error.ErrorRPC.UNKNOWN_ERROR, "Unknown error.");
            MESSAGE.put(JsonRPC20Error.ErrorRPC.RESPONCE_NOT_SAVE, "Не сохранили отзыв в базе.");
            MESSAGE.put(JsonRPC20Error.ErrorRPC.POSTPONED_NOT_FOUND, "Отложенный пользователь не найден по его ID.");
            MESSAGE.put(JsonRPC20Error.ErrorRPC.ADVANCED_NOT_FOUND, "Не верный номер предварительной записи.");
            MESSAGE.put(JsonRPC20Error.ErrorRPC.REQUIRED_CUSTOMER_NOT_FOUND, "Customer not found but required.");
         }
      }
   }
}
