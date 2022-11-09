package ru.apertum.qsystem.common;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.dom4j.Element;
import ru.apertum.qsystem.client.Locales;
import ru.apertum.qsystem.common.cmd.RpcGetAllServices;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.common.model.INetProperty;

public final class Uses {
   public static final String KEY_ROLL = "ticket_roll";
   public static final int PRIORITY_LOW = 0;
   public static final int PRIORITY_NORMAL = 1;
   public static final int PRIORITY_HI = 2;
   public static final int PRIORITY_VIP = 3;
   public static final int[] PRIORITYS = new int[]{0, 1, 2, 3};
   private static final LinkedHashMap<Integer, String> PRIORITYS_WORD = new LinkedHashMap<>();
   public static final String PROPERTIES_FILE = "config/qsystem.properties";
   public static final int SERVICE_REMAINS = 0;
   public static final int SERVICE_NORMAL = 1;
   public static final int SERVICE_VIP = 2;
   public static final LinkedHashMap<Integer, String> COEFF_WORD = new LinkedHashMap<>();
   public static final String TAG_REP_STATISTIC = "Статистика";
   public static final String TAG_REP_PARAM_COUNT = "Знаменатель";
   public static final String TAG_REP_PARAM_AVG = "Среднее";
   public static final String TAG_REP_RECORD = "Запись";
   public static final String TAG_REP_SERVICE_WORKED = "ОбслуженоПоУслуге";
   public static final String TAG_REP_SERVICE_WAIT = "ОжидаютПоУслуге";
   public static final String TAG_REP_SERVICE_AVG_WORK = "СрВрОбслуживанияПоУслуге";
   public static final String TAG_REP_SERVICE_AVG_WAIT = "СрВрОжиданияПоУслуге";
   public static final String TAG_REP_SERVICE_KILLED = "ОтклоненныхПоУслуге";
   public static final String TAG_REP_USER_WORKED = "ОбслуженоПользователем";
   public static final String TAG_REP_USER_AVG_WORK = "СрВрОбслуживанияПользователем";
   public static final String TAG_REP_USER_KILLED = "ОтклоненныхПользователем";
   public static final String TAG_REP_WORKED = "Обслуженных";
   public static final String TAG_REP_AVG_TIME_WORK = "СрВрОбслуживания";
   public static final String TAG_REP_KILLED = "Отклоненных";
   public static final String TAG_PROP_SERVICES = "Услуги";
   public static final String TAG_PROP_SERVICE = "Услуга";
   public static final String TAG_PROP_NAME = "Наименование";
   public static final String TAG_PROP_DESCRIPTION = "Описание";
   public static final String TAG_PROP_PREFIX = "Префикс";
   public static final String TAG_PROP_ADVANCE_LIMIT = "Лимит";
   public static final String TAG_PROP_ADVANCE_PERIOD_LIMIT = "ЛимитПредвЗаписиВДнях";
   public static final String TAG_PROP_USERS = "Пользователи";
   public static final String TAG_PROP_USER = "Пользователь";
   public static final String TAG_PROP_PASSWORD = "Пароль";
   public static final String TAG_PROP_OWN_SERVS = "ОказываемыеУслуги";
   public static final String TAG_PROP_OWN_SRV = "ОказываемаяУслуга";
   public static final String TAG_PROP_KOEF = "КоэффициентУчастия";
   public static final String TAG_PROP_CONNECTION = "Сеть";
   public static final String TAG_PROP_SERV_PORT = "ПортСервера";
   public static final String TAG_PROP_WEB_SERV_PORT = "ПортВебСервера";
   public static final String TAG_PROP_CLIENT_PORT = "ПортКлиента";
   public static final String TAG_PROP_SERV_ADDRESS = "АдресСервера";
   public static final String TAG_PROP_CLIENT_ADDRESS = "АдресКлиента";
   public static final String TAG_PROP_STATUS = "Статус";
   public static final String TAG_PROP_START_TIME = "ВремяНачалаРаботы";
   public static final String TAG_PROP_FINISH_TIME = "ВремяЗавершенияРаботы";
   public static final String TAG_PROP_VERSION = "ВерсияХранилищаКонфигурации";
   public static final String TAG_PROP_INPUT_REQUIRED = "ТребованиеКлиентскихДанных";
   public static final String TAG_PROP_INPUT_CAPTION = "ЗаголовокФормыВводаКлДанных";
   public static final String TAG_PROP_RESULT_REQUIRED = "ТребованиеРезультатаРаботы";
   public static final String TAG_BOARD_PROPS = "Параметры";
   public static final String TAG_BOARD_PROP = "Параметер";
   public static final String TAG_BOARD_NAME = "Наименование";
   public static final String TAG_BOARD_VALUE = "Значение";
   public static final String TAG_BOARD_TYPE = "Тип";
   public static final String TAG_BOARD_READ_ONLY = "ReadOnly";
   public static final String TAG_BOARD_FRACTAL = "Fractal";
   public static final String TAG_BOARD_MONITOR = "Номер дополнительного монитора для табло";
   public static final String TAG_BOARD_LINES_COUNT = "Количество строк на табло";
   public static final String TAG_BOARD_COLS_COUNT = "Количество столбцов на табло";
   public static final String TAG_BOARD_DELAY_VISIBLE = "Минимальное время индикации на табло";
   public static final String TAG_BOARD_FON_IMG = "Фоновое изображение";
   public static final String TAG_BOARD_FONT_SIZE = "Размер шрифта";
   public static final String TAG_BOARD_FONT_COLOR = "Цвет шрифта";
   public static final String TAG_BOARD_PANEL_SIZE = "Размер";
   public static final String TAG_BOARD_RUNNING_TEXT = "Бегущий текст";
   public static final String TAG_BOARD_VIDEO_FILE = "Видеофайл";
   public static final String TAG_BOARD_VISIBLE_PANEL = "visible";
   public static final String TAG_BOARD_SPEED_TEXT = "Скорость бегущего текста";
   public static final String TAG_BOARD_GRID_NEXT_COLS = "Колонки табл след";
   public static final String TAG_BOARD_GRID_NEXT_ROWS = "Строки табл след";
   public static final String TAG_BOARD_SIMPLE_DATE = "Простая дата";
   public static final String TAG_BOARD_GRID_NEXT = "Таблица следующих";
   public static final String TAG_BOARD_FON_COLOR = "Цвет фона";
   public static final String TAG_BOARD_FONT_SIZE_CAPTION = "Размер шрифта заголовка";
   public static final String TAG_BOARD_FONT_NAME = "Font name";
   public static final String TAG_BOARD_FONT_SIZE_LINE = "Размер шрифта строк";
   public static final String TAG_BOARD_FONT_COLOR_CAPTION = "Цвет шрифта заголовка";
   public static final String TAG_BOARD_FONT_COLOR_LEFT = "Цвет шрифта левого столбца";
   public static final String TAG_BOARD_FONT_COLOR_RIGHT = "Цвет шрифта правого столбца";
   public static final String TAG_BOARD_FONT_COLOR_LINE = "Цвет надписи строки табло";
   public static final String TAG_BOARD_LINE_BORDER = "Окантовка строк";
   public static final String TAG_BOARD_LINE_DELIMITER = "Разделитель столбцов";
   public static final String TAG_BOARD_LEFT_PIC = "Left column pic";
   public static final String TAG_BOARD_RIGHT_PIC = "Right column pic";
   public static final String TAG_BOARD_EXT_PIC = "Ext column pic";
   public static final String TAG_BOARD_LEFT_CAPTION = "Заголовок левого столбца";
   public static final String TAG_BOARD_RIGHT_CAPTION = "Заголовок правого столбца";
   public static final String TAG_BOARD_EXT_CAPTION = "Заголовок дополнительного столбца";
   public static final String TAG_BOARD_EXT_POSITION = "Порядок дополнительного столбца";
   public static final String TAG_BOARD_GRID_NEXT_CAPTION = "Заголовок таблицы следующих";
   public static final String TAG_BOARD_GRID_NEXT_FRAME_BORDER = "Рамка таблицы следующих";
   public static final String TAG_BOARD_LINE_COLOR = "Цвет рамки строки табло";
   public static final String TAG_BOARD_LINE_CAPTION = "Надпись строки табло";
   public static final String TAG_BOARD_CALL_PANEL = "Панель вызванного";
   public static final String TAG_BOARD_CALL_PANEL_BACKGROUND = "Картинка панели вызванного";
   public static final String TAG_BOARD_CALL_PANEL_X = "Панель вызванного-X";
   public static final String TAG_BOARD_CALL_PANEL_Y = "Панель вызванного-Y";
   public static final String TAG_BOARD_CALL_PANEL_WIDTH = "Панель вызванного-ширина";
   public static final String TAG_BOARD_CALL_PANEL_HEIGHT = "Панель вызванного-высота";
   public static final String TAG_BOARD_CALL_PANEL_DELAY = "Панель вызванного-время показа сек";
   public static final String TAG_BOARD_CALL_PANEL_TEMPLATE = "Панель вызванного-текст html+###";
   public static final String TAG_BOARD = "Board";
   public static final String TAG_BOARD_MAIN = "Main";
   public static final String TAG_BOARD_TOP = "Top";
   public static final String TAG_BOARD_BOTTOM = "Bottom";
   public static final String TAG_BOARD_BOTTOM_2 = "Bottom2";
   public static final String TAG_BOARD_LEFT = "Left";
   public static final String TAG_BOARD_RIGHT = "Right";
   public static final String BOARD_VALUE_PAUSE = "Время присутствия записи на табло";
   public static final String BOARD_ADRESS_MAIN_BOARD = "Адрес главного табло системы";
   public static final int BOARD_TYPE_INT = 1;
   public static final int BOARD_TYPE_DOUBLE = 2;
   public static final int BOARD_TYPE_STR = 3;
   public static final int BOARD_TYPE_BOOL = 4;
   public static final String TASK_FOR_ALL_SITE = "Для всех сайтов домена";
   public static final String TASK_STAND_IN = "Поставить в очередь";
   public static final String TASK_STAND_COMPLEX = "Поставить в несколько очередей";
   public static final String TASK_ADVANCE_STAND_IN = "Поставить в очередь предварительно";
   public static final String TASK_ADVANCE_CHECK_AND_STAND = "Поставить предварительно записанного";
   public static final String TASK_REMOVE_ADVANCE_CUSTOMER = "Удалить предварительно записанного";
   public static final String TASK_REDIRECT_CUSTOMER = "Переадресовать клиента к другой услуге";
   public static final String TASK_GET_SERVICES = "Получить перечень услуг";
   public static final String TASK_ABOUT_SERVICE = "Получить описание услуги";
   public static final String TASK_GET_SERVICE_CONSISANCY = "Получить очередь услуги";
   public static final String TASK_ABOUT_SERVICE_PERSON_LIMIT = "Получить возможность встать с этими данными";
   public static final String TASK_GET_SERVICE_PREINFO = "Получить информацию по услуге";
   public static final String TASK_GET_INFO_PRINT = "Получить информацию для печати";
   public static final String TASK_GET_USERS = "Получить перечень пользователей";
   public static final String TASK_GET_SELF = "Получить описание пользователя";
   public static final String TASK_GET_SELF_SERVICES = "Получить состояние очередей";
   public static final String TASK_GET_POSTPONED_POOL = "Получить состояние пула отложенных";
   public static final String TASK_GET_BAN_LIST = "Получить список забаненых";
   public static final String TASK_INVITE_POSTPONED = "Вызвать отложенного из пула отложенных";
   public static final String TASK_GET_SELF_SERVICES_CHECK = "Получить состояние очередей с проверкой";
   public static final String TASK_INVITE_NEXT_CUSTOMER = "Получить следующего клиента";
   public static final String TASK_KILL_NEXT_CUSTOMER = "Удалить следующего клиента";
   public static final String TASK_CUSTOMER_TO_POSTPON = "Клиента в пул отложенных";
   public static final String TASK_POSTPON_CHANGE_STATUS = "Сменить статус отложенному";
   public static final String TASK_START_CUSTOMER = "Начать работу с клиентом";
   public static final String TASK_FINISH_CUSTOMER = "Закончить работу с клиентом";
   public static final String TASK_I_AM_LIVE = "Я горец!";
   public static final String TASK_RESTART = "RESTART";
   public static final String TASK_RESTART_MAIN_TABLO = "Рестарт главного твбло";
   public static final String TASK_REFRESH_POSTPONED_POOL = "NEW_POSTPONED_NOW";
   public static final String TASK_SERVER_STATE = "Получить состояние сервера";
   public static final String TASK_SET_SERVICE_FIRE = "Добавить услугу на горячую";
   public static final String TASK_DELETE_SERVICE_FIRE = "Удалить услугу на горячую";
   public static final String TASK_GET_BOARD_CONFIG = "Получить конфигурацию табло";
   public static final String TASK_SAVE_BOARD_CONFIG = "Сохранить конфигурацию табло";
   public static final String TASK_GET_GRID_OF_WEEK = "Получить недельную предварительную таблицу";
   public static final String TASK_GET_GRID_OF_DAY = "Получить дневную предварительную таблицу";
   public static final String TASK_GET_INFO_TREE = "Получить информационное дерево";
   public static final String TASK_GET_RESULTS_LIST = "Получить получение списка возможных результатов";
   public static final String TASK_GET_RESPONSE_LIST = "Получить список отзывов";
   public static final String TASK_SET_RESPONSE_ANSWER = "Оставить отзыв";
   public static final String REPORT_CURRENT_USERS = "current_users";
   public static final String REPORT_CURRENT_SERVICES = "current_services";
   public static final String TASK_GET_CLIENT_AUTHORIZATION = "Идентифицировать клиента";
   public static final String TASK_SET_CUSTOMER_PRIORITY = "Изменить приоритет";
   public static final String TASK_CHECK_CUSTOMER_NUMBER = "Проверить номер";
   public static final String TASK_CHANGE_FLEX_PRIORITY = "Изменить гибкий приоритет";
   public static final String TASK_CHANGE_RUNNING_TEXT_ON_BOARD = "Изменить бегущий текст на табло";
   public static final String TASK_CHANGE_TEMP_AVAILABLE_SERVICE = "Изменить временную доступность";
   public static final String TASK_GET_STANDARDS = "Получить нормативы";
   public static final String TASK_SET_BUSSY = "Перерыв оператора";
   public static final String TASK_GET_PROPERTIES = "Все параметры из БД";
   public static final String TASK_SAVE_PROPERTIES = "Сохранить все параметры в БД";
   public static final String TASK_INIT_PROPERTIES = "Создать все параметры в БД";
   public static final String TASK_REINIT_ROLL = "Reinit roll of tickets";
   public static final String REPORT_FORMAT_HTML = "html";
   public static final String REPORT_FORMAT_RTF = "rtf";
   public static final String REPORT_FORMAT_PDF = "pdf";
   public static final String REPORT_FORMAT_XLSX = "xlsx";
   public static final String REPORT_FORMAT_CSV = "csv";
   public static final String ANCHOR_REPORT_LIST = "<tr><td><center>#REPORT_LIST_ANCHOR#</center></td></tr>";
   public static final String ANCHOR_DATA_FOR_REPORT = "#DATA_FOR_REPORT#";
   public static final String ANCHOR_ERROR_INPUT_DATA = "#ERROR_INPUT_DATA#";
   public static final String ANCHOR_USERS_FOR_REPORT = "#USERS_LIST_ANCHOR#";
   public static final String ANCHOR_PROJECT_NAME_FOR_REPORT = "#PROJECT_NAME_ANCHOR#";
   public static final String ANCHOR_COOCIES = "#COOCIES_ANCHOR#";
   public static final String WELCOME_LOCK = "#WELCOME_LOCK#";
   public static final String WELCOME_UNLOCK = "#WELCOME_UNLOCK#";
   public static final String WELCOME_OFF = "#WELCOME_OFF#";
   public static final String WELCOME_REINIT = "#WELCOME_REINIT#";
   public static final String[] RUSSIAN_MONAT = new String[]{
      "Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"
   };
   public static final String[] UKRAINIAN_MONAT = new String[]{
      "Січня", "Лютого", "Березня", "Квітня", "Травня", "Червня", "Липня", "Серпня", "Вересня", "Жовтня", "Листопада", "Грудня"
   };
   public static final String[] AZERBAIJAN_MONAT = new String[]{
      "Yanvar", "Fevral", "Mart", "Aprel", "May", "Iyun", "Iyul", "Avqust", "Sentyabr", "Oktyabr", "Noyabr", "Dekabr"
   };
   public static final String[] ARMENIAN_MONAT = new String[]{
      "Հունվարի", "Փետրվարի", "Մարտի", "Ապրիլի", "Մայիսի", "Հունիսի", "Հուլիսի", "Օգոստոսի", "Սեպտեմբերի", "Հոկտեմբերի", "Նոեմբերի", "Դեկտեմբերի"
   };
   public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
   public static final String DATE_FORMAT_ONLY = "dd.MM.yyyy";
   public static final DateFormat FORMAT_HH_MM = new SimpleDateFormat("HH:mm");
   public static final DateFormat FORMAT_HH_MM_SS = new SimpleDateFormat("HH:mm:ss");
   public static final DateFormat FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd.MM.yyyy");
   public static final DateFormat FORMAT_DD_MM_YYYY_TIME = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
   public static final DateFormat FORMAT_FOR_REP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   public static final DateFormat FORMAT_FOR_TRANS = new SimpleDateFormat("yyyy-MM-dd HH:mm");
   public static final String TEMP_FOLDER = "temp";
   public static final String CONFIG_FOLDER = "config";
   public static final String TEMP_STATE_FILE = "temp.json";
   public static final String TEMP_UPDLOG_FILE = "updlog.json";
   public static final String TEMP_COMPLEX_FILE = "complex.json";
   public static final String TEMP_STATATISTIC_FILE = "temp_statistic.xml";
   public static final int DELAY_BACK_TO_ROOT = 10000;
   public static final int DELAY_CHECK_TO_LOCK = 55000;
   public static final int LOCK_INT = 1000000000;
   public static final int LOCK_FREE_INT = 1000000011;
   public static final int LOCK_PER_DAY_INT = 1000000022;
   public static final String HOW_DO_YOU_DO = "do you live?";
   private static final HashMap<String, Image> HASH_IMG = new HashMap<>();
   public static final HashMap<Integer, Rectangle> DISPLAYS = new HashMap<>();
   public static GraphicsDevice firstMonitor = null;
   private static boolean sh;

   public static LinkedHashMap<Integer, String> get_PRIORITYS_WORD() {
      PRIORITYS_WORD.put(Integer.valueOf(0), "client.priority.low");
      PRIORITYS_WORD.put(Integer.valueOf(1), "client.priority.standard");
      PRIORITYS_WORD.put(Integer.valueOf(2), "client.priority.hi");
      PRIORITYS_WORD.put(Integer.valueOf(3), "client.priority.vip");
      return PRIORITYS_WORD;
   }

   public static LinkedHashMap<Integer, String> get_COEFF_WORD() {
      COEFF_WORD.put(Integer.valueOf(0), "service.priority.low");
      COEFF_WORD.put(Integer.valueOf(1), "service.priority.basic");
      COEFF_WORD.put(Integer.valueOf(2), "service.priority.vip");
      return COEFF_WORD;
   }

   private static void getList(ArrayList list, Element el, String tagName) {
      list.addAll(el.elements(tagName));

      for(Object obj : el.elements()) {
         getList(list, (Element)obj, tagName);
      }
   }

   public static ArrayList<Element> elements(Element root, String tagName) {
      ArrayList<Element> list = new ArrayList();
      getList(list, root, tagName);
      return list;
   }

   private static void getList(ArrayList list, Element el, String attrName, String attrValue) {
      if (attrValue.equals(el.attributeValue(attrName))) {
         list.add(el);
      }

      for(Object obj : el.elements()) {
         getList(list, (Element)obj, attrName, attrValue);
      }
   }

   public static ArrayList<Element> elementsByAttr(Element root, String attrName, String attrValue) {
      ArrayList<Element> list = new ArrayList();
      getList(list, root, attrName, attrValue);
      return list;
   }

   private static void getListCData(ArrayList list, Element el, String text) {
      if (text.equals(el.getTextTrim())) {
         list.add(el);
      }

      for(Object obj : el.elements()) {
         getListCData(list, (Element)obj, text);
      }
   }

   public static ArrayList<Element> elementsByCData(Element root, String text) {
      ArrayList<Element> list = new ArrayList();
      getListCData(list, root, text);
      return list;
   }

   public static InetAddress getInetAddress(String adress) {
      InetAddress adr = null;

      try {
         return InetAddress.getByName(adress);
      } catch (UnknownHostException var3) {
         throw new ServerException("Ошибка получения адреса по строке '" + adress + "\". " + var3);
      }
   }

   public static void sendUDPMessage(String message, InetAddress address, int port) {
      QLog.l().logger().trace("Отправка UDP сообшение \"" + message + "\" по адресу \"" + address.getHostAddress() + "\" на порт \"" + port + "\"");
      byte[] mess_b = message.getBytes();
      DatagramPacket packet = new DatagramPacket(mess_b, mess_b.length, address, port);

      DatagramSocket socket;
      try {
         socket = new DatagramSocket();
      } catch (SocketException var13) {
         throw new ServerException("Проблемы с сокетом UDP." + var13.getMessage());
      }

      try {
         socket.send(packet);
      } catch (IOException var11) {
         throw new ServerException("Ошибка отправки сообщения по UDP. " + var11.getMessage());
      } finally {
         socket.close();
      }
   }

   public static void sendUDPBroadcast(String message, int port) {
      try {
         sendUDPMessage(message, InetAddress.getByName("255.255.255.255"), port);
      } catch (UnknownHostException var3) {
         throw new ServerException("Проблемы с адресом " + var3.getMessage());
      }
   }

   public static byte[] readResource(Object o, String resourceName) throws IOException {
      InputStream inStream = o.getClass().getResourceAsStream(resourceName);
      return readInputStream(inStream);
   }

   public static Image loadImage(Object o, String resourceName, String defaultResourceName) {
      if ("".equals(resourceName)) {
         return null;
      } else {
         Image img = HASH_IMG.get(resourceName);
         if (img != null) {
            return img;
         } else {
            File f = new File(resourceName);
            if (f.exists()) {
               img = new ImageIcon(resourceName).getImage();
               HASH_IMG.put(resourceName, img);
               return img;
            } else {
               InputStream is = o.getClass().getResourceAsStream(resourceName);
               DataInputStream inStream;
               if (is == null) {
                  if (defaultResourceName == null || defaultResourceName.isEmpty()) {
                     return new BufferedImage(2000, 2000, 1);
                  }

                  if (o.getClass().getResourceAsStream(defaultResourceName) == null) {
                     QLog.l().logger().error("При загрузки ресурса не нашлось ни файла, ни ресурса, НИ ДЕФОЛТНОГО РЕСУРСА \"" + defaultResourceName + "\"");
                     return new BufferedImage(2000, 2000, 1);
                  }

                  inStream = new DataInputStream(o.getClass().getResourceAsStream(defaultResourceName));
               } else {
                  inStream = new DataInputStream(is);
               }

               byte[] b = null;

               try {
                  b = new byte[inStream.available()];
                  inStream.readFully(b);
                  inStream.close();
               } catch (IOException var8) {
                  QLog.l().logger().error("", var8);
               }

               img = new ImageIcon(b).getImage();
               HASH_IMG.put(resourceName, img);
               return img;
            }
         }
      }
   }

   public static byte[] readInputStream(InputStream stream) throws IOException {
      DataInputStream dis = new DataInputStream(stream);
      byte[] result = new byte[stream.available()];
      dis.readFully(result);
      return result;
   }

   public static double roundAs(double value, int scale) {
      return new BigDecimal(value).setScale(scale, RoundingMode.UP).doubleValue();
   }

   public static String getFileName(Component parent, String title, String description, String extension) {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setLocale(Locales.getInstance().getLangCurrent());
      fileChooser.resetChoosableFileFilters();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extension);
      fileChooser.setFileFilter(filter);
      fileChooser.setFileSelectionMode(0);
      fileChooser.setDialogTitle(title);
      return fileChooser.showOpenDialog(parent) == 0 && fileChooser.getSelectedFile().exists() ? fileChooser.getSelectedFile().getAbsolutePath() : null;
   }

   public static void setLocation(JDialog component) {
      component.setLocationRelativeTo(null);
   }

   public static void setLocation(JFrame component) {
      component.setLocationRelativeTo(null);
   }

   public static void setFullSize(Component component) {
      component.setBounds(0, 0, firstMonitor.getDefaultConfiguration().getBounds().width, firstMonitor.getDefaultConfiguration().getBounds().height);
   }

   public static void loadPlugins(String folder) {
      QLog.l().logger().info("Загрузка плагинов из папки plugins.");
      File[] list = new File(folder).listFiles(new FilenameFilter() {
         @Override
         public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".jar");
         }
      });
      if (list != null && list.length != 0) {
         URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
         Class sysclass = URLClassLoader.class;
         Class[] parameters = new Class[]{URL.class};

         for(File file : list) {
            QLog.l().logger().debug("Плагин " + file.getName());

            try {
               Method method = sysclass.getDeclaredMethod("addURL", parameters);
               method.setAccessible(true);
               method.invoke(sysloader, file.toURI().toURL());
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | MalformedURLException | NoSuchMethodException var10) {
               QLog.l().logger().error("Плагин " + file.getName() + " НЕ загружен. " + var10);
            }
         }
      }
   }

   public static void startSplash() {
      sh = true;
      SwingUtilities.invokeLater(new Uses.SplashRun());
   }

   public static void showSplash() {
      sh = true;
      SwingUtilities.invokeLater(new Uses.SplashRun());
   }

   public static void closeSplash() {
      sh = false;
   }

   static void renderSplashFrame(Graphics2D g, int frame) {
      String[] comps = new String[]{"foo", "bar", "baz"};
      g.setComposite(AlphaComposite.Clear);
      g.fillRect(120, 140, 200, 40);
      g.setPaintMode();
      g.setColor(Color.BLACK);
      g.drawString("Loading " + comps[frame / 5 % 3] + "...", 120, 150);
   }

   public static String prepareAbsolutPathForImg(String html) {
      File f = new File(html.startsWith("file:///") ? html.substring(8) : html);
      if (f.exists()) {
         return f.toURI().toString().replace("file:/", "file:///");
      } else {
         Pattern pattern = Pattern.compile("<\\s*img\\s*src\\s*=\\s*['\"].*?['\"]\\s*>");
         Matcher matcher = pattern.matcher(html);
         String res = html;

         while(matcher.find()) {
            String img = matcher.group();
            String tci = img.contains("'") ? "'" : "\"";
            img = img.substring(img.indexOf(tci) + 1, img.indexOf(tci, img.indexOf(tci) + 1));
            String ff = img;
            if (img.startsWith("file:///")) {
               ff = img.substring(8);
            }

            f = new File(ff);
            if (f.isFile()) {
               res = res.replace(tci + img + tci, tci + f.toURI().toString().replace("file:/", "file:///") + tci);
            } else {
               QLog.l().logger().error("Не найден файл \"" + img + "\" для HTML.");
            }
         }

         return res;
      }
   }

   public static boolean isInt(String str) {
      return str.matches("^-?\\d+$");
   }

   public static boolean isFloat(String str) {
      return str.matches("^-?\\d+\\.{0,1}\\d+$");
   }

   public static void downloadFile(String url, String destinationFilePath) throws MalformedURLException, IOException {
      URL website = new URL(url);
      ReadableByteChannel rbc = Channels.newChannel(website.openStream());
      FileOutputStream fos = new FileOutputStream(destinationFilePath);
      fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
   }

   public static Pair<Long, Long> aboutFile(String fileUrl) throws MalformedURLException, IOException {
      URL url = new URL(fileUrl);
      URLConnection urlConnection = url.openConnection();
      return new Pair(urlConnection.getLastModified(), urlConnection.getContentLengthLong());
   }

   public static void main(String[] args) {
      final QConfig config = new QConfig() {
         @Override
         public int getHttpProtocol() {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public Boolean getHttpRequestType() {
            return null;
         }

         @Override
         public String getPointN() {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public String getServerAddress() {
            return "127.0.0.1";
         }

         @Override
         public InetAddress getInetServerAddress() {
            try {
               return InetAddress.getByName("localhost");
            } catch (UnknownHostException var2) {
               throw new RuntimeException(var2);
            }
         }

         @Override
         public int getServerPort() {
            return 3128;
         }

         @Override
         boolean isAndroid() {
            return false;
         }

         @Override
         public QConfig.QProxy getProxy() {
            return null;
         }

         @Override
         public String getNumDivider(String prefix) {
            return prefix != null && !prefix.isEmpty() ? "-" : "";
         }
      };
      QConfig.setCfg(config);
      RpcGetAllServices.ServicesForWelcome sw = NetCommander.getServiсes(new INetProperty() {
         @Override
         public Integer getPort() {
            return config.getServerPort();
         }

         @Override
         public InetAddress getAddress() {
            return config.getInetServerAddress();
         }
      });
      System.out.println(sw.getRoot().getChildren());
   }

   static {
      GraphicsDevice[] screenDevices = null;

      try {
         screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
      } catch (HeadlessException var6) {
         System.out.println("No screen Devices");
      }

      if (screenDevices != null && screenDevices.length > 0) {
         firstMonitor = screenDevices[0];
         int i = 1;

         for(GraphicsDevice graphicsDevice : screenDevices) {
            System.out
               .println(
                  "monitor "
                     + i
                     + "; graphicsDevice = "
                     + graphicsDevice.getIDstring()
                     + " "
                     + graphicsDevice.toString()
                     + "; height, width = "
                     + graphicsDevice.getDefaultConfiguration().getBounds().height
                     + "x"
                     + graphicsDevice.getDefaultConfiguration().getBounds().width
                     + "; Coloreness = "
                     + graphicsDevice.getDisplayMode().getBitDepth()
                     + "; RefreshRate = "
                     + graphicsDevice.getDisplayMode().getRefreshRate()
                     + "; Origin(x, y) = "
                     + graphicsDevice.getDefaultConfiguration().getBounds().x
                     + "-"
                     + graphicsDevice.getDefaultConfiguration().getBounds().y
               );
            DISPLAYS.put(i++, graphicsDevice.getDefaultConfiguration().getBounds());
            if (graphicsDevice.getDefaultConfiguration().getBounds().x == 0 && graphicsDevice.getDefaultConfiguration().getBounds().y == 0) {
               firstMonitor = graphicsDevice;
            }
         }
      }

      COEFF_WORD.put(Integer.valueOf(0), "service.priority.low");
      COEFF_WORD.put(Integer.valueOf(1), "service.priority.basic");
      COEFF_WORD.put(Integer.valueOf(2), "service.priority.vip");
      sh = false;
   }

   private static class SplashRun implements Runnable {
      private SplashRun() {
      }

      @Override
      public void run() {
         Uses.SplashScreen screen = new Uses.SplashScreen();
         screen.setUndecorated(true);
         screen.setResizable(false);
         Uses.setLocation(screen);
         screen.pack();
         screen.setVisible(true);
         screen.setAlwaysOnTop(true);
      }
   }

   private static class SplashScreen extends JFrame {
      final BorderLayout borderLayout1 = new BorderLayout();
      final JLabel imageLabel = new JLabel();
      final JLabel imageLabel2 = new JLabel();
      final JLayeredPane lp = new JDesktopPane();
      final ImageIcon imageIcon;
      final ImageIcon imageIcon2;
      final Timer timer = new Timer(100, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (!Uses.sh) {
               this.stopTimer();
               SplashScreen.this.setVisible(false);
            }
         }

         private void stopTimer() {
            SplashScreen.this.timer.stop();
         }
      });

      public SplashScreen() {
         this.imageIcon = new ImageIcon(Uses.SplashScreen.class.getResource("/ru/apertum/qsystem/client/forms/resources/fon_login_bl.jpg"));
         this.imageIcon2 = new ImageIcon(Uses.SplashScreen.class.getResource("/ru/apertum/qsystem/client/forms/resources/loading.gif"));
         this.init();
      }

      private void init() {
         try {
            this.setIconImage(ImageIO.read(Uses.SplashScreen.class.getResource("/ru/apertum/qsystem/client/forms/resources/client.png")));
         } catch (IOException var2) {
            System.err.println(var2);
         }

         this.setTitle("Запуск QSystem");
         this.setSize(this.imageIcon.getIconWidth(), this.imageIcon.getIconHeight());
         this.imageLabel.setIcon(this.imageIcon);
         this.imageLabel2.setIcon(this.imageIcon2);
         this.lp.setBounds(0, 0, 400, 400);
         this.lp.setOpaque(false);
         this.add(this.lp);
         this.getContentPane().add(this.imageLabel, "Center");
         this.imageLabel2.setBounds(175, 165, 300, 30);
         this.lp.add(this.imageLabel2, null);
         this.timer.start();
      }
   }
}
