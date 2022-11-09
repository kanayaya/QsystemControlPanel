package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.LinkedHashMap;
import ru.apertum.qsystem.server.ServerProps;

public class RpcGetProperties extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private LinkedHashMap<String, ServerProps.Section> result;

   public RpcGetProperties() {
   }

   public void setResult(LinkedHashMap<String, ServerProps.Section> result) {
      this.result = result;
   }

   public LinkedHashMap<String, ServerProps.Section> getResult() {
      return this.result;
   }

   public RpcGetProperties(LinkedHashMap<String, ServerProps.Section> result) {
      this.result = result;
   }
}
