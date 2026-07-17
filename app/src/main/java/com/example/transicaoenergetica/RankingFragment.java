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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingFragment extends Fragment {

    private LinearLayout llRankingContainer;
    private Button btnVoltarMenu, btnFinalizarJogo;

    public RankingFragment() {
        // Obrigatório
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        llRankingContainer = view.findViewById(R.id.llRankingContainer);
        btnVoltarMenu = view.findViewById(R.id.btnVoltarMenu);
        btnFinalizarJogo = view.findViewById(R.id.btnFinalizarJogo);

        exibirRankingGeral();

        // ↩️ BOTÃO VOLTAR: Apenas remove o Ranking da tela e volta para o Tabuleiro
        if (btnVoltarMenu != null) {
            btnVoltarMenu.setOnClickListener(v -> {
                if (getParentFragmentManager() != null) {
                    getParentFragmentManager().popBackStack(); // Volta para o Tabuleiro
                }
            });
        }

        // 🛑 BOTÃO FINALIZAR MODIFICADO: Pergunta se tem certeza e vai para a tela do vencedor
        if (btnFinalizarJogo != null) {
            btnFinalizarJogo.setOnClickListener(v -> {

                new androidx.appcompat.app.AlertDialog.Builder(getContext())
                        .setTitle("Encerrar Partida")
                        .setMessage("Você tem certeza que deseja finalizar o jogo agora?")
                        .setPositiveButton("Sim, Finalizar", (dialog, which) -> {

                            // 🔍 Captura quem está em primeiro lugar para mandar para a tela final
                            List<Jogador> listaOrdenada = new ArrayList<>(GameManager.getInstance().getListaJogadores());

                            // Ordena do maior para o menor score (igual você fez abaixo)
                            Collections.sort(listaOrdenada, (j1, j2) -> Integer.compare(j2.getPontuacaoTotal(), j1.getPontuacaoTotal()));

                            String nomeGanhador = "Nenhum Jogador";
                            String corGanhadorHex = "#FFFFFF"; // Cor padrão caso a lista esteja vazia

                            if (!listaOrdenada.isEmpty()) {
                                Jogador campeao = listaOrdenada.get(0); // O primeiro da lista ordenada é o campeão!
                                nomeGanhador = campeao.getNome();

                                // Traduz a String de cor literal dele de volta para Hexadecimal para pintar o troféu
                                corGanhadorHex = converterNomeCorParaHex(campeao.getCorPeao());
                            }

                            // Abre a tela cheia do vencedor passando o campeão real
                            if (getParentFragmentManager() != null) {
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, VencedorFragment.newInstance(nomeGanhador, corGanhadorHex))
                                        .commit();
                            }
                        })
                        .setNegativeButton("Continuar Jogando", null)
                        .show();
            });
        }

        return view;
    }

    private void exibirRankingGeral() {
        if (llRankingContainer == null) return;
        llRankingContainer.removeAllViews();

        List<Jogador> listaOrdenada = new ArrayList<>(GameManager.getInstance().getListaJogadores());
        Collections.sort(listaOrdenada, (j1, j2) -> Integer.compare(j2.getPontuacaoTotal(), j1.getPontuacaoTotal()));

        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (int i = 0; i < listaOrdenada.size(); i++) {
            Jogador j = listaOrdenada.get(i);
            View itemView = inflater.inflate(R.layout.item_jogador_ranking, llRankingContainer, false);

            TextView tvPosicao = itemView.findViewById(R.id.tvPosicaoRanking);
            TextView tvNome = itemView.findViewById(R.id.tvNomeRanking);
            TextView tvPoints = itemView.findViewById(R.id.tvPontosRanking);

            String posicaoTexto = (i + 1) + "º";
            if (i == 0) posicaoTexto = "🥇 1º";
            else if (i == 1) posicaoTexto = "🥈 2º";
            else if (i == 2) posicaoTexto = "🥉 3º";

            if (tvPosicao != null) tvPosicao.setText(posicaoTexto);
            if (tvNome != null) tvNome.setText(j.getNome() + " (" + j.getCorPeao() + ")");
            if (tvPoints != null) tvPoints.setText(j.getPontuacaoTotal() + " pts");

            llRankingContainer.addView(itemView);
        }
    }

    // 🎨 Método auxiliar para reverter o nome literal da cor para o Hex correspondente da tela final
    private String converterNomeCorParaHex(String nomeCor) {
        if (nomeCor == null) return "#FF2E2E";
        switch (nomeCor) {
            case "Vermelho": return "#FF2E2E";
            case "Azul": return "#2E7DFF";
            case "Verde": return "#2EFF3A";
            case "Amarelo": return "#FFD42E";
            default: return "#FF2E2E";
        }
    }
}