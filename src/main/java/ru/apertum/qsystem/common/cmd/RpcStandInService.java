package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.common.model.QCustomer;

public class RpcStandInService extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private QCustomer result;

   public RpcStandInService() {
   }

   public void setResult(QCustomer result) {
      this.result = result;
   }

   public QCustomer getResult() {
      return this.result;
   }

   public RpcStandInService(QCustomer result) {
      this.result = result;
   }

   public RpcStandInService(QCustomer result, String method) {
      this.result = result;
      this.method = method;
   }
}
