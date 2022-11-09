package ru.apertum.qsystem.common.exceptions;

import ru.apertum.qsystem.common.QLog;

public class ServerException extends RuntimeException {
   public ServerException(String textException) {
      super(textException);
      QLog.l().logger().error("Error! " + textException, this);
   }

   public ServerException(Exception ex) {
      super(ex);
      QLog.l().logger().error("Error! " + ex.toString(), this);
   }

   public ServerException(String textException, Exception ex) {
      super(textException, ex);
      QLog.l().logger().error("Error! " + textException + "\n" + ex.toString(), this);
   }
}
