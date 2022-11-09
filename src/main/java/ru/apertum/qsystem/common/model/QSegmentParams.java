package ru.apertum.qsystem.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.awt.Color;

public class QSegmentParams {
   @Expose
   @SerializedName("visible")
   private boolean visible;
   @Expose
   @SerializedName("size")
   private double size;
   @Expose
   @SerializedName("html")
   private String html;
   @Expose
   @SerializedName("runningText")
   private String runningText;
   @Expose
   @SerializedName("runningTextSpeed")
   private int runningTextSpeed;
   @Expose
   @SerializedName("fontSize")
   private int fontSize;
   @Expose
   @SerializedName("fontColor")
   private Color fontColor;
   @Expose
   @SerializedName("date")
   private boolean date;
   @Expose
   @SerializedName("backgroundImg")
   private String backgroundImg;
   @Expose
   @SerializedName("video")
   private String video;
   @Expose
   @SerializedName("gridNext")
   private boolean gridNext;
   @Expose
   @SerializedName("colsGridNext")
   private int colsGridNext;
   @Expose
   @SerializedName("rowsGridNext")
   private int rowsGridNext;

   public String getBackgroundImg() {
      return this.backgroundImg;
   }

   public void setBackgroundImg(String backgroundImg) {
      this.backgroundImg = backgroundImg;
   }

   public int getColsGridNext() {
      return this.colsGridNext;
   }

   public void setColsGridNext(int colsGridNext) {
      this.colsGridNext = colsGridNext;
   }

   public boolean isDate() {
      return this.date;
   }

   public void setDate(boolean date) {
      this.date = date;
   }

   public Color getFontColor() {
      return this.fontColor;
   }

   public void setFontColor(Color fontColor) {
      this.fontColor = fontColor;
   }

   public int getFontSize() {
      return this.fontSize;
   }

   public void setFontSize(int fontSize) {
      this.fontSize = fontSize;
   }

   public boolean isGridNext() {
      return this.gridNext;
   }

   public void setGridNext(boolean gridNext) {
      this.gridNext = gridNext;
   }

   public int getRowsGridNext() {
      return this.rowsGridNext;
   }

   public void setRowsGridNext(int rowsGridNext) {
      this.rowsGridNext = rowsGridNext;
   }

   public String getRunningText() {
      return this.runningText;
   }

   public void setRunningText(String runningText) {
      this.runningText = runningText;
   }

   public int getRunningTextSpeed() {
      return this.runningTextSpeed;
   }

   public void setRunningTextSpeed(int runningTextSpeed) {
      this.runningTextSpeed = runningTextSpeed;
   }

   public double getSize() {
      return this.size;
   }

   public void setSize(double size) {
      this.size = size;
   }

   public String getVideo() {
      return this.video;
   }

   public void setVideo(String video) {
      this.video = video;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public String getHtml() {
      return this.html;
   }

   public void setHtml(String html) {
      this.html = html;
   }
}
