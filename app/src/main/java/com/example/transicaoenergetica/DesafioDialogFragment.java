package com.example.transicaoenergetica;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class DesafioDialogFragment extends DialogFragment {

    private String tipoEvento;
    private Pergunta perguntaSorteada;
    private CountDownTimer countDownTimer;

    private TextView tvModalType, tvTimer, tvModalEnunciado;
    private Button btnOptA, btnOptB, btnOptC, btnOptD;

    // Método estático para instanciar o modal passando o tipo de evento desejado
    public static DesafioDialogFragment newInstance(String tipoEvento) {
        DesafioDialogFragment fragment = new DesafioDialogFragment();
        Bundle args = new Bundle();
        args.putString("TIPO_EVENTO", tipoEvento);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipoEvento = getArguments().getString("TIPO_EVENTO");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_desafio, container, false);

        // Deixa o fundo do diálogo transparente para destacar as bordas arredondadas do CardView
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Inicializar componentes visuais
        tvModalType = view.findViewById(R.id.tvModalType);
        tvTimer = view.findViewById(R.id.tvTimer);
        tvModalEnunciado = view.findViewById(R.id.tvModalEnunciado);
        btnOptA = view.findViewById(R.id.btnOptA);
        btnOptB = view.findViewById(R.id.btnOptB);
        btnOptC = view.findViewById(R.id.btnOptC);
        btnOptD = view.findViewById(R.id.btnOptD);

        // Sortear pergunta do Repositório baseado no tipo de evento clicado
        perguntaSorteada = RepositorioPerguntas.sortearPerguntaPorTipo(tipoEvento);

        if (perguntaSorteada != null) {
            montarInterfacePergunta();
            iniciarCronometro();
        } else {
            tvModalEnunciado.setText("Nenhuma pergunta encontrada para este evento.");
        }

        return view;
    }

    private void montarInterfacePergunta() {
        tvModalType.setText("EVENTO: " + perguntaSorteada.getTipo());
        tvModalEnunciado.setText(perguntaSorteada.getEnunciado());
        btnOptA.setText("A) " + perguntaSorteada.getOpcaoA());
        btnOptB.setText("B) " + perguntaSorteada.getOpcaoB());

        // Se for um evento de ESCOLHA ou GLOBAL, geralmente não usamos as 4 alternativas
        if (perguntaSorteada.getOpcaoC().isEmpty()) {
            btnOptC.setVisibility(View.GONE);
        } else {
            btnOptC.setText("C) " + perguntaSorteada.getOpcaoC());
        }

        if (perguntaSorteada.getOpcaoD().isEmpty()) {
            btnOptD.setVisibility(View.GONE);
        } else {
            btnOptD.setText("D) " + perguntaSorteada.getOpcaoD());
        }

        // Configurar cliques de resposta
        btnOptA.setOnClickListener(v -> checarResposta("A"));
        btnOptB.setOnClickListener(v -> checarResposta("B"));
        btnOptC.setOnClickListener(v -> checarResposta("C"));
        btnOptD.setOnClickListener(v -> checarResposta("D"));
    }

    private void iniciarCronometro() {
        // 30000 milissegundos = 30 segundos, decrementando de 1 em 1 segundo (1000ms)
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("0s");
                // 🔊 TEMPO ESGOTADO: Considerado um erro, solta o som de fail antes de fechar
                dispararSomNoTabuleiro(false);
                Toast.makeText(getContext(), "Tempo esgotado! Você perdeu a vez.", Toast.LENGTH_LONG).show();

                // Fecha a pergunta e passa o turno
                dismiss();
                forçarProximoTurno();
            }
        }.start();
    }

    private void checarResposta(String alternativaSelecionada) {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Para o cronômetro imediatamente ao responder
        }

        Jogador jogadorAtual = GameManager.getInstance().getJogadorAtual();

        // 1. Se for um evento do tipo Sorte, valida se acertou a resposta técnica
        if (perguntaSorteada.getTipo().equals("SORTE")) {
            if (alternativaSelecionada.equalsIgnoreCase(perguntaSorteada.getRespostaCorreta())) {
                Toast.makeText(getContext(), "Resposta CORRETA! Seus recursos subiram.", Toast.LENGTH_SHORT).show();

                // RESPOSTA CORRETA: Solta o Win.mp3
                dispararSomNoTabuleiro(true);

                if (jogadorAtual != null) {
                    jogadorAtual.setSustentabilidade(jogadorAtual.getSustentabilidade() + perguntaSorteada.getImpactoSustentabilidade());
                    jogadorAtual.setMoedas(jogadorAtual.getMoedas() + perguntaSorteada.getImpactoMoedas());
                }
            } else {
                Toast.makeText(getContext(), "Resposta ERRADA! Resposta correta era: " + perguntaSorteada.getRespostaCorreta(), Toast.LENGTH_SHORT).show();

                //RESPOSTA ERRADA: Solta o Fail.mp3
                dispararSomNoTabuleiro(false);
            }
        } else {
            // 2. Se for evento de ESCOLHA ou GLOBAL, aplica o impacto direto da decisão tomada
            Toast.makeText(getContext(), "Decisão registrada com sucesso!", Toast.LENGTH_SHORT).show();

            if (jogadorAtual != null) {
                // Alternativa A é ecológica, alternativa B é econômica industrial
                if (alternativaSelecionada.equalsIgnoreCase("A")) {

                    //DECISÃO VERDE: Toca o Win.mp3 pelas boas práticas ambientais
                    dispararSomNoTabuleiro(true);

                    int sustAtual = jogadorAtual.getSustentabilidade();
                    int impacto = perguntaSorteada.getImpactoSustentabilidade();
                    jogadorAtual.setSustentabilidade(sustAtual + impacto);

                } else {

                    //DECISÃO POLUENTE: Toca o Fail.mp3 alertando o aumento da poluição
                    dispararSomNoTabuleiro(false);

                    jogadorAtual.setMoedas(jogadorAtual.getMoedas() + 1);
                    jogadorAtual.setPoluicao(jogadorAtual.getPoluicao() + 1);
                }
            }
        }

        dismiss(); // Fecha a pergunta
        forçarProximoTurno();
    }

    // Ponte de áudio segura com o SoundPool do Tabuleiro
    private void dispararSomNoTabuleiro(boolean ehSucesso) {
        if (getParentFragmentManager() != null) {
            Fragment fragment = getParentFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment instanceof GameBoardFragment) {
                GameBoardFragment tabuleiro = (GameBoardFragment) fragment;
                if (ehSucesso) {
                    tabuleiro.tocarSomWin();
                } else {
                    tabuleiro.tocarSomFail();
                }
            }
        }
    }

    // Avança o fluxo do jogo atualizando o placar do tabuleiro por trás
    private void forçarProximoTurno() {
        if (getParentFragmentManager() != null) {
            Fragment fragment = getParentFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment instanceof GameBoardFragment) {
                ((GameBoardFragment) fragment).proximoTurno();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Evita vazamento de memória se fechar o modal antes
        }
    }
}
