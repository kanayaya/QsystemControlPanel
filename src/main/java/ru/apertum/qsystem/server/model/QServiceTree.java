package ru.apertum.qsystem.server.model;

import java.util.LinkedList;

public class QServiceTree extends ATreeModel<QService> {
   public static QServiceTree getInstance() {
      return QServiceTree.QServiceTreeHolder.INSTANCE;
   }

   @Override
   protected LinkedList<QService> load() {
      return new LinkedList<>();
   }

   @Override
   public void save() {
   }

   private QServiceTree() {
   }

   private static class QServiceTreeHolder {
      private static final QServiceTree INSTANCE = new QServiceTree();
   }
}
