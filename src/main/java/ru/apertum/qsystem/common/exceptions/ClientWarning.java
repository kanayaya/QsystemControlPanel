package ru.apertum.qsystem.common.exceptions;

import javax.swing.JOptionPane;
import ru.apertum.qsystem.common.QLog;

public class ClientWarning {
   public static void showWarning(String textWarning) {
      QLog.l().logger().warn(textWarning);
      JOptionPane.showMessageDialog(null, textWarning, "QSystem warning", 2);
   }
}
