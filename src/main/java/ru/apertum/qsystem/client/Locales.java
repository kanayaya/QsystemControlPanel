package ru.apertum.qsystem.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public final class Locales {
   private final LinkedHashMap<String, Locale> locales = new LinkedHashMap<>();
   private final LinkedHashMap<Locale, String> locales_name = new LinkedHashMap<>();
   private final LinkedHashMap<String, String> lngs = new LinkedHashMap<>();
   private final LinkedHashMap<String, String> lngs_names = new LinkedHashMap<>();
   private final LinkedHashMap<String, String> lngs_buttontext = new LinkedHashMap<>();
   private final LinkedHashMap<String, String> lngs_welcome = new LinkedHashMap<>();
   private final String WELCOME = "welcome";
   private final String LANG_CURRENT = "locale.current";
   private final String WELCOME_LNG = "welcome.multylangs";
   private final String WELCOME_LNG_POS = "welcome.multylangs.position";
   private final String WELCOME_LNG_BTN_FILL = "welcome.multylangs.areafilled";
   private final String WELCOME_LNG_BTN_BORDER = "welcome.multylangs.border";
   private String configFileName = "config/langs.properties";

   private Locales() {
   }

   public static String locMes(String key) {
      return key;
   }

   public static Locales getInstance() {
      return Locales.LocalesHolder.INSTANCE;
   }

   public boolean isWelcomeMultylangs() {
      return false;
   }

   public void setWelcomeMultylangs(boolean multylangs) {
   }

   public boolean isIDE() {
      return false;
   }

   public boolean isWelcomeFirstLaunch() {
      return false;
   }

   public boolean isWelcomeMultylangsButtonsFilled() {
      return false;
   }

   public boolean isWelcomeMultylangsButtonsBorder() {
      return false;
   }

   public int getMultylangsPosition() {
      return 1;
   }

   public Locale getLangCurrent() {
      return Locale.getDefault();
   }

   public void setLangCurrent(String name) {
   }

   public Locale getLocaleByName(String name) {
      return this.locales.get(name) == null ? Locale.getDefault() : this.locales.get(name);
   }

   public String getLangCurrName() {
      return this.lngs_names.get("eng");
   }

   public String getLangButtonText(String lng) {
      return this.lngs_buttontext.get(lng);
   }

   public String getLangWelcome(String lng) {
      return this.lngs_welcome.get(lng);
   }

   public String getNameOfPresentLocale() {
      return this.locales_name.get(Locale.getDefault());
   }

   public void setWelcome(String count) {
   }

   public void setLangWelcome(String name, boolean on) {
      this.lngs_welcome.put(name, on ? "1" : "0");
   }

   public ArrayList<String> getAvailableLocales() {
      return new ArrayList<>(this.lngs.keySet());
   }

   public ArrayList<String> getAvailableLangs() {
      return new ArrayList<>(this.lngs_names.keySet());
   }

   private static class LocalesHolder {
      private static final Locales INSTANCE = new Locales();
   }
}
