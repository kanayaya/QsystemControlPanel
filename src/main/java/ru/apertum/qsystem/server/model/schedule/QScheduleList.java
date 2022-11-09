package ru.apertum.qsystem.server.model.schedule;

import java.util.LinkedList;
import ru.apertum.qsystem.server.model.ATListModel;

public class QScheduleList extends ATListModel<QSchedule> {
   private QSchedule selected;

   private QScheduleList() {
   }

   public static QScheduleList getInstance() {
      return QScheduleList.QScheduleListHolder.INSTANCE;
   }

   @Override
   protected LinkedList<QSchedule> load() {
      return null;
   }

   public Object getSelectedItem() {
      return this.selected;
   }

   public void setSelectedItem(Object anItem) {
      this.selected = (QSchedule)anItem;
   }

   private static class QScheduleListHolder {
      private static final QScheduleList INSTANCE = new QScheduleList();
   }
}
