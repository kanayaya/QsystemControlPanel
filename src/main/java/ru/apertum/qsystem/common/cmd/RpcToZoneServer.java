package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RpcToZoneServer extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private RpcToZoneServer.Data result;

   public RpcToZoneServer.Data getResult() {
      return this.result;
   }

   public void setResult(RpcToZoneServer.Data result) {
      this.result = result;
   }

   public RpcToZoneServer(RpcToZoneServer.Data result) {
      this.result = result;
   }

   public RpcToZoneServer() {
   }

   public static class Data {
      @Expose
      @SerializedName("userName")
      public String userName;
      @Expose
      @SerializedName("userPoint")
      public String userPoint;
      @Expose
      @SerializedName("customerPrefix")
      public String customerPrefix;
      @Expose
      @SerializedName("customerNumber")
      public int customerNumber;
      @Expose
      @SerializedName("userAddrRS")
      public int userAddrRS;

      public int getCustomerNumber() {
         return this.customerNumber;
      }

      public void setCustomerNumber(int customerNumber) {
         this.customerNumber = customerNumber;
      }

      public String getCustomerPrefix() {
         return this.customerPrefix;
      }

      public void setCustomerPrefix(String customerPrefix) {
         this.customerPrefix = customerPrefix;
      }

      public int getUserAddrRS() {
         return this.userAddrRS;
      }

      public void setUserAddrRS(int userAddrRS) {
         this.userAddrRS = userAddrRS;
      }

      public String getUserName() {
         return this.userName;
      }

      public void setUserName(String userName) {
         this.userName = userName;
      }

      public String getUserPoint() {
         return this.userPoint;
      }

      public void setUserPoint(String userPoint) {
         this.userPoint = userPoint;
      }

      public Data() {
      }

      public Data(String userName, String userPoint, String customerPrefix, int customerNumber, int userAddrRS) {
         this.userName = userName;
         this.userPoint = userPoint;
         this.customerPrefix = customerPrefix;
         this.customerNumber = customerNumber;
         this.userAddrRS = userAddrRS;
      }
   }
}
