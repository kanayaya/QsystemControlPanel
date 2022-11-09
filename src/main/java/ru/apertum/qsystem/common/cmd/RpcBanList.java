package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import ru.apertum.qsystem.common.model.QCustomer;
import ru.apertum.qsystem.server.ServerProps;

public class RpcBanList extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private final LinkedList<String> banCustomers = new LinkedList<>();
   private final HashMap<String, Long> banSrok = new HashMap<>();

   public LinkedList<String> getBanList() {
      return this.banCustomers;
   }

   public static RpcBanList getInstance() {
      return RpcBanList.BanListHolder.INSTANCE;
   }

   public void udo(String data) {
      if (data == null) {
         LinkedList<String> li = new LinkedList<>();

         for(String string : this.banCustomers) {
            Long l = this.banSrok.get(string);
            if (l != null && new Date().getTime() - l > (long)(60000 * ServerProps.getInstance().getProps().getBlackTime())) {
               this.deleteFromBanList(string);
               li.add(string);
            }
         }

         this.banCustomers.removeAll(li);
      } else {
         Long l = this.banSrok.get(data.trim());
         if (l != null && new Date().getTime() - l > (long)(60000 * ServerProps.getInstance().getProps().getBlackTime())) {
            this.deleteFromBanList(data.trim());
         }
      }
   }

   public void addToBanList(QCustomer customer) {
      this.banCustomers.add(customer.getInput_data().trim());
      this.banSrok.put(customer.getInput_data().trim(), new Date().getTime());
   }

   public void addToBanList(String data) {
      this.banCustomers.add(data.trim());
      this.banSrok.put(data.trim(), new Date().getTime());
   }

   public boolean isBaned(QCustomer customer) {
      this.udo(customer.getInput_data());
      return ServerProps.getInstance().getProps().getBlackTime() > 0 && this.banCustomers.contains(customer.getInput_data().trim());
   }

   public boolean isBaned(String data) {
      this.udo(data);
      return ServerProps.getInstance().getProps().getBlackTime() > 0 && this.banCustomers.contains(data.trim());
   }

   public void deleteFromBanList(QCustomer customer) {
      this.banCustomers.remove(customer.getInput_data().trim());
      this.banSrok.remove(customer.getInput_data().trim());
   }

   public void deleteFromBanList(String data) {
      this.banCustomers.remove(data.trim());
      this.banSrok.remove(data.trim());
   }

   private static class BanListHolder {
      private static final RpcBanList INSTANCE = new RpcBanList();
   }
}
