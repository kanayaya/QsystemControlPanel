package ru.apertum.qsystem.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.awt.Color;

public class QParam {
   @Expose
   @SerializedName("name")
   private String name;
   @Expose
   @SerializedName("type")
   private String type;
   @Expose
   @SerializedName("value")
   private String value;

   public void setValue(int value) {
      this.value = String.valueOf(value);
   }

   public void setValue(double value) {
      this.value = String.valueOf(value);
   }

   public void setValue(boolean value) {
      this.value = value ? "1" : "0";
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public int getAsInt() {
      return Integer.parseInt(this.value);
   }

   public Color getAsColor() {
      return new Color(Integer.parseInt(this.value));
   }

   public boolean getAsBool() {
      return !"0".equals(this.value);
   }

   @Override
   public String toString() {
      return this.name + " = " + this.value;
   }
}
