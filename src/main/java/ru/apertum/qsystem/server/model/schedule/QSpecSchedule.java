package ru.apertum.qsystem.server.model.schedule;

import java.io.Serializable;
import java.util.Date;
import ru.apertum.qsystem.common.Uses;
import ru.apertum.qsystem.server.model.IidGetter;
import ru.apertum.qsystem.server.model.calendar.QCalendar;

public class QSpecSchedule implements IidGetter, Serializable {
   private Long id;
   private QSchedule schedule;
   private QCalendar calendar;
   private Date from;
   private Date to;

   @Override
   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public String getName() {
      return this.getId() == null ? "NEW" : this.schedule.getName();
   }

   @Override
   public String toString() {
      return Uses.FORMAT_DD_MM_YYYY.format(this.getFrom()) + " - " + Uses.FORMAT_DD_MM_YYYY.format(this.getTo()) + "   " + this.getSchedule();
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

   public Date getFrom() {
      return this.from;
   }

   public void setFrom(Date from) {
      this.from = from;
   }

   public Date getTo() {
      return this.to;
   }

   public void setTo(Date to) {
      this.to = to;
   }
}
