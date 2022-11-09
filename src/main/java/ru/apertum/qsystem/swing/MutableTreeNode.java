package ru.apertum.qsystem.swing;

public interface MutableTreeNode extends TreeNode {
   void insert(MutableTreeNode var1, int var2);

   void remove(int var1);

   void remove(MutableTreeNode var1);

   void setUserObject(Object var1);

   void removeFromParent();

   void setParent(MutableTreeNode var1);
}
