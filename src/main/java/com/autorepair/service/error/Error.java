package com.autorepair.service.error;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Error {

  private int code;
  private String description;
  private String timestamp;

  public Error() {

    DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    Calendar calObj = Calendar.getInstance();
    this.timestamp = df.format(calObj.getTime());
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

}
