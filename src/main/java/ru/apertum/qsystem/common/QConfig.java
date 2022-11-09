package ru.apertum.qsystem.common;

import java.net.InetAddress;

public abstract class QConfig {
   private static volatile QConfig cfg;

   public static void setCfg(QConfig config) {
      cfg = config;
   }

   public static QConfig cfg() {
      return cfg;
   }

   public abstract int getHttpProtocol();

   public abstract Boolean getHttpRequestType();

   public abstract String getPointN();

   public abstract String getServerAddress();

   public abstract InetAddress getInetServerAddress();

   public abstract int getServerPort();

   abstract boolean isAndroid();

   public abstract String getNumDivider(String var1);

   public abstract QConfig.QProxy getProxy();

   public static final class QProxy {
      public final String host;
      public final int port;

      public QProxy(String host, int port) {
         this.host = host;
         this.port = port;
      }
   }
}
