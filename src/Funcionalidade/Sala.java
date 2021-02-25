/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public class Sala extends Espaco {

    public Sala(int id, String nome, int lotacao) {
        super(id, nome, lotacao, "sala");
       
    }

    public Sala(int id) {
        super(id, "sala");
    }
}
