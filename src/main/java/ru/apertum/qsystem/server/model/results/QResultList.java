package ru.apertum.qsystem.server.model.results;

import java.util.LinkedList;
import ru.apertum.qsystem.server.model.ATListModel;

public class QResultList extends ATListModel<QResult> {
   private QResult selected;

   private QResultList() {
   }

   public static QResultList getInstance() {
      return QResultList.QResultListHolder.INSTANCE;
   }

   @Override
   protected LinkedList<QResult> load() {
      return null;
   }

   public void setSelectedItem(Object anItem) {
      this.selected = (QResult)anItem;
   }

   public Object getSelectedItem() {
      return this.selected;
   }

   private static class QResultListHolder {
      private static final QResultList INSTANCE = new QResultList();
   }
}
