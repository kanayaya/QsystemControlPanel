package ru.apertum.qsystem.swing;

import java.util.EventObject;

public class TreeModelEvent extends EventObject {
   protected TreePath path;
   protected int[] childIndices;
   protected Object[] children;

   public TreeModelEvent(Object source, Object[] path, int[] childIndices, Object[] children) {
      this(source, path == null ? null : new TreePath(path), childIndices, children);
   }

   public TreeModelEvent(Object source, TreePath path, int[] childIndices, Object[] children) {
      super(source);
      this.path = path;
      this.childIndices = childIndices;
      this.children = children;
   }

   public TreeModelEvent(Object source, Object[] path) {
      this(source, path == null ? null : new TreePath(path));
   }

   public TreeModelEvent(Object source, TreePath path) {
      super(source);
      this.path = path;
      this.childIndices = new int[0];
   }

   public TreePath getTreePath() {
      return this.path;
   }

   public Object[] getPath() {
      return this.path != null ? this.path.getPath() : null;
   }

   public Object[] getChildren() {
      if (this.children != null) {
         int cCount = this.children.length;
         Object[] retChildren = new Object[cCount];
         System.arraycopy(this.children, 0, retChildren, 0, cCount);
         return retChildren;
      } else {
         return null;
      }
   }

   public int[] getChildIndices() {
      if (this.childIndices != null) {
         int cCount = this.childIndices.length;
         int[] retArray = new int[cCount];
         System.arraycopy(this.childIndices, 0, retArray, 0, cCount);
         return retArray;
      } else {
         return null;
      }
   }

   @Override
   public String toString() {
      StringBuffer retBuffer = new StringBuffer();
      retBuffer.append(this.getClass().getName() + " " + Integer.toString(this.hashCode()));
      if (this.path != null) {
         retBuffer.append(" path " + this.path);
      }

      if (this.childIndices != null) {
         retBuffer.append(" indices [ ");

         for(int counter = 0; counter < this.childIndices.length; ++counter) {
            retBuffer.append(Integer.toString(this.childIndices[counter]) + " ");
         }

         retBuffer.append("]");
      }

      if (this.children != null) {
         retBuffer.append(" children [ ");

         for(int counter = 0; counter < this.children.length; ++counter) {
            retBuffer.append(this.children[counter] + " ");
         }

         retBuffer.append("]");
      }

      return retBuffer.toString();
   }
}
