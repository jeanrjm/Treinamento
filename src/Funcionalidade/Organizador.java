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

    private int[] getLocaisVagosNasEtapas(Espaco[] espaco) throws Exception {
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
                novaLotacao = atual.getLotacaoEtapaUm() + 1;

                //não excedeu lotacao maxima
                if (novaLotacao <= atual.getLotacaoMaxima()) {

                    for (int j = 0; j < qtdEspacos; j++) {

                        if (j != i) {
                            Espaco comparada = espaco[j];
                            int compLotacao = comparada.getLotacaoEtapaUm() + 1;

                            //Se excedeu a diferença de 1, em relação a qualquer outra sala
                            if (novaLotacao > compLotacao) {
                                aceitavel = false;
                                break; //para de comparar de imediato
                            }
                        }
                    }

                    if (aceitavel) {
                        etapaUm = atual.getId();
                        indexSelecaoUm = i;
                        break;
                    }
                }
            }

            //SALA ETAPA 2
            if (etapaUm == 0) {
                etapaDois = 0;
            } else {
                //SE SÓ HOUVER UMA SALA CADASTRADA E HOUVER DISPONIBILIDADE NA ETAPA DOIS, REPETE
                if (qtdEspacos < 2 && (espaco[indexSelecaoUm].getLotacaoEtapaDois() + 1) <= espaco[indexSelecaoUm].getLotacaoMaxima()) {
                    etapaDois = etapaUm;
                } //´SOMENTE METADE DOS PESSOAS TROCAM DE SALA. VERIFICA SE TEM DISPONIBILIDADE NA ETAPA DOIS PARA REPETIR
                else if ((espaco[indexSelecaoUm].getLotacaoEtapaUm() + 1) % 2 != 0 && (espaco[indexSelecaoUm].getLotacaoEtapaDois() + 1) <= espaco[indexSelecaoUm].getLotacaoMaxima()) {
                    etapaDois = etapaUm;
                  
                } else {
                    for (int i = 0; i < qtdEspacos; i++) {

                        Espaco atual = espaco[i];

                        boolean aceitavel = true;
                        int novaLotacao;
                        novaLotacao = atual.getLotacaoEtapaDois() + 1;

                        if (novaLotacao <= atual.getLotacaoMaxima()) {
                            //não excedeu lotacao maxima
                            for (int j = 0; j < qtdEspacos; j++) {
                                if (j != i) {
                                    Espaco comparada = espaco[j];
                                    int compLotacao = comparada.getLotacaoEtapaDois() + 1;

                                    //Se excedeu a diferença de 1, em relação a aqualquer outra sala, para e comparar de imediato
                                    if (novaLotacao > compLotacao) {
                                        aceitavel = false;
                                        break;
                                    }
                                }
                            }
                            //SE TEM ESPACO POREM É O MESMO LOCAL DA ETAPA 1, NÃO É ACEITAVEL 
                            //POREM SE NÃO HOUVER OUTRA POSSIBILIDADE É AUTORIZADO A REPETIR, JA QUE NÃO EXEDE DIFERENÇA 
                            if (atual.getId() == etapaUm) {
                                aceitavel = false;
                                repetivel = true;
                            }

                            if (aceitavel) {
                                etapaDois = atual.getId();
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

    public void alocarNovaPessoa(String nomecompleto, Connection conexao) throws Exception {

        try {

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

            //conexao.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage() + " Falha ao alocar pessoa: " + nomecompleto);
        }
    }

    public void reAlocarTodos() throws Exception {

        try {
            conexao = new Conexao().criarConexao();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }

        Evento evento = new Evento();
        System.out.println("Iniciando re-organização");
        try {
            sql = "DELETE FROM ensalamento";
            stm = conexao.prepareStatement(sql);
            stm.execute();
            
            ArrayList<Pessoa> pessoas = evento.getPessoas();
            int nPessoas = pessoas.size();
            if (nPessoas > 0) {
                for (Pessoa pessoa : pessoas) {

                    alocarNovaPessoa(pessoa.getNomeCompleto(), conexao);

                }
            }
            System.out.println("Re-organização completada");
            conexao.close();

        } catch (Exception e) {
            conexao.close();
            throw new Exception(e.getMessage());
        }

    }
}
