package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import ru.apertum.qsystem.common.Uses;

public class QProperty implements Serializable {
   @Expose
   @SerializedName("id")
   private Long id;
   @Expose
   @SerializedName("section")
   private String section;
   @Expose
   @SerializedName("key")
   private String key;
   @Expose
   @SerializedName("value")
   private String value;
   @Expose
   @SerializedName("comment")
   private String comment;
   @Expose
   @SerializedName("data")
   private String data;
   @Expose
   @SerializedName("hide")
   private Boolean hide = false;

   public QProperty(String section, String key, String value) {
      if (key == null) {
         throw new IllegalArgumentException("Key must be not NULL");
      } else {
         this.section = section;
         this.key = key;
         this.value = value;
      }
   }

   public QProperty(String section, String key, String value, String comment) {
      if (key == null) {
         throw new IllegalArgumentException("Key must be not NULL");
      } else {
         this.section = section;
         this.key = key;
         this.value = value;
         this.comment = comment;
      }
   }

   public QProperty(String section, String key) {
      if (key == null) {
         throw new IllegalArgumentException("Key must be not NULL");
      } else {
         this.section = section;
         this.key = key;
      }
   }

   public QProperty() {
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getId() {
      return this.id;
   }

   public String getData() {
      return this.data;
   }

   public void setData(String data) {
      this.data = data;
   }

   public Boolean getHide() {
      return this.hide;
   }

   public void setHide(Boolean hide) {
      this.hide = hide;
   }

   public String getSection() {
      return this.section;
   }

   public void setSection(String section) {
      this.section = section;
   }

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      if (key == null) {
         throw new IllegalArgumentException("Key must be not NULL");
      } else {
         this.key = key;
      }
   }

   public String getValue() {
      return this.value;
   }

   public Integer getValueAsInt() {
      return this.value == null ? null : Integer.parseInt(this.value);
   }

   public Long getValueAsLong() {
      return this.value == null ? null : Long.parseLong(this.value);
   }

   public Double getValueAsDouble() {
      return this.value == null ? null : Double.parseDouble(this.value);
   }

   public Boolean getValueAsBool() {
      return this.value == null ? null : this.value.equals("1") || this.value.equalsIgnoreCase("true");
   }

   public Date getValueAsDate(String pattern) throws ParseException {
      return this.value == null ? null : new SimpleDateFormat(pattern).parse(this.value);
   }

   public Date getValueAsDate() throws ParseException {
      return this.value == null ? null : Uses.FORMAT_FOR_REP.parse(this.value);
   }

   public String[] getValueAsStrArray(String regExpDivider) {
      return this.value == null ? new String[0] : this.value.split(regExpDivider);
   }

   public ArrayList<Integer> getValueAsIntArray(String regExpDivider) {
      String[] ss = this.value == null ? new String[0] : this.value.split(regExpDivider);
      ArrayList<Integer> list = new ArrayList<>();

      for(String s : ss) {
         list.add(Integer.parseInt(s));
      }

      return list;
   }

   public ArrayList<Long> getValueAsLongArray(String regExpDivider) {
      String[] ss = this.value == null ? new String[0] : this.value.split(regExpDivider);
      ArrayList<Long> list = new ArrayList<>();

      for(String s : ss) {
         list.add(Long.parseLong(s));
      }

      return list;
   }

   public void setValueAsStrArray(List<String> strings, String divider) {
      this.value = strings.stream().collect(Collectors.joining(divider));
   }

   public void setValueAsIntArray(List<Integer> ints, String divider) {
      String s = "";
      String d = "";

      for(Integer aInt : ints) {
         s = s + d + aInt;
         d = divider;
      }

      this.value = s;
   }

   public void setValueAsLongArray(List<Long> longs, String divider) {
      String s = "";
      String d = "";

      for(Long aInt : longs) {
         s = s + d + aInt;
         d = divider;
      }

      this.value = s;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public void setValue(Integer value) {
      this.value = value == null ? null : value.toString();
   }

   public void setValue(Long value) {
      this.value = value == null ? null : value.toString();
   }

   public void setValue(Double value) {
      this.value = value == null ? null : value.toString();
   }

   public void setValue(Boolean value) {
      this.value = value == null ? null : (value ? "true" : "false");
   }

   public void setValue(Date value) {
      this.value = value == null ? null : Uses.FORMAT_FOR_REP.format(value);
   }

   public void setValue(Date value, String pattern) {
      this.value = value == null ? null : new SimpleDateFormat(pattern).format(value);
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   @Override
   public String toString() {
      return (this.getSection() == null ? "" : "[" + (this.getSection().length() > 24 ? this.getSection().substring(0, 23) : this.getSection()) + "]")
         + (this.getKey().length() > 24 ? this.getKey().substring(0, 23) : this.getKey())
         + ":"
         + (this.getValue() != null && this.getValue().length() > 24 ? this.getValue().substring(0, 23) : (this.getValue() == null ? "" : this.getValue()));
   }

   @Override
   public boolean equals(Object obj) {
      if (obj != null && obj instanceof QProperty) {
         QProperty p = (QProperty)obj;
         return (this.section == null ? p.getSection() == null : this.section.equals(p.getSection()))
            && (this.key == null ? p.getKey() == null : this.key.equals(p.getKey()))
            && (this.value == null ? p.getValue() == null : this.value.equals(p.getValue()));
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode();
   }
}
