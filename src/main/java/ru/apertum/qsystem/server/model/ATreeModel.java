package ru.apertum.qsystem.server.model;

import java.util.LinkedList;
import ru.apertum.qsystem.common.QLog;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.server.controller.IServerListener;
import ru.apertum.qsystem.server.controller.ServerEvents;
import ru.apertum.qsystem.swing.DefaultTreeModel;
import ru.apertum.qsystem.swing.MutableTreeNode;
import ru.apertum.qsystem.swing.TreeNode;

public abstract class ATreeModel<T extends ITreeIdGetter> extends DefaultTreeModel {
   protected final LinkedList<T> deleted = new LinkedList<>();

   protected ATreeModel() {
      super(null);
      this.createTree();
      ServerEvents.getInstance().registerListener(new IServerListener() {
         @Override
         public void restartEvent() {
            ATreeModel.this.createTree();
         }
      });
   }

   protected abstract LinkedList<T> load();

   protected final void createTree() {
      LinkedList<T> nodes = this.load();

      for(T node : nodes) {
         if (node.getParentId() == null) {
            this.setRoot(node);
            break;
         }
      }

      this.bildTree(this.getRoot(), nodes);
      QLog.l().logger().info("Создали дерево.");
   }

   private void bildTree(T root, LinkedList<T> nodes) {
      for(T node : nodes) {
         if (root.getId().equals(node.getParentId())) {
            node.setParent(root);
            root.addChild(node);
            this.bildTree(node, nodes);
         }
      }
   }

   public LinkedList<T> getNodes() {
      final LinkedList<T> nodes = new LinkedList<>();
      sailToStorm(this.root, new ISailListener() {
         @Override
         public void actionPerformed(TreeNode service) {
            nodes.add((T)service);
         }
      });
      return nodes;
   }

   public T getById(long id) {
      for(T node : this.getNodes()) {
         if (id == node.getId()) {
            return node;
         }
      }

      throw new ServerException("Не найдена услуга по ID \"" + id + "\"");
   }

   public boolean hasById(long id) {
      for(T node : this.getNodes()) {
         if (id == node.getId()) {
            return true;
         }
      }

      return false;
   }

   public boolean hasByName(String name) {
      for(T node : this.getNodes()) {
         if (name.equals(node.getName())) {
            return true;
         }
      }

      return false;
   }

   public int size() {
      return this.getNodes().size();
   }

   public static void sailToStorm(TreeNode root, ISailListener listener) {
      seil(root, listener);
   }

   private static void seil(TreeNode parent, ISailListener listener) {
      listener.actionPerformed(parent);

      for(int i = 0; i < parent.getChildCount(); ++i) {
         seil(parent.getChildAt(i), listener);
      }
   }

   public T getRoot() {
      return (T)super.getRoot();
   }

   public T getChild(Object parent, int index) {
      return (T)((TreeNode)parent).getChildAt(index);
   }

   @Override
   public int getChildCount(Object parent) {
      return ((TreeNode)parent).getChildCount();
   }

   @Override
   public boolean isLeaf(Object node) {
      return ((TreeNode)node).isLeaf();
   }

   @Override
   public int getIndexOfChild(Object parent, Object child) {
      return ((TreeNode)parent).getIndex((TreeNode)child);
   }

   @Override
   public void removeNodeFromParent(MutableTreeNode node) {
      sailToStorm(node, new ISailListener() {
         @Override
         public void actionPerformed(TreeNode service) {
            ATreeModel.this.deleted.add((T)service);
         }
      });
      this.deleted.add((T)node);
      super.removeNodeFromParent(node);
      this.updateSeqSibling((QService)node.getParent());
   }

   @Override
   public void insertNodeInto(MutableTreeNode newChild, MutableTreeNode parent, int index) {
      super.insertNodeInto(newChild, parent, index);
      if (parent instanceof QService) {
         this.updateSeqSibling((QService)parent);
      }
   }

   public void moveNode(MutableTreeNode moveChild, MutableTreeNode parent, int index) {
      if (((QService)moveChild.getParent()).getId().equals(((QService)parent).getId())) {
         int pos = parent.getIndex(moveChild);
         super.removeNodeFromParent(moveChild);
         super.insertNodeInto(moveChild, parent, index - (pos < index ? 1 : 0));
         this.updateSeqSibling((QService)parent);
      } else {
         QService prt = (QService)moveChild.getParent();
         int pos = prt.getIndex(moveChild);
         prt.remove(moveChild);
         this.nodesWereRemoved(prt, new int[]{pos}, new Object[]{moveChild});
         this.insertNodeInto(moveChild, parent, index - (pos < index ? 1 : 0));
         this.updateSeqSibling((QService)parent);
         this.updateSeqSibling(prt);
      }
   }

   public void updateSeqSibling(QService parent) {
      for(QService sib = (QService)parent.getFirstChild(); sib != null; sib = (QService)sib.getNextSibling()) {
         sib.setSeqId(parent.getIndex(sib));
      }
   }

   public void save() {
      LinkedList<T> del = new LinkedList<>();

      for(T t : this.deleted) {
         boolean flag = false;
         T parent = (T)t.getParent();

         while(parent != null && !flag) {
            for(T t2 : this.deleted) {
               if (t2.getId().equals(parent.getId())) {
                  flag = true;
               }
            }

            if (!flag) {
               parent = (T)parent.getParent();
            }
         }

         if (flag) {
            del.add(t);
         }
      }

      this.deleted.removeAll(del);
   }
}
