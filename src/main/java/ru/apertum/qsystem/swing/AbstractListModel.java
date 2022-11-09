package ru.apertum.qsystem.swing;

import java.io.Serializable;
import java.util.EventListener;

public abstract class AbstractListModel<E> implements ListModel<E>, Serializable {
   protected EventListenerList listenerList = new EventListenerList();

   @Override
   public void addListDataListener(ListDataListener l) {
      this.listenerList.add(ListDataListener.class, l);
   }

   @Override
   public void removeListDataListener(ListDataListener l) {
      this.listenerList.remove(ListDataListener.class, l);
   }

   public ListDataListener[] getListDataListeners() {
      return this.listenerList.getListeners(ListDataListener.class);
   }

   protected void fireContentsChanged(Object source, int index0, int index1) {
      Object[] listeners = this.listenerList.getListenerList();
      ListDataEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if (listeners[i] == ListDataListener.class) {
            if (e == null) {
               e = new ListDataEvent(source, 0, index0, index1);
            }

            ((ListDataListener)listeners[i + 1]).contentsChanged(e);
         }
      }
   }

   protected void fireIntervalAdded(Object source, int index0, int index1) {
      Object[] listeners = this.listenerList.getListenerList();
      ListDataEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if (listeners[i] == ListDataListener.class) {
            if (e == null) {
               e = new ListDataEvent(source, 1, index0, index1);
            }

            ((ListDataListener)listeners[i + 1]).intervalAdded(e);
         }
      }
   }

   protected void fireIntervalRemoved(Object source, int index0, int index1) {
      Object[] listeners = this.listenerList.getListenerList();
      ListDataEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if (listeners[i] == ListDataListener.class) {
            if (e == null) {
               e = new ListDataEvent(source, 2, index0, index1);
            }

            ((ListDataListener)listeners[i + 1]).intervalRemoved(e);
         }
      }
   }

   public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
      return this.listenerList.getListeners(listenerType);
   }
}
