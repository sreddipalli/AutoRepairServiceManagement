package com.autorepair.service.result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Response<T> {
  T data;
  List<ErrorData> error;
  String timestamp;

  public Response(T data, List<ErrorData> error) {
    this.data = data;
    this.error = error;
    DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    Calendar calObj = Calendar.getInstance();
    this.timestamp = df.format(calObj.getTime());
  }

  public static <T> Response<T> success(T data) {
    return new Response<>(data, Arrays.asList(new ErrorData("0", null, null)));
  }

  public static <T> Response<T> failure(String code, String errorMessage, String errorDetails) {
    return new Response<>(null, Arrays.asList(new ErrorData(code, errorMessage, errorDetails)));
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public List<ErrorData> getError() {
    return error;
  }

  public void setError(List<ErrorData> error) {
    this.error = error;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

}
