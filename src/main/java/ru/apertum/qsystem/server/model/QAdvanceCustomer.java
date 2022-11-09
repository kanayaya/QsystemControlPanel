package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class QAdvanceCustomer implements Serializable {
   @Expose
   @SerializedName("id")
   private Long id = new Date().getTime() % 1000000L;
   @Expose
   @SerializedName("advance_time")
   private Date advanceTime;
   @Expose
   @SerializedName("priority")
   private Integer priority;
   @Expose
   @SerializedName("service")
   private QService service;
   @Expose
   @SerializedName("authorization")
   private QAuthorizationCustomer authorizationCustomer;
   @Expose
   @SerializedName("input_data")
   private String inputData;
   @Expose
   @SerializedName("comments")
   private String comments;

   public QAdvanceCustomer() {
   }

   public QAdvanceCustomer(Long id) {
      this.id = id;
   }

   public QAdvanceCustomer(String inputData) {
      this.inputData = inputData;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Date getAdvanceTime() {
      return this.advanceTime;
   }

   public void setAdvanceTime(Date advanceTime) {
      this.advanceTime = advanceTime;
   }

   public Integer getPriority() {
      return this.priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
   }

   public QService getService() {
      return this.service;
   }

   public void setService(QService service) {
      this.service = service;
   }

   public QAuthorizationCustomer getAuthorizationCustomer() {
      return this.authorizationCustomer;
   }

   public void setAuthorizationCustomer(QAuthorizationCustomer authorizationCustomer) {
      this.authorizationCustomer = authorizationCustomer;
   }

   public String getInputData() {
      return this.inputData;
   }

   public void setInputData(String inputData) {
      this.inputData = inputData;
   }

   public String getComments() {
      return this.comments;
   }

   public void setComments(String comments) {
      this.comments = comments;
   }
}
