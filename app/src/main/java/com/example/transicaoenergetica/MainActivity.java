package com.example.transicaoenergetica;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imgPlaneta;
    private FrameLayout container;

    //Tocador de áudio principal do jogo
    private MediaPlayer tocadorMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //COMEÇA A MÚSICA 1 (MENU/SETUP) LOGO NA INICIALIZAÇÃO
        trocarMusicaGlobal(R.raw.musica_menu);

        imgPlaneta = findViewById(R.id.imgPlanetaGirando);
        container = findViewById(R.id.fragment_container);

        if (imgPlaneta != null) {
            imgPlaneta.post(() -> {
                Animation girar = AnimationUtils.loadAnimation(MainActivity.this, R.anim.girar_planeta);
                imgPlaneta.startAnimation(girar);
            });
        }

        new Handler().postDelayed(() -> {
            if (imgPlaneta != null) {
                imgPlaneta.clearAnimation();
                imgPlaneta.setVisibility(View.GONE);
            }
            if (container != null) {
                container.setVisibility(View.VISIBLE);
            }
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MainMenuFragment())
                        .commit();
            }
        }, 2500);
    }

    //MÉTODO GLOBAL MODIFICADO: Só toca música de fundo se o switch de música estiver ativado
    public void trocarMusicaGlobal(int arquivoAudioRaw) {
        // Se já tem alguma música tocando, para ela e limpa a memória
        if (tocadorMusica != null) {
            tocadorMusica.stop();
            tocadorMusica.release();
            tocadorMusica = null;
        }

        //VERIFICAÇÃO: Se o usuário mutou as músicas nas configurações, não inicia o tocador
        if (!isMusicaAtivada()) return;

        // Inicia a nova música escolhida
        tocadorMusica = MediaPlayer.create(this, arquivoAudioRaw);
        if (tocadorMusica != null) {
            tocadorMusica.setLooping(true);
            tocadorMusica.setVolume(0.5f, 0.5f); // Volume em 50%
            tocadorMusica.start();
        }
    }

    //MÉTODOS DE CONTROLE DO SHAREDPREFERENCES (MÚSICA E EFEITOS INDEPENDENTES)

    public boolean isMusicaAtivada() {
        SharedPreferences preferences = getSharedPreferences("ConfigTransicao", Context.MODE_PRIVATE);
        return preferences.getBoolean("musica_ligada", true); // Padrão é true (com som)
    }

    public void setMusicaAtivada(boolean ativar) {
        getSharedPreferences("ConfigTransicao", Context.MODE_PRIVATE).edit().putBoolean("musica_ligada", ativar).apply();

        // Se desligou agora pelo switch, pausa a reprodução imediatamente
        if (!ativar && tocadorMusica != null && tocadorMusica.isPlaying()) {
            tocadorMusica.pause();
        }
        // Se ligou agora pelo switch e o tocador existe mas está pausado, volta a tocar
        else if (ativar && tocadorMusica != null && !tocadorMusica.isPlaying()) {
            tocadorMusica.start();
        }
    }

    public boolean isEfeitosAtivados() {
        SharedPreferences preferences = getSharedPreferences("ConfigTransicao", Context.MODE_PRIVATE);
        return preferences.getBoolean("efeitos_ligados", true); // Padrão é true (com efeitos)
    }

    public void setEfeitosAtivados(boolean ativar) {
        getSharedPreferences("ConfigTransicao", Context.MODE_PRIVATE).edit().putBoolean("efeitos_ligados", ativar).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tocadorMusica != null) {
            tocadorMusica.stop();
            tocadorMusica.release();
            tocadorMusica = null;
        }
    }
}
