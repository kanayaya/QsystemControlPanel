package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.LinkedList;
import ru.apertum.qsystem.common.model.QCustomer;
import ru.apertum.qsystem.server.model.QService;

public class RpcGetServerState extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private LinkedList<RpcGetServerState.ServiceInfo> result;

   public RpcGetServerState() {
   }

   public RpcGetServerState(LinkedList<RpcGetServerState.ServiceInfo> result) {
      this.result = result;
   }

   public void setResult(LinkedList<RpcGetServerState.ServiceInfo> result) {
      this.result = result;
   }

   public LinkedList<RpcGetServerState.ServiceInfo> getResult() {
      return this.result;
   }

   public static class ServiceInfo {
      @Expose
      @SerializedName("service_name")
      private String serviceName;
      @Expose
      @SerializedName("waiting")
      private int countWait;
      @Expose
      @SerializedName("first")
      private String firstNumber;
      @Expose
      @SerializedName("id")
      private Long id;
      @Expose
      @SerializedName("wait_max")
      private Integer waitMax;

      @Override
      public String toString() {
         return this.serviceName;
      }

      public ServiceInfo() {
      }

      public ServiceInfo(QService service, int countWait, String firstNumber) {
         this.serviceName = service.getName();
         this.countWait = countWait;
         this.firstNumber = firstNumber;
         this.id = service.getId();
         long nn = new Date().getTime();
         long max = 0L;

         for(QCustomer customer : service.getClients()) {
            if (nn - customer.getStandTime().getTime() > max) {
               max = nn - customer.getStandTime().getTime();
            }
         }

         this.waitMax = (int)(max / 1000L / 60L);
      }

      public int getCountWait() {
         return this.countWait;
      }

      public void setCountWait(int countWait) {
         this.countWait = countWait;
      }

      public String getFirstNumber() {
         return this.firstNumber;
      }

      public void setFirstNumber(String firstNumber) {
         this.firstNumber = firstNumber;
      }

      public String getServiceName() {
         return this.serviceName;
      }

      public void setServiceName(String serviceName) {
         this.serviceName = serviceName;
      }

      public Long getId() {
         return this.id;
      }

      public void setId(Long id) {
         this.id = id;
      }

      public Integer getWaitMax() {
         return this.waitMax;
      }

      public void setWaitMax(Integer waitMax) {
         this.waitMax = waitMax;
      }
   }
}
