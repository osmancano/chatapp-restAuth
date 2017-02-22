package com.ironyard.data;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import io.swagger.models.auth.In;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wailm.yousif on 2/21/17.
 */

@Entity
public class ChatToken
{
    @Id
    @GeneratedValue
    private long id;

    private String token;
    private Date createdDateTime;
    private Integer numOfCalls;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId=true)
    private ChatUser chatUser;

    public ChatToken() { }

    public ChatToken(String token, Date dateTime, Integer numOfCalls, ChatUser chatUser) {
        this.token = token;
        this.createdDateTime = dateTime;
        this.numOfCalls = numOfCalls;
        this.chatUser = chatUser;
    }

    public String getTokenString() {
        return token;
    }

    public void setTokenString(String token) {
        this.token = token;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Integer getNumOfCalls() {
        return numOfCalls;
    }

    public void setNumOfCalls(Integer numOfCalls) {
        this.numOfCalls = numOfCalls;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChatUser getChatUser() {
        return chatUser;
    }

    public void setChatUser(ChatUser chatUser) {
        this.chatUser = chatUser;
    }
}