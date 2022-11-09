package ru.apertum.qsystem.swing;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.Vector;

public class DefaultMutableTreeNode implements Cloneable, MutableTreeNode, Serializable {
   public static final Enumeration<TreeNode> EMPTY_ENUMERATION = Collections.emptyEnumeration();
   private static final long serialVersionUID = -4298474751201349152L;
   protected MutableTreeNode parent = null;
   protected Vector children;
   protected transient Object userObject;
   protected boolean allowsChildren;

   public DefaultMutableTreeNode() {
      this(null);
   }

   public DefaultMutableTreeNode(Object userObject) {
      this(userObject, true);
   }

   public DefaultMutableTreeNode(Object userObject, boolean allowsChildren) {
      this.allowsChildren = allowsChildren;
      this.userObject = userObject;
   }

   @Override
   public void insert(MutableTreeNode newChild, int childIndex) {
      if (!this.allowsChildren) {
         throw new IllegalStateException("node does not allow children");
      } else if (newChild == null) {
         throw new IllegalArgumentException("new child is null");
      } else if (this.isNodeAncestor(newChild)) {
         throw new IllegalArgumentException("new child is an ancestor");
      } else {
         MutableTreeNode oldParent = (MutableTreeNode)newChild.getParent();
         if (oldParent != null) {
            oldParent.remove(newChild);
         }

         newChild.setParent(this);
         if (this.children == null) {
            this.children = new Vector();
         }

         this.children.insertElementAt(newChild, childIndex);
      }
   }

   @Override
   public void remove(int childIndex) {
      MutableTreeNode child = (MutableTreeNode)this.getChildAt(childIndex);
      this.children.removeElementAt(childIndex);
      child.setParent(null);
   }

   @Override
   public TreeNode getParent() {
      return this.parent;
   }

   @Override
   public void setParent(MutableTreeNode newParent) {
      this.parent = newParent;
   }

   @Override
   public TreeNode getChildAt(int index) {
      if (this.children == null) {
         throw new ArrayIndexOutOfBoundsException("node has no children");
      } else {
         return (TreeNode)this.children.elementAt(index);
      }
   }

   @Override
   public int getChildCount() {
      return this.children == null ? 0 : this.children.size();
   }

   @Override
   public int getIndex(TreeNode aChild) {
      if (aChild == null) {
         throw new IllegalArgumentException("argument is null");
      } else {
         return !this.isNodeChild(aChild) ? -1 : this.children.indexOf(aChild);
      }
   }

   @Override
   public Enumeration children() {
      return this.children == null ? EMPTY_ENUMERATION : this.children.elements();
   }

   @Override
   public boolean getAllowsChildren() {
      return this.allowsChildren;
   }

   public void setAllowsChildren(boolean allows) {
      if (allows != this.allowsChildren) {
         this.allowsChildren = allows;
         if (!this.allowsChildren) {
            this.removeAllChildren();
         }
      }
   }

   public Object getUserObject() {
      return this.userObject;
   }

   @Override
   public void setUserObject(Object userObject) {
      this.userObject = userObject;
   }

   @Override
   public void removeFromParent() {
      MutableTreeNode parent = (MutableTreeNode)this.getParent();
      if (parent != null) {
         parent.remove(this);
      }
   }

   @Override
   public void remove(MutableTreeNode aChild) {
      if (aChild == null) {
         throw new IllegalArgumentException("argument is null");
      } else if (!this.isNodeChild(aChild)) {
         throw new IllegalArgumentException("argument is not a child");
      } else {
         this.remove(this.getIndex(aChild));
      }
   }

   public void removeAllChildren() {
      for(int i = this.getChildCount() - 1; i >= 0; --i) {
         this.remove(i);
      }
   }

   public void add(MutableTreeNode newChild) {
      if (newChild != null && newChild.getParent() == this) {
         this.insert(newChild, this.getChildCount() - 1);
      } else {
         this.insert(newChild, this.getChildCount());
      }
   }

   public boolean isNodeAncestor(TreeNode anotherNode) {
      if (anotherNode == null) {
         return false;
      } else {
         TreeNode ancestor = this;

         while(ancestor != anotherNode) {
            if ((ancestor = ancestor.getParent()) == null) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isNodeDescendant(DefaultMutableTreeNode anotherNode) {
      return anotherNode == null ? false : anotherNode.isNodeAncestor(this);
   }

   public TreeNode getSharedAncestor(DefaultMutableTreeNode aNode) {
      if (aNode == this) {
         return this;
      } else if (aNode == null) {
         return null;
      } else {
         int level1 = this.getLevel();
         int level2 = aNode.getLevel();
         int diff;
         TreeNode node1;
         TreeNode node2;
         if (level2 > level1) {
            diff = level2 - level1;
            node1 = aNode;
            node2 = this;
         } else {
            diff = level1 - level2;
            node1 = this;
            node2 = aNode;
         }

         while(diff > 0) {
            node1 = node1.getParent();
            --diff;
         }

         while(node1 != node2) {
            node1 = node1.getParent();
            node2 = node2.getParent();
            if (node1 == null) {
               if (node1 == null && node2 == null) {
                  return null;
               }

               throw new Error("nodes should be null");
            }
         }

         return node1;
      }
   }

   public boolean isNodeRelated(DefaultMutableTreeNode aNode) {
      return aNode != null && this.getRoot() == aNode.getRoot();
   }

   public int getDepth() {
      Object last = null;
      Enumeration enum_ = this.breadthFirstEnumeration();

      while(enum_.hasMoreElements()) {
         last = enum_.nextElement();
      }

      if (last == null) {
         throw new Error("nodes should be null");
      } else {
         return ((DefaultMutableTreeNode)last).getLevel() - this.getLevel();
      }
   }

   public int getLevel() {
      int levels = 0;
      TreeNode ancestor = this;

      while((ancestor = ancestor.getParent()) != null) {
         ++levels;
      }

      return levels;
   }

   public TreeNode[] getPath() {
      return this.getPathToRoot(this, 0);
   }

   protected TreeNode[] getPathToRoot(TreeNode aNode, int depth) {
      TreeNode[] retNodes;
      if (aNode == null) {
         if (depth == 0) {
            return null;
         }

         retNodes = new TreeNode[depth];
      } else {
         retNodes = this.getPathToRoot(aNode.getParent(), ++depth);
         retNodes[retNodes.length - depth] = aNode;
      }

      return retNodes;
   }

   public Object[] getUserObjectPath() {
      TreeNode[] realPath = this.getPath();
      Object[] retPath = new Object[realPath.length];

      for(int counter = 0; counter < realPath.length; ++counter) {
         retPath[counter] = ((DefaultMutableTreeNode)realPath[counter]).getUserObject();
      }

      return retPath;
   }

   public TreeNode getRoot() {
      TreeNode ancestor = this;

      TreeNode previous;
      do {
         previous = ancestor;
         ancestor = ancestor.getParent();
      } while(ancestor != null);

      return previous;
   }

   public boolean isRoot() {
      return this.getParent() == null;
   }

   public DefaultMutableTreeNode getNextNode() {
      if (this.getChildCount() != 0) {
         return (DefaultMutableTreeNode)this.getChildAt(0);
      } else {
         DefaultMutableTreeNode nextSibling = this.getNextSibling();
         if (nextSibling != null) {
            return nextSibling;
         } else {
            for(DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)this.getParent(); aNode != null; aNode = (DefaultMutableTreeNode)aNode.getParent()) {
               nextSibling = aNode.getNextSibling();
               if (nextSibling != null) {
                  return nextSibling;
               }
            }

            return null;
         }
      }
   }

   public DefaultMutableTreeNode getPreviousNode() {
      DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)this.getParent();
      if (myParent == null) {
         return null;
      } else {
         DefaultMutableTreeNode previousSibling = this.getPreviousSibling();
         if (previousSibling != null) {
            return previousSibling.getChildCount() == 0 ? previousSibling : previousSibling.getLastLeaf();
         } else {
            return myParent;
         }
      }
   }

   public Enumeration preorderEnumeration() {
      return new DefaultMutableTreeNode.PreorderEnumeration(this);
   }

   public Enumeration postorderEnumeration() {
      return new DefaultMutableTreeNode.PostorderEnumeration(this);
   }

   public Enumeration breadthFirstEnumeration() {
      return new DefaultMutableTreeNode.BreadthFirstEnumeration(this);
   }

   public Enumeration depthFirstEnumeration() {
      return this.postorderEnumeration();
   }

   public Enumeration pathFromAncestorEnumeration(TreeNode ancestor) {
      return new DefaultMutableTreeNode.PathBetweenNodesEnumeration(ancestor, this);
   }

   public boolean isNodeChild(TreeNode aNode) {
      boolean retval;
      if (aNode == null) {
         retval = false;
      } else if (this.getChildCount() == 0) {
         retval = false;
      } else {
         retval = aNode.getParent() == this;
      }

      return retval;
   }

   public TreeNode getFirstChild() {
      if (this.getChildCount() == 0) {
         throw new NoSuchElementException("node has no children");
      } else {
         return this.getChildAt(0);
      }
   }

   public TreeNode getLastChild() {
      if (this.getChildCount() == 0) {
         throw new NoSuchElementException("node has no children");
      } else {
         return this.getChildAt(this.getChildCount() - 1);
      }
   }

   public TreeNode getChildAfter(TreeNode aChild) {
      if (aChild == null) {
         throw new IllegalArgumentException("argument is null");
      } else {
         int index = this.getIndex(aChild);
         if (index == -1) {
            throw new IllegalArgumentException("node is not a child");
         } else {
            return index < this.getChildCount() - 1 ? this.getChildAt(index + 1) : null;
         }
      }
   }

   public TreeNode getChildBefore(TreeNode aChild) {
      if (aChild == null) {
         throw new IllegalArgumentException("argument is null");
      } else {
         int index = this.getIndex(aChild);
         if (index == -1) {
            throw new IllegalArgumentException("argument is not a child");
         } else {
            return index > 0 ? this.getChildAt(index - 1) : null;
         }
      }
   }

   public boolean isNodeSibling(TreeNode anotherNode) {
      boolean retval;
      if (anotherNode == null) {
         retval = false;
      } else if (anotherNode == this) {
         retval = true;
      } else {
         TreeNode myParent = this.getParent();
         retval = myParent != null && myParent == anotherNode.getParent();
         if (retval && !((DefaultMutableTreeNode)this.getParent()).isNodeChild(anotherNode)) {
            throw new Error("sibling has different parent");
         }
      }

      return retval;
   }

   public int getSiblingCount() {
      TreeNode myParent = this.getParent();
      return myParent == null ? 1 : myParent.getChildCount();
   }

   public DefaultMutableTreeNode getNextSibling() {
      DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)this.getParent();
      DefaultMutableTreeNode retval;
      if (myParent == null) {
         retval = null;
      } else {
         retval = (DefaultMutableTreeNode)myParent.getChildAfter(this);
      }

      if (retval != null && !this.isNodeSibling(retval)) {
         throw new Error("child of parent is not a sibling");
      } else {
         return retval;
      }
   }

   public DefaultMutableTreeNode getPreviousSibling() {
      DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)this.getParent();
      DefaultMutableTreeNode retval;
      if (myParent == null) {
         retval = null;
      } else {
         retval = (DefaultMutableTreeNode)myParent.getChildBefore(this);
      }

      if (retval != null && !this.isNodeSibling(retval)) {
         throw new Error("child of parent is not a sibling");
      } else {
         return retval;
      }
   }

   @Override
   public boolean isLeaf() {
      return this.getChildCount() == 0;
   }

   public DefaultMutableTreeNode getFirstLeaf() {
      DefaultMutableTreeNode node = this;

      while(!node.isLeaf()) {
         node = (DefaultMutableTreeNode)node.getFirstChild();
      }

      return node;
   }

   public DefaultMutableTreeNode getLastLeaf() {
      DefaultMutableTreeNode node = this;

      while(!node.isLeaf()) {
         node = (DefaultMutableTreeNode)node.getLastChild();
      }

      return node;
   }

   public DefaultMutableTreeNode getNextLeaf() {
      DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)this.getParent();
      if (myParent == null) {
         return null;
      } else {
         DefaultMutableTreeNode nextSibling = this.getNextSibling();
         return nextSibling != null ? nextSibling.getFirstLeaf() : myParent.getNextLeaf();
      }
   }

   public DefaultMutableTreeNode getPreviousLeaf() {
      DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)this.getParent();
      if (myParent == null) {
         return null;
      } else {
         DefaultMutableTreeNode previousSibling = this.getPreviousSibling();
         return previousSibling != null ? previousSibling.getLastLeaf() : myParent.getPreviousLeaf();
      }
   }

   public int getLeafCount() {
      int count = 0;
      Enumeration enum_ = this.breadthFirstEnumeration();

      while(enum_.hasMoreElements()) {
         TreeNode node = (TreeNode)enum_.nextElement();
         if (node.isLeaf()) {
            ++count;
         }
      }

      if (count < 1) {
         throw new Error("tree has zero leaves");
      } else {
         return count;
      }
   }

   @Override
   public String toString() {
      return this.userObject == null ? "" : this.userObject.toString();
   }

   @Override
   public Object clone() {
      try {
         DefaultMutableTreeNode newNode = (DefaultMutableTreeNode)super.clone();
         newNode.children = null;
         newNode.parent = null;
         return newNode;
      } catch (CloneNotSupportedException var3) {
         throw new Error(var3.toString());
      }
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      Object[] tValues;
      if (this.userObject != null && this.userObject instanceof Serializable) {
         tValues = new Object[2];
         tValues[0] = "userObject";
         tValues[1] = this.userObject;
      } else {
         tValues = new Object[0];
      }

      s.writeObject(tValues);
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      Object[] tValues = (Object[])s.readObject();
      if (tValues.length > 0 && tValues[0].equals("userObject")) {
         this.userObject = tValues[1];
      }
   }

   final class BreadthFirstEnumeration implements Enumeration<TreeNode> {
      protected DefaultMutableTreeNode.BreadthFirstEnumeration.Queue queue;

      public BreadthFirstEnumeration(TreeNode rootNode) {
         Vector<TreeNode> v = new Vector<>(1);
         v.addElement(rootNode);
         this.queue = new DefaultMutableTreeNode.BreadthFirstEnumeration.Queue();
         this.queue.enqueue(v.elements());
      }

      @Override
      public boolean hasMoreElements() {
         return !this.queue.isEmpty() && ((Enumeration)this.queue.firstObject()).hasMoreElements();
      }

      public TreeNode nextElement() {
         Enumeration enumer = (Enumeration)this.queue.firstObject();
         TreeNode node = (TreeNode)enumer.nextElement();
         Enumeration children = node.children();
         if (!enumer.hasMoreElements()) {
            this.queue.dequeue();
         }

         if (children.hasMoreElements()) {
            this.queue.enqueue(children);
         }

         return node;
      }

      final class Queue {
         DefaultMutableTreeNode.BreadthFirstEnumeration.Queue.QNode head;
         DefaultMutableTreeNode.BreadthFirstEnumeration.Queue.QNode tail;

         public void enqueue(Object anObject) {
            if (this.head == null) {
               this.head = this.tail = new DefaultMutableTreeNode.BreadthFirstEnumeration.Queue.QNode(anObject, null);
            } else {
               this.tail.next = new DefaultMutableTreeNode.BreadthFirstEnumeration.Queue.QNode(anObject, null);
               this.tail = this.tail.next;
            }
         }

         public Object dequeue() {
            if (this.head == null) {
               throw new NoSuchElementException("No more elements");
            } else {
               Object retval = this.head.object;
               DefaultMutableTreeNode.BreadthFirstEnumeration.Queue.QNode oldHead = this.head;
               this.head = this.head.next;
               if (this.head == null) {
                  this.tail = null;
               } else {
                  oldHead.next = null;
               }

               return retval;
            }
         }

         public Object firstObject() {
            if (this.head == null) {
               throw new NoSuchElementException("No more elements");
            } else {
               return this.head.object;
            }
         }

         public boolean isEmpty() {
            return this.head == null;
         }

         final class QNode {
            public Object object;
            public DefaultMutableTreeNode.BreadthFirstEnumeration.Queue.QNode next;

            public QNode(Object object, DefaultMutableTreeNode.BreadthFirstEnumeration.Queue.QNode next) {
               this.object = object;
               this.next = next;
            }
         }
      }
   }

   final class PathBetweenNodesEnumeration implements Enumeration<TreeNode> {
      protected Stack<TreeNode> stack;

      public PathBetweenNodesEnumeration(TreeNode ancestor, TreeNode descendant) {
         if (ancestor != null && descendant != null) {
            this.stack = new Stack<>();
            this.stack.push(descendant);
            TreeNode current = descendant;

            while(current != ancestor) {
               current = current.getParent();
               if (current == null && descendant != ancestor) {
                  throw new IllegalArgumentException("node " + ancestor + " is not an ancestor of " + descendant);
               }

               this.stack.push(current);
            }
         } else {
            throw new IllegalArgumentException("argument is null");
         }
      }

      @Override
      public boolean hasMoreElements() {
         return this.stack.size() > 0;
      }

      public TreeNode nextElement() {
         try {
            return this.stack.pop();
         } catch (EmptyStackException var2) {
            throw new NoSuchElementException("No more elements");
         }
      }
   }

   final class PostorderEnumeration implements Enumeration<TreeNode> {
      protected TreeNode root;
      protected Enumeration<TreeNode> children;
      protected Enumeration<TreeNode> subtree;

      public PostorderEnumeration(TreeNode rootNode) {
         this.root = rootNode;
         this.children = this.root.children();
         this.subtree = DefaultMutableTreeNode.EMPTY_ENUMERATION;
      }

      @Override
      public boolean hasMoreElements() {
         return this.root != null;
      }

      public TreeNode nextElement() {
         TreeNode retval;
         if (this.subtree.hasMoreElements()) {
            retval = this.subtree.nextElement();
         } else if (this.children.hasMoreElements()) {
            this.subtree = DefaultMutableTreeNode.this.new PostorderEnumeration(this.children.nextElement());
            retval = this.subtree.nextElement();
         } else {
            retval = this.root;
            this.root = null;
         }

         return retval;
      }
   }

   private final class PreorderEnumeration implements Enumeration<TreeNode> {
      private final Stack<Enumeration> stack = new Stack<>();

      public PreorderEnumeration(TreeNode rootNode) {
         Vector<TreeNode> v = new Vector<>(1);
         v.addElement(rootNode);
         this.stack.push(v.elements());
      }

      @Override
      public boolean hasMoreElements() {
         return !this.stack.empty() && this.stack.peek().hasMoreElements();
      }

      public TreeNode nextElement() {
         Enumeration enumer = this.stack.peek();
         TreeNode node = (TreeNode)enumer.nextElement();
         Enumeration children = node.children();
         if (!enumer.hasMoreElements()) {
            this.stack.pop();
         }

         if (children.hasMoreElements()) {
            this.stack.push(children);
         }

         return node;
      }
   }
}
