package ru.apertum.qsystem.server.model;

import java.util.LinkedList;
import ru.apertum.qsystem.common.QLog;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.server.controller.IServerListener;
import ru.apertum.qsystem.server.controller.ServerEvents;
import ru.apertum.qsystem.swing.AbstractListModel;

public abstract class ATListModel<T extends IidGetter> extends AbstractListModel {
   protected final LinkedList<T> deleted = new LinkedList<>();
   private LinkedList<T> items;
   private ATListModel.Filtering filter = null;

   protected ATListModel() {
      this.createList();
      ServerEvents.getInstance().registerListener(new IServerListener() {
         @Override
         public void restartEvent() {
            ATListModel.this.createList();
         }
      });
   }

   protected abstract LinkedList<T> load();

   private void createList() {
      this.items = this.load();
      QLog.l().logger().info("Создали список.");
   }

   public LinkedList<T> getItems() {
      return this.items;
   }

   public T getById(long id) throws ServerException {
      for(T item : this.items) {
         if (id == item.getId()) {
            return item;
         }
      }

      throw new ServerException("Не найден элемент по ID: \"" + id + "\"");
   }

   public boolean hasById(long id) {
      for(T item : this.items) {
         if (id == item.getId()) {
            return true;
         }
      }

      return false;
   }

   public boolean hasByName(String name) {
      for(T item : this.items) {
         if (name != null && name.equals(item.getName())) {
            return true;
         }
      }

      return false;
   }

   public boolean removeElement(T obj) {
      this.deleted.add(obj);
      int index = this.items.indexOf(obj);
      return this.items.remove(obj);
   }

   public void addElement(T obj) {
      int index = this.items.size();
      this.items.add(obj);
   }

   public void clear() {
      int index1 = this.items.size() - 1;
      this.deleted.addAll(this.items);
      this.items.clear();
      if (index1 >= 0) {
      }
   }

   public void setFilter(ATListModel.Filtering filter) {
      this.filter = filter;
   }

   public T getElementAt(int index) {
      if (this.filter == null) {
         return this.items.get(index);
      } else {
         int i = -1;

         int f;
         for(f = 0; i != index; ++f) {
            if (this.filter.filter(this.items.get(f))) {
               ++i;
            }
         }

         return this.items.get(f - 1);
      }
   }

   @Override
   public int getSize() {
      if (this.filter == null) {
         return this.items.size();
      } else {
         int i = 0;

         for(T t : this.items) {
            if (this.filter.filter(t)) {
               ++i;
            }
         }

         return i;
      }
   }

   public void save() {
      this.deleted.clear();
   }

   public interface Filtering {
      boolean filter(Object var1);
   }
}
