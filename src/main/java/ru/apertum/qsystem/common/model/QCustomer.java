package ru.apertum.qsystem.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import ru.apertum.qsystem.common.CustomerState;
import ru.apertum.qsystem.common.QConfig;
import ru.apertum.qsystem.common.QLog;
import ru.apertum.qsystem.server.model.IidGetter;
import ru.apertum.qsystem.server.model.QService;
import ru.apertum.qsystem.server.model.QUser;
import ru.apertum.qsystem.server.model.response.QRespEvent;
import ru.apertum.qsystem.server.model.results.QResult;

public final class QCustomer implements Comparable<QCustomer>, Serializable, IidGetter {
   @Expose
   @SerializedName("id")
   private Long id = new Date().getTime();
   @Expose
   @SerializedName("number")
   private Integer number;
   @Expose
   @SerializedName("stateIn")
   private Integer stateIn;
   @Expose
   @SerializedName("state")
   private CustomerState state;
   private final LinkedList<QRespEvent> resps = new LinkedList<>();
   @Expose
   @SerializedName("priority")
   private Integer priority;
   @Expose
   @SerializedName("to_service")
   private QService service;
   private QResult result;
   @Expose
   @SerializedName("from_user")
   private QUser user;
   @Expose
   @SerializedName("prefix")
   private String prefix;
   @Expose
   @SerializedName("stand_time")
   private Date standTime;
   @Expose
   @SerializedName("start_time")
   private Date startTime;
   private Date callTime;
   @Expose
   @SerializedName("finish_time")
   private Date finishTime;
   @Expose
   @SerializedName("input_data")
   private String input_data = "";
   private final LinkedList<QService> serviceBack = new LinkedList<>();
   @Expose
   @SerializedName("need_back")
   private boolean needBack = false;
   @Expose
   @SerializedName("temp_comments")
   private String tempComments = "";
   @Expose
   @SerializedName("post_atatus")
   private String postponedStatus = "";
   @Expose
   @SerializedName("postpone_period")
   private int postponPeriod = 0;
   @Expose
   @SerializedName("is_mine")
   private Long isMine = null;
   @Expose
   @SerializedName("recall_cnt")
   private Integer recallCount = 0;
   @Expose
   @SerializedName("start_postpone_period")
   private long startPontpone = 0L;
   private long finishPontpone = 0L;
   @Expose
   @SerializedName("complex_id")
   public LinkedList<LinkedList<LinkedList<Long>>> complexId = new LinkedList<>();

   public QCustomer() {
      this.id = new Date().getTime();
   }

   public QCustomer(int number) {
      this.number = number;
      this.id = new Date().getTime();
      this.setStandTime(new Date());
      QLog.l().logger().debug("Создали кастомера с номером " + number);
   }

   @Override
   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setNumber(Integer number) {
      this.number = number;
   }

   public int getNumber() {
      return this.number;
   }

   public Integer getStateIn() {
      return this.stateIn;
   }

   public void setStateIn(Integer stateIn) {
      this.stateIn = stateIn;
   }

   public void setState(CustomerState state) {
      this.setState(state, new Long(-1L));
   }

   public void setState(CustomerState state, Long newServiceId) {
      this.state = state;
      this.stateIn = state.ordinal();
      if (this.getUser() != null) {
         this.getUser().getShadow().setCustomerState(state);
      }

      switch(state) {
         case STATE_DEAD:
            QLog.l().logger().debug("Статус: Кастомер с номером \"" + this.getPrefix() + this.getNumber() + "\" идет домой по неявке");
            this.getUser().getPlanService(this.getService()).inkKilled();
            this.setStartTime(new Date());
            this.setFinishTime(new Date());
            this.saveToSelfDB();
            break;
         case STATE_WAIT:
            QLog.l().logger().debug("Статус: Кастомер пришел и ждет с номером \"" + this.getPrefix() + this.getNumber() + "\"");
            this.setStandTime(new Date());
            break;
         case STATE_WAIT_AFTER_POSTPONED:
            QLog.l()
               .logger()
               .debug("Статус: Кастомер был возвращен из отложенных по истечению времени и ждет с номером \"" + this.getPrefix() + this.getNumber() + "\"");
            this.setStandTime(new Date());
            break;
         case STATE_WAIT_COMPLEX_SERVICE:
            QLog.l()
               .logger()
               .debug(
                  "Статус: Кастомер был опять поставлен в очередь т.к. услуга комплекстая и ждет с номером \"" + this.getPrefix() + this.getNumber() + "\""
               );
            this.setStandTime(new Date());
            break;
         case STATE_INVITED:
            QLog.l().logger().debug("Статус: Пригласили кастомера с номером \"" + this.getPrefix() + this.getNumber() + "\"");
            this.setCallTime(new Date());
            break;
         case STATE_INVITED_SECONDARY:
            QLog.l().logger().debug("Статус: Пригласили повторно в цепочке обработки кастомера с номером \"" + this.getPrefix() + this.getNumber() + "\"");
            this.setCallTime(new Date());
            break;
         case STATE_REDIRECT:
            QLog.l().logger().debug("Статус: Кастомера редиректили с номером \"" + this.getPrefix() + this.getNumber() + "\"");
            this.getUser().getPlanService(this.getService()).inkWorked(System.currentTimeMillis() - this.getStartTime().getTime());
            this.setFinishTime(new Date());
            this.saveToSelfDB();
            this.setStandTime(new Date());
            break;
         case STATE_WORK:
            QLog.l().logger().debug("Начали работать с кастомером с номером \"" + this.getPrefix() + this.getNumber() + "\"");
            this.getUser().getPlanService(this.getService()).upWait(System.currentTimeMillis() - this.getStandTime().getTime());
            break;
         case STATE_WORK_SECONDARY:
            QLog.l().logger().debug("Статус: Далее по цепочки начали работать с кастомером с номером \"" + this.getPrefix() + this.getNumber() + "\"");
            this.getUser().getPlanService(this.getService()).upWait(System.currentTimeMillis() - this.getStandTime().getTime());
            break;
         case STATE_BACK:
            QLog.l().logger().debug("Статус: Кастомер с номером \"" + this.getPrefix() + this.getNumber() + "\" вернут в преднюю услугу");
            this.setStandTime(new Date());
            break;
         case STATE_FINISH:
            QLog.l().logger().debug("Статус: С кастомером с номером \"" + this.getPrefix() + this.getNumber() + "\" закончили работать");
            this.getUser().getPlanService(this.getService()).inkWorked(System.currentTimeMillis() - this.getStartTime().getTime());
            this.setFinishTime(new Date());
            this.saveToSelfDB();
            break;
         case STATE_POSTPONED:
            QLog.l().logger().debug("Кастомер с номером \"" + this.getPrefix() + this.getNumber() + "\" идет ждать в список отложенных");
            this.getUser().getPlanService(this.getService()).inkWorked(System.currentTimeMillis() - this.getStartTime().getTime());
            this.setFinishTime(new Date());
            this.saveToSelfDB();
            this.setStandTime(new Date());
      }
   }

   public void addNewRespEvent(QRespEvent event) {
      this.resps.add(event);
   }

   private void saveToSelfDB() {
   }

   public CustomerState getState() {
      return this.state;
   }

   public void setPriority(int priority) {
      this.priority = priority;
   }

   public IPriority getPriority() {
      return new Priority(this.priority);
   }

   public int compareTo(QCustomer customer) {
      int resultCmp = -1 * this.getPriority().compareTo(customer.getPriority());
      if (resultCmp == 0) {
         if (this.getStandTime().before(customer.getStandTime())) {
            resultCmp = -1;
         } else if (this.getStandTime().after(customer.getStandTime())) {
            resultCmp = 1;
         }
      }

      if (resultCmp == 0) {
         QLog.l().logger().warn("Клиенты не могут быть равны.");
         resultCmp = -1;
      }

      return resultCmp;
   }

   public QService getService() {
      return this.service;
   }

   public void setService(QService service) {
      this.service = service;
      if (this.getPrefix() == null) {
         this.setPrefix(service.getPrefix());
      }

      QLog.l().logger().debug("Клиента \"" + this.getFullNumber() + "\" поставили к услуге \"" + service.getName() + "\"");
   }

   public QResult getResult() {
      return this.result;
   }

   public void setResult(QResult result) {
      this.result = result;
      if (result == null) {
         QLog.l().logger().debug("Обозначать результат работы с кастомером не требуется");
      } else {
         QLog.l().logger().debug("Обозначили результат работы с кастомером: \"" + result.getName() + "\"");
      }
   }

   public QUser getUser() {
      return this.user;
   }

   public void setUser(QUser user) {
      this.user = user;
      QLog.l()
         .logger()
         .debug("Клиенту \"" + this.getFullNumber() + (user == null ? " юзера нету, еще он его не вызывал\"" : " опредилили юзера \"" + user.getName() + "\""));
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getFullNumber() {
      String div = "".equals(this.getPrefix()) ? "" : QConfig.cfg().getNumDivider(this.getPrefix());
      return "" + this.getPrefix() + (this.getNumber() < 1 ? "" : div + this.getNumber());
   }

   public void setPrefix(String prefix) {
      this.prefix = prefix == null ? "" : prefix;
   }

   public Date getStandTime() {
      return this.standTime;
   }

   public void setStandTime(Date date) {
      this.standTime = date;
   }

   public Date getStartTime() {
      return this.startTime;
   }

   public void setStartTime(Date date) {
      this.startTime = date;
   }

   public void setCallTime(Date date) {
      this.callTime = date;
   }

   public Date getCallTime() {
      return this.callTime;
   }

   public Date getFinishTime() {
      return this.finishTime;
   }

   public void setFinishTime(Date date) {
      this.finishTime = date;
   }

   public String getInput_data() {
      return this.input_data;
   }

   public void setInput_data(String input_data) {
      this.input_data = input_data;
   }

   public void addServiceForBack(QService service) {
      this.serviceBack.addFirst(service);
      this.needBack = !this.serviceBack.isEmpty();
   }

   public QService getServiceForBack() {
      this.needBack = this.serviceBack.size() > 1;
      return this.serviceBack.pollFirst();
   }

   public boolean needBack() {
      return this.needBack;
   }

   public String getTempComments() {
      return this.tempComments;
   }

   public void setTempComments(String tempComments) {
      this.tempComments = tempComments;
   }

   public String getPostponedStatus() {
      return this.postponedStatus;
   }

   public void setPostponedStatus(String postponedStatus) {
      this.postponedStatus = postponedStatus;
   }

   public int getPostponPeriod() {
      return this.postponPeriod;
   }

   public Long getIsMine() {
      return this.isMine;
   }

   public void setIsMine(Long userId) {
      this.isMine = userId;
   }

   public Integer getRecallCount() {
      return this.recallCount;
   }

   public void setRecallCount(Integer recallCount) {
      this.recallCount = recallCount;
   }

   public void upRecallCount() {
      Integer var2 = this.recallCount;
      Integer var3 = this.recallCount = this.recallCount + 1;
   }

   public void setPostponPeriod(int postponPeriod) {
      this.postponPeriod = postponPeriod;
      this.startPontpone = System.currentTimeMillis();
      this.finishPontpone = this.startPontpone + (long)(postponPeriod * 60 * 1000);
   }

   public long getStartPontpone() {
      return this.startPontpone;
   }

   public long getFinishPontpone() {
      return this.finishPontpone;
   }

   @Override
   public String toString() {
      return this.getFullNumber()
         + (this.getInput_data().isEmpty() ? "" : " " + this.getInput_data())
         + (
            this.postponedStatus.isEmpty()
               ? ""
               : " "
                  + this.postponedStatus
                  + " ("
                  + (this.postponPeriod > 0 ? this.postponPeriod : "...")
                  + " / "
                  + (System.currentTimeMillis() - this.startPontpone) / 1000L / 60L
                  + " min.)"
                  + (this.isMine != null ? " Private!" : "")
         );
   }

   @Override
   public String getName() {
      return this.getFullNumber() + " " + this.getInput_data();
   }

   public LinkedList<LinkedList<LinkedList<Long>>> getComplexId() {
      return this.complexId;
   }

   public void setComplexId(LinkedList<LinkedList<LinkedList<Long>>> complexId) {
      this.complexId = complexId;
   }

   public Integer getWaitingMinutes() {
      return new Long((System.currentTimeMillis() - this.getStandTime().getTime()) / 1000L / 60L + 1L).intValue();
   }
}
