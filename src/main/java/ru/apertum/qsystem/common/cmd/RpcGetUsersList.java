package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.LinkedList;
import ru.apertum.qsystem.server.model.QUser;

public class RpcGetUsersList extends JsonRPC20 {
   @Expose
   @SerializedName("result")
   private LinkedList<QUser> result;

   public RpcGetUsersList() {
   }

   public void setResult(LinkedList<QUser> result) {
      this.result = result;
   }

   public LinkedList<QUser> getResult() {
      return this.result;
   }

   public RpcGetUsersList(LinkedList<QUser> result) {
      this.result = result;
   }
}
