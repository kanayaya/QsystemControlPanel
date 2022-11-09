package ru.apertum.qsystem.server.model.response;

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

public class QRespItem extends DefaultMutableTreeNode implements ITreeIdGetter, Serializable {
   public String data;
   @Expose
   @SerializedName("id")
   private Long id = System.currentTimeMillis();
   private Long parentId;
   @Expose
   @SerializedName("name")
   private String name;
   @Expose
   @SerializedName("html")
   private String htmlText;
   @Expose
   @SerializedName("input_required")
   private Boolean input_required = false;
   @Expose
   @SerializedName("input_caption")
   private String input_caption = "";
   private Date deleted;
   private QRespItem parentService;
   @Expose
   @SerializedName("child_items")
   private LinkedList<QRespItem> childrenOfService = new LinkedList<>();

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

   public Boolean getInput_required() {
      return this.input_required;
   }

   public void setInput_required(Boolean input_required) {
      this.input_required = input_required;
   }

   public Boolean isInput_required() {
      return this.input_required;
   }

   public String getInput_caption() {
      return this.input_caption;
   }

   public void setInput_caption(String input_caption) {
      this.input_caption = input_caption;
   }

   public Date getDeleted() {
      return this.deleted;
   }

   public void setDeleted(Date deleted) {
      this.deleted = deleted;
   }

   public LinkedList<QRespItem> getChildren() {
      return this.childrenOfService;
   }

   public QRespItem getChildAt(int childIndex) {
      return this.childrenOfService.get(childIndex);
   }

   @Override
   public int getChildCount() {
      return this.childrenOfService.size();
   }

   public QRespItem getParent() {
      return this.parentService;
   }

   @Override
   public void setParent(MutableTreeNode newParent) {
      this.parentService = (QRespItem)newParent;
      if (this.parentService != null) {
         this.setParentId(this.parentService.id);
      } else {
         this.parentId = null;
      }
   }

   public int getIndex(QRespItem node) {
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
      this.childrenOfService.add(index, (QRespItem)child);
   }

   @Override
   public void remove(int index) {
      this.childrenOfService.remove(index);
   }

   @Override
   public void remove(MutableTreeNode node) {
      this.childrenOfService.remove((QRespItem)node);
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
      this.childrenOfService.add((QRespItem)child);
   }
}
