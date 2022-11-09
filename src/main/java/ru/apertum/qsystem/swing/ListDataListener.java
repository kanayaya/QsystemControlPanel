package ru.apertum.qsystem.swing;

import java.util.EventListener;

public interface ListDataListener extends EventListener {
   void intervalAdded(ListDataEvent var1);

   void intervalRemoved(ListDataEvent var1);

   void contentsChanged(ListDataEvent var1);
}
