package ru.apertum.qsystem.common;

public class QLog {
   public static int loggerType = 0;
   private QLog.Logger logger = QLog.Logger.getLogger("server.file");
   private QLog.Logger logRep = QLog.Logger.getLogger("reports.file");

   private QLog() {
   }

   public static QLog l() {
      return QLog.LogerHolder.INSTANCE;
   }

   public static QLog initial(String[] args, int type) {
      loggerType = type;
      return QLog.LogerHolder.INSTANCE;
   }

   public QLog.Logger logger() {
      return this.logger;
   }

   public QLog.Logger logRep() {
      return this.logRep;
   }

   private static class LogerHolder {
      private static final QLog INSTANCE = new QLog();
   }

   public static class Logger {
      public static QLog.Logger getLogger(String s) {
         return new QLog.Logger();
      }

      public void trace(String s) {
         System.out.println(s);
      }

      public void debug(String s) {
         System.out.println(s);
      }

      public void info(String s) {
         System.out.println(s);
      }

      public void warn(String s) {
         System.err.println(s);
      }

      public void error(String s, Throwable th) {
         System.err.println(s + " " + th);
      }

      public void error(String s) {
         System.err.println(s);
      }
   }
}
