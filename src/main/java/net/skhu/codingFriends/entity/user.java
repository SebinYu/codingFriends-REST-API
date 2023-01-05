package net.skhu.codingFriends.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int user_id;

    String userid;
    String password;
    String name;
    String email;
    boolean enabled;
    String userType;
    boolean admin;
    String address;
    String address_detail;
}
