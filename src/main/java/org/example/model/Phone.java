package org.example.model;

import org.yaml.snakeyaml.events.Event;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Phone {
  @Id
  private Integer id;
  private long number;
  private int citycode;
  private String countrycode;

  @ManyToOne
  private User user;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = 1; //TODO: VER
  }

  public long getNumber() {
    return number;
  }

  public int getCitycode() {
    return citycode;
  }

  public String getCountrycode() {
    return countrycode;
  }
}
