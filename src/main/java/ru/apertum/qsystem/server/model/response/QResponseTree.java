package ru.apertum.qsystem.server.model.response;

import java.util.LinkedList;
import ru.apertum.qsystem.server.controller.IServerListener;
import ru.apertum.qsystem.server.controller.ServerEvents;
import ru.apertum.qsystem.server.model.ATreeModel;

public class QResponseTree extends ATreeModel<QRespItem> {
   private QResponseTree() {
      ServerEvents.getInstance().registerListener(new IServerListener() {
         @Override
         public void restartEvent() {
            QResponseTree.this.createTree();
         }
      });
   }

   public static QResponseTree getInstance() {
      return QResponseTree.QInfoTreeHolder.INSTANCE;
   }

   public static void formTree(QRespItem root) {
      for(QRespItem resp : root.getChildren()) {
         resp.setParent(root);
         formTree(resp);
      }
   }

   @Override
   protected LinkedList<QRespItem> load() {
      return null;
   }

   @Override
   public void save() {
   }

   private static class QInfoTreeHolder {
      private static final QResponseTree INSTANCE = new QResponseTree();
   }
}
