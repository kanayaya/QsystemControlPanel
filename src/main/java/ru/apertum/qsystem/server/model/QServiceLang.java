package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class QServiceLang implements Serializable, IidGetter {
   @Expose
   @SerializedName("id")
   private Long id;
   @Expose
   @SerializedName("input_caption")
   private String input_caption = "";
   @Expose
   @SerializedName("pre_info_html")
   private String preInfoHtml = "";
   @Expose
   @SerializedName("pre_info_print_text")
   private String preInfoPrintText = "";
   @Expose
   @SerializedName("ticket_text")
   private String ticketText = "";
   @Expose
   @SerializedName("description")
   private String description;
   @Expose
   @SerializedName("lang")
   private String lang;
   @Expose
   @SerializedName("name")
   private String name;
   @Expose
   @SerializedName("buttonText")
   private String buttonText;
   private QService service;

   @Override
   public Long getId() {
      return this.id;
   }

   public final void setId(Long id) {
      this.id = id;
   }

   public String getInput_caption() {
      return this.input_caption;
   }

   public void setInput_caption(String input_caption) {
      this.input_caption = input_caption;
   }

   public String getPreInfoHtml() {
      return this.preInfoHtml;
   }

   public void setPreInfoHtml(String preInfoHtml) {
      this.preInfoHtml = preInfoHtml;
   }

   public String getPreInfoPrintText() {
      return this.preInfoPrintText;
   }

   public void setPreInfoPrintText(String preInfoPrintText) {
      this.preInfoPrintText = preInfoPrintText;
   }

   public String getTicketText() {
      return this.ticketText;
   }

   public void setTicketText(String ticketText) {
      this.ticketText = ticketText;
   }

   @Override
   public String toString() {
      return this.getLang() + " " + this.getName();
   }

   public final void setDescription(String description) {
      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }

   public String getLang() {
      return this.lang;
   }

   public void setLang(String lang) {
      this.lang = lang;
   }

   public final void setName(String name) {
      this.name = name;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public String getButtonText() {
      return this.buttonText;
   }

   public final void setButtonText(String buttonText) {
      this.buttonText = buttonText;
   }

   public QService getService() {
      return this.service;
   }

   public void setService(QService service) {
      this.service = service;
   }
}
