package ru.apertum.qsystem.server.model.infosystem;

import java.util.LinkedList;
import ru.apertum.qsystem.server.controller.IServerListener;
import ru.apertum.qsystem.server.controller.ServerEvents;
import ru.apertum.qsystem.server.model.ATreeModel;

public class QInfoTree extends ATreeModel<QInfoItem> {
   private QInfoTree() {
      ServerEvents.getInstance().registerListener(new IServerListener() {
         @Override
         public void restartEvent() {
            QInfoTree.this.createTree();
         }
      });
   }

   public static QInfoTree getInstance() {
      return QInfoTree.QInfoTreeHolder.INSTANCE;
   }

   @Override
   protected LinkedList<QInfoItem> load() {
      return null;
   }

   private static class QInfoTreeHolder {
      private static final QInfoTree INSTANCE = new QInfoTree();
   }
}
