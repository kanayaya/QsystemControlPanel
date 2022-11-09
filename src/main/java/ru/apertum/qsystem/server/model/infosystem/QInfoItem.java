package ru.apertum.qsystem.server.model.infosystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import ru.apertum.qsystem.server.model.ITreeIdGetter;
import ru.apertum.qsystem.swing.DefaultMutableTreeNode;
import ru.apertum.qsystem.swing.MutableTreeNode;
import ru.apertum.qsystem.swing.TreeNode;

public class QInfoItem extends DefaultMutableTreeNode implements ITreeIdGetter, Serializable {
   @Expose
   @SerializedName("id")
   private Long id = new Date().getTime();
   private Long parentId;
   @Expose
   @SerializedName("name")
   private String name;
   @Expose
   @SerializedName("html")
   private String htmlText;
   @Expose
   @SerializedName("print")
   private String textPrint;
   private QInfoItem parentService;
   @Expose
   @SerializedName("child_items")
   private LinkedList<QInfoItem> childrenOfService = new LinkedList<>();

   @Override
   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public Long getParentId() {
      return this.parentId;
   }

   public void setParentId(Long paremtId) {
      this.parentId = paremtId;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return this.name;
   }

   public String getHTMLText() {
      return this.htmlText;
   }

   public void setHTMLText(String htmlText) {
      this.htmlText = htmlText;
   }

   public String getTextPrint() {
      return this.textPrint;
   }

   public void setTextPrint(String textPrint) {
      this.textPrint = textPrint;
   }

   public LinkedList<QInfoItem> getChildren() {
      return this.childrenOfService;
   }

   public QInfoItem getChildAt(int childIndex) {
      return this.childrenOfService.get(childIndex);
   }

   @Override
   public int getChildCount() {
      return this.childrenOfService.size();
   }

   public QInfoItem getParent() {
      return this.parentService;
   }

   @Override
   public void setParent(MutableTreeNode newParent) {
      this.parentService = (QInfoItem)newParent;
      if (this.parentService != null) {
         this.setParentId(this.parentService.id);
      } else {
         this.parentId = null;
      }
   }

   public int getIndex(QInfoItem node) {
      return this.childrenOfService.indexOf(node);
   }

   @Override
   public boolean getAllowsChildren() {
      return true;
   }

   @Override
   public boolean isLeaf() {
      return this.getChildCount() == 0;
   }

   @Override
   public Enumeration children() {
      return Collections.enumeration(this.childrenOfService);
   }

   @Override
   public void insert(MutableTreeNode child, int index) {
      child.setParent(this);
      this.childrenOfService.add(index, (QInfoItem)child);
   }

   @Override
   public void remove(int index) {
      this.childrenOfService.remove(index);
   }

   @Override
   public void remove(MutableTreeNode node) {
      this.childrenOfService.remove((QInfoItem)node);
   }

   @Override
   public void removeFromParent() {
      this.getParent().remove(this.getParent().getIndex(this));
   }

   @Override
   public int getIndex(TreeNode node) {
      return this.childrenOfService.indexOf(node);
   }

   @Override
   public void addChild(ITreeIdGetter child) {
      this.childrenOfService.add((QInfoItem)child);
   }
}
