package org.example.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class User {
  @Id
  private UUID id;
  private String name;
  private String email;
  private String password;
  @CreationTimestamp
  private Date created;
  private Date lastLogin;
  private UUID token;
  private Boolean isActive;

  @OneToMany(mappedBy = "user")
  private List<Phone> phones;

  public User() {
    this.id = UUID.randomUUID();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Phone> getPhones() {
    return phones;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public UUID getToken() {
    return token;
  }

  public void setToken(UUID token) {
    this.token = token;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }
}
