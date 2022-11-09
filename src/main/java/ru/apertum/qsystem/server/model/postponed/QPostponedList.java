package ru.apertum.qsystem.server.model.postponed;

import java.util.LinkedList;
import java.util.Timer;
import ru.apertum.qsystem.common.model.QCustomer;
import ru.apertum.qsystem.swing.DefaultListModel;

public class QPostponedList extends DefaultListModel {
   private Timer timerOut;

   private QPostponedList() {
   }

   public static QPostponedList getInstance() {
      return QPostponedList.QPostponedListHolder.INSTANCE;
   }

   public QPostponedList loadPostponedList(LinkedList<QCustomer> customers) {
      this.clear();

      for(QCustomer cust : customers) {
         boolean fl = true;

         for(int i = 0; i < this.size(); ++i) {
            QCustomer inn = (QCustomer)this.get(i);
            if (inn.getPostponedStatus().compareTo(cust.getPostponedStatus()) > 0) {
               this.add(i, cust);
               fl = false;
               break;
            }
         }

         if (fl) {
            this.addElement(cust);
         }
      }

      return this;
   }

   public LinkedList<QCustomer> getPostponedCustomers() {
      return new LinkedList<>();
   }

   public QCustomer getById(long id) {
      for(Object object : this.toArray()) {
         QCustomer c = (QCustomer)object;
         if (id == c.getId()) {
            return c;
         }
      }

      return null;
   }

   private static class QPostponedListHolder {
      private static final QPostponedList INSTANCE = new QPostponedList();
   }
}
