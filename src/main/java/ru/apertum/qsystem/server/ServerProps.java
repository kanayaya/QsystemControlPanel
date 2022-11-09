package ru.apertum.qsystem.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import ru.apertum.qsystem.server.controller.IServerListener;
import ru.apertum.qsystem.server.controller.ServerEvents;
import ru.apertum.qsystem.server.model.QNet;
import ru.apertum.qsystem.server.model.QProperty;
import ru.apertum.qsystem.server.model.QStandards;

public class ServerProps {
   private final QNet netProp = new QNet();
   private final QStandards standards = new QStandards();
   private final LinkedHashMap<String, ServerProps.Section> properties = new LinkedHashMap<>();

   public QNet getProps() {
      return this.netProp;
   }

   public QStandards getStandards() {
      return this.standards;
   }

   public LinkedHashMap<String, ServerProps.Section> getDBproperties() {
      return this.properties;
   }

   public LinkedHashMap<String, ServerProps.Section> getDBproperties(boolean needReload) {
      if (needReload) {
         this.reloadProperties();
      }

      return this.getDBproperties();
   }

   private void applyLocalProperties(String filePath) {
   }

   private ServerProps() {
      this.load();
      this.applyLocalProperties("config/properties.xml");
      ServerEvents.getInstance().registerListener(new IServerListener() {
         @Override
         public void restartEvent() {
            ServerProps.this.load();
         }
      });
   }

   private void load() {
   }

   private void loadProperties() {
   }

   public static ServerProps getInstance() {
      return ServerProps.ServerPropsHolder.INSTANCE;
   }

   public LinkedHashMap<String, QProperty> getSectionProps(String section) {
      return this.properties.get(section).properties;
   }

   public ServerProps.Section getSection(String section) {
      return this.properties.get(section);
   }

   public ServerProps.Section addSection(String section) {
      if (this.getSection(section) == null) {
         this.properties.put(section, new ServerProps.Section(section, new LinkedHashMap<>()));
      }

      return this.getSection(section);
   }

   public ArrayList<QProperty> getAllProperties() {
      ArrayList<QProperty> col = new ArrayList<>();

      for(ServerProps.Section sec : this.properties.values()) {
         col.addAll(sec.properties.values());
      }

      return col;
   }

   public Collection<ServerProps.Section> getSections() {
      return this.properties.values();
   }

   public QProperty getProperty(String section, String key) {
      if (key == null) {
         return null;
      } else {
         ServerProps.Section secmap = this.getSection(section);
         if (secmap == null) {
            return null;
         } else {
            return !secmap.properties.containsKey(key) ? null : secmap.properties.get(key);
         }
      }
   }

   public String getProperty(String section, String key, String deafultValue) {
      QProperty p = this.getProperty(section, key);
      return p == null ? deafultValue : p.getValue();
   }

   public void reloadProperties() {
      this.properties.clear();
      this.loadProperties();
   }

   public static final class Section {
      @Expose
      @SerializedName("name")
      private final String name;
      @Expose
      @SerializedName("properties")
      private final LinkedHashMap<String, QProperty> properties;

      public Section(String name, LinkedHashMap<String, QProperty> properties) {
         this.name = name;
         this.properties = properties;
      }

      public String getName() {
         return this.name;
      }

      public LinkedHashMap<String, QProperty> getProperties() {
         return this.properties;
      }

      public QProperty getProperty(String key) {
         return this.properties.get(key);
      }

      public QProperty addProperty(String key, String value, String comment) {
         QProperty res = new QProperty(this.name, key, value, comment);
         this.properties.put(key, res);
         return res;
      }

      public void addProperty(QProperty prop) {
         if ((prop.getSection() != null || this.name != null) && !this.name.equals(prop.getSection())) {
            throw new IllegalArgumentException("Property " + prop + " already has the section but not from \"" + this.name + "\"");
         } else if (this.properties.containsKey(prop.getKey())) {
            throw new IllegalArgumentException("Key " + prop.getKey() + " already exists in this section \"" + this.name + "\"");
         } else {
            this.properties.put(prop.getKey(), prop);
         }
      }

      public void removeProperty(QProperty prop) {
         if ((prop.getSection() != null || this.name != null) && !this.name.equals(prop.getSection())) {
            throw new IllegalArgumentException("Property " + prop + " is not from this section \"" + this.name + "\"");
         } else {
            this.properties.remove(prop.getKey());
         }
      }

      public void removeProperty(String key) {
         if (this.properties.containsKey(key)) {
            this.properties.remove(key);
         }
      }

      @Override
      public String toString() {
         return this.name;
      }
   }

   private static class ServerPropsHolder {
      private static final ServerProps INSTANCE = new ServerProps();
   }
}
