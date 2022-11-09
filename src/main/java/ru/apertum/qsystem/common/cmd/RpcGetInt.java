package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RpcGetInt extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private int result;

   public RpcGetInt() {
   }

   public void setResult(int result) {
      this.result = result;
   }

   public int getResult() {
      return this.result;
   }

   public RpcGetInt(int result) {
      this.result = result;
   }
}
