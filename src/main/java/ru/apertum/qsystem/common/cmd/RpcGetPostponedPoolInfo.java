package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.LinkedList;
import ru.apertum.qsystem.common.model.QCustomer;

public class RpcGetPostponedPoolInfo extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private LinkedList<QCustomer> result;

   public RpcGetPostponedPoolInfo() {
   }

   public RpcGetPostponedPoolInfo(LinkedList<QCustomer> result) {
      this.result = result;
   }

   public void setResult(LinkedList<QCustomer> result) {
      this.result = result;
   }

   public LinkedList<QCustomer> getResult() {
      return this.result;
   }
}
