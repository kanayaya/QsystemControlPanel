package ru.apertum.qsystem.server.model.calendar;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class FreeDay implements Serializable {
   private Long id;
   private Date date;
   private Long calendarId;

   @Override
   public String toString() {
      return this.date.toString();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof FreeDay) {
         FreeDay f = (FreeDay)obj;
         return this.hashCode() == f.hashCode();
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(this.date);
      return (int)((long)(gc.get(1) * 1000 + gc.get(6)) + this.getCalendarId() * 100000000L);
   }

   public FreeDay() {
   }

   public FreeDay(Date date, Long calendarId) {
      this.date = date;
      this.calendarId = calendarId;
   }

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

   public Long getCalendarId() {
      return this.calendarId;
   }

   public void setCalendarId(Long calendarId) {
      this.calendarId = calendarId;
   }

   public boolean equals(Date date) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date);
      GregorianCalendar gc2 = new GregorianCalendar();
      gc2.setTime(this.getDate());
      return gc.get(6) == gc2.get(6) && gc.get(1) == gc2.get(1);
   }
}
