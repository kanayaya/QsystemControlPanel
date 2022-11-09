package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonRPC20 extends AJsonRPC20 {
   @Expose
   @SerializedName("params")
   private CmdParams params;

   public JsonRPC20() {
   }

   public JsonRPC20(String method, CmdParams params) {
      this.method = method;
      this.params = params;
   }

   public void setParams(CmdParams params) {
      this.params = params;
   }

   public CmdParams getParams() {
      return this.params;
   }
}
