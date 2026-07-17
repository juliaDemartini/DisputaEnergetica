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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

public class DialogTurnoFragment extends DialogFragment {

    public interface OnDadoRoladoListener {
        void onDadoRolado();
    }

    private OnDadoRoladoListener listener;
    private LottieAnimationView lottieDado;
    private TextView tvResultadoDado, tvTurnoJogadorNome;
    private Button btnRollDice;
    private final Handler handler = new Handler();

    public static DialogTurnoFragment newInstance(OnDadoRoladoListener listener) {
        DialogTurnoFragment fragment = new DialogTurnoFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_turno, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setCancelable(false);
        }

        CardView cvBackground = view.findViewById(R.id.cvTurnoBackground);
        tvTurnoJogadorNome = view.findViewById(R.id.tvTurnoJogadorNome);
        lottieDado = view.findViewById(R.id.lottieDado);
        tvResultadoDado = view.findViewById(R.id.tvResultadoDado);
        btnRollDice = view.findViewById(R.id.btnModalRollDice);

        Jogador atual = GameManager.getInstance().getJogadorAtual();
        boolean ehVirtual = GameManager.getInstance().isUsarDadoVirtual();

        if (atual != null) {
            tvTurnoJogadorNome.setText(atual.getNome() + " (" + atual.getCorPeao() + ")");

            if (atual.getCorPeao().equalsIgnoreCase("Vermelho")) cvBackground.setCardBackgroundColor(Color.parseColor("#D32F2F"));
            else if (atual.getCorPeao().equalsIgnoreCase("Azul")) cvBackground.setCardBackgroundColor(Color.parseColor("#1976D2"));
            else if (atual.getCorPeao().equalsIgnoreCase("Amarelo")) cvBackground.setCardBackgroundColor(Color.parseColor("#FBC02D"));
            else if (atual.getCorPeao().equalsIgnoreCase("Verde")) cvBackground.setCardBackgroundColor(Color.parseColor("#388E3C"));
        }

        // Configuração inicial dinâmica baseada na escolha do Setup
        if (ehVirtual) {
            lottieDado.setVisibility(View.VISIBLE);
            tvResultadoDado.setVisibility(View.GONE);
            btnRollDice.setText("GIRAR DADO");
            btnRollDice.setOnClickListener(v -> rodarDadoComRevelacao());
        } else {
            lottieDado.setVisibility(View.GONE);
            tvResultadoDado.setVisibility(View.GONE);
            btnRollDice.setText("OK");
            btnRollDice.setOnClickListener(v -> {
                // 🎲 DADO FÍSICO: Som removido, fecha direto em silêncio
                concluirAcaoDado();
            });
        }

        return view;
    }

    private void rodarDadoComRevelacao() {
        btnRollDice.setEnabled(false);
        btnRollDice.setText("GIRANDO...");

        // 🎲 DADO VIRTUAL: Dispara o som do SoundPool mapeado no Tabuleiro
        dispararSomDoTabuleiro();

        // 1. Liga o loop da animação do dado 3D
        lottieDado.playAnimation();

        // 2. Sorteia o número de 1 a 6
        int resultadoFinal = (int) (Math.random() * 6) + 1;

        // 3. Primeiro Timer: Espera 1.5 segundos da animação rodando
        handler.postDelayed(() -> {
            // Desliga e esconde o Lottie
            lottieDado.cancelAnimation();
            lottieDado.setVisibility(View.GONE);

            // Coloca o número sorteado na tela e deixa ele visível bem grande
            tvResultadoDado.setText(String.valueOf(resultadoFinal));
            tvResultadoDado.setVisibility(View.VISIBLE);

            // Esconde o botão branco para sumir com o "remendo" esquisito
            btnRollDice.setVisibility(View.GONE);

            // 4. Segundo Timer: Deixa o número na tela por 1.5 segundos para o jogador ver e depois fecha
            handler.postDelayed(() -> concluirAcaoDado(), 1500);

        }, 1500);
    }

    // ⚡ Envia o comando de áudio para o Tabuleiro apenas se o dado for virtual
    private void dispararSomDoTabuleiro() {
        if (getParentFragmentManager() != null) {
            Fragment tabuleiro = getParentFragmentManager().findFragmentById(R.id.fragment_container);
            if (tabuleiro instanceof GameBoardFragment) {
                ((GameBoardFragment) tabuleiro).tocarSomDado();
            }
        }
    }

    private void concluirAcaoDado() {
        if (listener != null) listener.onDadoRolado();
        dismiss();
    }
}