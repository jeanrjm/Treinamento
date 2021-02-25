/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Jean
 */
public class EspacoCafe extends Espaco {

    public EspacoCafe(int id, String nome, int lotacao) {
        super(id, nome, lotacao, "cafe");
    }
    
     public EspacoCafe(int id) {
        super(id, "cafe");
    }

  
  
    
}
