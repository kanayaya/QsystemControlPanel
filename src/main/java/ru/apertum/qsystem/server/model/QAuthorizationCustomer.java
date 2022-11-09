package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

public class QAuthorizationCustomer implements Serializable {
   @Expose
   @SerializedName("id")
   private Long id;
   @Expose
   @SerializedName("auth_id")
   private String authId;
   @Expose
   @SerializedName("name")
   private String name;
   @Expose
   @SerializedName("surname")
   private String surname;
   @Expose
   @SerializedName("otchestvo")
   private String otchestvo;
   @Expose
   @SerializedName("birthday")
   private Date birthday;
   @Expose
   @SerializedName("comments")
   private String comments;

   public QAuthorizationCustomer() {
   }

   public QAuthorizationCustomer(String name) {
      this.name = name;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getAuthId() {
      return this.authId;
   }

   public void setAuthId(String authId) {
      this.authId = authId;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSurname() {
      return this.surname;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public String getOtchestvo() {
      return this.otchestvo;
   }

   public void setOtchestvo(String otchestvo) {
      this.otchestvo = otchestvo;
   }

   public Date getBirthday() {
      return this.birthday;
   }

   public void setBirthday(Date birthday) {
      this.birthday = birthday;
   }

   public String getComments() {
      return this.comments;
   }

   public void setComments(String comments) {
      this.comments = comments;
   }
}
