package com.example.transicaoenergetica;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class MainMenuFragment extends Fragment {

    private CardView btnStartGame;
    private Button btnInstructions;

    //Controle de efeitos sonoros curtos para o Menu
    private SoundPool soundPool;
    private int somClique;

    public MainMenuFragment() {
        // Obrigatório
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        //CONFIGURAÇÃO DO SOUNDPOOL: Carrega o som de clique na memória
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(attrs)
                .build();

        // Carrega o arquivo click.mp3 da pasta res/raw
        somClique = soundPool.load(getContext(), R.raw.click, 1);

        btnStartGame = view.findViewById(R.id.btnStartGame);

        //Ação para o botão da engrenagem branca de configurações
        ImageView btnSettings = view.findViewById(R.id.btnSettings);
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> {
                tocarSomClique(); // Toca o som de clique
                ConfiguracoesDialogFragment dialog = new ConfiguracoesDialogFragment();
                dialog.show(getParentFragmentManager(), "ConfiguracoesDialogFragment");
            });
        }

        // Vincular o botão de Instruções da tela inicial
        Button btnInstructions = view.findViewById(R.id.btnInstructions);
        if (btnInstructions != null) {
            btnInstructions.setOnClickListener(v -> {
                tocarSomClique(); // Toca o som de clique
                InstrucoesDialogFragment dialog = new InstrucoesDialogFragment();
                dialog.show(getParentFragmentManager(), "InstrucoesDialogFragment");
            });
        }

        if (btnStartGame != null) {
            btnStartGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tocarSomClique(); // Toca o som de clique antes de mudar de tela
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new SetupPlayersFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        return view;
    }

    //Método auxiliar para disparar o efeito sonoro de forma segura
    private void tocarSomClique() {
        if (soundPool != null) {
            soundPool.play(somClique, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Libera o SoundPool da memória ao sair do Menu
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
