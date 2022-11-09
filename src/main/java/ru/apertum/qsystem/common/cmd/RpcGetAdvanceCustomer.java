package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.server.model.QAdvanceCustomer;

public class RpcGetAdvanceCustomer extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private QAdvanceCustomer result;

   public RpcGetAdvanceCustomer() {
   }

   public void setResult(QAdvanceCustomer result) {
      this.result = result;
   }

   public QAdvanceCustomer getResult() {
      return this.result;
   }

   public RpcGetAdvanceCustomer(QAdvanceCustomer result) {
      this.result = result;
   }
}
