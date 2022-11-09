package ru.apertum.qsystem.server.model.calendar;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.server.model.IidGetter;
import ru.apertum.qsystem.server.model.schedule.QSchedule;
import ru.apertum.qsystem.server.model.schedule.QSpecSchedule;

public class QCalendar implements IidGetter, Serializable {
   private Long id = new Date().getTime();
   private String name;
   private List<QSpecSchedule> specSchedules = new LinkedList<>();

   @Override
   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<QSpecSchedule> getSpecSchedules() {
      return this.specSchedules;
   }

   public void setSpecSchedules(List<QSpecSchedule> specSchedules) {
      this.specSchedules = specSchedules;
   }

   @Override
   public String toString() {
      return this.name;
   }

   @Override
   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof QCalendar)) {
         throw new TypeNotPresentException("Неправильный тип для сравнения", new ServerException("Неправильный тип для сравнения"));
      } else {
         return this.id.equals(((QCalendar)o).id);
      }
   }

   @Override
   public int hashCode() {
      return (int)(this.id != null ? this.id : 0L);
   }

   public boolean checkFreeDay(Date date) {
      return false;
   }

   public QSchedule getSpecSchedule(Date forDate) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(forDate);
      gc.set(11, 12);
      gc.set(12, 0);
      forDate = gc.getTime();

      for(QSpecSchedule sps : this.getSpecSchedules()) {
         gc.setTime(sps.getFrom());
         gc.set(11, 0);
         gc.set(12, 0);
         Date f = gc.getTime();
         gc.setTime(sps.getTo());
         gc.set(11, 23);
         gc.set(12, 59);
         if (f.before(forDate) && gc.getTime().after(forDate)) {
            return sps.getSchedule();
         }
      }

      return null;
   }
}
