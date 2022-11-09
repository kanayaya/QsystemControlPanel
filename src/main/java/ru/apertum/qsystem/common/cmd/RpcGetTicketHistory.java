package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.LinkedList;
import java.util.List;

public class RpcGetTicketHistory extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private RpcGetTicketHistory.TicketHistory result;

   public RpcGetTicketHistory(RpcGetTicketHistory.TicketHistory result) {
      this.result = result;
   }

   public void setResult(RpcGetTicketHistory.TicketHistory result) {
      this.result = result;
   }

   public RpcGetTicketHistory.TicketHistory getResult() {
      return this.result;
   }

   public static class TicketHistory {
      @Expose
      @SerializedName("info")
      private String info;
      @Expose
      @SerializedName("history")
      private List<String> custs;

      public TicketHistory(String info, List<String> custs) {
         this.info = info;
         this.custs = custs;
      }

      public String getInfo() {
         return this.info;
      }

      public void setInfo(String info) {
         this.info = info;
      }

      public List<String> getCusts() {
         return this.custs;
      }

      public void setCusts(LinkedList<String> custs) {
         this.custs = custs;
      }
   }
}
