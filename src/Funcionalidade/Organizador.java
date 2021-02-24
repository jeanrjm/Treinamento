/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public class Organizador {

    private Connection conexao;
    private PreparedStatement stm;
    private String sql;

    private int[] getLocaisVagosNasEtapas(Espaco[] espaco) {

        int qtdEspacos = espaco.length;
        int[] selecao = new int[2];
        if (espaco.length > 0) {
            int etapaUm = 0;
            int etapaDois = 0;
            boolean repetivel = false;
            int indexSelecaoUm = -1;

            //SALA ETAPA 1
            for (int i = 0; i < qtdEspacos; i++) {
                Espaco atual = espaco[i];

                boolean aceitavel = true;

                int novaLotacao;
                novaLotacao = atual.lotacaoEtapaUm + 1;

                // System.out.println("Lotacao" + novaLotacao + "MAx" + atual.lotacaoMax);
                //não excedeu lotacao maxima
                if (novaLotacao <= atual.lotacaoMax) {

                    for (int j = 0; j < qtdEspacos; j++) {

                        if (j != i) {
                            Espaco comparada = espaco[j];
                            int compLotacao = comparada.lotacaoEtapaUm + 1;

                            //Se excedeu a diferença de 1, em relação a qualquer outra sala
                            if (novaLotacao > compLotacao) {
                                aceitavel = false;
                                break; //para de comparar de imediato
                            }
                        }
                    }

                    if (aceitavel) {
                        etapaUm = atual.id;
                        indexSelecaoUm = i;
                        break;
                    }
                }
            }

            //SALA ETAPA 2
            //SE SÓ HOUVER UMA SALA CADASTRADA E HOUVER DISPONIBILIDADE NA ETAPA DOIS, REPETE
            if (etapaUm == 0) {
                etapaDois = 0;
            } else {
                if (qtdEspacos < 2 && (espaco[indexSelecaoUm].lotacaoEtapaDois + 1) <= espaco[indexSelecaoUm].lotacaoMax) {
                    etapaDois = etapaUm;
                } else {
                    for (int i = 0; i < qtdEspacos; i++) {

                        Espaco atual = espaco[i];

                        boolean aceitavel = true;
                        int novaLotacao;
                        novaLotacao = atual.lotacaoEtapaDois + 1;

                        if (novaLotacao <= atual.lotacaoMax) {
                            //não excedeu lotacao maxima
                            for (int j = 0; j < qtdEspacos; j++) {
                                if (j != i) {
                                    Espaco comparada = espaco[j];
                                    int compLotacao = comparada.lotacaoEtapaDois + 1;

                                    //Se excedeu a diferença de 1, em relação a aqualquer outra sala, para e comparar de imediato
                                    if (novaLotacao > compLotacao) {
                                        aceitavel = false;
                                        break;
                                    }
                                }
                            }
                            //SE TEM ESPACO POREM É O MESMO LOCAL DA ETAPA 1, NÃO É ACEITAVEL 
                            //POREM SE NÃO HOUVER OUTRA POSSIBILIDADE É AUTORIZADO A REPETIR, JA QUE NÃO EXEDE DIFERENÇA 
                            if (atual.id == etapaUm) {
                                aceitavel = false;
                                repetivel = true;
                            }

                            if (aceitavel) {
                                etapaDois = atual.id;
                                break;
                            }
                        }

                    }
                }

                //NÃO FOI ENCONTRADO UM ESPACO PARA ETAPA DOIS, POREM TEM DISPONIBILIDADE NO MESMO ESPAÇO NA ETAPA SEM VIOLAR REGRAS
                if (etapaDois == 0 && repetivel) {
                    etapaDois = etapaUm;
                }
            }
            selecao[0] = etapaUm;
            selecao[1] = etapaDois;
            return selecao;

        } else {
            selecao[0] = 0;
            selecao[1] = 0;
            return selecao;
        }
    }

    public void alocarNovaPessoa(String nomecompleto) {

        try {
            conexao = new Conexao().criarConexao();
            int pessoaId = 0;

            sql = "SELECT id FROM pessoas WHERE nomecompleto = ? ;";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, nomecompleto);
            ResultSet res = stm.executeQuery();

            // res.next();
            res.last();
            int pessoasEncontradas = res.getRow();

            res.first();

            if (pessoasEncontradas > 0) {
                pessoaId = res.getInt(1);
                Evento evento = new Evento();

                sql = "INSERT INTO ensalamento (pessoa, salaUm, salaDois, cafeUm, cafeDois) VALUES (?, ? , ? , ? , ?);";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, pessoaId);

                try {

                    Espaco[] salas = evento.getSalas();
                    int[] salasEscolhidas = getLocaisVagosNasEtapas(salas);
                    stm.setInt(2, salasEscolhidas[0]);
                    stm.setInt(3, salasEscolhidas[1]);

                } catch (Exception ex) {
                    stm.setInt(2, 0);
                    stm.setInt(3, 0);
                }

                try {

                    Espaco[] cafes = evento.getCafes();
                    int[] cafesEscolhidos = getLocaisVagosNasEtapas(cafes);
                    stm.setInt(4, cafesEscolhidos[0]);
                    stm.setInt(5, cafesEscolhidos[1]);

                } catch (Exception ex) {
                    stm.setInt(4, 0);
                    stm.setInt(5, 0);
                }

                stm.execute();
                System.out.println("Alocou pessoa: " + nomecompleto);

            }

            conexao.close();
        } catch (Exception e) {
            System.out.println("Não fez ensalamento");
        }
    }

    public void reAlocarTodos() throws Exception {
        conexao = new Conexao().criarConexao();
        Evento evento = new Evento();

        sql = "DELETE FROM ensalamento";
        stm = conexao.prepareStatement(sql);
        stm.execute();

        ArrayList<Pessoa> pessoas = evento.getPessoas();

        for (Pessoa pessoa : pessoas) {

            alocarNovaPessoa(pessoa.nomecompleto);

        }

    }

    public Ensalamento getEnsalamentoPessoa(int id) {
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
}
