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
    public String getSobrenome() {
        return this.sobrenome;
    }
    public String getNome() {
        return this.nome;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Ensalamento getEnsalamentoComNomes() {
        try {
            conexao = new Conexao().criarConexao();
            sql = "SELECT p.nome, p.sobrenome, e.salaUm, e.salaDois, e.cafeUm, e.cafeDois FROM pessoas p INNER JOIN ensalamento e ON p.id = e.pessoa WHERE p.id = ?;";
            //sql="SELECT p.nome, p.sobrenome, i.nome as salum, j.nome as sadois, x.nome as cafum, y.nome as cafdois FROM pessoas p INNER JOIN ensalamento e ON p.id = e.pessoa INNER JOIN salas i ON e.salaUm = i.id INNER JOIN salas j ON e.salaDois = j.id INNER JOIN cafe x ON e.cafeUm = x.id INNER JOIN cafe y ON e.cafeDois = y.id WHERE p.id = ?";
            stm = conexao.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet res = stm.executeQuery();

            res.next();
            String nome = res.getString(1);
            String sobrenome = res.getString(2);
            int salaUm = res.getInt(3);
            int salaDois = res.getInt(4);
            int cafeUm = res.getInt(5);
            int cafeDois = res.getInt(6);
            
            String salUm;
            String salDois;
            String cafUm;
            String cafDois;

            if (salaUm == 0) {
                salUm = "SEM ALOCAÇÃO";
            } else {
                sql = "SELECT nome FROM salas WHERE id = ?;";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, salaUm);
                res = stm.executeQuery(); 
                res.next();
                salUm = res.getString(1);
            }
            if (salaDois == 0) {
                salDois = "SEM ALOCAÇÃO";
            } else {
                sql = "SELECT nome FROM salas WHERE id = ?;";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, salaDois);
                res = stm.executeQuery(); 
                res.next();
                salDois = res.getString(1);
            }
            
            if (cafeUm == 0) {
                cafUm = "SEM ALOCAÇÃO";
            } else {
                sql = "SELECT nome FROM cafe WHERE id = ?;";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, cafeUm);
                res = stm.executeQuery(); 
                res.next();
                cafUm = res.getString(1);
            }
            if (cafeDois == 0) {
                cafDois = "SEM ALOCAÇÃO";
            } else {
                sql = "SELECT nome FROM cafe WHERE id = ?;";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, cafeDois);
                res = stm.executeQuery(); 
                res.next();
                cafDois = res.getString(1);
            }
            Ensalamento ensalamento = new Ensalamento(nome, sobrenome, salUm, salDois, cafUm, cafDois);
            return ensalamento;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
   
    public Pessoa(int id, String nome, String sobrenome, String nomecompleto) throws Exception {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nomecompleto = nomecompleto;
    }

    public Pessoa(int id, String nomecompleto) {
        this.id = id;
         this.nomecompleto = nomecompleto;
    }
    
    public Pessoa(int id) {
        this.id = id;
    }

    
}
