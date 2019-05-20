/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.app.bean.ChatMessage;

/**
 *
 * @author iago
 */
public class ClienteService {
	private Socket socket;
	private ObjectOutputStream output;
	private String nomeDaMaquina;
	private String ip;
	private int porta = 5555;

	public Socket connect() {
		try {
			socket = new Socket("localhost", porta);
			output = new ObjectOutputStream(socket.getOutputStream());
			setNomeDaMaquina(InetAddress.getLocalHost().getHostName());
			setIp(InetAddress.getLocalHost().getHostAddress());
		} catch (IOException ex) {
			Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
		}
		return socket;
	}

	public void send(ChatMessage message) {
		try {
			output.writeObject(message);
		} catch (IOException ex) {
			Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String getNomeDaMaquina() {
		return nomeDaMaquina;
	}

	public void setNomeDaMaquina(String nomeDaMaquina) {
		this.nomeDaMaquina = nomeDaMaquina;
	}

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
}
