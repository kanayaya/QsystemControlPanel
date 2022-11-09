package ru.apertum.qsystem.server.model.schedule;

import java.util.ArrayList;
import java.util.LinkedList;
import ru.apertum.qsystem.server.model.ATListModel;

public class QBreaksList extends ATListModel<QBreaks> {
   private final ArrayList<QBreak> breakForDel = new ArrayList<>();
   private QBreaks selected;

   private QBreaksList() {
   }

   public static QBreaksList getInstance() {
      return QBreaksList.QBreaksListHolder.INSTANCE;
   }

   @Override
   protected LinkedList<QBreaks> load() {
      return null;
   }

   public Object getSelectedItem() {
      return this.selected;
   }

   public void setSelectedItem(Object anItem) {
      this.selected = (QBreaks)anItem;
   }

   @Override
   public void save() {
   }

   public void addBreakForDelete(QBreak qbreak) {
      this.breakForDel.add(qbreak);
   }

   private static class QBreaksListHolder {
      private static final QBreaksList INSTANCE = new QBreaksList();
   }
}
