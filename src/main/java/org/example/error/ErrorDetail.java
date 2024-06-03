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

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}