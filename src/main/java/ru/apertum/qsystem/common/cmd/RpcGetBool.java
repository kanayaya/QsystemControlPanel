package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RpcGetBool extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private boolean result;

   public RpcGetBool() {
   }

   public void setResult(boolean result) {
      this.result = result;
   }

   public boolean getResult() {
      return this.result;
   }

   public RpcGetBool(boolean result) {
      this.result = result;
   }
}
