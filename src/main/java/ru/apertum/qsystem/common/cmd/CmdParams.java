package ru.apertum.qsystem.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import ru.apertum.qsystem.server.model.QProperty;

public class CmdParams {
   public static final String CMD = "cmd";
   @Expose
   @SerializedName("service_id")
   public Long serviceId;
   @Expose
   @SerializedName("user_id")
   public Long userId;
   @Expose
   @SerializedName("pass")
   public String password;
   @Expose
   @SerializedName("priority")
   public Integer priority;
   @Expose
   @SerializedName("text_data")
   public String textData;
   @Expose
   @SerializedName("result_id")
   public Long resultId;
   @Expose
   @SerializedName("request_back")
   public Boolean requestBack;
   @Expose
   @SerializedName("drop_tickets_cnt")
   public Boolean dropTicketsCounter;
   @Expose
   @SerializedName("is_only_mine")
   public Boolean isMine;
   @Expose
   @SerializedName("coeff")
   public Integer coeff;
   @Expose
   @SerializedName("date")
   public Long date;
   @Expose
   @SerializedName("customer_id")
   public Long customerId;
   @Expose
   @SerializedName("response_id")
   public Long responseId;
   @Expose
   @SerializedName("client_auth_id")
   public String clientAuthId;
   @Expose
   @SerializedName("info_item_name")
   public String infoItemName;
   @Expose
   @SerializedName("postponed_period")
   public Integer postponedPeriod;
   @Expose
   @SerializedName("comments")
   public String comments;
   @Expose
   @SerializedName("first_in_roll")
   public Integer first;
   @Expose
   @SerializedName("lastt_in_roll")
   public Integer last;
   @Expose
   @SerializedName("currentt_in_roll")
   public Integer current;
   @Expose
   @SerializedName("complex_id")
   public LinkedList<LinkedList<LinkedList<Long>>> complexId;
   @Expose
   @SerializedName("strings_map")
   public Map<String, String> stringsMap;
   @Expose
   @SerializedName("properties")
   public List<QProperty> properties;

   public CmdParams() {
   }

   public CmdParams(String params) {
      this.initFromString(params);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("^?");
      Field[] fs = this.getClass().getDeclaredFields();

      try {
         for(Field field : fs) {
            if ((!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers())) && field.get(this) != null) {
               field.setAccessible(true);
               if (field.getType().getSimpleName().contains("List")) {
                  List list = (List)field.get(this);
                  if (list.size() > 0 && list.get(0) instanceof QProperty) {
                     sb.append("&").append(field.getName()).append("=");

                     for(Object object : list) {
                        try {
                           sb.append(URLEncoder.encode("{" + object.toString() + "}", "utf-8"));
                        } catch (UnsupportedEncodingException var12) {
                           System.err.println(var12);
                        }
                     }
                  }
               } else if (field.getType().getSimpleName().endsWith("Map")) {
                  Map map = (Map)field.get(this);
                  if (map.size() > 0) {
                     sb.append("&").append(field.getName()).append("=");

                     for(Object object : map.keySet()) {
                        try {
                           sb.append(URLEncoder.encode("{" + object.toString() + "->" + map.get(object) + "}", "utf-8"));
                        } catch (UnsupportedEncodingException var11) {
                           System.err.println(var11);
                        }
                     }
                  }
               } else {
                  String map = field.getType().getSimpleName().toLowerCase(Locale.US);
                  switch(map) {
                     case "int":
                        sb.append("&").append(field.getName()).append("=").append(field.get(this));
                        break;
                     case "integer":
                        sb.append("&").append(field.getName()).append("=").append(field.get(this));
                        break;
                     case "string":
                        sb.append("&").append(field.getName()).append("=").append(URLEncoder.encode((String)field.get(this), "utf-8"));
                        break;
                     case "boolean":
                        sb.append("&").append(field.getName()).append("=").append(field.get(this));
                        break;
                     case "long":
                        sb.append("&").append(field.getName()).append("=").append(field.get(this));
                        break;
                     default:
                        throw new AssertionError();
                  }
               }
            }
         }
      } catch (IllegalAccessException | UnsupportedEncodingException | IllegalArgumentException var13) {
         System.err.println(var13);
      }

      String st = sb.toString().replaceFirst("^\\^\\?\\&", "");
      sb.setLength(0);
      return st.length() < 3 ? "" : st;
   }

   public final void initFromString(String params) {
      if (params != null && !params.isEmpty()) {
         for(String str : params.split("&")) {
            String[] pp = str.split("=");
            Field[] fs = this.getClass().getDeclaredFields();

            try {
               for(Field field : fs) {
                  if ((!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers())) && pp[0].equals(field.getName())) {
                     field.setAccessible(true);
                     if ("properties".equals(field.getName())) {
                        List<QProperty> list = new LinkedList<>();
                        String lst = URLDecoder.decode(pp[1], "utf-8");
                        lst = lst.substring(1, lst.length() - 1);
                        String[] ll = lst.split("\\}\\{");

                        for(String el : ll) {
                           String[] ss = el.split("\\[|\\]|:");
                           if (ss.length == 4) {
                              list.add(new QProperty(ss[1], ss[2], ss[3]));
                           }
                        }

                        field.set(this, list);
                     } else {
                        String var23 = field.getType().getSimpleName().toLowerCase(Locale.US);
                        switch(var23) {
                           case "int":
                              field.set(this, Integer.parseInt(pp[1]));
                              break;
                           case "integer":
                              field.set(this, Integer.parseInt(pp[1]));
                              break;
                           case "string":
                              field.set(this, pp.length == 1 ? "" : URLDecoder.decode(pp[1], "utf-8"));
                              break;
                           case "boolean":
                              field.set(this, Boolean.parseBoolean(pp[1]));
                              break;
                           case "long":
                              field.set(this, Long.parseLong(pp[1]));
                              break;
                           case "map":
                              HashMap<String, String> map = new HashMap<>();
                              String s = URLDecoder.decode(pp[1], "utf-8");
                              String[] ss = s.split("\\{|\\}");

                              for(String s1 : ss) {
                                 String[] ss1 = s1.split("->");
                                 if (ss1.length == 2) {
                                    map.put(ss1[0], ss1[1]);
                                 }
                              }

                              field.set(this, map);
                              break;
                           default:
                              throw new AssertionError();
                        }
                     }
                  }
               }
            } catch (IllegalAccessException | UnsupportedEncodingException | IllegalArgumentException var22) {
               System.err.println(var22);
            }
         }
      }
   }

   public static void main(String[] args) {
      CmdParams cp = new CmdParams();
      cp.clientAuthId = "str1";
      cp.password = "Парольчег";
      cp.coeff = 101;
      cp.isMine = false;
      cp.requestBack = true;
      cp.date = System.currentTimeMillis();
      cp.properties = new LinkedList<>();
      cp.properties.add(new QProperty("sec1", "key1", "Русс1", "com1"));
      cp.properties.add(new QProperty("sec2", "key2", "Русс2", "com2"));
      cp.properties.add(new QProperty("sec3", "key3", "Русс3", "com3"));
      cp.stringsMap = new HashMap<>();
      cp.stringsMap.put("1", "aaa");
      cp.stringsMap.put("2", "bbb");
      cp.stringsMap.put("3", "ccc");
      String url = cp.toString();
      System.out.println(url);
      cp = new CmdParams();
      cp.initFromString(url);
      System.out.println(url.equals(cp.toString()) ? "!" : ":(");
      url = cp.toString();
      System.out.println(url);
      cp = new CmdParams();
      cp.initFromString(url);
      System.out.println(url.equals(cp.toString()) ? "!" : ":(");
      url = cp.toString();
      System.out.println(url);
      cp = new CmdParams();
      cp.initFromString("");
      url = cp.toString();
      System.out.println(url);
      cp = new CmdParams();
      cp.initFromString(null);
      System.out.println(url.equals(cp.toString()) ? "!" : ":(");
      url = cp.toString();
      System.out.println(url);
   }
}
