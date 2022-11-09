package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

public abstract class AJsonRPC20 {
   @Expose
   @SerializedName("jsonrpc")
   private final String jsonrpc = "2.0";
   @Expose
   @SerializedName("id")
   private String id = Long.toString(new Date().getTime());
   @Expose
   @SerializedName("method")
   protected String method;

   public AJsonRPC20() {
   }

   public AJsonRPC20(String id) {
      this.id = id;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setMethod(String method) {
      this.method = method;
   }

   public String getMethod() {
      return this.method;
   }
}
