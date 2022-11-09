package ru.apertum.qsystem.server.controller;

import java.util.ArrayList;

public class ServerEvents implements IServerListener {
   private final ArrayList<IServerListener> listeners = new ArrayList<>();

   private ServerEvents() {
   }

   public static ServerEvents getInstance() {
      return ServerEvents.ServerEventsHolder.INSTANCE;
   }

   public void registerListener(IServerListener listener) {
      this.listeners.add(listener);
   }

   @Override
   public void restartEvent() {
      for(IServerListener listener : this.listeners) {
         listener.restartEvent();
      }
   }

   private static class ServerEventsHolder {
      private static final ServerEvents INSTANCE = new ServerEvents();
   }
}
