package com.example.transicaoenergetica;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.DialogFragment;
import com.airbnb.lottie.LottieAnimationView;

public class DialogRoletaFragment extends DialogFragment {

    private LottieAnimationView lottieRoleta;
    private TextView tvResultadoRoleta;
    private Button btnGirarRoleta;
    private final Handler handler = new Handler();

    // Lista de prêmios possíveis que a roleta pode sortear
    private final String[] premios = {
            "+2 Moedas",
            "+3 Energias",
            "+2 Sustentabilidade",
            "-1 Poluição",
            "+1 Moeda"
    };

    public DialogRoletaFragment() {
        // Obrigatório
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_roleta, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setCancelable(false);
        }

        lottieRoleta = view.findViewById(R.id.lottieRoleta);
        tvResultadoRoleta = view.findViewById(R.id.tvResultadoRoleta);
        btnGirarRoleta = view.findViewById(R.id.btnGirarRoleta);

        btnGirarRoleta.setOnClickListener(v -> girarRoletaMecanica());

        return view;
    }

    private void girarRoletaMecanica() {
        btnGirarRoleta.setEnabled(false);
        btnGirarRoleta.setText("GIRANDO...");

        // TRILHA DA ROLETA: Avisa o tabuleiro para soltar o som de giro no clique real do botão!
        dispararEfeitoNoTabuleiro(0);

        // 1. Ativa o giro visual da roleta
        lottieRoleta.playAnimation();

        // 2. Sorteia o índice do prêmio
        int indiceSorteado = (int) (Math.random() * premios.length);
        String premioFinal = premios[indiceSorteado];

        // 3. Aplica o prêmio automaticamente no Jogador Atual
        Jogador atual = GameManager.getInstance().getJogadorAtual();
        if (atual != null) {
            switch (indiceSorteado) {
                case 0: atual.setMoedas(atual.getMoedas() + 2); break;
                case 1: atual.setEnergia(atual.getEnergia() + 3); break; // Usando o setter correto da sua classe
                case 2: atual.setSustentabilidade(atual.getSustentabilidade() + 2); break;
                case 3:
                    if (atual.getPoluicao() > 0) atual.setPoluicao(atual.getPoluicao() - 1);
                    break;
                case 4: atual.setMoedas(atual.getMoedas() + 1); break;
            }
        }

        // 4. Timer de 2 segundos para simular a roleta parando
        handler.postDelayed(() -> {
            lottieRoleta.cancelAnimation();
            lottieRoleta.setVisibility(View.GONE);

            // Revela o texto do prêmio na tela
            tvResultadoRoleta.setText(premioFinal);
            tvResultadoRoleta.setVisibility(View.VISIBLE);
            btnGirarRoleta.setText("CONCLUÍDO");

            //GATILHO DA RECOMPENSA: Toca o som baseado no item sorteado
            if (indiceSorteado == 3) {
                dispararEfeitoNoTabuleiro(2); // Toca Fail.mp3 para o evento de Poluição
            } else {
                dispararEfeitoNoTabuleiro(1); // Toca Win.mp3 para Moedas, Energia ou Sustentabilidade
            }

            handler.postDelayed(() -> {
                dismiss(); // Fecha o pop-up da roleta

                // Abre a conexão com o fragmento do Tabuleiro que está por trás
                if (getParentFragmentManager() != null) {
                    Fragment fragmentTab = getParentFragmentManager().findFragmentById(R.id.fragment_container);

                    if (fragmentTab instanceof GameBoardFragment) {
                        GameBoardFragment tabuleiro = (GameBoardFragment) fragmentTab;

                        // Primeiro: Atualiza o placar na tela com os novos pontos que a roleta deu
                        tabuleiro.atualizarPlacar();

                        // Segundo: Passa a vez para o próximo peão jogar
                        tabuleiro.proximoTurno();
                    }
                }
            }, 2000);

        }, 2000);
    }

    //Ponte de áudio segura com o SoundPool do GameBoardFragment
    private void dispararEfeitoNoTabuleiro(int tipoSom) {
        if (getParentFragmentManager() != null) {
            Fragment tabuleiro = getParentFragmentManager().findFragmentById(R.id.fragment_container);
            if (tabuleiro instanceof GameBoardFragment) {
                GameBoardFragment gameBoard = (GameBoardFragment) tabuleiro;
                switch (tipoSom) {
                    case 0: gameBoard.tocarSomRoleta(); break; // Som do giro iniciando
                    case 1: gameBoard.tocarSomWin(); break;    // Som de sucesso no prêmio
                    case 2: gameBoard.tocarSomFail(); break;   // Som de revés na poluição
                }
            }
        }
    }
}
