package com.example.transicaoenergetica;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

public class ConfiguracoesDialogFragment extends DialogFragment {

    private SwitchCompat switchMusica, switchSons;
    private Button btnFecharConfig, btnSairApp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_configuracoes, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        switchMusica = view.findViewById(R.id.switchMusica);
        switchSons = view.findViewById(R.id.switchSons);

        
        ImageView btnFecharConfig = view.findViewById(R.id.btnFecharConfig);

        MainActivity activity = (MainActivity) getActivity();

        if (activity != null) {
            // 1. Carrega os estados salvos para os Switches iniciarem na posição certa (aceso ou apagado)
            if (switchMusica != null) {
                switchMusica.setChecked(activity.isMusicaAtivada());
            }
            if (switchSons != null) {
                switchSons.setChecked(activity.isEfeitosAtivados());
            }

            // 2. Escuta os cliques no Switch da Música de fundo
            if (switchMusica != null) {
                switchMusica.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    activity.setMusicaAtivada(isChecked);
                    if (isChecked) {
                        Toast.makeText(getContext(), "Música de fundo ativada!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Música mutada", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // 3. Escuta os cliques no Switch dos Efeitos Sonoros (Dado, Roleta, Pergunta)
            if (switchSons != null) {
                switchSons.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    activity.setEfeitosAtivados(isChecked);
                    if (isChecked) {
                        Toast.makeText(getContext(), "Efeitos sonoros ativados!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Efeitos mutados", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        if (btnFecharConfig != null) {
            btnFecharConfig.setOnClickListener(v -> dismiss());
        }

        return view;
    }

    
    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
}
