package ru.apertum.qsystem.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.LinkedList;

public class QCenterParams {
   @Expose
   @SerializedName("props")
   private LinkedList<QParam> params = new LinkedList<>();

   public LinkedList<QParam> getParams() {
      return this.params;
   }

   public void setParams(LinkedList<QParam> params) {
      this.params = params;
   }
}
