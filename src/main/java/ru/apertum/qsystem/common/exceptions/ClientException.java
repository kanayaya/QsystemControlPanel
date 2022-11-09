package ru.apertum.qsystem.common.exceptions;

import javax.swing.JOptionPane;
import ru.apertum.qsystem.client.Locales;

public class ClientException extends ServerException {
   public ClientException(String textException) {
      super(textException);
      JOptionPane.showMessageDialog(null, textException, Locales.locMes("client_exception"), 0);
   }

   public ClientException(String textException, Exception ex) {
      super(textException, ex);
      JOptionPane.showMessageDialog(null, textException + ex, Locales.locMes("client_exception"), 0);
   }

   public ClientException(Exception ex) {
      super(ex);
      JOptionPane.showMessageDialog(null, ex, Locales.locMes("client_exception"), 0);
   }
}
