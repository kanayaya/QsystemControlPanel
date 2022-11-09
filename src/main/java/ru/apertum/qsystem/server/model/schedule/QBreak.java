package ru.apertum.qsystem.server.model.schedule;

import java.io.Serializable;
import java.util.Date;
import ru.apertum.qsystem.common.Uses;
import ru.apertum.qsystem.server.model.IidGetter;

public class QBreak implements IidGetter, Serializable {
   private Long id;
   private Date from_time;
   private Date to_time;
   private QBreaks breaks;

   public QBreak(Date from_time, Date to_time, QBreaks breaks) {
      this.from_time = from_time;
      this.to_time = to_time;
      this.breaks = breaks;
   }

   public QBreak() {
   }

   @Override
   public Long getId() {
      return this.id;
   }

   public Date getFrom_time() {
      return this.from_time;
   }

   public void setFrom_time(Date from_time) {
      this.from_time = from_time;
   }

   public Date getTo_time() {
      return this.to_time;
   }

   public void setTo_time(Date to_time) {
      this.to_time = to_time;
   }

   @Override
   public String getName() {
      return Uses.FORMAT_HH_MM.format(this.from_time) + "-" + Uses.FORMAT_HH_MM.format(this.to_time);
   }

   @Override
   public String toString() {
      return Uses.FORMAT_HH_MM.format(this.from_time) + "-" + Uses.FORMAT_HH_MM.format(this.to_time);
   }

   public QBreaks getBreaks() {
      return this.breaks;
   }

   public void setBreaks(QBreaks breaks) {
      this.breaks = breaks;
   }

   public long diff() {
      return this.getTo_time().getTime() - this.getFrom_time().getTime();
   }
}
