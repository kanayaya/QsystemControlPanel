package ru.apertum.qsystem.server.model.response;

import java.io.Serializable;
import java.util.Date;

public class QRespEvent implements Serializable {
   private Long id;
   private Date date;
   private Long respID;
   private Long userID;
   private Long serviceID;
   private Long clientID;
   private String clientData = "";
   private String comment = "";

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Date getDate() {
      return this.date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public Long getRespID() {
      return this.respID;
   }

   public void setRespID(Long respID) {
      this.respID = respID;
   }

   public Long getUserID() {
      return this.userID;
   }

   public void setUserID(Long userID) {
      this.userID = userID;
   }

   public Long getServiceID() {
      return this.serviceID;
   }

   public void setServiceID(Long serviceID) {
      this.serviceID = serviceID;
   }

   public Long getClientID() {
      return this.clientID;
   }

   public void setClientID(Long clientID) {
      this.clientID = clientID;
   }

   public String getClientData() {
      return this.clientData;
   }

   public void setClientData(String clientData) {
      this.clientData = clientData;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }
}
