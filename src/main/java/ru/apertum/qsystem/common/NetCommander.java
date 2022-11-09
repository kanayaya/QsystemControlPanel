package ru.apertum.qsystem.common;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.Proxy.Type;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import ru.apertum.qsystem.client.Locales;
import ru.apertum.qsystem.client.QProperties;
import ru.apertum.qsystem.common.cmd.AJsonRPC20;
import ru.apertum.qsystem.common.cmd.CmdParams;
import ru.apertum.qsystem.common.cmd.JsonRPC20;
import ru.apertum.qsystem.common.cmd.JsonRPC20Error;
import ru.apertum.qsystem.common.cmd.JsonRPC20OK;
import ru.apertum.qsystem.common.cmd.RpcBanList;
import ru.apertum.qsystem.common.cmd.RpcGetAdvanceCustomer;
import ru.apertum.qsystem.common.cmd.RpcGetAllServices;
import ru.apertum.qsystem.common.cmd.RpcGetAuthorizCustomer;
import ru.apertum.qsystem.common.cmd.RpcGetBool;
import ru.apertum.qsystem.common.cmd.RpcGetGridOfDay;
import ru.apertum.qsystem.common.cmd.RpcGetGridOfWeek;
import ru.apertum.qsystem.common.cmd.RpcGetInfoTree;
import ru.apertum.qsystem.common.cmd.RpcGetInt;
import ru.apertum.qsystem.common.cmd.RpcGetPostponedPoolInfo;
import ru.apertum.qsystem.common.cmd.RpcGetProperties;
import ru.apertum.qsystem.common.cmd.RpcGetRespTree;
import ru.apertum.qsystem.common.cmd.RpcGetResultsList;
import ru.apertum.qsystem.common.cmd.RpcGetSelfSituation;
import ru.apertum.qsystem.common.cmd.RpcGetServerState;
import ru.apertum.qsystem.common.cmd.RpcGetServiceState;
import ru.apertum.qsystem.common.cmd.RpcGetSrt;
import ru.apertum.qsystem.common.cmd.RpcGetStandards;
import ru.apertum.qsystem.common.cmd.RpcGetTicketHistory;
import ru.apertum.qsystem.common.cmd.RpcGetUsersList;
import ru.apertum.qsystem.common.cmd.RpcInviteCustomer;
import ru.apertum.qsystem.common.cmd.RpcStandInService;
import ru.apertum.qsystem.common.exceptions.ClientException;
import ru.apertum.qsystem.common.exceptions.QException;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.common.model.INetProperty;
import ru.apertum.qsystem.common.model.QCustomer;
import ru.apertum.qsystem.server.ServerProps;
import ru.apertum.qsystem.server.model.ISailListener;
import ru.apertum.qsystem.server.model.QAdvanceCustomer;
import ru.apertum.qsystem.server.model.QAuthorizationCustomer;
import ru.apertum.qsystem.server.model.QProperty;
import ru.apertum.qsystem.server.model.QService;
import ru.apertum.qsystem.server.model.QServiceTree;
import ru.apertum.qsystem.server.model.QStandards;
import ru.apertum.qsystem.server.model.QUser;
import ru.apertum.qsystem.server.model.infosystem.QInfoItem;
import ru.apertum.qsystem.server.model.response.QRespItem;
import ru.apertum.qsystem.server.model.results.QResult;
import ru.apertum.qsystem.swing.TreeNode;

public class NetCommander {
   private static final JsonRPC20 JSON_RPC = new JsonRPC20();

   public static synchronized String send(INetProperty netProperty, String commandName, CmdParams params) throws QException {
      if (QConfig.cfg().isAndroid()) {
         return sendAndroid(netProperty, commandName, params);
      } else {
         JSON_RPC.setMethod(commandName);
         JSON_RPC.setParams(params);
         return sendRpc(netProperty, JSON_RPC);
      }
   }

   private static Proxy getProxy(Type proxyType) {
      if (QConfig.cfg().getProxy() != null) {
         QLog.l().logger().trace("Proxy.Type." + proxyType + ": " + QConfig.cfg().getProxy().host + ":" + QConfig.cfg().getProxy().port);
         return new Proxy(proxyType, new InetSocketAddress(QConfig.cfg().getProxy().host, QConfig.cfg().getProxy().port));
      } else if (QProperties.get().getProperty("proxy", "hostname") != null && QProperties.get().getProperty("proxy", "port") != null) {
         QLog.l()
            .logger()
            .trace(
               "Proxy.Type."
                  + proxyType
                  + ": "
                  + QProperties.get().getProperty("proxy", "hostname").getValue()
                  + ":"
                  + QProperties.get().getProperty("proxy", "port").getValueAsInt()
            );
         return new Proxy(
            proxyType,
            new InetSocketAddress(
               QProperties.get().getProperty("proxy", "hostname").getValue(), QProperties.get().getProperty("proxy", "port").getValueAsInt()
            )
         );
      } else {
         return null;
      }
   }

   public static synchronized String sendRpc(INetProperty netProperty, JsonRPC20 jsonRpc) throws QException {
      Gson gson = GsonPool.getInstance().borrowGson();

      String message;
      try {
         message = gson.toJson(jsonRpc);
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      String data;
      try {
         if (QConfig.cfg().getHttpRequestType() != null && !jsonRpc.getMethod().startsWith("#") && !"empty".equalsIgnoreCase(jsonRpc.getMethod())) {
            data = QConfig.cfg().getHttpRequestType() ? sendPost(netProperty, message, jsonRpc) : sendGet(netProperty, jsonRpc);
         } else {
            Proxy proxy = getProxy(Type.SOCKS);
            QLog.l()
               .logger()
               .trace("Task \"" + jsonRpc.getMethod() + "\" on " + netProperty.getAddress().getHostAddress() + ":" + netProperty.getPort() + "#\n" + message);
            Socket socket = proxy == null ? new Socket() : new Socket(proxy);

            try {
               socket.connect(new InetSocketAddress(netProperty.getAddress(), netProperty.getPort()), 15000);
            } catch (IOException var30) {
               Uses.closeSplash();
               throw new QException(Locales.locMes("no_connect_to_server"), var30);
            }

            QLog.l().logger().trace("Socket was created.");

            try {
               PrintWriter writer = new PrintWriter(socket.getOutputStream());
               writer.print(URLEncoder.encode(message, "utf-8"));
               QLog.l().logger().trace("Sending...");
               writer.flush();
               QLog.l().logger().trace("Reading...");
               StringBuilder sb = new StringBuilder();
               Scanner in = new Scanner(socket.getInputStream());

               while(in.hasNextLine()) {
                  sb = sb.append(in.nextLine()).append("\n");
               }

               data = URLDecoder.decode(sb.toString(), "utf-8");
               sb.setLength(0);
               writer.close();
               in.close();
            } finally {
               socket.close();
            }

            QLog.l().logger().trace("Response:\n" + data);
         }
      } catch (Exception var35) {
         throw new QException(Locales.locMes("no_response_from_server"), var35);
      }

      gson = GsonPool.getInstance().borrowGson();

      try {
         JsonRPC20Error rpc = (JsonRPC20Error)gson.fromJson(data, JsonRPC20Error.class);
         if (rpc == null) {
            throw new QException(Locales.locMes("error_on_server_no_get_response"));
         }

         if (rpc.getError() != null) {
            throw new QException(Locales.locMes("tack_failed") + " " + rpc.getError().getCode() + ":" + rpc.getError().getMessage());
         }
      } catch (JsonSyntaxException var32) {
         throw new QException(Locales.locMes("bad_response") + "\n" + var32.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return data;
   }

   private static String sendPost(INetProperty netProperty, String outputData, JsonRPC20 jsonRpc) throws Exception {
      String url = "http://" + netProperty.getAddress().getHostAddress() + ":" + QConfig.cfg().getHttpProtocol() + "/qsystem/command";
      QLog.l().logger().trace("HTTP POST request \"" + jsonRpc.getMethod() + "\" on " + url + "\n" + outputData);
      URL obj = new URL(url);
      Proxy proxy = getProxy(Type.HTTP);
      HttpURLConnection con = (HttpURLConnection)(proxy == null ? obj.openConnection() : obj.openConnection(proxy));
      con.setRequestMethod("POST");
      con.setRequestProperty("User-Agent", "User-Agent");
      con.setRequestProperty("Content-Type", "text/json; charset=UTF-8");
      con.setDoOutput(true);

      try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF8"))) {
         out.append(outputData);
         out.flush();
      }

      if (con.getResponseCode() != 200) {
         QLog.l().logger().error("HTTP response code = " + con.getResponseCode());
         throw new QException(Locales.locMes("no_connect_to_server"));
      } else {
         StringBuffer response;
         try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
            response = new StringBuffer();

            String inputLine;
            while((inputLine = in.readLine()) != null) {
               response.append(inputLine);
            }
         }

         String res = response.toString();
         response.setLength(0);
         QLog.l().logger().trace("HTTP response:\n" + res);
         return res;
      }
   }

   private static String sendGet(INetProperty netProperty, JsonRPC20 jsonRpc) throws Exception {
      String p = jsonRpc.getParams() == null ? "" : jsonRpc.getParams().toString();
      String url = "http://"
         + netProperty.getAddress().getHostAddress()
         + ":"
         + QConfig.cfg().getHttpProtocol()
         + "/qsystem/command"
         + "?"
         + "cmd"
         + "="
         + URLEncoder.encode(jsonRpc.getMethod(), "utf-8")
         + "&"
         + p;
      QLog.l().logger().trace("HTTP GET request \"" + jsonRpc.getMethod() + "\" on " + url + "\n" + p);
      URL obj = new URL(url);
      Proxy proxy = getProxy(Type.HTTP);
      HttpURLConnection con = (HttpURLConnection)(proxy == null ? obj.openConnection() : obj.openConnection(proxy));
      con.setRequestMethod("GET");
      con.setRequestProperty("User-Agent", "User-Agent");
      if (con.getResponseCode() != 200) {
         QLog.l().logger().error("HTTP response code = " + con.getResponseCode());
         throw new QException(Locales.locMes("no_connect_to_server"));
      } else {
         StringBuffer response;
         try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
            response = new StringBuffer();

            String inputLine;
            while((inputLine = in.readLine()) != null) {
               response.append(inputLine);
            }
         }

         String res = response.toString();
         response.setLength(0);
         QLog.l().logger().trace("HTTP response:\n" + res);
         return res;
      }
   }

   public static synchronized String sendAndroid(INetProperty netProperty, String commandName, CmdParams params) throws QException {
      JSON_RPC.setMethod(commandName);
      JSON_RPC.setParams(params);
      return sendRpcAndroid(netProperty, JSON_RPC);
   }

   public static synchronized String sendRpcAndroid(final INetProperty netProperty, AJsonRPC20 jsonRpc) throws QException {
      Gson gson = GsonPool.getInstance().borrowGson();

      final String message;
      try {
         message = gson.toJson(jsonRpc);
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      QLog.l()
         .logger()
         .trace("Задание \"" + jsonRpc.getMethod() + "\" на " + netProperty.getAddress().getHostAddress() + ":" + netProperty.getPort() + "#\n" + message);
      final StringBuilder result = new StringBuilder();
      final Semaphore sema = new Semaphore(0);
      Thread tr = new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               Socket socket = new Socket();

               try {
                  socket.connect(new InetSocketAddress(netProperty.getAddress(), netProperty.getPort()), 5000);
               } catch (Throwable var16) {
                  Uses.closeSplash();
                  throw new QException(Locales.locMes("no_connect_to_server"), var16);
               }

               QLog.l().logger().trace("Создали Socket.");

               String data;
               try {
                  PrintWriter writer = new PrintWriter(socket.getOutputStream());
                  writer.print(URLEncoder.encode(message, "utf-8"));
                  QLog.l().logger().trace("Высылаем задание.");
                  writer.flush();
                  QLog.l().logger().trace("Читаем ответ ...");
                  StringBuilder sb = new StringBuilder();
                  Scanner in = new Scanner(socket.getInputStream());

                  while(in.hasNextLine()) {
                     sb = sb.append(in.nextLine()).append("\n");
                  }

                  data = URLDecoder.decode(sb.toString(), "utf-8");
                  writer.close();
                  in.close();
               } finally {
                  socket.close();
               }

               QLog.l().logger().trace("Ответ:\n" + data);
               result.append(data);
            } catch (Exception var18) {
               QLog.l().logger().error("Q", var18);
            } finally {
               sema.release(10);
            }
         }
      });
      tr.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
         @Override
         public void uncaughtException(Thread thread, Throwable ex) {
            QLog.l().logger().error("QOperator", ex);
         }
      });
      tr.start();

      try {
         sema.acquire(1);
      } catch (Exception var18) {
         QLog.l().logger().error("QOperator", var18);
      }

      String data = result.toString();
      result.setLength(0);
      gson = GsonPool.getInstance().borrowGson();

      try {
         JsonRPC20Error rpc = (JsonRPC20Error)gson.fromJson(data, JsonRPC20Error.class);
         if (rpc == null) {
            throw new QException(Locales.locMes("error_on_server_no_get_response"));
         }

         if (rpc.getError() != null) {
            throw new QException(Locales.locMes("tack_failed") + " " + rpc.getError().getCode() + ":" + rpc.getError().getMessage());
         }
      } catch (JsonSyntaxException var20) {
         throw new QException(Locales.locMes("bad_response") + "\n" + var20.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return data;
   }

   public static AJsonRPC20 runCmd(INetProperty netProperty, String cmdName, CmdParams params) {
      QLog.l().logger().info("Выполнение универсальной команды.");

      String res;
      try {
         res = send(netProperty, cmdName, params);
      } catch (QException var13) {
         throw new ClientException(Locales.locMes("command_error"), var13);
      }

      if (res == null) {
         return null;
      } else {
         Gson gson = GsonPool.getInstance().borrowGson();

         AJsonRPC20 rpc;
         try {
            rpc = (AJsonRPC20)gson.fromJson(res, AJsonRPC20.class);
         } catch (JsonSyntaxException var11) {
            throw new ClientException(Locales.locMes("bad_response") + "\n" + var11.toString());
         } finally {
            GsonPool.getInstance().returnGson(gson);
         }

         return rpc;
      }
   }

   public static RpcGetAllServices.ServicesForWelcome getServiсes(INetProperty netProperty) {
      QLog.l().logger().info("Получение возможных услуг.");
      String res = null;

      try {
         res = send(netProperty, "Получить перечень услуг", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error"), var11);
      }

      if (res == null) {
         return null;
      } else {
         Gson gson = GsonPool.getInstance().borrowGson();

         RpcGetAllServices rpc;
         try {
            rpc = (RpcGetAllServices)gson.fromJson(res, RpcGetAllServices.class);
            QServiceTree.sailToStorm(rpc.getResult().getRoot(), new ISailListener() {
               @Override
               public void actionPerformed(TreeNode service) {
                  QService perent = (QService)service;

                  for(QService svr : perent.getChildren()) {
                     svr.setParent(perent);
                  }
               }
            });
         } catch (JsonSyntaxException var9) {
            throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
         } finally {
            GsonPool.getInstance().returnGson(gson);
         }

         return rpc.getResult();
      }
   }

   public static QCustomer standInService(INetProperty netProperty, long serviceId, String password, int priority, String inputData) {
      QLog.l().logger().info("Встать в очередь.");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      params.password = password;
      params.priority = priority;
      params.textData = inputData;
      String res = null;

      try {
         res = send(netProperty, "Поставить в очередь", params);
      } catch (QException var17) {
         throw new ClientException(Locales.locMes("command_error"), var17);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcStandInService rpc;
      try {
         rpc = (RpcStandInService)gson.fromJson(res, RpcStandInService.class);
      } catch (JsonSyntaxException var15) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var15.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static QCustomer standInSetOfServices(
      INetProperty netProperty, LinkedList<LinkedList<LinkedList<Long>>> servicesId, String password, int priority, String inputData
   ) {
      QLog.l().logger().info("Встать в очередь комплексно.");
      CmdParams params = new CmdParams();
      params.complexId = servicesId;
      params.password = password;
      params.priority = priority;
      params.textData = inputData;
      String res = null;

      try {
         res = send(netProperty, "Поставить в несколько очередей", params);
      } catch (QException var16) {
         throw new ClientException(Locales.locMes("command_error"), var16);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcStandInService rpc;
      try {
         rpc = (RpcStandInService)gson.fromJson(res, RpcStandInService.class);
      } catch (JsonSyntaxException var14) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var14.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static void changeTempAvailableService(INetProperty netProperty, long serviceId, String reason) {
      QLog.l().logger().info("Сделать услугу временно неактивной/активной.");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      params.textData = reason;

      try {
         send(netProperty, "Изменить временную доступность", params);
      } catch (QException var6) {
         throw new ClientException(Locales.locMes("command_error"), var6);
      }
   }

   public static RpcGetServiceState.ServiceState aboutService(INetProperty netProperty, long serviceId) throws QException {
      QLog.l().logger().info("Встать в очередь.");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      String res = null;

      try {
         res = send(netProperty, "Получить описание услуги", params);
      } catch (QException var14) {
         throw new QException(Locales.locMes("command_error"), var14);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetServiceState rpc;
      try {
         rpc = (RpcGetServiceState)gson.fromJson(res, RpcGetServiceState.class);
      } catch (JsonSyntaxException var12) {
         throw new QException(Locales.locMes("bad_response") + "\n" + var12.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static RpcGetServiceState.ServiceState getServiceConsistency(INetProperty netProperty, long serviceId) throws QException {
      QLog.l().logger().info("Встать в очередь.");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      String res = null;

      try {
         res = send(netProperty, "Получить очередь услуги", params);
      } catch (QException var14) {
         throw new QException(Locales.locMes("command_error"), var14);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetServiceState rpc;
      try {
         rpc = (RpcGetServiceState)gson.fromJson(res, RpcGetServiceState.class);
      } catch (JsonSyntaxException var12) {
         throw new QException(Locales.locMes("bad_response") + "\n" + var12.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static int aboutServicePersonLimitOver(INetProperty netProperty, long serviceId, String inputData) throws QException {
      QLog.l().logger().info("Узнать можно ли вставать в услугу с такими введенными данными.");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      params.textData = inputData;
      String res = null;

      try {
         res = send(netProperty, "Получить возможность встать с этими данными", params);
      } catch (QException var15) {
         throw new QException(Locales.locMes("command_error"), var15);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetInt rpc;
      try {
         rpc = (RpcGetInt)gson.fromJson(res, RpcGetInt.class);
      } catch (JsonSyntaxException var13) {
         throw new QException(Locales.locMes("bad_response") + "\n" + var13.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static LinkedList<QUser> getUsers(INetProperty netProperty) {
      QLog.l().logger().info("Получение описания всех юзеров для выбора себя.");
      String res = null;

      try {
         res = send(netProperty, "Получить перечень пользователей", null);
      } catch (QException var16) {
         Uses.closeSplash();
         throw new ClientException(Locales.locMes("command_error2"), var16);
      } finally {
         if (res == null || res.isEmpty()) {
            System.exit(1);
         }
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetUsersList rpc;
      try {
         rpc = (RpcGetUsersList)gson.fromJson(res, RpcGetUsersList.class);
      } catch (JsonSyntaxException var14) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var14.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static RpcGetSelfSituation.SelfSituation getSelfServices(INetProperty netProperty, long userId) throws QException {
      return getSelfServices(netProperty, userId, null);
   }

   public static RpcGetSelfSituation.SelfSituation getSelfServices(INetProperty netProperty, long userId, Boolean forced) throws QException {
      QLog.l().logger().info("Получение описания очередей для юзера.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.textData = QConfig.cfg().getPointN();
      params.requestBack = forced;

      String res;
      try {
         res = send(netProperty, "Получить состояние очередей", params);
      } catch (QException var15) {
         Uses.closeSplash();
         throw new QException(Locales.locMes("command_error2"), var15);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetSelfSituation rpc;
      try {
         rpc = (RpcGetSelfSituation)gson.fromJson(res, RpcGetSelfSituation.class);
      } catch (JsonSyntaxException var13) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var13.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static boolean getSelfServicesCheck(INetProperty netProperty, long userId) {
      QLog.l().logger().info("Получение описания очередей для юзера.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.textData = QConfig.cfg().getPointN();

      String res;
      try {
         res = send(netProperty, "Получить состояние очередей с проверкой", params);
      } catch (QException var14) {
         Uses.closeSplash();
         throw new ServerException(Locales.locMes("command_error2"), var14);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetBool rpc;
      try {
         rpc = (RpcGetBool)gson.fromJson(res, RpcGetBool.class);
      } catch (JsonSyntaxException var12) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var12.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static QCustomer inviteNextCustomer(INetProperty netProperty, long userId) {
      QLog.l().logger().info("Получение следующего юзера из очередей, обрабатываемых юзером.");
      CmdParams params = new CmdParams();
      params.userId = userId;

      String res;
      try {
         res = send(netProperty, "Получить следующего клиента", params);
      } catch (QException var14) {
         throw new ClientException(Locales.locMes("command_error2"), var14);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcInviteCustomer rpc;
      try {
         rpc = (RpcInviteCustomer)gson.fromJson(res, RpcInviteCustomer.class);
      } catch (JsonSyntaxException var12) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var12.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static void killNextCustomer(INetProperty netProperty, long userId, Long customerId) {
      QLog.l().logger().info("Удаление вызванного юзером кастомера.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.customerId = customerId;

      try {
         send(netProperty, "Удалить следующего клиента", params);
      } catch (QException var6) {
         throw new ClientException(Locales.locMes("command_error2"), var6);
      }
   }

   public static void сustomerToPostpone(INetProperty netProperty, long userId, Long customerId, String status, int postponedPeriod, boolean isMine) {
      QLog.l().logger().info("Перемещение вызванного юзером кастомера в пул отложенных.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.customerId = customerId;
      params.textData = status;
      params.postponedPeriod = postponedPeriod;
      params.isMine = isMine;

      try {
         send(netProperty, "Клиента в пул отложенных", params);
      } catch (QException var9) {
         throw new ClientException(Locales.locMes("command_error2"), var9);
      }
   }

   public static void postponeCustomerChangeStatus(INetProperty netProperty, long postponCustomerId, String status) {
      QLog.l().logger().info("Перемещение вызванного юзером кастомера в пул отложенных.");
      CmdParams params = new CmdParams();
      params.customerId = postponCustomerId;
      params.textData = status;

      try {
         send(netProperty, "Сменить статус отложенному", params);
      } catch (QException var6) {
         throw new ClientException(Locales.locMes("command_error2"), var6);
      }
   }

   public static void getStartCustomer(INetProperty netProperty, long userId) {
      QLog.l().logger().info("Начать работу с вызванным кастомером.");
      CmdParams params = new CmdParams();
      params.userId = userId;

      try {
         send(netProperty, "Начать работу с клиентом", params);
      } catch (QException var5) {
         throw new ClientException(Locales.locMes("command_error2"), var5);
      }
   }

   public static QCustomer getFinishCustomer(INetProperty netProperty, long userId, Long customerId, Long resultId, String comments) {
      QLog.l().logger().info("Закончить работу с вызванным кастомером.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.customerId = customerId;
      params.resultId = resultId;
      params.textData = comments;
      String res = null;

      try {
         res = send(netProperty, "Закончить работу с клиентом", params);
      } catch (QException var17) {
         throw new ClientException(Locales.locMes("command_error2"), var17);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcStandInService rpc;
      try {
         rpc = (RpcStandInService)gson.fromJson(res, RpcStandInService.class);
      } catch (JsonSyntaxException var15) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var15.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static void redirectCustomer(
      INetProperty netProperty, long userId, Long customerId, long serviceId, boolean requestBack, String comments, Long resultId
   ) {
      QLog.l().logger().info("Переадресовать клиента в другую очередь.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.customerId = customerId;
      params.serviceId = serviceId;
      params.requestBack = requestBack;
      params.resultId = resultId;
      params.textData = comments;

      try {
         send(netProperty, "Переадресовать клиента к другой услуге", params);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error2"), var11);
      }
   }

   /** @deprecated */
   public static Element setLive(INetProperty netProperty, long userId) {
      QLog.l().logger().info("Ответим что живы и здоровы.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      return null;
   }

   public static LinkedList<RpcGetServerState.ServiceInfo> getServerState(INetProperty netProperty) {
      QLog.l().logger().info("Получение описания состояния сервера.");
      String res = null;

      try {
         res = send(netProperty, "Получить состояние сервера", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error"), var11);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetServerState rpc;
      try {
         rpc = (RpcGetServerState)gson.fromJson(res, RpcGetServerState.class);
      } catch (JsonSyntaxException var9) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static String getWelcomeState(INetProperty netProperty, String message, boolean dropTicketsCounter) {
      QLog.l().logger().info("Получение описания состояния пункта регистрации.");
      String res = null;
      CmdParams params = new CmdParams();
      params.dropTicketsCounter = dropTicketsCounter;

      try {
         res = send(netProperty, message, params);
      } catch (QException var14) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var14.toString());
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetSrt rpc;
      try {
         rpc = (RpcGetSrt)gson.fromJson(res, RpcGetSrt.class);
      } catch (JsonSyntaxException var12) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var12.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static String setServiseFire(INetProperty netProperty, long serviceId, long userId, int coeff) {
      QLog.l().logger().info("Привязка услуги пользователю на горячую.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.serviceId = serviceId;
      params.coeff = coeff;

      String res;
      try {
         res = send(netProperty, "Добавить услугу на горячую", params);
      } catch (QException var17) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var17.toString());
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetSrt rpc;
      try {
         rpc = (RpcGetSrt)gson.fromJson(res, RpcGetSrt.class);
      } catch (JsonSyntaxException var15) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var15.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static String deleteServiseFire(INetProperty netProperty, long serviceId, long userId) {
      QLog.l().logger().info("Удаление услуги пользователю на горячую.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.serviceId = serviceId;

      String res;
      try {
         res = send(netProperty, "Удалить услугу на горячую", params);
      } catch (QException var16) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var16.toString());
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetSrt rpc;
      try {
         rpc = (RpcGetSrt)gson.fromJson(res, RpcGetSrt.class);
      } catch (JsonSyntaxException var14) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var14.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static Element getBoardConfig(INetProperty netProperty) throws DocumentException {
      QLog.l().logger().info("Получение конфигурации главного табло - ЖК или плазмы.");

      String res;
      try {
         res = send(netProperty, "Получить конфигурацию табло", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var11.toString());
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetSrt rpc;
      try {
         rpc = (RpcGetSrt)gson.fromJson(res, RpcGetSrt.class);
      } catch (JsonSyntaxException var9) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return DocumentHelper.parseText(rpc.getResult()).getRootElement();
   }

   public static void saveBoardConfig(INetProperty netProperty, Element boardConfig) {
      QLog.l().logger().info("Сохранение конфигурации главного табло - ЖК или плазмы.");
      CmdParams params = new CmdParams();
      params.textData = boardConfig.asXML();

      try {
         send(netProperty, "Сохранить конфигурацию табло", params);
      } catch (QException var4) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var4.toString());
      }
   }

   public static RpcGetGridOfDay.GridDayAndParams getPreGridOfDay(INetProperty netProperty, long serviceId, Date date, long advancedCustomer) {
      QLog.l().logger().info("Получить таблицу дня");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      params.date = date.getTime();
      params.customerId = advancedCustomer;

      String res;
      try {
         res = send(netProperty, "Получить дневную предварительную таблицу", params);
      } catch (QException var17) {
         throw new ClientException(Locales.locMes("command_error2"), var17);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetGridOfDay rpc;
      try {
         rpc = (RpcGetGridOfDay)gson.fromJson(res, RpcGetGridOfDay.class);
      } catch (JsonSyntaxException var15) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var15.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static RpcGetGridOfWeek.GridAndParams getGridOfWeek(INetProperty netProperty, long serviceId, Date date, long advancedCustomer) {
      QLog.l().logger().info("Получить таблицу");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      params.date = date.getTime();
      params.customerId = advancedCustomer;

      String res;
      try {
         res = send(netProperty, "Получить недельную предварительную таблицу", params);
      } catch (QException var17) {
         throw new ClientException(Locales.locMes("command_error2"), var17);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetGridOfWeek rpc;
      try {
         rpc = (RpcGetGridOfWeek)gson.fromJson(res, RpcGetGridOfWeek.class);
      } catch (JsonSyntaxException var15) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var15.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static QAdvanceCustomer standInServiceAdvance(
      INetProperty netProperty, long serviceId, Date date, long advancedCustomer, String inputData, String comments
   ) {
      QLog.l().logger().info("Записать предварительно в очередь.");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      params.date = date.getTime();
      params.customerId = advancedCustomer;
      params.textData = inputData;
      params.comments = comments;

      String res;
      try {
         res = send(netProperty, "Поставить в очередь предварительно", params);
      } catch (QException var19) {
         throw new ClientException(Locales.locMes("command_error"), var19);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetAdvanceCustomer rpc;
      try {
         rpc = (RpcGetAdvanceCustomer)gson.fromJson(res, RpcGetAdvanceCustomer.class);
      } catch (JsonSyntaxException var17) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var17.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static RpcStandInService standAndCheckAdvance(INetProperty netProperty, Long advanceID) {
      QLog.l().logger().info("Постановка предварительно записанных в очередь.");
      CmdParams params = new CmdParams();
      params.customerId = advanceID;

      String res;
      try {
         res = send(netProperty, "Поставить предварительно записанного", params);
      } catch (QException var13) {
         throw new ClientException(Locales.locMes("command_error2"), var13);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcStandInService rpc;
      try {
         rpc = (RpcStandInService)gson.fromJson(res, RpcStandInService.class);
      } catch (JsonSyntaxException var11) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var11.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc;
   }

   public static JsonRPC20OK removeAdvancedCustomer(INetProperty netProperty, Long advanceID) {
      QLog.l().logger().info("Удаление предварительно записанных в очередь.");
      CmdParams params = new CmdParams();
      params.customerId = advanceID;

      String res;
      try {
         res = send(netProperty, "Удалить предварительно записанного", params);
      } catch (QException var13) {
         throw new ClientException(Locales.locMes("command_error2"), var13);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      JsonRPC20OK rpc;
      try {
         rpc = (JsonRPC20OK)gson.fromJson(res, JsonRPC20OK.class);
      } catch (JsonSyntaxException var11) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var11.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc;
   }

   public static void restartServer(INetProperty netProperty) {
      QLog.l().logger().info("Команда на рестарт сервера.");

      try {
         send(netProperty, "RESTART", null);
      } catch (QException var2) {
         throw new ClientException(Locales.locMes("command_error2"), var2);
      }
   }

   public static QRespItem getResporseList(INetProperty netProperty) {
      QLog.l().logger().info("Команда на получение дерева отзывов.");
      String res = null;

      try {
         res = send(netProperty, "Получить список отзывов", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error"), var11);
      }

      if (res == null) {
         return null;
      } else {
         Gson gson = GsonPool.getInstance().borrowGson();

         RpcGetRespTree rpc;
         try {
            rpc = (RpcGetRespTree)gson.fromJson(res, RpcGetRespTree.class);
         } catch (JsonSyntaxException var9) {
            throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
         } finally {
            GsonPool.getInstance().returnGson(gson);
         }

         return rpc.getResult();
      }
   }

   public static void setResponseAnswer(INetProperty netProperty, QRespItem resp, Long userID, Long serviceID, Long customerID, String clientData) {
      QLog.l().logger().info("Отправка выбранного отзыва.");
      CmdParams params = new CmdParams();
      params.responseId = resp.getId();
      params.serviceId = serviceID;
      params.userId = userID;
      params.customerId = customerID;
      params.textData = clientData;
      params.comments = resp.data;

      try {
         send(netProperty, "Оставить отзыв", params);
      } catch (QException var8) {
         throw new ServerException(Locales.locMes("command_error"), var8);
      }
   }

   public static QInfoItem getInfoTree(INetProperty netProperty) {
      QLog.l().logger().info("Команда на получение информационного дерева.");
      String res = null;

      try {
         res = send(netProperty, "Получить информационное дерево", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error"), var11);
      }

      if (res == null) {
         return null;
      } else {
         Gson gson = GsonPool.getInstance().borrowGson();

         RpcGetInfoTree rpc;
         try {
            rpc = (RpcGetInfoTree)gson.fromJson(res, RpcGetInfoTree.class);
         } catch (JsonSyntaxException var9) {
            throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
         } finally {
            GsonPool.getInstance().returnGson(gson);
         }

         return rpc.getResult();
      }
   }

   public static QAuthorizationCustomer getClientAuthorization(INetProperty netProperty, String id) {
      QLog.l().logger().info("Получение описания авторизованного пользователя.");
      CmdParams params = new CmdParams();
      params.clientAuthId = id;

      String res;
      try {
         res = send(netProperty, "Идентифицировать клиента", params);
      } catch (QException var13) {
         throw new ClientException(Locales.locMes("command_error"), var13);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetAuthorizCustomer rpc;
      try {
         rpc = (RpcGetAuthorizCustomer)gson.fromJson(res, RpcGetAuthorizCustomer.class);
      } catch (JsonSyntaxException var11) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var11.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static LinkedList<QResult> getResultsList(INetProperty netProperty) {
      QLog.l().logger().info("Команда на получение списка возможных результатов работы с клиентом.");

      String res;
      try {
         res = send(netProperty, "Получить получение списка возможных результатов", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error"), var11);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetResultsList rpc;
      try {
         rpc = (RpcGetResultsList)gson.fromJson(res, RpcGetResultsList.class);
      } catch (JsonSyntaxException var9) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static String setCustomerPriority(INetProperty netProperty, int prioritet, String customer) {
      QLog.l().logger().info("Команда на повышение приоритета кастомеру.");
      CmdParams params = new CmdParams();
      params.priority = prioritet;
      params.clientAuthId = customer;

      String res;
      try {
         res = send(netProperty, "Изменить приоритет", params);
      } catch (QException var14) {
         throw new ClientException(Locales.locMes("command_error"), var14);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetSrt rpc;
      try {
         rpc = (RpcGetSrt)gson.fromJson(res, RpcGetSrt.class);
      } catch (JsonSyntaxException var12) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var12.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static RpcGetTicketHistory.TicketHistory checkCustomerNumber(INetProperty netProperty, String customerNumber) {
      QLog.l().logger().info("Команда проверки номера кастомера.");
      CmdParams params = new CmdParams();
      params.clientAuthId = customerNumber;

      String res;
      try {
         res = send(netProperty, "Проверить номер", params);
      } catch (QException var13) {
         throw new ClientException(Locales.locMes("command_error"), var13);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetTicketHistory rpc;
      try {
         rpc = (RpcGetTicketHistory)gson.fromJson(res, RpcGetTicketHistory.class);
      } catch (JsonSyntaxException var11) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var11.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static LinkedList<QCustomer> getPostponedPoolInfo(INetProperty netProperty) {
      QLog.l().logger().info("Команда на обновление пула отложенных.");

      String res;
      try {
         res = send(netProperty, "Получить состояние пула отложенных", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error"), var11);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetPostponedPoolInfo rpc;
      try {
         rpc = (RpcGetPostponedPoolInfo)gson.fromJson(res, RpcGetPostponedPoolInfo.class);
      } catch (JsonSyntaxException var9) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static LinkedList<String> getBanedList(INetProperty netProperty) {
      QLog.l().logger().info("Команда получение списка забаненных.");

      String res;
      try {
         res = send(netProperty, "Получить список забаненых", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error"), var11);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcBanList rpc;
      try {
         rpc = (RpcBanList)gson.fromJson(res, RpcBanList.class);
      } catch (JsonSyntaxException var9) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getBanList();
   }

   public static void invitePostponeCustomer(INetProperty netProperty, long userId, Long id) {
      QLog.l().logger().info("Команда на вызов кастомера из пула отложенных.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.customerId = id;

      try {
         send(netProperty, "Вызвать отложенного из пула отложенных", params);
      } catch (QException var6) {
         throw new ClientException(Locales.locMes("command_error"), var6);
      }
   }

   public static void restartMainTablo(INetProperty serverNetProperty) {
      QLog.l().logger().info("Команда на рестарт главного табло.");

      try {
         send(serverNetProperty, "Рестарт главного твбло", null);
      } catch (QException var2) {
         throw new ClientException(Locales.locMes("command_error"), var2);
      }
   }

   public static void changeFlexPriority(INetProperty netProperty, long userId, String smartData) {
      QLog.l().logger().info("Изменение приоритетов услуг оператором.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.textData = smartData;

      try {
         send(netProperty, "Изменить гибкий приоритет", params);
      } catch (QException var6) {
         throw new ClientException(Locales.locMes("command_error"), var6);
      }
   }

   public static void setRunningText(INetProperty netProperty, String text, String nameSection) {
      QLog.l().logger().info("Получение описания авторизованного пользователя.");
      CmdParams params = new CmdParams();
      params.textData = text;
      params.infoItemName = nameSection;

      try {
         send(netProperty, "Изменить бегущий текст на табло", params);
      } catch (QException var5) {
         throw new ClientException(Locales.locMes("command_error"), var5);
      }
   }

   public static QStandards getStandards(INetProperty netProperty) {
      QLog.l().logger().info("Команда получение нормативов.");

      String res;
      try {
         res = send(netProperty, "Получить нормативы", null);
      } catch (QException var11) {
         throw new ClientException(Locales.locMes("command_error"), var11);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetStandards rpc;
      try {
         rpc = (RpcGetStandards)gson.fromJson(res, RpcGetStandards.class);
      } catch (JsonSyntaxException var9) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var9.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static boolean setBussy(INetProperty netProperty, long userId, boolean lock) {
      QLog.l().logger().info("Изменение приоритетов услуг оператором.");
      CmdParams params = new CmdParams();
      params.userId = userId;
      params.requestBack = lock;

      String res;
      try {
         res = send(netProperty, "Перерыв оператора", params);
      } catch (QException var15) {
         throw new ClientException(Locales.locMes("command_error"), var15);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetBool rpc;
      try {
         rpc = (RpcGetBool)gson.fromJson(res, RpcGetBool.class);
      } catch (JsonSyntaxException var13) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var13.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static LinkedHashMap<String, ServerProps.Section> getProperties(INetProperty netProperty) {
      QLog.l().logger().info("Получить параметры.");
      CmdParams params = new CmdParams();

      String res;
      try {
         res = send(netProperty, "Все параметры из БД", params);
      } catch (QException var12) {
         throw new ClientException(Locales.locMes("command_error"), var12);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetProperties rpc;
      try {
         rpc = (RpcGetProperties)gson.fromJson(res, RpcGetProperties.class);
      } catch (JsonSyntaxException var10) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var10.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static LinkedHashMap<String, ServerProps.Section> saveProperties(INetProperty netProperty, List<QProperty> properties) {
      QLog.l().logger().info("Изменить и сохранить параметеры в ДБ на сервере.");
      CmdParams params = new CmdParams();
      params.properties = properties;

      String res;
      try {
         res = send(netProperty, "Создать все параметры в БД", params);
      } catch (QException var13) {
         throw new ClientException(Locales.locMes("command_error"), var13);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetProperties rpc;
      try {
         rpc = (RpcGetProperties)gson.fromJson(res, RpcGetProperties.class);
      } catch (JsonSyntaxException var11) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var11.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static LinkedHashMap<String, ServerProps.Section> initProperties(INetProperty netProperty, List<QProperty> properties) {
      QLog.l().logger().info("Если таких параметров нет, то создать их в ДБ на сервере.");
      CmdParams params = new CmdParams();
      params.properties = properties;

      String res;
      try {
         res = send(netProperty, "Сохранить все параметры в БД", params);
      } catch (QException var13) {
         throw new ClientException(Locales.locMes("command_error"), var13);
      }

      Gson gson = GsonPool.getInstance().borrowGson();

      RpcGetProperties rpc;
      try {
         rpc = (RpcGetProperties)gson.fromJson(res, RpcGetProperties.class);
      } catch (JsonSyntaxException var11) {
         throw new ClientException(Locales.locMes("bad_response") + "\n" + var11.toString());
      } finally {
         GsonPool.getInstance().returnGson(gson);
      }

      return rpc.getResult();
   }

   public static void initRoll(INetProperty netProperty, Long serviceId, int first, int last, int current) {
      QLog.l().logger().info("Изменение приоритетов услуг оператором.");
      CmdParams params = new CmdParams();
      params.serviceId = serviceId;
      params.first = first;
      params.last = last;
      params.current = current;

      try {
         String res = send(netProperty, "Reinit roll of tickets", params);
      } catch (QException var8) {
         throw new ClientException(Locales.locMes("command_error"), var8);
      }
   }
}
