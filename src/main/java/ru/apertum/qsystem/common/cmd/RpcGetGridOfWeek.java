package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.LinkedList;

public class RpcGetGridOfWeek extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private RpcGetGridOfWeek.GridAndParams result;

   public RpcGetGridOfWeek() {
   }

   public RpcGetGridOfWeek(RpcGetGridOfWeek.GridAndParams result) {
      this.result = result;
   }

   public void setResult(RpcGetGridOfWeek.GridAndParams result) {
      this.result = result;
   }

   public RpcGetGridOfWeek.GridAndParams getResult() {
      return this.result;
   }

   public static class GridAndParams {
      @Expose
      @SerializedName("error")
      private String spError;
      @Expose
      @SerializedName("times")
      private LinkedList<Date> times = new LinkedList<>();
      @Expose
      @SerializedName("start")
      private Date startTime;
      @Expose
      @SerializedName("finish")
      private Date finishTime;
      @Expose
      @SerializedName("limit")
      private int advanceLimit;
      @Expose
      @SerializedName("limit_period")
      private int advanceLimitPeriod;
      @Expose
      @SerializedName("limit_time")
      private int advanceTimePeriod;

      public GridAndParams(String spError) {
         this.spError = spError;
      }

      public String getSpError() {
         return this.spError;
      }

      public void setSpError(String spError) {
         this.spError = spError;
      }

      public GridAndParams() {
      }

      public void addTime(Date time) {
         if (this.times == null) {
            this.times = new LinkedList<>();
         }

         this.times.add(time);
      }

      public LinkedList<Date> getTimes() {
         return this.times;
      }

      public int getAdvanceTimePeriod() {
         return this.advanceTimePeriod;
      }

      public void setAdvanceTimePeriod(int advanceTimePeriod) {
         this.advanceTimePeriod = advanceTimePeriod;
      }

      public int getAdvanceLimit() {
         return this.advanceLimit;
      }

      public void setAdvanceLimit(int advanceLimit) {
         this.advanceLimit = advanceLimit;
      }

      public int getAdvanceLimitPeriod() {
         return this.advanceLimitPeriod;
      }

      public void setAdvanceLimitPeriod(int advanceLimitPeriod) {
         this.advanceLimitPeriod = advanceLimitPeriod;
      }

      public Date getFinishTime() {
         return this.finishTime;
      }

      public void setFinishTime(Date finishTime) {
         this.finishTime = finishTime;
      }

      public Date getStartTime() {
         return this.startTime;
      }

      public void setStartTime(Date startTime) {
         this.startTime = startTime;
      }
   }
}
