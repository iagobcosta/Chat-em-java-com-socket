/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.bean;

import java.io.Serializable;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author iago
 */
public class ChatMessage implements Serializable{
    
    private String ip;
    private int porta;
    private String nome;
    private String text;
    private String nameReserved;
    private String data;
    private String hora;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNameReserved() {
        return nameReserved;
    }

    public void setNameReserved(String nameReserved) {
        this.nameReserved = nameReserved;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Set<String> getSetOnlines() {
        return setOnlines;
    }

    public void setSetOnlines(Set<String> setOnlines) {
        this.setOnlines = setOnlines;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    
    public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	private Set<String> setOnlines = new HashSet<String>();
    
    private Action action;
    
    public enum Action {
        CONNECT,DISCONNECT, SEND_USER, SEND_ALL, USERS_ONLINE, RENAME
    }
    
    
    
}
