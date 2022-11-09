package ru.apertum.qsystem.common.model;

import java.util.Arrays;
import ru.apertum.qsystem.common.Uses;

public final class Priority implements IPriority {
   private int priority;

   public Priority(int priority) {
      this.set(priority);
   }

   public Priority() {
      this.priority = 1;
   }

   @Override
   public void set(int priority) {
      if (Arrays.binarySearch(Uses.PRIORITYS, priority) == -1) {
         throw new IllegalArgumentException(
            "Не возможно установить значение приоритета. Значение " + priority + " не принадлежит допустимым значениям: " + Arrays.toString(Uses.PRIORITYS)
         );
      } else {
         this.priority = priority;
      }
   }

   @Override
   public int get() {
      return this.priority;
   }

   public int compareTo(IPriority priority) {
      int res = 0;
      if (this.get() > priority.get()) {
         res = 1;
      } else if (this.get() < priority.get()) {
         res = -1;
      }

      return res;
   }
}
