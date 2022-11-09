package ru.apertum.qsystem.server.model.schedule;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.server.model.IidGetter;

public class QSchedule implements IidGetter, Serializable {
   private Long id = new Date().getTime();
   private String name;
   private Integer type;
   private Date time_begin_1;
   private Date time_end_1;
   private Date time_begin_2;
   private Date time_end_2;
   private Date time_begin_3;
   private Date time_end_3;
   private Date time_begin_4;
   private Date time_end_4;
   private Date time_begin_5;
   private Date time_end_5;
   private Date time_begin_6;
   private Date time_end_6;
   private Date time_begin_7;
   private Date time_end_7;
   private QBreaks breaks_1;
   private QBreaks breaks_2;
   private QBreaks breaks_3;
   private QBreaks breaks_4;
   private QBreaks breaks_5;
   private QBreaks breaks_6;
   private QBreaks breaks_7;

   @Override
   public Long getId() {
      return this.id;
   }

   @Override
   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof QSchedule)) {
         throw new TypeNotPresentException("Неправильный тип для сравнения", new ServerException("Неправильный тип для сравнения"));
      } else {
         return this.id.equals(((QSchedule)o).id);
      }
   }

   @Override
   public int hashCode() {
      return (int)(this.id != null ? this.id : 0L);
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

   @Override
   public String toString() {
      return this.name;
   }

   public Integer getType() {
      return this.type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public QSchedule.Interval getWorkInterval(Date date) {
      GregorianCalendar gc_day = new GregorianCalendar();
      gc_day.setTime(date);
      QSchedule.Interval in;
      if (this.getType() == 1) {
         if (0 == gc_day.get(5) % 2) {
            in = new QSchedule.Interval(this.getTime_begin_1(), this.getTime_end_1());
         } else {
            in = new QSchedule.Interval(this.getTime_begin_2(), this.getTime_end_2());
         }
      } else {
         switch(gc_day.get(7)) {
            case 1:
               in = new QSchedule.Interval(this.getTime_begin_7(), this.getTime_end_7());
               break;
            case 2:
               in = new QSchedule.Interval(this.getTime_begin_1(), this.getTime_end_1());
               break;
            case 3:
               in = new QSchedule.Interval(this.getTime_begin_2(), this.getTime_end_2());
               break;
            case 4:
               in = new QSchedule.Interval(this.getTime_begin_3(), this.getTime_end_3());
               break;
            case 5:
               in = new QSchedule.Interval(this.getTime_begin_4(), this.getTime_end_4());
               break;
            case 6:
               in = new QSchedule.Interval(this.getTime_begin_5(), this.getTime_end_5());
               break;
            case 7:
               in = new QSchedule.Interval(this.getTime_begin_6(), this.getTime_end_6());
               break;
            default:
               throw new ServerException("32-е мая!");
         }
      }

      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(in.start);
      gc_day.set(11, gc.get(11));
      gc_day.set(12, gc.get(12));
      gc_day.set(13, 0);
      Date ds = gc_day.getTime();
      gc.setTime(in.finish);
      gc_day.setTime(date);
      gc_day.set(11, gc.get(11));
      gc_day.set(12, gc.get(12));
      gc_day.set(13, 0);
      return new QSchedule.Interval(ds, gc_day.getTime());
   }

   public boolean inBreak(Date date) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date);
      gc.add(13, 3);
      gc.set(1, 0);
      gc.set(2, 0);
      gc.set(6, 0);
      int ii = gc.get(7) - 1;
      if (ii < 1) {
         ii = 7;
      }

      QBreaks qb;
      switch(ii) {
         case 1:
            qb = this.getBreaks_1();
            break;
         case 2:
            qb = this.getBreaks_2();
            break;
         case 3:
            qb = this.getBreaks_3();
            break;
         case 4:
            qb = this.getBreaks_4();
            break;
         case 5:
            qb = this.getBreaks_5();
            break;
         case 6:
            qb = this.getBreaks_6();
            break;
         case 7:
            qb = this.getBreaks_7();
            break;
         default:
            throw new AssertionError();
      }

      if (qb != null) {
         for(QBreak br : qb.getBreaks()) {
            GregorianCalendar gc1 = new GregorianCalendar();
            gc1.setTime(br.getFrom_time());
            gc1.set(1, 0);
            gc1.set(2, 0);
            gc1.set(6, 0);
            GregorianCalendar gc2 = new GregorianCalendar();
            gc2.setTime(br.getTo_time());
            gc2.set(1, 0);
            gc2.set(2, 0);
            gc2.set(6, 0);
            if (gc1.getTime().before(gc.getTime()) && gc2.getTime().after(gc.getTime())) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean inBreak(QSchedule.Interval interval) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(interval.finish);
      gc.add(13, -3);
      return this.inBreak(interval.start) || this.inBreak(gc.getTime());
   }

   public boolean inBreak(Date start, Date finish) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(finish);
      gc.add(13, -3);
      return this.inBreak(start) || this.inBreak(gc.getTime());
   }

   public Date getTime_begin_1() {
      return this.time_begin_1;
   }

   public void setTime_begin_1(Date time_begin_1) {
      this.time_begin_1 = time_begin_1;
   }

   public Date getTime_end_1() {
      return this.time_end_1;
   }

   public void setTime_end_1(Date time_end_1) {
      this.time_end_1 = time_end_1;
   }

   public Date getTime_begin_2() {
      return this.time_begin_2;
   }

   public void setTime_begin_2(Date time_begin_2) {
      this.time_begin_2 = time_begin_2;
   }

   public Date getTime_end_2() {
      return this.time_end_2;
   }

   public void setTime_end_2(Date time_end_2) {
      this.time_end_2 = time_end_2;
   }

   public Date getTime_begin_3() {
      return this.time_begin_3;
   }

   public void setTime_begin_3(Date time_begin_3) {
      this.time_begin_3 = time_begin_3;
   }

   public Date getTime_end_3() {
      return this.time_end_3;
   }

   public void setTime_end_3(Date time_end_3) {
      this.time_end_3 = time_end_3;
   }

   public Date getTime_begin_4() {
      return this.time_begin_4;
   }

   public void setTime_begin_4(Date time_begin_4) {
      this.time_begin_4 = time_begin_4;
   }

   public Date getTime_end_4() {
      return this.time_end_4;
   }

   public void setTime_end_4(Date time_end_4) {
      this.time_end_4 = time_end_4;
   }

   public Date getTime_begin_5() {
      return this.time_begin_5;
   }

   public void setTime_begin_5(Date time_begin_5) {
      this.time_begin_5 = time_begin_5;
   }

   public Date getTime_end_5() {
      return this.time_end_5;
   }

   public void setTime_end_5(Date time_end_5) {
      this.time_end_5 = time_end_5;
   }

   public Date getTime_begin_6() {
      return this.time_begin_6;
   }

   public void setTime_begin_6(Date time_begin_6) {
      this.time_begin_6 = time_begin_6;
   }

   public Date getTime_end_6() {
      return this.time_end_6;
   }

   public void setTime_end_6(Date time_end_6) {
      this.time_end_6 = time_end_6;
   }

   public Date getTime_begin_7() {
      return this.time_begin_7;
   }

   public void setTime_begin_7(Date time_begin_7) {
      this.time_begin_7 = time_begin_7;
   }

   public Date getTime_end_7() {
      return this.time_end_7;
   }

   public void setTime_end_7(Date time_end_7) {
      this.time_end_7 = time_end_7;
   }

   public QBreaks getBreaks_1() {
      return this.breaks_1;
   }

   public void setBreaks_1(QBreaks breaks_1) {
      this.breaks_1 = breaks_1;
   }

   public QBreaks getBreaks_2() {
      return this.breaks_2;
   }

   public void setBreaks_2(QBreaks breaks_2) {
      this.breaks_2 = breaks_2;
   }

   public QBreaks getBreaks_3() {
      return this.breaks_3;
   }

   public void setBreaks_3(QBreaks breaks_3) {
      this.breaks_3 = breaks_3;
   }

   public QBreaks getBreaks_4() {
      return this.breaks_4;
   }

   public void setBreaks_4(QBreaks breaks_4) {
      this.breaks_4 = breaks_4;
   }

   public QBreaks getBreaks_5() {
      return this.breaks_5;
   }

   public void setBreaks_5(QBreaks breaks_5) {
      this.breaks_5 = breaks_5;
   }

   public QBreaks getBreaks_6() {
      return this.breaks_6;
   }

   public void setBreaks_6(QBreaks breaks_6) {
      this.breaks_6 = breaks_6;
   }

   public QBreaks getBreaks_7() {
      return this.breaks_7;
   }

   public void setBreaks_7(QBreaks breaks_7) {
      this.breaks_7 = breaks_7;
   }

   public static class Interval {
      public final Date start;
      public final Date finish;

      public Interval(Date start, Date finish) {
         if (start != null && finish != null) {
            if (finish.before(start)) {
               throw new ServerException("Finish date " + finish + " before than start date " + start);
            }

            this.start = start;
            this.finish = finish;
         } else {
            this.start = new Date(111L);
            this.finish = new Date(222L);
         }
      }

      public long diff() {
         return this.finish.getTime() - this.start.getTime();
      }
   }
}
