package com.example.transicaoenergetica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class InstrucoesDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_instrucoes, container, false);

        // Configura o fundo transparente para manter os cantos arredondados
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        // 🔍 Inicializa os componentes do XML
        ImageView btnFechar = view.findViewById(R.id.btnFecharInstrucoes);
        Button btnBaixar = view.findViewById(R.id.btnBaixarPdf);
        Button btnManual = view.findViewById(R.id.btnVerManual);

        // ❌ Ação do botão fechar (Ícone X)
        if (btnFechar != null) {
            btnFechar.setOnClickListener(v -> dismiss());
        }

        // 📥 Ação do botão Baixar Tabuleiro (Offline + Menu de Salvamento)
        if (btnBaixar != null) {
            btnBaixar.setOnClickListener(v -> baixarECompartilharOffline("tabuleiro_imprimir.pdf", "Tabuleiro_Transicao_Energetica.pdf"));
        }

        // 📄 Ação do botão Visualizar/Baixar Manual (Offline + Menu de Salvamento)
        if (btnManual != null) {
            btnManual.setOnClickListener(v -> baixarECompartilharOffline("manual.pdf", "Manual_Transicao_Energetica.pdf"));
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

    // 🔥 FUNÇÃO ADAPTADA: Extração interna e compartilhamento nativo 100% offline
    private void baixarECompartilharOffline(String nomeAsset, String nomeArquivoFinal) {
        Context context = getContext();
        if (context == null) return;

        try {
            // 1. Cria um diretório interno seguro para o app extrair o PDF (Privado e livre de erros de escrita)
            File pastaInterna = new File(context.getFilesDir(), "docs_jogo");
            if (!pastaInterna.exists()) {
                pastaInterna.mkdirs();
            }

            File arquivoDestino = new File(pastaInterna, nomeArquivoFinal);

            // 2. Abre o fluxo de cópia lendo o arquivo embutido na pasta assets
            InputStream inputStream = context.getAssets().open(nomeAsset);
            FileOutputStream outputStream = new FileOutputStream(arquivoDestino);

            // 3. Copia os blocos de bytes offline
            byte[] buffer = new byte[1024];
            int bytesLidos;
            while ((bytesLidos = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesLidos);
            }

            // 4. Fecha as conexões de arquivos com segurança
            inputStream.close();
            outputStream.flush();
            outputStream.close();

            // 5. Gera a URI segura através do FileProvider para o Android permitir o compartilhamento
            String providerAuthority = context.getPackageName() + ".provider";
            Uri uriDoArquivo = FileProvider.getUriForFile(context, providerAuthority, arquivoDestino);

            // 6. Monta a Intent de Envio/Salvar do sistema operacional
            Intent intentCompartilhar = new Intent(Intent.ACTION_SEND);
            intentCompartilhar.setType("application/pdf");
            intentCompartilhar.putExtra(Intent.EXTRA_STREAM, uriDoArquivo);
            intentCompartilhar.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // 🚀 Abre a folha de opções nativa do Android na tela do usuário
            startActivity(Intent.createChooser(intentCompartilhar, "Salvar ou Enviar Ficheiro:"));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao processar ficheiro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}