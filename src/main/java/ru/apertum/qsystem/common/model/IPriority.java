package ru.apertum.qsystem.common.model;

public interface IPriority extends Comparable<IPriority> {
   void set(int var1);

   int get();
}
