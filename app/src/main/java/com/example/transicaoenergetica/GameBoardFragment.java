package com.example.transicaoenergetica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class GameBoardFragment extends Fragment {

    private Button btnConcluirTurno, btnGoToRanking, btnEventSorte, btnEventEscolha;
    private LinearLayout llPlayersContainer;

    // 🔊 Variáveis para o controle de efeitos sonoros curtos
    private SoundPool soundPool;
    private int somDado, somRoleta, somWin, somFail;

    public GameBoardFragment() {
        // Obrigatório
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_board, container, false);

        // 🔊 CONFIGURAÇÃO DO SOUNDPOOL (Carrega os efeitos leves na memória do celular)
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(attrs)
                .build();

        // Carrega os arquivos .mp3 salvos na pasta res/raw
        somDado = soundPool.load(getContext(), R.raw.dado, 1);
        somRoleta = soundPool.load(getContext(), R.raw.roleta, 1);
        somWin = soundPool.load(getContext(), R.raw.win, 1);
        somFail = soundPool.load(getContext(), R.raw.fail, 1);

        llPlayersContainer = view.findViewById(R.id.llPlayersContainer);
        btnConcluirTurno = view.findViewById(R.id.btnRollDice);
        btnGoToRanking = view.findViewById(R.id.btnGoToRanking);
        btnEventSorte = view.findViewById(R.id.btnEventSorte);
        btnEventEscolha = view.findViewById(R.id.btnEventEscolha);

        btnConcluirTurno.setText("CONCLUIR TURNO SEM EVENTO");

        View btnMoreInfo = view.findViewById(R.id.btnConfigTabuleiro);
        if (btnMoreInfo != null) {
            btnMoreInfo.setOnClickListener(v -> {
                MoreInfoDialogFragment dialog = new MoreInfoDialogFragment();
                dialog.show(getParentFragmentManager(), "MoreInfoDialog");
            });
        }

        // Atualiza a lista na tela
        atualizarPlacar();

        // Dispara o primeiro Pop-up de turno assim que a tela carrega
        view.post(this::chamarPopUpTurno);

        // Se o jogador não caiu em nenhuma pergunta, ele clica para passar a vez manualmente
        btnConcluirTurno.setOnClickListener(v -> proximoTurno());

        btnEventSorte.setOnClickListener(v -> {
            DesafioDialogFragment dialog = DesafioDialogFragment.newInstance("SORTE");
            dialog.show(getParentFragmentManager(), "DesafioSorte");
        });

        btnEventEscolha.setOnClickListener(v -> {
            DesafioDialogFragment dialog = DesafioDialogFragment.newInstance("ESCOLHA");
            dialog.show(getParentFragmentManager(), "DesafioEscolha");
        });

        btnGoToRanking.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RankingFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 🎰 ABA DA ROLETA: Apenas abre o diálogo (O som foi movido para o método público)
        Button btnTab2 = view.findViewById(R.id.btnTab2);
        btnTab2.setOnClickListener(v -> {
            DialogRoletaFragment dialogRoleta = new DialogRoletaFragment();
            dialogRoleta.show(getParentFragmentManager(), "PopUpRoleta");
        });

        // 📊 ABA DO PLACAR
        Button btnTab1 = view.findViewById(R.id.btnTab1);
        btnTab1.setOnClickListener(v -> {
            PlacarDialogFragment dialogPlacar = new PlacarDialogFragment();
            dialogPlacar.show(getParentFragmentManager(), "PlacarDialogFragment");
        });

        return view;
    }

    private void chamarPopUpTurno() {
        DialogTurnoFragment dialogTurno = DialogTurnoFragment.newInstance(() -> {
            // Callback padrão mantido para liberar ações do tabuleiro após fechar o turno
        });
        dialogTurno.show(getParentFragmentManager(), "PopUpTurno");
    }

    public void proximoTurno() {
        GameManager.getInstance().avancarTurno();
        atualizarPlacar();
        chamarPopUpTurno();
    }

    public void atualizarPlacar() {
        llPlayersContainer.removeAllViews();
        List<Jogador> jogadores = GameManager.getInstance().getListaJogadores();
        int indexJogadorAtual = GameManager.getInstance().getJogadorAtualIndex();

        if (jogadores != null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            for (int i = 0; i < jogadores.size(); i++) {
                Jogador jogador = jogadores.get(i);
                View cardView = inflater.inflate(R.layout.item_jogador_placar, llPlayersContainer, false);

                TextView tvNome = cardView.findViewById(R.id.tvNomeJogadorPlacar);
                TextView tvRecursos = cardView.findViewById(R.id.tvRecursosJogadorPlacar);

                String emoji = "⚪ ";
                if (jogador.getCorPeao().equalsIgnoreCase("Vermelho")) emoji = "🔴 ";
                else if (jogador.getCorPeao().equalsIgnoreCase("Azul")) emoji = "🔵 ";
                else if (jogador.getCorPeao().equalsIgnoreCase("Amarelo")) emoji = "🟡 ";
                else if (jogador.getCorPeao().equalsIgnoreCase("Verde")) emoji = "🟢 ";

                if (i == indexJogadorAtual) {
                    tvNome.setText(emoji + jogador.getNome() + " ⭐ (Sua Vez)");
                    cardView.setBackgroundColor(Color.parseColor("#E0F2F1"));
                } else {
                    tvNome.setText(emoji + jogador.getNome());
                    cardView.setBackgroundColor(Color.parseColor("#F5F5F5"));
                }

                tvRecursos.setText("Moedas: " + jogador.getMoedas() +
                        "  |  Energia: " + Math.max(0, jogador.getEnergia()) +
                        "  |  Sust: " + jogador.getSustentabilidade() +
                        "  |  Poluição: " + jogador.getPoluicao());

                llPlayersContainer.addView(cardView);
            }
        }
    }

    // 🎲 MÉTODOS PÚBLICOS DE ÁUDIO ATUALIZADOS: Agora eles respeitam as configurações da MainActivity!

    public void tocarSomDado() {
        if (getActivity() instanceof MainActivity && !((MainActivity) getActivity()).isEfeitosAtivados()) return;

        if (soundPool != null) {
            soundPool.play(somDado, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public void tocarSomRoleta() {
        if (getActivity() instanceof MainActivity && !((MainActivity) getActivity()).isEfeitosAtivados()) return;

        if (soundPool != null) {
            soundPool.play(somRoleta, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public void tocarSomWin() {
        if (getActivity() instanceof MainActivity && !((MainActivity) getActivity()).isEfeitosAtivados()) return;

        if (soundPool != null) {
            soundPool.play(somWin, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public void tocarSomFail() {
        if (getActivity() instanceof MainActivity && !((MainActivity) getActivity()).isEfeitosAtivados()) return;

        if (soundPool != null) {
            soundPool.play(somFail, 1.0f, 1.0f, 1, 0, 1.0f);
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