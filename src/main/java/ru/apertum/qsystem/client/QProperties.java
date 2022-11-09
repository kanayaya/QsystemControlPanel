package ru.apertum.qsystem.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import ru.apertum.qsystem.common.NetCommander;
import ru.apertum.qsystem.common.model.INetProperty;
import ru.apertum.qsystem.server.ServerProps;
import ru.apertum.qsystem.server.model.QProperty;

public class QProperties {
   public static final String SECTION_SERVER = "SERVER";
   public static final String SECTION_WELCOME = "WELCOME";
   public static final String SECTION_CLIENT = "OPERATOR";
   private LinkedHashMap<String, ServerProps.Section> properties = new LinkedHashMap<>();
   private INetProperty netProperty = null;

   private QProperties() {
   }

   public static QProperties get() {
      return QProperties.QPropertiesHolder.INSTANCE;
   }

   public void load(INetProperty netProperty) {
      this.load(netProperty, false);
   }

   public void load(INetProperty netProperty, boolean force) {
      if (force || this.properties == null || this.properties.isEmpty()) {
         this.properties = NetCommander.getProperties(netProperty);
         this.netProperty = netProperty;
      }
   }

   public void reload() {
      if (this.netProperty != null) {
         this.properties = NetCommander.getProperties(this.netProperty);
      }
   }

   public LinkedHashMap<String, QProperty> getSectionProps(String section) {
      return this.properties.get(section).getProperties();
   }

   public ServerProps.Section getSection(String section) {
      return this.properties.get(section);
   }

   public ArrayList<QProperty> getAllProperties() {
      ArrayList<QProperty> col = new ArrayList<>();

      for(ServerProps.Section sec : this.properties.values()) {
         col.addAll(sec.getProperties().values());
      }

      return col;
   }

   public Collection<ServerProps.Section> getSections() {
      return this.properties.values();
   }

   public QProperty getProperty(String section, String key) {
      if (key == null) {
         throw new IllegalArgumentException("Key must be not NULL");
      } else {
         ServerProps.Section secmap = this.getSection(section);
         if (secmap == null) {
            return null;
         } else {
            return !secmap.getProperties().containsKey(key) ? null : secmap.getProperties().get(key);
         }
      }
   }

   public String getProperty(String section, String key, String defValue) {
      if (key == null) {
         return defValue;
      } else {
         ServerProps.Section secmap = this.getSection(section);
         if (secmap == null) {
            return defValue;
         } else {
            return !secmap.getProperties().containsKey(key) ? defValue : secmap.getProperties().get(key).getValue();
         }
      }
   }

   public void init(INetProperty netProperty, List<QProperty> propList) {
      this.properties = NetCommander.initProperties(netProperty, propList);
   }

   public void save(INetProperty netProperty, List<QProperty> propList) {
      this.properties = NetCommander.saveProperties(netProperty, propList);
   }

   private static class QPropertiesHolder {
      private static final QProperties INSTANCE = new QProperties();
   }
}
