package ru.apertum.qsystem.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QBoardParams {
   @Expose
   @SerializedName("top")
   private QSegmentParams top = new QSegmentParams();
   @Expose
   @SerializedName("bottom")
   private QSegmentParams bottom = new QSegmentParams();
   @Expose
   @SerializedName("left")
   private QSegmentParams left = new QSegmentParams();
   @Expose
   @SerializedName("right")
   private QSegmentParams right = new QSegmentParams();
   @Expose
   @SerializedName("center")
   private QCenterParams center;
   @Expose
   @SerializedName("visible")
   private boolean isVisible;
   @Expose
   @SerializedName("monitor")
   private int monitor;
   @Expose
   @SerializedName("description")
   private String description;

   public QSegmentParams getBottom() {
      return this.bottom;
   }

   public void setBottom(QSegmentParams bottom) {
      this.bottom = bottom;
   }

   public QCenterParams getCenter() {
      return this.center;
   }

   public void setCenter(QCenterParams center) {
      this.center = center;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public boolean isIsVisible() {
      return this.isVisible;
   }

   public void setIsVisible(boolean isVisible) {
      this.isVisible = isVisible;
   }

   public QSegmentParams getLeft() {
      return this.left;
   }

   public void setLeft(QSegmentParams left) {
      this.left = left;
   }

   public int getMonitor() {
      return this.monitor;
   }

   public void setMonitor(int monitor) {
      this.monitor = monitor;
   }

   public QSegmentParams getRight() {
      return this.right;
   }

   public void setRight(QSegmentParams right) {
      this.right = right;
   }

   public QSegmentParams getTop() {
      return this.top;
   }

   public void setTop(QSegmentParams top) {
      this.top = top;
   }
}
