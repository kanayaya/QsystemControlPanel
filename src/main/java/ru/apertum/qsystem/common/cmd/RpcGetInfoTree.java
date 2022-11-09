package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.server.model.infosystem.QInfoItem;

public class RpcGetInfoTree extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private QInfoItem result;

   public RpcGetInfoTree() {
   }

   public void setResult(QInfoItem result) {
      this.result = result;
   }

   public QInfoItem getResult() {
      return this.result;
   }

   public RpcGetInfoTree(QInfoItem result) {
      this.result = result;
   }
}
