package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.concurrent.LinkedBlockingDeque;
import ru.apertum.qsystem.common.model.QCustomer;

public class RpcGetServiceState extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private RpcGetServiceState.ServiceState result;

   public RpcGetServiceState() {
   }

   public RpcGetServiceState(int code, String message) {
      this.result = new RpcGetServiceState.ServiceState(code, message);
   }

   public RpcGetServiceState(LinkedBlockingDeque<QCustomer> line) {
      this.result = new RpcGetServiceState.ServiceState(line);
   }

   public RpcGetServiceState.ServiceState getResult() {
      return this.result;
   }

   public void setResult(RpcGetServiceState.ServiceState result) {
      this.result = result;
   }

   public static class ServiceState {
      @Expose
      @SerializedName("code")
      private int code;
      @Expose
      @SerializedName("message")
      private String message;
      @Expose
      @SerializedName("clients")
      private LinkedBlockingDeque<QCustomer> clients;

      public ServiceState() {
      }

      public ServiceState(int code, String message) {
         this.code = code;
         this.message = message;
      }

      public ServiceState(LinkedBlockingDeque<QCustomer> line) {
         this.code = 1;
         this.message = null;
         this.clients = line;
      }

      public int getCode() {
         return this.code;
      }

      public void setCode(int code) {
         this.code = code;
      }

      public String getMessage() {
         return this.message;
      }

      public void setMessage(String message) {
         this.message = message;
      }

      public LinkedBlockingDeque<QCustomer> getClients() {
         return this.clients;
      }

      public void setClients(LinkedBlockingDeque<QCustomer> clients) {
         this.clients = clients;
      }
   }
}
