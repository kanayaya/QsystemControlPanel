package ru.apertum.qsystem.server.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class QPlanServiceList implements List {
   private final List<QPlanService> services;

   public QPlanServiceList(List<QPlanService> services) {
      this.services = services;
   }

   public List<QPlanService> getServices() {
      return this.services;
   }

   public int getSize() {
      return this.services.size();
   }

   public Object getElementAt(int index) {
      return this.services.get(index);
   }

   public boolean removeElement(QPlanService obj) {
      int index = this.services.indexOf(obj);
      return this.services.remove(obj);
   }

   public void addElement(QPlanService obj) {
      int index = this.services.size();
      this.services.add(obj);
   }

   @Override
   public int size() {
      return this.getSize();
   }

   @Override
   public boolean isEmpty() {
      return this.getSize() == 0;
   }

   @Override
   public boolean contains(Object o) {
      return this.services.contains(o);
   }

   @Override
   public Iterator iterator() {
      return this.services.iterator();
   }

   public QPlanService[] toArray() {
      return (QPlanService[])this.services.toArray();
   }

   @Override
   public boolean add(Object e) {
      return this.services.add((QPlanService)e);
   }

   @Override
   public boolean remove(Object o) {
      return this.services.remove((QPlanService)o);
   }

   @Override
   public boolean containsAll(Collection c) {
      return this.services.containsAll(c);
   }

   @Override
   public boolean addAll(Collection c) {
      return this.services.addAll(c);
   }

   @Override
   public boolean addAll(int index, Collection c) {
      return this.services.addAll(index, c);
   }

   @Override
   public boolean removeAll(Collection c) {
      return this.services.removeAll(c);
   }

   @Override
   public boolean retainAll(Collection c) {
      return this.services.retainAll(c);
   }

   @Override
   public void clear() {
      this.services.clear();
   }

   public QPlanService get(int index) {
      return this.services.get(index);
   }

   public QPlanService set(int index, Object element) {
      return this.services.set(index, (QPlanService)element);
   }

   @Override
   public void add(int index, Object element) {
      this.services.add(index, (QPlanService)element);
   }

   public QPlanService remove(int index) {
      return this.services.remove(index);
   }

   @Override
   public int indexOf(Object o) {
      return this.services.indexOf(o);
   }

   @Override
   public int lastIndexOf(Object o) {
      return this.services.lastIndexOf(o);
   }

   @Override
   public ListIterator listIterator() {
      return this.services.listIterator();
   }

   @Override
   public ListIterator listIterator(int index) {
      return this.services.listIterator(index);
   }

   @Override
   public List subList(int fromIndex, int toIndex) {
      return this.services.subList(fromIndex, toIndex);
   }

   public QPlanService[] toArray(Object[] a) {
      return (QPlanService[]) this.services.toArray(a);
   }
}
