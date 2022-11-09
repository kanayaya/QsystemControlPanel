package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class QStandards implements Serializable {
   private Long id;
   @Expose
   @SerializedName("wait_max")
   private Integer waitMax;
   @Expose
   @SerializedName("work_max")
   private Integer workMax;
   @Expose
   @SerializedName("downtime_max")
   private Integer downtimeMax;
   @Expose
   @SerializedName("line_service_max")
   private Integer lineServiceMax;
   @Expose
   @SerializedName("line_total_max")
   private Integer lineTotalMax;
   @Expose
   @SerializedName("relocation")
   private Integer relocation;

   public void setId(Long id) {
      this.id = id;
   }

   public Long getId() {
      return this.id;
   }

   public Integer getDowntimeMax() {
      return this.downtimeMax;
   }

   public void setDowntimeMax(Integer downtimeMax) {
      this.downtimeMax = downtimeMax;
   }

   public Integer getLineServiceMax() {
      return this.lineServiceMax;
   }

   public void setLineServiceMax(Integer lineServiceMax) {
      this.lineServiceMax = lineServiceMax;
   }

   public Integer getLineTotalMax() {
      return this.lineTotalMax;
   }

   public void setLineTotalMax(Integer lineTotalMax) {
      this.lineTotalMax = lineTotalMax;
   }

   public Integer getWaitMax() {
      return this.waitMax;
   }

   public void setWaitMax(Integer waitMax) {
      this.waitMax = waitMax;
   }

   public Integer getWorkMax() {
      return this.workMax;
   }

   public void setWorkMax(Integer workMax) {
      this.workMax = workMax;
   }

   @Override
   public String toString() {
      return "[MaxWait="
         + this.getWaitMax()
         + ",WorkMax="
         + this.getWorkMax()
         + ",DowntimeMax="
         + this.getDowntimeMax()
         + ",LineServiceMax="
         + this.getLineServiceMax()
         + ",LineTotalMax="
         + this.getLineTotalMax()
         + "]";
   }

   public Integer getRelocation() {
      return this.relocation;
   }

   public void setRelocation(Integer relocation) {
      this.relocation = relocation;
   }
}
