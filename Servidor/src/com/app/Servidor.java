/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app;

import com.app.service.ServidorService;

/**
 *
 * @author iago
 */
public class Servidor {

    /**
     * @param args the command line arguments
     **/
    //Método para chamar o contrutor da classe ServidorService e criar um servidor para os clientes que se conectar.
    public static void main(String[] args) {
        new ServidorService();
    }
    
}
