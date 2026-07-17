package com.example.transicaoenergetica;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment; // Importado para funcionar como Pop-up

public class PreparadosDialogFragment extends DialogFragment {

    private TextView txtTimer;
    private CountDownTimer contador;
    private boolean partidaIniciada = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preparados, container, false);

        // Configura o Pop-up para ter fundo transparente (destacando os cantos arredondados do seu CardView)
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        // 🚨 MUDANÇA DE TRILHA: Troca imediatamente para a música de tensão/adrenalina
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).trocarMusicaGlobal(R.raw.musica_tensao);
        }

        txtTimer = view.findViewById(R.id.txtTimer);
        Button btnIniciar = view.findViewById(R.id.btnIniciarImediato);

        // Inicia o timer de 15 segundos (15000 milissegundos)
        iniciarTimer(15000);

        if (btnIniciar != null) {
            btnIniciar.setOnClickListener(v -> finalizarPreparacao());
        }

        return view;
    }

    private void iniciarTimer(long tempo) {
        contador = new CountDownTimer(tempo, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (txtTimer != null) {
                    txtTimer.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                if (txtTimer != null) {
                    txtTimer.setText("0");
                }
                finalizarPreparacao();
            }
        }.start();
    }

    private void finalizarPreparacao() {
        if (partidaIniciada) return;
        partidaIniciada = true;

        if (contador != null) {
            contador.cancel();
        }

        // MUDANÇA DE TRILHA: Para a tensão e inicia a música oficial do Tabuleiro/Jogo
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).trocarMusicaGlobal(R.raw.musica_jogo);
        }

        dismiss(); // Fecha o pop-up vermelho

        // Abre a tela do tabuleiro passando por cima da seleção de jogadores
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new GameBoardFragment())
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Define que o pop-up deve ocupar 90% da largura da tela do celular de forma responsiva
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.90),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (contador != null) {
            contador.cancel();
        }
    }
}