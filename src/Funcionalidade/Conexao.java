/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Jean
 */
public class Conexao {
    private Connection conexao;
    private PreparedStatement stm;
    private String sql;
    
    public Connection criarConexao() throws Exception {
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost", "root", "");
            sql = "use treinamento;";
            stm = conexao.prepareStatement(sql);
            stm.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conexao.close();
            throw new Exception("Erro: Falha ao se conectar à base de dados");
        }
        return conexao;
    }
    
}
