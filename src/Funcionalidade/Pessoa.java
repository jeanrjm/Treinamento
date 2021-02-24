/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public class Pessoa {

    private Connection conexao;
    private PreparedStatement stm;
    private String sql;
    int id;
    String nome;
    String sobrenome;
    String nomecompleto;

    public String getNomeCompleto() {
        return this.nomecompleto;
    }
    
    

    public Pessoa(int id, String nome, String sobrenome, String nomecompleto) throws Exception {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nomecompleto = nomecompleto;
    }

    public Pessoa(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
       }
}
