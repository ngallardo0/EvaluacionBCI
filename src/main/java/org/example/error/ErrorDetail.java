package org.example.error;


import java.util.Date;

public class ErrorDetail {
  private Date timestamp;
  private int code;
  private String detail;

  public ErrorDetail(Date timestamp, int code, String detail) {
    this.timestamp = timestamp;
    this.code = code;
    this.detail = detail;
  }


}