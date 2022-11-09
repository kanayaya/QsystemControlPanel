package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.common.model.QCustomer;

public class RpcInviteCustomer extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private QCustomer result;

   public RpcInviteCustomer() {
   }

   public void setResult(QCustomer result) {
      this.result = result;
   }

   public QCustomer getResult() {
      return this.result;
   }

   public RpcInviteCustomer(QCustomer result) {
      this.result = result;
   }
}
