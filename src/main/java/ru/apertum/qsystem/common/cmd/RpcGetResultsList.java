package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.LinkedList;
import ru.apertum.qsystem.server.model.results.QResult;

public class RpcGetResultsList extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private LinkedList<QResult> result;

   public RpcGetResultsList() {
   }

   public void setResult(LinkedList<QResult> result) {
      this.result = result;
   }

   public LinkedList<QResult> getResult() {
      return this.result;
   }

   public RpcGetResultsList(LinkedList<QResult> result) {
      this.result = result;
   }
}
