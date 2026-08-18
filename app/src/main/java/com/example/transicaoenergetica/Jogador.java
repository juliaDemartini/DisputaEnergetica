package com.example.transicaoenergetica;

public class Jogador {
    private String nome;
    private String corPeao;
    private int moedas;
    private int energia;
    private int sustentabilidade;
    private int poluicao;

    // Construtor que define os recursos iniciais padrão do jogo
    public Jogador(String nome, String corPeao) {
        this.nome = nome;
        this.corPeao = corPeao;
        this.moedas = 5;            // Escopo: Inicia com 5 moedas
        this.energia = 3;           // Escopo: Inicia com 3 energias
        this.sustentabilidade = 4;  // Escopo: Inicia com 4 sust
        this.poluicao = 1;         // Escopo: Inicia com 1 poluição
    }

    // Getters e Setters normais (eles alteram a instância real na memória)
    public String getNome() { return nome; }
    public String getCorPeao() { return corPeao; }

    public int getMoedas() { return moedas; }
    public void setMoedas(int moedas) { this.moedas = moedas; }

    public int getEnergia() { return energia; }
    public void setEnergia(int energia) { this.energia = energia; }

    public int getSustentabilidade() { return sustentabilidade; }
    public void setSustentabilidade(int sustentabilidade) { this.sustentabilidade = sustentabilidade; }

    public int getPoluicao() { return poluicao; }
    public void setPoluicao(int poluicao) { this.poluicao = poluicao; }

    //MÉTODO MÁGICO PARA O RANKING: Calcula a pontuação final de vitória
    // Fórmula baseada no equilíbrio do jogo: Soma os recursos bons e subtrai a poluição
    public int getPontuacaoTotal() {
        return (this.moedas + this.energia + this.sustentabilidade) - this.poluicao;
    }
}
