package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ru.apertum.qsystem.common.CustomerState;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.common.model.QCustomer;

public class QUser implements IidGetter, Serializable {
   private final LinkedList<QPlanService> forDel = new LinkedList<>();
   @Expose
   @SerializedName("pause")
   public Boolean pause = false;
   @Expose
   @SerializedName("id")
   private Long id;
   private Date deleted;
   @Expose
   @SerializedName("enable")
   private Integer enable = 1;
   @Expose
   @SerializedName("is_admin")
   private Boolean adminAccess = false;
   @Expose
   @SerializedName("is_report_access")
   private Boolean reportAccess = false;
   @Expose
   @SerializedName("pass")
   private String password = "";
   @Expose
   @SerializedName("point")
   private String point;
   @Expose
   @SerializedName("name")
   private String name;
   @Expose
   @SerializedName("adress_rs")
   private Integer adressRS;
   @Expose
   @SerializedName("point_ext")
   private String pointExt = "";
   @Expose
   @SerializedName("plan")
   private List<QPlanService> planServices;
   private QPlanServiceList planServiceList = new QPlanServiceList(new LinkedList<>());
   @Expose
   @SerializedName("services_cnt")
   private int servicesCnt = 0;
   private QCustomer customer = null;
   @Expose
   @SerializedName("shadow")
   private QUser.Shadow shadow = null;

   @Override
   public String toString() {
      return this.getName();
   }

   @Override
   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Date getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Date deleted) {
      this.deleted = deleted;
   }

   public Integer getEnable() {
      return this.enable;
   }

   public void setEnable(Integer enable) {
      this.enable = enable;
   }

   public Boolean getAdminAccess() {
      return this.adminAccess;
   }

   public void setAdminAccess(Boolean adminAccess) {
      this.adminAccess = adminAccess;
   }

   public Boolean getReportAccess() {
      return this.reportAccess;
   }

   public void setReportAccess(Boolean reportAccess) {
      this.reportAccess = reportAccess;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isCorrectPassword(String password) {
      return this.password.equals(password);
   }

   public void recoverAccess(String access) {
      this.password = access;
   }

   public String getPoint() {
      return this.point;
   }

   public void setPoint(String point) {
      this.point = point;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getAdressRS() {
      return this.adressRS;
   }

   public void setAdressRS(Integer adressRS) {
      this.adressRS = adressRS;
   }

   public String getPointExt() {
      return this.pointExt;
   }

   public void setPointExt(String pointExt) {
      this.pointExt = pointExt;
   }

   public List<QPlanService> getPlanServices() {
      return this.planServices;
   }

   public void setPlanServices(List<QPlanService> planServices) {
      this.planServices = planServices;
      this.planServiceList = new QPlanServiceList(planServices);
   }

   public QPlanServiceList getPlanServiceList() {
      return this.planServiceList;
   }

   public boolean hasService(long serviceId) {
      for(QPlanService qPlanService : this.planServices) {
         if (serviceId == qPlanService.getService().getId()) {
            return true;
         }
      }

      return false;
   }

   public boolean hasService(QService service) {
      return this.hasService(service.getId());
   }

   public QPlanService getPlanService(long serviceId) {
      for(QPlanService qPlanService : this.planServices) {
         if (serviceId == qPlanService.getService().getId()) {
            return qPlanService;
         }
      }

      throw new ServerException("Не найдена обрабатываемая услуга по ID \"" + serviceId + "\" у услуги c ID = " + this.id);
   }

   public QPlanService getPlanService(QService service) {
      return this.getPlanService(service.getId());
   }

   public void addPlanService(QService service) {
      this.planServiceList.addElement(new QPlanService(service, this, 1));
      this.servicesCnt = this.planServices.size();
   }

   public void addPlanService(QService service, int coefficient) {
      this.planServiceList.addElement(new QPlanService(service, this, coefficient));
      this.servicesCnt = this.planServices.size();
   }

   public QPlanService deletePlanService(long serviceId) {
      for(QPlanService qPlanService : this.planServices) {
         if (serviceId == qPlanService.getService().getId()) {
            this.planServiceList.removeElement(qPlanService);
            this.forDel.add(qPlanService);
            this.servicesCnt = this.planServices.size();
            return qPlanService;
         }
      }

      throw new ServerException("Не найдена услуга по ID \"" + serviceId + "\" у услуги c ID = " + this.id);
   }

   public QPlanService deletePlanService(QService service) {
      return this.deletePlanService(service.getId());
   }

   public void savePlan() {
   }

   public int getServicesCnt() {
      return this.servicesCnt;
   }

   public void setServicesCnt(int servicesCnt) {
      this.servicesCnt = servicesCnt;
   }

   public QCustomer getCustomer() {
      return this.customer;
   }

   public void setCustomer(QCustomer customer) {
      if (customer != null || this.getCustomer() != null) {
         if (customer == null && this.getCustomer() != null) {
            this.finalizeCustomer();
            if (this.getCustomer().getUser() != null) {
               this.getCustomer().setUser(null);
            }

            if (this.getCustomer().getStartTime() != null) {
               this.getCustomer().setStartTime(null);
            }
         } else {
            customer.setUser(this);
            this.initCustomer(customer);
         }

         this.customer = customer;
      }
   }

   public void finalizeCustomer() {
      this.shadow = new QUser.Shadow(this.customer);
      this.shadow.setStartTime(null);
   }

   public void initCustomer(QCustomer cust) {
      this.shadow = new QUser.Shadow(cust);
      this.shadow.setFinTime(null);
   }

   public Boolean isPause() {
      return this.pause;
   }

   public void setPause(Boolean pause) {
      this.pause = pause;
   }

   public QUser.Shadow getShadow() {
      return this.shadow;
   }

   public void setShadow(QUser.Shadow shadow) {
      this.shadow = shadow;
   }

   public static class Shadow {
      private QService oldService;
      private QCustomer oldCustomer;
      @Expose
      @SerializedName("id_old_service")
      private Long idOldService;
      @Expose
      @SerializedName("id_old_customer")
      private Long idOldCustomer;
      @Expose
      @SerializedName("old_nom")
      private int oldNom;
      @Expose
      @SerializedName("old_pref")
      private String oldPref;
      @Expose
      @SerializedName("old_fin_time")
      private Date finTime;
      @Expose
      @SerializedName("old_start_time")
      private Date startTime;
      @Expose
      @SerializedName("old_cust_state")
      private CustomerState customerState;

      public Shadow() {
      }

      public Shadow(QCustomer oldCostomer) {
         this.oldCustomer = oldCostomer;
         this.idOldCustomer = oldCostomer.getId();
         this.idOldService = oldCostomer.getService().getId();
         this.oldService = oldCostomer.getService();
         this.oldNom = oldCostomer.getNumber();
         this.oldPref = oldCostomer.getPrefix();
         this.finTime = new Date();
         this.startTime = new Date();
         if (oldCostomer.getState() == null) {
            this.customerState = CustomerState.STATE_INVITED;
         } else {
            this.customerState = oldCostomer.getState();
         }
      }

      public Date getStartTime() {
         return this.startTime;
      }

      public void setStartTime(Date startTime) {
         this.startTime = startTime;
      }

      public Long getIdOldCustomer() {
         return this.idOldCustomer;
      }

      public void setIdOldCustomer(Long idOldCustomer) {
         this.idOldCustomer = idOldCustomer;
      }

      public Long getIdOldService() {
         return this.idOldService;
      }

      public void setIdOldService(Long idOldService) {
         this.idOldService = idOldService;
      }

      public QCustomer getOldCustomer() {
         return this.oldCustomer;
      }

      public void setOldCustomer(QCustomer oldCustomer) {
         this.oldCustomer = oldCustomer;
      }

      public Date getFinTime() {
         return this.finTime;
      }

      public void setFinTime(Date finTime) {
         this.finTime = finTime;
      }

      public QCustomer getOldCostomer() {
         return this.oldCustomer;
      }

      public void setOldCostomer(QCustomer oldCostomer) {
         this.oldCustomer = oldCostomer;
      }

      public int getOldNom() {
         return this.oldNom;
      }

      public void setOldNom(int oldNom) {
         this.oldNom = oldNom;
      }

      public String getOldPref() {
         return this.oldPref;
      }

      public void setOldPref(String oldPref) {
         this.oldPref = oldPref;
      }

      public QService getOldService() {
         return this.oldService;
      }

      public void setOldService(QService oldService) {
         this.oldService = oldService;
      }

      public CustomerState getCustomerState() {
         return this.customerState;
      }

      public void setCustomerState(CustomerState customerState) {
         this.customerState = customerState;
      }
   }
}
