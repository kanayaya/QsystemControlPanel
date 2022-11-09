package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RpcGetSrt extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private String result;

   public RpcGetSrt() {
   }

   public void setResult(String result) {
      this.result = result;
   }

   public String getResult() {
      return this.result;
   }

   public RpcGetSrt(String result) {
      this.result = result;
   }
}
