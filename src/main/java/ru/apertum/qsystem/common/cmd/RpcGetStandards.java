package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.server.model.QStandards;

public class RpcGetStandards extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private QStandards result;

   public RpcGetStandards() {
   }

   public void setResult(QStandards result) {
      this.result = result;
   }

   public QStandards getResult() {
      return this.result;
   }

   public RpcGetStandards(QStandards result) {
      this.result = result;
   }
}
