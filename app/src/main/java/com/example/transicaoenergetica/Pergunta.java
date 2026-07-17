package com.example.transicaoenergetica;

public class Pergunta {
    private String tipo; // "SORTE", "ESCOLHA", "GLOBAL"
    private String enunciado;
    private String opcaoA;
    private String opcaoB;
    private String opcaoC;
    private String opcaoD;
    private String respostaCorreta; // "A", "B", "C" ou "D"
    private int impactoSustentabilidade;
    private int impactoMoedas;

    public Pergunta(String tipo, String enunciado, String opcaoA, String opcaoB, String opcaoC, String opcaoD, String respostaCorreta, int impactSust, int impactMoedas) {
        this.tipo = tipo;
        this.enunciado = enunciado;
        this.opcaoA = opcaoA;
        this.opcaoB = opcaoB;
        this.opcaoC = opcaoC;
        this.opcaoD = opcaoD;
        this.respostaCorreta = respostaCorreta;
        this.impactoSustentabilidade = impactSust;
        this.impactoMoedas = impactMoedas;
    }

    // Getters rápidos para o nosso Fragment ler os dados
    public String getTipo() { return tipo; }
    public String getEnunciado() { return enunciado; }
    public String getOpcaoA() { return opcaoA; }
    public String getOpcaoB() { return opcaoB; }
    public String getOpcaoC() { return opcaoC; }
    public String getOpcaoD() { return opcaoD; }
    public String getRespostaCorreta() { return respostaCorreta; }
    public int getImpactoSustentabilidade() { return impactoSustentabilidade; }
    public int getImpactoMoedas() { return impactoMoedas; }
}