package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.LinkedList;
import ru.apertum.qsystem.server.model.QAdvanceCustomer;

public class RpcGetGridOfDay extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private RpcGetGridOfDay.GridDayAndParams result;

   public RpcGetGridOfDay() {
   }

   public RpcGetGridOfDay(RpcGetGridOfDay.GridDayAndParams result) {
      this.result = result;
   }

   public void setResult(RpcGetGridOfDay.GridDayAndParams result) {
      this.result = result;
   }

   public RpcGetGridOfDay.GridDayAndParams getResult() {
      return this.result;
   }

   public static class AdvTime {
      @Expose
      @SerializedName("date")
      private Date date;
      @Expose
      @SerializedName("acusts")
      private LinkedList<QAdvanceCustomer> acusts;

      public AdvTime() {
      }

      public AdvTime(Date date) {
         this.date = date;
      }

      public LinkedList<QAdvanceCustomer> getAcusts() {
         return this.acusts;
      }

      public void setAcusts(LinkedList<QAdvanceCustomer> acusts) {
         this.acusts = acusts;
      }

      public void addACustomer(QAdvanceCustomer aCustomer) {
         if (this.acusts == null) {
            this.acusts = new LinkedList<>();
         }

         this.acusts.add(aCustomer);
      }

      public Date getDate() {
         return this.date;
      }

      public void setDate(Date date) {
         this.date = date;
      }
   }

   public static class GridDayAndParams {
      @Expose
      @SerializedName("limit")
      private int advanceLimit;
      @Expose
      @SerializedName("times")
      private LinkedList<RpcGetGridOfDay.AdvTime> times = new LinkedList<>();

      public GridDayAndParams() {
      }

      public GridDayAndParams(int advanceLimit) {
         this.advanceLimit = advanceLimit;
      }

      public int getAdvanceLimit() {
         return this.advanceLimit;
      }

      public void setAdvanceLimit(int advanceLimit) {
         this.advanceLimit = advanceLimit;
      }

      public void addTime(RpcGetGridOfDay.AdvTime time) {
         if (this.times == null) {
            this.times = new LinkedList<>();
         }

         this.times.add(time);
      }

      public LinkedList<RpcGetGridOfDay.AdvTime> getTimes() {
         return this.times;
      }
   }
}
