# Disputa Sustentável (Disputa Energética)

> Um companion app Android nativo desenvolvido em Java para atuar como mestre digital em jogos de tabuleiro educativos focados em sustentabilidade e transição energética.

---

## Sobre o Projeto

O **Disputa Sustentável** é um aplicativo mobile criado para integrar o mundo físico ao digital (formato híbrido), servindo de suporte a um jogo de tabuleiro focado na conscientização sobre **Transição Energética**. 

Desenvolvido no contexto do programa **Jovens Líderes pelo Clima**, o app substitui pilhas de cartas e blocos de papel (Eco-Design) por uma interface interativa que gerencia eventos, sorteios e recursos em tempo real. O jogo foi testado e validado diretamente em ambiente escolar.

---

## Mecânicas e Funcionalidades

* **Casa Amarela (Evento Sorte):** Desafios de perguntas e respostas rápidas com foco em energias renováveis e sustentabilidade.
* **Casa Vermelha (Evento Escolha):** Dilemas ambientais e socioeconômicos que exigem tomadas de decisão estratégicas.
* **Casa Roxa (Roleta / Eventos Globais):** Acontecimentos climáticos em grande escala que impactam a pontuação e os recursos de todos os jogadores na mesa simultaneamente.
* **Gestão de Recursos em Tempo Real:** Controle individual de **Moedas**, **Energia**, **Sustentabilidade** e **Poluição** para até 4 participantes.
* **Regra de Vitória Ecológica:** A pontuação final é penalizada pela **Poluição** acumulada, incentivando os jogadores a buscarem o crescimento econômico com o menor impacto ambiental possível.

---

## Tecnologias Utilizadas

* **Linguagem:** Java (Android Nativo)
* **Ambiente de Desenvolvimento:** Android Studio
* **Estrutura de Dados Local:** Gerenciamento de perguntas, eventos e dilemas estruturado diretamente em memória com POJOs e coleções nativas (`ArrayList`), garantindo execução 100% offline e sem dependência de internet ou servidores.
* **Persistência de Preferências:** `SharedPreferences` para salvar preferências do usuário e configurações de áudio no aparelho.
* **Efeitos Sonoros:** `SoundPool` para carregamento e execução de áudio com baixa latência.
* **Animações:** `Lottie` para animações fluidas via JSON sem sobrecarregar o tamanho do APK.

---

## Pré-requisitos

Antes de começar, certifique-se de ter instalado no seu computador:

1. **[Git](https://git-scm.com/)** instalado para clonar o repositório.
2. **[Android Studio](https://developer.android.com/studio)** (versão Flamingo, Hedgehog ou mais recente recomendada).
3. **Java Development Kit (JDK):** Versão 11 ou 17 (o próprio Android Studio já vem com o JDK embutido).
4. **Android SDK:** Configurado via SDK Manager dentro do Android Studio (Android API 24+ recomendada).
5. **Para testar a execução:**
   * Um **emulador Android (AVD)** configurado no Android Studio, OU
   * Um **celular físico com Android** com a opção de *Depuração USB* ativada nas Opções do Desenvolvedor.

---

## Como Baixar e Executar o Projeto

Siga os passos abaixo para rodar o aplicativo no seu computador:

### 1. Clonar o Repositório
Abra o seu terminal (ou Git Bash) e execute:
```bash
git clone [https://github.com/SEU_USUARIO/disputa-sustentavel.git](https://github.com/SEU_USUARIO/disputa-sustentavel.git)
```

### 2: Abrir e Sincronizar no Android Studio
Abra o Android Studio.

Selecione a opção Open e localize a pasta raiz onde o projeto foi clonado.

Aguarde o Gradle baixar as dependências e finalizar a sincronização dos arquivos (Sync Project with Gradle Files).

### 3: Executar a Aplicação
- Inicie um emulador pelo Device Manager ou conecte seu smartphone Android via cabo USB.
- Selecione o dispositivo ativo no menu superior.
- Clique no botão Run 'app' (ícone verde ▶) ou pressione o atalho Shift + F10.

O app será compilado e iniciado automaticamente na tela do dispositivo.

