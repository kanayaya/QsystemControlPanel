package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.server.model.response.QRespItem;

public class RpcGetRespTree extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private QRespItem result;

   public RpcGetRespTree() {
   }

   public void setResult(QRespItem result) {
      this.result = result;
   }

   public QRespItem getResult() {
      return this.result;
   }

   public RpcGetRespTree(QRespItem result) {
      this.result = result;
   }
}
