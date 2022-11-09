package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.LinkedList;
import ru.apertum.qsystem.common.model.QCustomer;
import ru.apertum.qsystem.server.model.QService;
import ru.apertum.qsystem.server.model.QUser;

public class RpcGetSelfSituation extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private RpcGetSelfSituation.SelfSituation result;

   public RpcGetSelfSituation() {
   }

   public RpcGetSelfSituation(RpcGetSelfSituation.SelfSituation result) {
      this.result = result;
   }

   public void setResult(RpcGetSelfSituation.SelfSituation result) {
      this.result = result;
   }

   public RpcGetSelfSituation.SelfSituation getResult() {
      return this.result;
   }

   public static class SelfService {
      @Expose
      @SerializedName("id")
      private long id;
      @Expose
      @SerializedName("waiting")
      private int countWait;
      @Expose
      @SerializedName("duration")
      private int duration;
      @Expose
      @SerializedName("service_name")
      private String serviceName;
      @Expose
      @SerializedName("priority")
      private int priority;
      @Expose
      @SerializedName("flexy")
      private boolean flexy;
      @Expose
      @SerializedName("roll")
      private boolean roll;
      @Expose
      @SerializedName("line")
      private LinkedList<RpcGetSelfSituation.StPair> line;

      public SelfService() {
      }

      public SelfService(QService service, int countWait, int priority, boolean flexy) {
         this.serviceName = service.getName();
         this.countWait = countWait;
         this.duration = service.getDuration();
         this.priority = priority;
         this.flexy = flexy;
         this.roll = service.getStatus() == 5;
         this.id = service.getId();
         this.line = new LinkedList<>();

         for(QCustomer cu : service.getClients()) {
            String fn = cu.getFullNumber();
            RpcGetSelfSituation.StPair sp = new RpcGetSelfSituation.StPair(fn, cu.getInput_data(), cu.getWaitingMinutes());
            this.line.addLast(sp);
         }
      }

      public long getId() {
         return this.id;
      }

      public void setId(long id) {
         this.id = id;
      }

      public int getCountWait() {
         return this.countWait;
      }

      public void setCountWait(int countWait) {
         this.countWait = countWait;
      }

      public int getDuration() {
         return this.duration;
      }

      public void setDuration(int duration) {
         this.duration = duration;
      }

      public String getServiceName() {
         return this.serviceName;
      }

      public void setServiceName(String serviceName) {
         this.serviceName = serviceName;
      }

      public int getPriority() {
         return this.priority;
      }

      public void setPriority(int priority) {
         this.priority = priority;
      }

      public boolean isFlexy() {
         return this.flexy;
      }

      public void setFlexy(boolean flexy) {
         this.flexy = flexy;
      }

      public boolean isRoll() {
         return this.roll;
      }

      public void setRoll(boolean roll) {
         this.roll = roll;
      }

      public LinkedList<RpcGetSelfSituation.StPair> getLine() {
         return this.line;
      }

      public void setLine(LinkedList<RpcGetSelfSituation.StPair> line) {
         this.line = line;
      }
   }

   public static class SelfSituation {
      @Expose
      @SerializedName("self_services")
      private LinkedList<RpcGetSelfSituation.SelfService> selfservices;
      @Expose
      @SerializedName("customer")
      private QCustomer customer;
      @Expose
      @SerializedName("postponed")
      private LinkedList<QCustomer> postponedList;
      @Expose
      @SerializedName("parallel")
      private LinkedList<QCustomer> parallelList;
      @Expose
      @SerializedName("limit_remove")
      private Integer limitRecall;
      @Expose
      @SerializedName("shadow")
      private QUser.Shadow shadow;
      @Expose
      @SerializedName("ext_prior")
      private Integer extPror = 0;

      public SelfSituation() {
      }

      public SelfSituation(
         LinkedList<RpcGetSelfSituation.SelfService> selfservices,
         QCustomer customer,
         LinkedList<QCustomer> parallelList,
         LinkedList<QCustomer> postponedList,
         int limitRecall,
         int extPrior,
         QUser.Shadow shadow
      ) {
         this.selfservices = selfservices;
         this.customer = customer;
         this.parallelList = parallelList;
         this.postponedList = postponedList;
         this.limitRecall = limitRecall;
         this.extPror = extPrior;
         this.shadow = shadow;
      }

      public QCustomer getCustomer() {
         return this.customer;
      }

      public void setCustomer(QCustomer customer) {
         this.customer = customer;
      }

      public LinkedList<RpcGetSelfSituation.SelfService> getSelfservices() {
         return this.selfservices;
      }

      public void setSelfservices(LinkedList<RpcGetSelfSituation.SelfService> selfservices) {
         this.selfservices = selfservices;
      }

      public LinkedList<QCustomer> getPostponedList() {
         return this.postponedList;
      }

      public void setPostponedList(LinkedList<QCustomer> postponedList) {
         this.postponedList = postponedList;
      }

      public LinkedList<QCustomer> getParallelList() {
         return this.parallelList;
      }

      public void setParallelList(LinkedList<QCustomer> parallelList) {
         this.parallelList = parallelList;
      }

      public Integer getLimitRemove() {
         return this.limitRecall;
      }

      public void setLimitRemove(Integer limitRemove) {
         this.limitRecall = limitRemove;
      }

      public QUser.Shadow getShadow() {
         return this.shadow;
      }

      public void setShadow(QUser.Shadow shadow) {
         this.shadow = shadow;
      }

      public Integer getExtPror() {
         return this.extPror;
      }

      public void setExtPror(Integer extPror) {
         this.extPror = extPror;
      }
   }

   public static class StPair {
      @Expose
      @SerializedName("num")
      public final String number;
      @Expose
      @SerializedName("dat")
      public final String data;
      @Expose
      @SerializedName("waiting")
      public final Integer waiting;

      public StPair(String number, String data, Integer waiting) {
         this.number = number;
         this.data = data;
         this.waiting = waiting;
      }

      public StPair() {
         this.number = null;
         this.data = null;
         this.waiting = null;
      }
   }
}
