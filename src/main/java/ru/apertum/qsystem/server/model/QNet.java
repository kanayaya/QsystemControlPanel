package ru.apertum.qsystem.server.model;

import java.io.Serializable;
import java.util.Date;

public class QNet implements Serializable {
   private Long id;
   private Integer serverPort;
   private Integer webServerPort;
   private Integer clientPort;
   private Date startTime;
   private Date finishTime;
   private String version = "Не присвоена";
   private Integer lastNumber;
   private Integer extPriorNumber;
   private Integer firstNumber;
   private Boolean numering;
   private Integer point;
   private Integer sound;
   private Integer voice = 0;
   private Integer blackTime = 0;
   private Long branchOfficeId;
   private String skyServerUrl;
   private String zoneBoardServAddr;
   private String[] zbsal = null;
   private Integer zoneBoardServPort;
   private Integer limitRecall;
   private Boolean buttonFreeDesign;

   public void setId(Long id) {
      this.id = id;
   }

   public Long getId() {
      return this.id;
   }

   public void setServerPort(Integer serverPort) {
      this.serverPort = serverPort;
   }

   public Integer getServerPort() {
      return this.serverPort;
   }

   public void setWebServerPort(Integer webServerPort) {
      this.webServerPort = webServerPort;
   }

   public Integer getWebServerPort() {
      return this.webServerPort;
   }

   public void setClientPort(Integer clientPort) {
      this.clientPort = clientPort;
   }

   public Integer getClientPort() {
      return this.clientPort;
   }

   public void setStartTime(Date startTime) {
      this.startTime = startTime;
   }

   public Date getStartTime() {
      return this.startTime;
   }

   public void setFinishTime(Date finishTime) {
      this.finishTime = finishTime;
   }

   public Date getFinishTime() {
      return this.finishTime;
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public Integer getLastNumber() {
      return this.lastNumber;
   }

   public void setLastNumber(Integer lastNumber) {
      this.lastNumber = lastNumber;
   }

   public Integer getExtPriorNumber() {
      return this.extPriorNumber;
   }

   public void setExtPriorNumber(Integer extPriorNumber) {
      this.extPriorNumber = extPriorNumber;
   }

   public Integer getFirstNumber() {
      return this.firstNumber;
   }

   public void setFirstNumber(Integer firstNumber) {
      this.firstNumber = firstNumber;
   }

   public Boolean getNumering() {
      return this.numering;
   }

   public void setNumering(Boolean numering) {
      this.numering = numering;
   }

   public Integer getPoint() {
      return this.point;
   }

   public void setPoint(Integer point) {
      this.point = point;
   }

   public Integer getSound() {
      return this.sound;
   }

   public void setSound(Integer sound) {
      this.sound = sound;
   }

   public Integer getVoice() {
      return this.voice;
   }

   public void setVoice(Integer voice) {
      this.voice = voice;
   }

   public Integer getBlackTime() {
      return this.blackTime;
   }

   public void setBlackTime(Integer blackTime) {
      this.blackTime = blackTime;
   }

   public Long getBranchOfficeId() {
      return this.branchOfficeId;
   }

   public void setBranchOfficeId(Long branchOfficeId) {
      this.branchOfficeId = branchOfficeId;
   }

   public String getSkyServerUrl() {
      return this.skyServerUrl;
   }

   public void setSkyServerUrl(String skyServerUrl) {
      this.skyServerUrl = skyServerUrl;
   }

   public String getZoneBoardServAddr() {
      return this.zoneBoardServAddr;
   }

   public String[] getZoneBoardServAddrList() {
      if (this.zbsal == null || this.zbsal.length == 0) {
         String l = this.getZoneBoardServAddr();
         l = l.replaceAll("  ", " ");
         this.zbsal = l.split(", |; |,|;| ");
      }

      return this.zbsal;
   }

   public void setZoneBoardServAddr(String zoneBoardServAddr) {
      this.zoneBoardServAddr = zoneBoardServAddr;
   }

   public Integer getZoneBoardServPort() {
      return this.zoneBoardServPort;
   }

   public void setZoneBoardServPort(Integer zoneBoardServPort) {
      this.zoneBoardServPort = zoneBoardServPort;
   }

   public Integer getLimitRecall() {
      return this.limitRecall;
   }

   public void setLimitRecall(Integer limitRecall) {
      this.limitRecall = limitRecall;
   }

   public Boolean getButtonFreeDesign() {
      return this.buttonFreeDesign;
   }

   public void setButtonFreeDesign(Boolean buttonFreeDesign) {
      this.buttonFreeDesign = buttonFreeDesign;
   }
}
