package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import ru.apertum.qsystem.server.model.QNet;
import ru.apertum.qsystem.server.model.QService;

public class RpcGetAllServices extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private RpcGetAllServices.ServicesForWelcome result;

   public RpcGetAllServices() {
   }

   public RpcGetAllServices(RpcGetAllServices.ServicesForWelcome result) {
      this.result = result;
   }

   public void setResult(RpcGetAllServices.ServicesForWelcome result) {
      this.result = result;
   }

   public RpcGetAllServices.ServicesForWelcome getResult() {
      return this.result;
   }

   public static class ServicesForWelcome {
      @Expose
      @SerializedName("root")
      private QService root;
      @Expose
      @SerializedName("start_time")
      private Date startTime;
      @Expose
      @SerializedName("finish_time")
      private Date finishTime;
      @Expose
      @SerializedName("btn_free_dsn")
      private Boolean buttonFreeDesign;

      public ServicesForWelcome() {
      }

      public ServicesForWelcome(QService root, QNet qnet) {
         this.root = root;
         this.startTime = qnet.getStartTime();
         this.finishTime = qnet.getFinishTime();
         this.buttonFreeDesign = qnet.getButtonFreeDesign();
      }

      public QService getRoot() {
         return this.root;
      }

      public void setRoot(QService root) {
         this.root = root;
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

      public Boolean getButtonFreeDesign() {
         return this.buttonFreeDesign;
      }

      public void setButtonFreeDesign(Boolean buttonFreeDesign) {
         this.buttonFreeDesign = buttonFreeDesign;
      }
   }
}
