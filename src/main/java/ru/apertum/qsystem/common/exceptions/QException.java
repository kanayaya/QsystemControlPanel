package ru.apertum.qsystem.common.exceptions;

import ru.apertum.qsystem.common.QLog;

public class QException extends Exception {
   public QException(String textException) {
      super(textException);
      QLog.l().logger().error("Exception!: " + textException);
   }

   public QException(String textException, Throwable tr) {
      super(textException, tr);
      QLog.l().logger().error("Exception!: " + textException, tr);
   }
}
