package ru.apertum.qsystem.common.exceptions;

import ru.apertum.qsystem.common.QLog;

public class ReportException extends RuntimeException {
   public ReportException(String textException) {
      super(textException);
      QLog.l().logRep().error("Error!", this);
   }
}
