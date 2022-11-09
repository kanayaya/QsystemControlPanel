package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import ru.apertum.qsystem.common.Uses;

public class QPlanService implements Serializable {
   @Expose
   @SerializedName("coeff")
   protected Integer coefficient = 1;
   @Expose
   @SerializedName("id")
   private Long id;
   @Expose
   @SerializedName("flex")
   private Boolean flexible_coef = false;
   @Expose
   @SerializedName("service")
   private QService service;
   private QUser user;
   private int worked = 0;
   private long avg_work = 0L;
   private int killed = 0;
   private long avg_wait = 0L;
   private int waiters = 0;

   public QPlanService() {
   }

   public QPlanService(QService service, QUser user, Integer coefficient) {
      this.coefficient = coefficient;
      this.service = service;
      this.user = user;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Integer getCoefficient() {
      return this.coefficient;
   }

   public void setCoefficient(Integer coefficient) {
      if (coefficient >= 0 && coefficient <= Uses.get_COEFF_WORD().size()) {
         this.coefficient = coefficient;
      } else {
         this.coefficient = 1;
      }
   }

   public Boolean getFlexible_coef() {
      return this.flexible_coef;
   }

   public void setFlexible_coef(Boolean flexible_coef) {
      this.flexible_coef = flexible_coef;
   }

   public QService getService() {
      return this.service;
   }

   public void setService(QService service) {
      this.service = service;
   }

   public QUser getUser() {
      return this.user;
   }

   public void setUser(QUser user) {
      this.user = user;
   }

   @Override
   public String toString() {
      return (this.getFlexible_coef() ? "* " : "")
         + "["
         + (String)Uses.get_COEFF_WORD().get(this.getCoefficient())
         + "]"
         + this.service.getPrefix()
         + " "
         + this.service.getName();
   }

   public long getAvg_wait() {
      return this.avg_wait;
   }

   public void setAvg_wait(long avg_wait) {
      if (avg_wait == 0L) {
         this.waiters = 0;
      }

      this.avg_wait = avg_wait;
   }

   public long getAvg_work() {
      return this.avg_work;
   }

   public void setAvg_work(long avg_work) {
      this.avg_work = avg_work;
   }

   public int getKilled() {
      return this.killed;
   }

   public void setKilled(int killed) {
      this.killed = killed;
   }

   public int getWorked() {
      return this.worked;
   }

   public void setWorked(int worked) {
      this.worked = worked;
   }

   public synchronized void inkKilled() {
      ++this.killed;
   }

   public synchronized void inkWorked(long work_time) {
      ++this.worked;
      this.avg_work = (this.avg_work * (long)(this.worked - 1) + work_time / 60000L) / (long)this.worked;
      this.avg_work = this.avg_work == 0L ? 1L : this.avg_work;
   }

   public synchronized void upWait(long wait_time) {
      ++this.waiters;
      this.avg_wait = (this.avg_wait * (long)(this.waiters - 1) + wait_time / 60000L) / (long)this.waiters;
      this.avg_wait = this.avg_wait == 0L ? 1L : this.avg_wait;
   }
}
