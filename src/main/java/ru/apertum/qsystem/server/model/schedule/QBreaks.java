package ru.apertum.qsystem.server.model.schedule;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import ru.apertum.qsystem.server.model.IidGetter;

public class QBreaks implements IidGetter, Serializable {
   private Long id;
   private String name;
   private Set<QBreak> breaks = new HashSet<>();

   @Override
   public Long getId() {
      return this.id;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Set<QBreak> getBreaks() {
      return this.breaks;
   }

   public void setBreaks(Set<QBreak> breaks) {
      this.breaks = breaks;
   }

   @Override
   public String toString() {
      String s = "";

      for(QBreak qBreak : this.breaks) {
         s = s + (s.isEmpty() ? "" : ", ") + qBreak;
      }

      return this.name + "(" + s + ")";
   }

   @Override
   public int hashCode() {
      return super.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         QBreaks other = (QBreaks)obj;
         return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name) && Objects.equals(this.breaks.size(), other.breaks.size());
      }
   }
}
