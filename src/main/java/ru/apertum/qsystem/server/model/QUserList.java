package ru.apertum.qsystem.server.model;

import java.util.LinkedList;

public class QUserList extends ATListModel<QUser> {
   private QUserList() {
   }

   public static QUserList getInstance() {
      return QUserList.QUserListHolder.INSTANCE;
   }

   @Override
   protected LinkedList<QUser> load() {
      return null;
   }

   @Override
   public void save() {
   }

   private static class QUserListHolder {
      private static final QUserList INSTANCE = new QUserList();
   }
}
