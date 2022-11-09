package ru.apertum.qsystem.server.model.calendar;

import java.util.LinkedList;
import ru.apertum.qsystem.server.model.ATListModel;

public class QCalendarList extends ATListModel<QCalendar> {
   private QCalendar selected;

   private QCalendarList() {
   }

   public static QCalendarList getInstance() {
      return QCalendarList.QCalendarListHolder.INSTANCE;
   }

   @Override
   protected LinkedList<QCalendar> load() {
      return null;
   }

   public Object getSelectedItem() {
      return this.selected;
   }

   public void setSelectedItem(Object anItem) {
      this.selected = (QCalendar)anItem;
   }

   private static class QCalendarListHolder {
      private static final QCalendarList INSTANCE = new QCalendarList();
   }
}
