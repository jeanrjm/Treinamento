/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public class Evento {

    private Connection conexao;
    private PreparedStatement stm;
    private String sql;

    public void cadastrarNovaSala(String nome, String lotacao) throws Exception {
        conexao = new Conexao().criarConexao();
        try {
            sql = "INSERT INTO salas (nome, lotacao) VALUES (? , ?);";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setInt(2, Integer.parseInt(lotacao));
            stm.execute();
            conexao.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conexao.close();
            throw new Exception("Erro: Houve um erro ao cadastrar " + nome);
        }
         try {
            new Organizador().reAlocarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        

    }

    public void cadastrarNovoEspacoCafe(String nome, String lotacao) throws Exception {
        conexao = new Conexao().criarConexao();

        try {
            sql = "INSERT INTO cafe (nome, lotacao) VALUES (? , ?);";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setInt(2, Integer.parseInt(lotacao));
            stm.execute();
            conexao.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conexao.close();
            throw new Exception("Erro: Houve um erro ao cadastrar " + nome);
        }
        
         try {
            new Organizador().reAlocarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void cadastrarNovaPessoa(String nome, String sobrenome) throws Exception {
        String nomecompleto = nome + " " + sobrenome;
        conexao = new Conexao().criarConexao();
        try {

            sql = "INSERT INTO pessoas (nome, sobrenome, nomecompleto) VALUES (? , ?, ?);";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setString(2, sobrenome);
            stm.setString(3, nomecompleto);
            stm.execute();
            conexao.close();
        } catch (SQLException e) {
            conexao.close();
            throw new Exception("Erro: Houve um erro ao cadastrar " + nome + " " + sobrenome);
        }

        try {
            
            new Organizador().alocarNovaPessoa(nomecompleto, new Conexao().criarConexao());
        } catch (Exception e) {
            System.out.println("Não alocou pessoa");
        }

    }

    public Espaco[] getSalas() throws Exception {
        conexao = new Conexao().criarConexao();
        sql = "SELECT count(*) FROM salas;";
        stm = conexao.prepareStatement(sql);
        ResultSet res = stm.executeQuery();

        res.next();
        int nSalas = res.getInt(1);

        Espaco[] salas = new Espaco[nSalas];
        int index = 0;

        if (nSalas > 0) {
            sql = "SELECT id, nome, lotacao FROM salas;";
            stm = conexao.prepareStatement(sql);
            res = stm.executeQuery();

            while (res.next()) {
                int id = res.getInt(1);
                String nome = res.getString(2);
                int lotacaoMax = res.getInt(3);

                //Salas.add(new Sala(id, lotacaoMax, lotEtapaUm, lotEtapaDois));
                salas[index] = new Sala(id, nome, lotacaoMax);
                
               
                index++;
            }

        } else {
           
            throw new Exception("Não existem salas cadastradas");

        }
        conexao.close();
        
        return salas;

    }

    public ArrayList<Pessoa> getPessoas() throws Exception {
        conexao = new Conexao().criarConexao();
        sql = "SELECT * FROM pessoas;";
        stm = conexao.prepareStatement(sql);
        ResultSet res = stm.executeQuery();

        res.last();
        int numPessoas = res.getRow();
        res.first();

        ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();

        if (numPessoas > 0) {
            while (!res.isAfterLast()) {
                int id = res.getInt(1);
                String nome = res.getString(2);
                String sobre = res.getString(3);
                String completo = res.getString(4);

                pessoas.add(new Pessoa(id, nome, sobre, completo));
                res.next();
            }
        }
        conexao.close();
        return pessoas;
    }

    public Espaco[] getCafes() throws Exception {
        conexao = new Conexao().criarConexao();
        sql = "SELECT count(*) FROM cafe;";
        stm = conexao.prepareStatement(sql);
        ResultSet res = stm.executeQuery();
         

        res.next();
        int numCafes = res.getInt(1);
 
        Espaco[] cafes = new Espaco[numCafes];
        int index = 0;

        if (numCafes > 0) {
            sql = "SELECT id, nome, lotacao FROM cafe;";
            stm = conexao.prepareStatement(sql);
            res = stm.executeQuery();

            while (res.next()) {
                int id = res.getInt(1);
                String nome = res.getString(2);
                int lotacaoMax = res.getInt(3);

                cafes[index] = new EspacoCafe(id, nome, lotacaoMax);
                index++;
            }

        } else {
           
            throw new Exception("Não existem cafes cadastrados");

        }
        conexao.close();
        return cafes;

    }

}
