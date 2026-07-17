package com.example.transicaoenergetica;

import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class SetupPlayersFragment extends Fragment {

    // 🎯 Elementos visuais dos novos Cards Customizáveis
    private CardView cardJogador1, cardJogador2, cardJogador3, cardJogador4;
    private TextView tvNomeJ1, tvNomeJ2, tvNomeJ3, tvNomeJ4;
    private ImageView ivPeaoJ1, ivPeaoJ2, ivPeaoJ3, ivPeaoJ4;

    // 🎨 Variáveis dinâmicas para guardar as cores atuais (Hexadecimal) de cada um
    private String corJ1Hex = "#FF2E2E"; // Vermelho inicial
    private String corJ2Hex = "#2E7DFF"; // Azul inicial
    private String corJ3Hex = "#2EFF3A"; // Verde inicial
    private String corJ4Hex = "#FFD42E"; // Amarelo inicial

    private Button btnCount2, btnCount3, btnCount4, btnConfirmPlayers;
    private androidx.appcompat.widget.SwitchCompat switchDadoVirtual;

    private int qtdJogadoresAtivos = 2; // Começa por padrão com 2 jogadores ativos

    // 🔊 Controle de efeitos sonoros curtos para a seleção
    private SoundPool soundPool;
    private int somClique, somPop;

    public SetupPlayersFragment() {
        // Obrigatório
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_players, container, false);

        // 🔊 CONFIGURAÇÃO DO SOUNDPOOL: Carrega os sons na memória
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(attrs)
                .build();

        somClique = soundPool.load(getContext(), R.raw.click, 1);
        somPop = soundPool.load(getContext(), R.raw.pop, 1);

        // 🔍 Mapeamento dos Cards
        cardJogador1 = view.findViewById(R.id.cardJogador1);
        cardJogador2 = view.findViewById(R.id.cardJogador2);
        cardJogador3 = view.findViewById(R.id.cardJogador3);
        cardJogador4 = view.findViewById(R.id.cardJogador4);

        // 🔍 Mapeamento dos Textos dos Nomes
        tvNomeJ1 = view.findViewById(R.id.tvNomeJ1);
        tvNomeJ2 = view.findViewById(R.id.tvNomeJ2);
        tvNomeJ3 = view.findViewById(R.id.tvNomeJ3);
        tvNomeJ4 = view.findViewById(R.id.tvNomeJ4);

        // 🔍 Mapeamento das Imagens dos Peões
        ivPeaoJ1 = view.findViewById(R.id.ivPeaoJ1);
        ivPeaoJ2 = view.findViewById(R.id.ivPeaoJ2);
        ivPeaoJ3 = view.findViewById(R.id.ivPeaoJ3);
        ivPeaoJ4 = view.findViewById(R.id.ivPeaoJ4);

        switchDadoVirtual = view.findViewById(R.id.switchDado);

        // 🔍 Vincula os botões seletores do topo e o de iniciar
        btnCount2 = view.findViewById(R.id.btnCount2);
        btnCount3 = view.findViewById(R.id.btnCount3);
        btnCount4 = view.findViewById(R.id.btnCount4);
        btnConfirmPlayers = view.findViewById(R.id.btnIniciarJogo);

        // Configura o estado inicial da tela exibindo 2 jogadores
        ajustarVisibilidadeCampos(2);

        // Ouvintes dos seletores de quantidade (com som de clique)
        if (btnCount2 != null) btnCount2.setOnClickListener(v -> { tocarClique(); ajustarVisibilidadeCampos(2); });
        if (btnCount3 != null) btnCount3.setOnClickListener(v -> { tocarClique(); ajustarVisibilidadeCampos(3); });
        if (btnCount4 != null) btnCount4.setOnClickListener(v -> { tocarClique(); ajustarVisibilidadeCampos(4); });

        // ✨ OUVINTES DE CLIQUE NOS CARDS: Abrem o pop-up de edição (com som de pop)
        if (cardJogador1 != null) {
            cardJogador1.setOnClickListener(v -> {
                tocarPop();
                abrirPopUpEdicao(tvNomeJ1.getText().toString(), corJ1Hex, (novoNome, novaCorHex) -> {
                    corJ1Hex = novaCorHex;
                    tvNomeJ1.setText(novoNome);
                    ivPeaoJ1.setColorFilter(Color.parseColor(novaCorHex));
                });
            });
        }

        if (cardJogador2 != null) {
            cardJogador2.setOnClickListener(v -> {
                tocarPop();
                abrirPopUpEdicao(tvNomeJ2.getText().toString(), corJ2Hex, (novoNome, novaCorHex) -> {
                    corJ2Hex = novaCorHex;
                    tvNomeJ2.setText(novoNome);
                    ivPeaoJ2.setColorFilter(Color.parseColor(novaCorHex));
                });
            });
        }

        if (cardJogador3 != null) {
            cardJogador3.setOnClickListener(v -> {
                tocarPop();
                abrirPopUpEdicao(tvNomeJ3.getText().toString(), corJ3Hex, (novoNome, novaCorHex) -> {
                    corJ3Hex = novaCorHex;
                    tvNomeJ3.setText(novoNome);
                    ivPeaoJ3.setColorFilter(Color.parseColor(novaCorHex));
                });
            });
        }

        if (cardJogador4 != null) {
            cardJogador4.setOnClickListener(v -> {
                tocarPop();
                abrirPopUpEdicao(tvNomeJ4.getText().toString(), corJ4Hex, (novoNome, novaCorHex) -> {
                    corJ4Hex = novaCorHex;
                    tvNomeJ4.setText(novoNome);
                    ivPeaoJ4.setColorFilter(Color.parseColor(novaCorHex));
                });
            });
        }

        // 🟩 Ação de confirmar e enviar os dados tratados para a partida global
        btnConfirmPlayers.setOnClickListener(v -> {
            tocarClique();
            List<Jogador> listaDaPartida = new ArrayList<>();

            String n1 = tvNomeJ1.getText().toString().trim();
            String n2 = tvNomeJ2.getText().toString().trim();
            String n3 = tvNomeJ3.getText().toString().trim();
            String n4 = tvNomeJ4.getText().toString().trim();

            // Validação de nomes vazios (caso tenham apagado no pop-up)
            if (n1.isEmpty() || n2.isEmpty()) {
                Toast.makeText(getContext(), "Nomes dos Jogadores 1 e 2 não podem estar vazios!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Adiciona J1 e J2 com os nomes e nomes literais das cores traduzidas do Hex
            listaDaPartida.add(new Jogador(n1, converterHexParaNomeCor(corJ1Hex)));
            listaDaPartida.add(new Jogador(n2, converterHexParaNomeCor(corJ2Hex)));

            if (qtdJogadoresAtivos >= 3) {
                if (n3.isEmpty()) {
                    Toast.makeText(getContext(), "Nome do Jogador 3 não pode ser vazio!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listaDaPartida.add(new Jogador(n3, converterHexParaNomeCor(corJ3Hex)));
            }

            if (qtdJogadoresAtivos == 4) {
                if (n4.isEmpty()) {
                    Toast.makeText(getContext(), "Nome do Jogador 4 não pode ser vazio!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listaDaPartida.add(new Jogador(n4, converterHexParaNomeCor(corJ4Hex)));
            }

            if (switchDadoVirtual != null) {
                GameManager.getInstance().setUsarDadoVirtual(switchDadoVirtual.isChecked());
            }

            // 1. Salva a partida globalmente na memória
            GameManager.getInstance().iniciarNovaPartida(listaDaPartida);

            // 2. 🚀 EM VEZ DE IR PRO TABULEIRO, MOSTRA O POP-UP VERMELHO DE TENSÃO AQUI!
            PreparadosDialogFragment popup = new PreparadosDialogFragment();
            popup.show(getParentFragmentManager(), "PreparadosPopUp");
        });

        return view;
    }

    // 🪄 Métodos auxiliares de áudio rápidos
    private void tocarClique() {
        if (soundPool != null) soundPool.play(somClique, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    private void tocarPop() {
        if (soundPool != null) soundPool.play(somPop, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    // 🪄 Método auxiliar para invocar o pop-up passando a interface de escuta
    private void abrirPopUpEdicao(String nomeAtual, String corAtualHex, EditarJogadorDialogFragment.OnJogadorEditadoListener listener) {
        EditarJogadorDialogFragment dialog = EditarJogadorDialogFragment.newInstance(nomeAtual, corAtualHex, listener);
        dialog.show(getParentFragmentManager(), "EditarJogadorDialog");
    }

    // 🎨 Traduz o código hexadecimal de volta para a String literal que sua classe Jogador espera
    private String converterHexParaNomeCor(String hex) {
        switch (hex) {
            case "#FF2E2E": return "Vermelho";
            case "#2E7DFF": return "Azul";
            case "#2EFF3A": return "Verde";
            case "#FFD42E": return "Amarelo";
            default: return "Vermelho";
        }
    }

    // 🔄 Controla a exibição das linhas dos cards na tela de seleção
    private void ajustarVisibilidadeCampos(int quantidade) {
        this.qtdJogadoresAtivos = quantidade;

        // 1. ESTADO APAGADO: Força todos os botões seletores do topo a ficarem com um cinza fraco
        String cinzaFraco = "#CCCCCC";
        if (btnCount2 != null) btnCount2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(cinzaFraco)));
        if (btnCount3 != null) btnCount3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(cinzaFraco)));
        if (btnCount4 != null) btnCount4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(cinzaFraco)));

        // 2. ESTADO ACESO: Acende em verde apenas o botão do número que foi selecionado
        if (quantidade > 0) {
            int corVerde = getResources().getColor(R.color.green_button);
            if (quantidade == 2 && btnCount2 != null) {
                btnCount2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(corVerde));
            } else if (quantidade == 3 && btnCount3 != null) {
                btnCount3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(corVerde));
            } else if (quantidade == 4 && btnCount4 != null) {
                btnCount4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(corVerde));
            }
        }

        // 3. ATIVAÇÃO DINÂMICA DOS CARDS (Oculta ou exibe baseado no número)
        if (getView() != null) {
            View linhaInferior = getView().findViewById(R.id.linhaInferiorCards);

            if (quantidade == 2) {
                if (linhaInferior != null) linhaInferior.setVisibility(View.GONE);
                if (cardJogador3 != null) { cardJogador3.setVisibility(View.GONE); cardJogador3.setClickable(false); }
                if (cardJogador4 != null) { cardJogador4.setVisibility(View.GONE); cardJogador4.setClickable(false); }

            } else if (quantidade == 3) {
                if (linhaInferior != null) linhaInferior.setVisibility(View.VISIBLE);
                if (cardJogador3 != null) { cardJogador3.setVisibility(View.VISIBLE); cardJogador3.setClickable(true); }
                if (cardJogador4 != null) { cardJogador4.setVisibility(View.INVISIBLE); cardJogador4.setClickable(false); }

            } else if (quantidade == 4) {
                if (linhaInferior != null) linhaInferior.setVisibility(View.VISIBLE);
                if (cardJogador3 != null) { cardJogador3.setVisibility(View.VISIBLE); cardJogador3.setClickable(true); }
                if (cardJogador4 != null) { cardJogador4.setVisibility(View.VISIBLE); cardJogador4.setClickable(true); }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}