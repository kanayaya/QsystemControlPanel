package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.server.model.QAuthorizationCustomer;

public class RpcGetAuthorizCustomer extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private QAuthorizationCustomer result;

   public RpcGetAuthorizCustomer() {
   }

   public void setResult(QAuthorizationCustomer result) {
      this.result = result;
   }

   public QAuthorizationCustomer getResult() {
      return this.result;
   }

   public RpcGetAuthorizCustomer(QAuthorizationCustomer result) {
      this.result = result;
   }
}
