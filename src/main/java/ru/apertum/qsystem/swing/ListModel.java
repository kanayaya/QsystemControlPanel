package ru.apertum.qsystem.swing;

public interface ListModel<E> {
   int getSize();

   E getElementAt(int var1);

   void addListDataListener(ListDataListener var1);

   void removeListDataListener(ListDataListener var1);
}
