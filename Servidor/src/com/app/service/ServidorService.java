/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.app.bean.ChatMessage;
import com.app.bean.ChatMessage.Action;

/**
 *
 * @author iago
 */
public class ServidorService {

    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();

    public ServidorService() {
        try {
            serverSocket = new ServerSocket(5555);

            System.out.println("Servidor online!");

            while (true) {
                socket = serverSocket.accept();

                new Thread(new ListenerSocket(socket)).start();
            }

        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class ListenerSocket implements Runnable {

        private ObjectOutputStream output;
        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());

            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void run() {
            ChatMessage message = null;

            try {
                while ((message = (ChatMessage) input.readObject()) != null) {
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        boolean isConect = connect(message, output);
                        if (isConect) {
                            mapOnlines.put(message.getNome(), output);
                            sendOnlines();
                        }
                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnect(message, output);
                        sendOnlines();
                        return;
                    } else if (action.equals(Action.SEND_USER)) {
                        sendUser(message);
                    } else if (action.equals(Action.SEND_ALL)) {
                        sendAll(message);
                    } else if (action.equals(Action.RENAME)) {

                    }

                }
            } catch (IOException ex) {
                ChatMessage cm = new ChatMessage();
                cm.setNome(message.getNome());
                disconnect(cm, output);
                sendOnlines();
                System.out.println(message.getNome() + " deixou o chat" + "\n");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean connect(ChatMessage message, ObjectOutputStream output) {
        if (mapOnlines.size() == 0) {
            message.setText("YES");
            send(message, output);
            return true;
        }
        if (mapOnlines.containsKey(message.getNome())) {
            message.setText("NO");
            send(message, output);
            return false;
        } else {
            message.setText("YES");
            send(message, output);
            return true;
        }

    }

    private void disconnect(ChatMessage message, ObjectOutputStream output) {
        mapOnlines.remove(message.getNome());

        message.setText("saiu do chat");
        message.setAction(Action.SEND_USER);

        sendAll(message);

        System.out.print(" User " + message.getNome() + " saiu da chat " + "\n");
    }

    private void sendAll(ChatMessage message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(message.getNome())) {
                message.setAction(Action.SEND_USER);
                try {

                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void sendOnlines() {

        Set<String> setNames = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            setNames.add(kv.getKey());
        }

        ChatMessage message = new ChatMessage();
        message.setAction(Action.USERS_ONLINE);
        message.setSetOnlines(setNames);

        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setNome(kv.getKey());

            try {

                kv.getValue().writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void send(ChatMessage message, ObjectOutputStream output) {
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendUser(ChatMessage message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (kv.getKey().equals(message.getNameReserved())) {
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
