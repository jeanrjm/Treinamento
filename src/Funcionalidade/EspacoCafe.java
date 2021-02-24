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

    public EspacoCafe(int id, int lotacao, int lotacaoum, int lotacaodois) {
        super(id, lotacao, lotacaoum, lotacaodois);
    }
    
     public EspacoCafe(int id) {
        super(id);
    }

  
  
    
}
