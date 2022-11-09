package ru.apertum.qsystem.server.model.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import ru.apertum.qsystem.server.model.IidGetter;

public class QResult implements IidGetter, Serializable {
   @Expose
   @SerializedName("id")
   private Long id;
   @Expose
   @SerializedName("name")
   private String name;

   @Override
   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
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
      return this.getName();
   }
}
