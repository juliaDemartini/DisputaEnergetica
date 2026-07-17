package com.example.transicaoenergetica;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager instance;

    private List<Jogador> listaJogadores;
    private int jogadorAtualIndex;

    // Construtor privado para garantir o padrão Singleton
    private GameManager() {
        listaJogadores = new ArrayList<>();
        jogadorAtualIndex = 0;
    }

    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // Inicializa a partida limpando dados antigos
    public void iniciarNovaPartida(List<Jogador> novosJogadores) {
        this.listaJogadores = novosJogadores;
        this.jogadorAtualIndex = 0;
    }

    public List<Jogador> getListaJogadores() {
        return listaJogadores;
    }

    public Jogador getJogadorAtual() {
        if (listaJogadores.isEmpty()) return null;
        // CORRIGIDO: Trocado os colchetes [] por .get()
        return listaJogadores.get(getJogadorAtualIndex());
    }

    public int getJogadorAtualIndex() {
        return jogadorAtualIndex;
    }

    // Passa a vez para o próximo jogador da mesa
    public void avancarTurno() {
        if (!listaJogadores.isEmpty()) {
            jogadorAtualIndex = (jogadorAtualIndex + 1) % listaJogadores.size();
        }
    }

    // Variável para guardar o tipo de dado (true = virtual, false = físico)
    private boolean usarDadoVirtual = true;

    // Método para salvar a escolha do jogador
    public void setUsarDadoVirtual(boolean usar) {
        this.usarDadoVirtual = usar;
    }

    // Método para o app consultar qual dado usar
    public boolean isUsarDadoVirtual() {
        return this.usarDadoVirtual;
    }
}