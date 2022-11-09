package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonRPC20OK extends AJsonRPC20 {
   @Expose
   @SerializedName("result")
   private int result = 1;

   public JsonRPC20OK() {
   }

   public void setResult(int result) {
      this.result = result;
   }

   public int getResult() {
      return this.result;
   }

   public JsonRPC20OK(int result) {
      this.result = result;
   }
}
