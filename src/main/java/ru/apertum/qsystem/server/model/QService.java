package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import ru.apertum.qsystem.client.Locales;
import ru.apertum.qsystem.common.CustomerState;
import ru.apertum.qsystem.common.QLog;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.common.model.QCustomer;
import ru.apertum.qsystem.server.model.calendar.QCalendar;
import ru.apertum.qsystem.server.model.schedule.QBreak;
import ru.apertum.qsystem.server.model.schedule.QBreaks;
import ru.apertum.qsystem.server.model.schedule.QSchedule;
import ru.apertum.qsystem.swing.DefaultMutableTreeNode;
import ru.apertum.qsystem.swing.MutableTreeNode;
import ru.apertum.qsystem.swing.TreeNode;

public class QService extends DefaultMutableTreeNode implements ITreeIdGetter, Serializable {
   private static volatile int lastStNumber = Integer.MIN_VALUE;
   private final PriorityQueue<QCustomer> customers = new PriorityQueue<>();
   private final LinkedBlockingDeque<QCustomer> clients = new LinkedBlockingDeque<>(this.customers);
   @Expose
   @SerializedName("inner_services")
   private final LinkedList<QService> childrenOfService = new LinkedList<>();
   @Expose
   @SerializedName("id")
   private Long id = new Date().getTime();
   private Date deleted;
   @Expose
   @SerializedName("status")
   private Integer status;
   @Expose
   @SerializedName("point")
   private Integer point = 0;
   @Expose
   @SerializedName("duration")
   private Integer duration = 1;
   @Expose
   @SerializedName("exp")
   private Integer expectation = 0;
   @Expose
   @SerializedName("sound_template")
   private String soundTemplate;
   @Expose
   @SerializedName("advance_limit")
   private Integer advanceLimit = 1;
   @Expose
   @SerializedName("day_limit")
   private Integer dayLimit = 0;
   @Expose
   @SerializedName("person_day_limit")
   private Integer personDayLimit = 0;
   @Expose
   @SerializedName("advance_limit_period")
   private Integer advanceLimitPeriod = 0;
   @Expose
   @SerializedName("advance_time_period")
   private Integer advanceTimePeriod = 60;
   @Expose
   @SerializedName("enable")
   private Integer enable = 1;
   private Integer seqId = 0;
   @Expose
   @SerializedName("result_required")
   private Boolean result_required = false;
   @Expose
   @SerializedName("input_required")
   private Boolean input_required = false;
   @Expose
   @SerializedName("input_caption")
   private String input_caption = "";
   @Expose
   @SerializedName("pre_info_html")
   private String preInfoHtml = "";
   @Expose
   @SerializedName("pre_info_print_text")
   private String preInfoPrintText = "";
   @Expose
   @SerializedName("ticket_text")
   private String ticketText = "";
   @Expose
   @SerializedName("but_x")
   private Integer butX = 100;
   @Expose
   @SerializedName("but_y")
   private Integer butY = 100;
   @Expose
   @SerializedName("but_b")
   private Integer butB = 100;
   @Expose
   @SerializedName("but_h")
   private Integer butH = 100;
   private int lastNumber = Integer.MIN_VALUE;
   private final int day_y = -100;
   private final int dayAdvs = -100;
   @Expose
   @SerializedName("countPerDay")
   private int countPerDay = 0;
   @Expose
   @SerializedName("day")
   private int day = 0;
   @Expose
   @SerializedName("description")
   private String description;
   @Expose
   @SerializedName("service_prefix")
   private String prefix = "";
   @Expose
   @SerializedName("name")
   private String name;
   @Expose
   @SerializedName("buttonText")
   private String buttonText;
   private Long parentId;
   private QService link;
   private QSchedule schedule;
   private QCalendar calendar;
   @Expose
   @SerializedName("langs")
   private Set<QServiceLang> langs = new HashSet<>();
   private final HashMap<String, QServiceLang> qslangs = null;
   private String tempReasonUnavailable;
   private QService parentService;

   public static void clearNextStNumber() {
   }

   private PriorityQueue<QCustomer> getCustomers() {
      return this.customers;
   }

   public LinkedBlockingDeque<QCustomer> getClients() {
      return this.clients;
   }

   @Override
   public Long getId() {
      return this.id;
   }

   public final void setId(Long id) {
      this.id = id;
   }

   public Date getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Date deleted) {
      this.deleted = deleted;
   }

   public Integer getStatus() {
      return this.status;
   }

   public final void setStatus(Integer status) {
      this.status = status;
   }

   public Integer getPoint() {
      return this.point;
   }

   public void setPoint(Integer point) {
      this.point = point;
   }

   public Integer getDuration() {
      return this.duration;
   }

   public void setDuration(Integer duration) {
      this.duration = duration;
   }

   public Integer getExpectation() {
      return this.expectation;
   }

   public void setExpectation(Integer expectation) {
      this.expectation = expectation;
   }

   public String getSoundTemplate() {
      return this.soundTemplate;
   }

   public void setSoundTemplate(String soundTemplate) {
      this.soundTemplate = soundTemplate;
   }

   public Integer getAdvanceLimit() {
      return this.advanceLimit;
   }

   public void setAdvanceLinit(Integer advanceLimit) {
      this.advanceLimit = advanceLimit;
   }

   public Integer getDayLimit() {
      return this.dayLimit;
   }

   public void setDayLimit(Integer dayLimit) {
      this.dayLimit = dayLimit;
   }

   public Integer getPersonDayLimit() {
      return this.personDayLimit;
   }

   public void setPersonDayLimit(Integer personDayLimit) {
      this.personDayLimit = personDayLimit;
   }

   public Integer getAdvanceLimitPeriod() {
      return this.advanceLimitPeriod;
   }

   public void setAdvanceLimitPeriod(Integer advanceLimitPeriod) {
      this.advanceLimitPeriod = advanceLimitPeriod;
   }

   public Integer getAdvanceTimePeriod() {
      return this.advanceTimePeriod;
   }

   public void setAdvanceTimePeriod(Integer advanceTimePeriod) {
      this.advanceTimePeriod = advanceTimePeriod;
   }

   public Integer getEnable() {
      return this.enable;
   }

   public void setEnable(Integer enable) {
      this.enable = enable;
   }

   public Integer getSeqId() {
      return this.seqId;
   }

   public void setSeqId(Integer seqId) {
      this.seqId = seqId;
   }

   public Boolean getResult_required() {
      return this.result_required;
   }

   public void setResult_required(Boolean result_required) {
      this.result_required = result_required;
   }

   public Boolean getInput_required() {
      return this.input_required;
   }

   public void setInput_required(Boolean input_required) {
      this.input_required = input_required;
   }

   public String getInput_caption() {
      return this.input_caption;
   }

   public void setInput_caption(String input_caption) {
      this.input_caption = input_caption;
   }

   public String getPreInfoHtml() {
      return this.preInfoHtml;
   }

   public void setPreInfoHtml(String preInfoHtml) {
      this.preInfoHtml = preInfoHtml;
   }

   public String getPreInfoPrintText() {
      return this.preInfoPrintText;
   }

   public void setPreInfoPrintText(String preInfoPrintText) {
      this.preInfoPrintText = preInfoPrintText;
   }

   public String getTicketText() {
      return this.ticketText;
   }

   public void setTicketText(String ticketText) {
      this.ticketText = ticketText;
   }

   public Integer getButB() {
      return this.butB;
   }

   public void setButB(Integer butB) {
      this.butB = butB;
   }

   public Integer getButH() {
      return this.butH;
   }

   public void setButH(Integer butH) {
      this.butH = butH;
   }

   public Integer getButX() {
      return this.butX;
   }

   public void setButX(Integer butX) {
      this.butX = butX;
   }

   public Integer getButY() {
      return this.butY;
   }

   public void setButY(Integer butY) {
      this.butY = butY;
   }

   @Override
   public String toString() {
      return this.getName().trim().isEmpty() ? "<NO_NAME>" : this.getName();
   }

   public int getNextNumber() {
      return 0;
   }

   public int getAdvancedCount(Date date, boolean strictStart) {
      return 0;
   }

   public boolean isLimitPersonPerDayOver(String data) {
      int today = new GregorianCalendar().get(6);
      if (today != this.day) {
         this.day = today;
         this.setCountPerDay(0);
      }

      return this.getPersonDayLimit() != 0 && this.getPersonDayLimit() <= this.getCountPersonsPerDay(data);
   }

   private int getCountPersonsPerDay(String data) {
      return 0;
   }

   public boolean isLimitPerDayOver() {
      Date now = new Date();
      int advCusts = this.getAdvancedCount(now, true);
      int today = new GregorianCalendar().get(6);
      if (today != this.day) {
         this.day = today;
         this.setCountPerDay(0);
      }

      long p = this.getPossibleTickets();
      long c = (long)this.getCountPerDay();
      boolean f = this.getDayLimit() != 0 && p <= c + (long)advCusts;
      if (f) {
         QLog.l()
            .logger()
            .trace("Customers overflow: DayLimit()=" + this.getDayLimit() + " && PossibleTickets=" + p + " <= CountPerDay=" + c + " + advCusts=" + advCusts);
      }

      return f;
   }

   public long getPossibleTickets() {
      if (this.getDayLimit() == 0) {
         return 2147483647L;
      } else {
         GregorianCalendar gc = new GregorianCalendar();
         Date now = new Date();
         gc.setTime(now);
         long dif = this.getSchedule().getWorkInterval(gc.getTime()).finish.getTime() - now.getTime();
         int ii = gc.get(7) - 1;
         if (ii < 1) {
            ii = 7;
         }

         QBreaks qb;
         switch(ii) {
            case 1:
               qb = this.getSchedule().getBreaks_1();
               break;
            case 2:
               qb = this.getSchedule().getBreaks_2();
               break;
            case 3:
               qb = this.getSchedule().getBreaks_3();
               break;
            case 4:
               qb = this.getSchedule().getBreaks_4();
               break;
            case 5:
               qb = this.getSchedule().getBreaks_5();
               break;
            case 6:
               qb = this.getSchedule().getBreaks_6();
               break;
            case 7:
               qb = this.getSchedule().getBreaks_7();
               break;
            default:
               throw new AssertionError();
         }

         if (qb != null) {
            for(QBreak br : qb.getBreaks()) {
               if (br.getTo_time().after(now)) {
                  if (br.getFrom_time().before(now)) {
                     dif -= br.getTo_time().getTime() - now.getTime();
                  } else {
                     dif -= br.diff();
                  }
               }
            }
         }

         QLog.l()
            .logger()
            .trace(
               "Осталось рабочего времени "
                  + dif / 1000L / 60L
                  + " минут. Если на каждого "
                  + this.getDayLimit()
                  + " минут, то остается принять "
                  + dif / 1000L / 60L / (long)this.getDayLimit().intValue()
                  + " посетителей."
            );
         return dif / 1000L / 60L / (long)this.getDayLimit().intValue();
      }
   }

   public int getCountPerDay() {
      return this.countPerDay;
   }

   public void setCountPerDay(int countPerDay) {
      this.countPerDay = countPerDay;
   }

   public int getDay() {
      return this.day;
   }

   public void setDay(int day) {
      this.day = day;
   }

   public void clearNextNumber() {
   }

   public void addCustomerForRecoveryOnly(QCustomer customer) {
      if (customer.getPrefix() != null) {
         int number = customer.getNumber();
         if (CustomerState.STATE_REDIRECT != customer.getState()
            && CustomerState.STATE_WAIT_AFTER_POSTPONED != customer.getState()
            && CustomerState.STATE_WAIT_COMPLEX_SERVICE != customer.getState()) {
            if (number > this.lastNumber) {
               this.lastNumber = number;
            }

            if (number > lastStNumber) {
               lastStNumber = number;
            }
         }
      }

      this.addCustomer(customer);
   }

   public void addCustomer(QCustomer customer) {
      if (customer.getPrefix() == null) {
         customer.setPrefix(this.getPrefix());
      }

      if (!this.getCustomers().add(customer)) {
         throw new ServerException("Невозможно добавить нового кастомера в хранилище кастомеров.");
      } else {
         QCustomer before = null;
         QCustomer after = null;

         for(QCustomer c : this.getCustomers()) {
            if (!customer.getId().equals(c.getId())) {
               if (customer.compareTo(c) == 1) {
                  if (before == null) {
                     before = c;
                  } else if (before.compareTo(c) == -1) {
                     before = c;
                  }
               } else if (customer.compareTo(c) != 0) {
                  if (after == null) {
                     after = c;
                  } else if (after.compareTo(c) == 1) {
                     after = c;
                  }
               }
            }
         }

         this.clients.clear();
         this.clients.addAll(this.getCustomers());
      }
   }

   public void freeCustomers() {
      this.getCustomers().clear();
      this.clients.clear();
      this.clients.addAll(this.getCustomers());
   }

   public QCustomer getCustomer() {
      return this.getCustomers().element();
   }

   public QCustomer removeCustomer() {
      QCustomer customer = this.getCustomers().remove();
      this.clients.clear();
      this.clients.addAll(this.getCustomers());
      return customer;
   }

   public QCustomer peekCustomer() {
      return this.getCustomers().peek();
   }

   public QCustomer polCustomer() {
      QCustomer customer = this.getCustomers().poll();
      this.clients.clear();
      this.clients.addAll(this.getCustomers());
      return customer;
   }

   public boolean removeCustomer(QCustomer customer) {
      Boolean res = this.getCustomers().remove(customer);
      this.clients.clear();
      this.clients.addAll(this.getCustomers());
      return res;
   }

   public int getCountCustomers() {
      return this.getCustomers().size();
   }

   public boolean changeCustomerPriorityByNumber(String number, int newPriority) {
      for(QCustomer customer : this.getCustomers()) {
         if (number.equalsIgnoreCase(customer.getPrefix() + customer.getNumber())) {
            customer.setPriority(newPriority);
            this.removeCustomer(customer);
            this.addCustomer(customer);
            return true;
         }
      }

      return false;
   }

   public QCustomer gnawOutCustomerByNumber(String number) {
      for(QCustomer customer : this.getCustomers()) {
         if (number.equalsIgnoreCase(customer.getPrefix() + customer.getNumber())) {
            this.removeCustomer(customer);
            return customer;
         }
      }

      return null;
   }

   public String getDescription() {
      return this.description;
   }

   public final void setDescription(String description) {
      this.description = description;
   }

   public String getPrefix() {
      return this.prefix == null ? "" : this.prefix;
   }

   public final void setPrefix(String prefix) {
      this.prefix = prefix == null ? "" : prefix;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public final void setName(String name) {
      this.name = name;
   }

   public String getButtonText() {
      return this.buttonText;
   }

   public final void setButtonText(String buttonText) {
      this.buttonText = buttonText;
   }

   @Override
   public Long getParentId() {
      return this.parentId;
   }

   public void setParentId(Long parentId) {
      this.parentId = parentId;
   }

   public QService getLink() {
      return this.link;
   }

   public void setLink(QService link) {
      this.link = link;
   }

   public QSchedule getSchedule() {
      return this.schedule;
   }

   public void setSchedule(QSchedule schedule) {
      this.schedule = schedule;
   }

   public QCalendar getCalendar() {
      return this.calendar;
   }

   public void setCalendar(QCalendar calendar) {
      this.calendar = calendar;
   }

   public Set<QServiceLang> getLangs() {
      return this.langs;
   }

   public void setLangs(Set<QServiceLang> langs) {
      this.langs = langs;
   }

   public QServiceLang getServiceLang(String nameLocale) {
      return this.qslangs.get(nameLocale);
   }

   public String getTextToLocale(QService.Field field) {
      String nl = Locales.getInstance().getNameOfPresentLocale();
      QServiceLang sl = this.getServiceLang(nl);
      switch(field) {
         case BUTTON_TEXT:
            return Locales.getInstance().isWelcomeMultylangs() && sl != null && sl.getButtonText() != null && !sl.getButtonText().isEmpty()
               ? sl.getButtonText()
               : this.getButtonText();
         case INPUT_CAPTION:
            return Locales.getInstance().isWelcomeMultylangs() && sl != null && sl.getInput_caption() != null && !sl.getInput_caption().isEmpty()
               ? sl.getInput_caption()
               : this.getInput_caption();
         case PRE_INFO_HTML:
            return Locales.getInstance().isWelcomeMultylangs() && sl != null && sl.getPreInfoHtml() != null && !sl.getPreInfoHtml().isEmpty()
               ? sl.getPreInfoHtml()
               : this.getPreInfoHtml();
         case PRE_INFO_PRINT_TEXT:
            return Locales.getInstance().isWelcomeMultylangs() && sl != null && sl.getPreInfoPrintText() != null && !sl.getPreInfoPrintText().isEmpty()
               ? sl.getPreInfoPrintText()
               : this.getPreInfoPrintText();
         case TICKET_TEXT:
            return Locales.getInstance().isWelcomeMultylangs() && sl != null && sl.getTicketText() != null && !sl.getTicketText().isEmpty()
               ? sl.getTicketText()
               : this.getTicketText();
         case DESCRIPTION:
            return Locales.getInstance().isWelcomeMultylangs() && sl != null && sl.getDescription() != null && !sl.getDescription().isEmpty()
               ? sl.getDescription()
               : this.getDescription();
         case NAME:
            return Locales.getInstance().isWelcomeMultylangs() && sl != null && sl.getName() != null && !sl.getName().isEmpty()
               ? sl.getName()
               : this.getName();
         default:
            throw new AssertionError();
      }
   }

   public String getTempReasonUnavailable() {
      return this.tempReasonUnavailable;
   }

   public void setTempReasonUnavailable(String tempReasonUnavailable) {
      this.tempReasonUnavailable = tempReasonUnavailable;
   }

   public LinkedList<QService> getChildren() {
      return this.childrenOfService;
   }

   @Override
   public void addChild(ITreeIdGetter child) {
      if (!this.childrenOfService.contains((QService)child)) {
         this.childrenOfService.add((QService)child);
      }
   }

   public QService getChildAt(int childIndex) {
      return this.childrenOfService.get(childIndex);
   }

   @Override
   public int getChildCount() {
      return this.childrenOfService.size();
   }

   public QService getParent() {
      return this.parentService;
   }

   @Override
   public void setParent(MutableTreeNode newParent) {
      this.parentService = (QService)newParent;
      if (this.parentService != null) {
         this.setParentId(this.parentService.id);
      } else {
         this.parentId = null;
      }
   }

   @Override
   public int getIndex(TreeNode node) {
      return this.childrenOfService.indexOf(node);
   }

   @Override
   public boolean getAllowsChildren() {
      return true;
   }

   @Override
   public boolean isLeaf() {
      return this.getChildCount() == 0;
   }

   @Override
   public Enumeration children() {
      return Collections.enumeration(this.childrenOfService);
   }

   @Override
   public void insert(MutableTreeNode child, int index) {
      child.setParent(this);
      this.childrenOfService.add(index, (QService)child);
   }

   @Override
   public void remove(int index) {
      this.childrenOfService.remove(index);
   }

   @Override
   public void remove(MutableTreeNode node) {
      this.childrenOfService.remove((QService)node);
   }

   @Override
   public void removeFromParent() {
      this.getParent().remove(this.getParent().getIndex(this));
   }

   public static enum Field {
      BUTTON_TEXT,
      INPUT_CAPTION,
      PRE_INFO_HTML,
      PRE_INFO_PRINT_TEXT,
      TICKET_TEXT,
      DESCRIPTION,
      NAME;
   }
}
