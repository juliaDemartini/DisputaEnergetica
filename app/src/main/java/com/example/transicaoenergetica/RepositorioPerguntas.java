package com.example.transicaoenergetica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioPerguntas {

    private static List<Pergunta> listaMaster;

    static {
        listaMaster = new ArrayList<>();

        // --- PERGUNTAS DE SORTE (Técnicas / Desafios) ---
        listaMaster.add(new Pergunta("SORTE", "Qual dessas fontes de energia não emite gases de efeito estufa na geração?", "Carvão", "Eólica", "Gás Natural", "Petróleo", "B", 1, 0));
        listaMaster.add(new Pergunta("SORTE", "O que significa a sigla GNV?", "Gás Natural Veicular", "Gasolina Nova Voltagem", "Gerador de Núcleo Volátil", "Gás Nitro-Vegetal", "A", 0, 1));
        listaMaster.add(new Pergunta("SORTE", "Qual o principal componente do biogás produzido em aterros?", "Oxigênio", "Metano", "Nitrogênio", "Hélio", "B", 1, 0));
        listaMaster.add(new Pergunta("SORTE", "Qual país lidera a produção mundial de painéis solares?", "Brasil", "China", "Egito", "Austrália", "B", 0, 1));
        listaMaster.add(new Pergunta("SORTE", "A energia geotérmica é gerada a partir de qual fonte?", "Vento do litoral", "Calor interno da Terra", "Ondas do mar", "Queima de cana", "B", 1, 0));
        listaMaster.add(new Pergunta("SORTE", "Qual destas é considerada uma fonte de energia limpa e intermitente?", "Nuclear", "Carvão", "Solar", "Termelétrica", "C", 1, 0));
        listaMaster.add(new Pergunta("SORTE", "O descarte incorreto de baterias de lítio causa qual impacto?", "Nenhum", "Poluição por metais pesados", "Aumento de oxigênio", "Reflorestamento", "B", -1, -1));
        listaMaster.add(new Pergunta("SORTE", "Qual elemento é usado como combustível nas usinas nucleares tradicionais?", "Carvão", "Urânio", "Hidrogênio", "Sódio", "B", 0, 1));
        listaMaster.add(new Pergunta("SORTE", "O que é biomassa?", "Massa de peixes", "Matéria orgânica para energia", "Poeira de mineração", "Gases estofados", "B", 1, 0));
        listaMaster.add(new Pergunta("SORTE", "Qual usina depende diretamente do ciclo de chuvas?", "Eólica", "Hidrelétrica", "Solar", "Geotérmica", "B", 0, 0));
        listaMaster.add(new Pergunta("SORTE", "O que o indicador pegada de carbono mede?", "Consumo de água", "Emissão de gases estufa", "Uso de solo", "Gasto de energia elétrica", "B", 0, 0));
        listaMaster.add(new Pergunta("SORTE", "Qual veículo polui menos os centros urbanos?", "Carro a Diesel", "Carro Elétrico", "Moto a Gasolina", "Caminhão de Carga", "B", 1, -1));
        listaMaster.add(new Pergunta("SORTE", "Qual setor econômico historicamente mais emite CO2?", "Artesanato", "Energia e Transportes", "Educação", "Turismo", "B", -1, 0));
        listaMaster.add(new Pergunta("SORTE", "As usinas maremotrizes usam qual recurso?", "Força das marés", "Calor do sol", "Queima de lixo", "Vento das montanhas", "A", 1, 0));
        listaMaster.add(new Pergunta("SORTE", "Qual lâmpada é mais eficiente e sustentável?", "Incandescente", "Fluorescente", "LED", "Halógena", "C", 1, 1));
        listaMaster.add(new Pergunta("SORTE", "Qual gás é o maior vilão do efeito estufa em volume?", "Dióxido de Carbono (CO2)", "Argônio", "Hélio", "Criptônio", "A", -1, 0));
        listaMaster.add(new Pergunta("SORTE", "O hidrogênio verde é obtido através de qual processo?", "Queima de carvão", "Eletrolise com energia limpa", "Fraturamento hidráulico", "Destilação de petróleo", "B", 2, -1));
        listaMaster.add(new Pergunta("SORTE", "Qual destas fontes causa maior impacto local por alagamento?", "Solar", "Hidrelétrica", "Eólica", "Biogás", "B", -1, 1));
        listaMaster.add(new Pergunta("SORTE", "O que são combustíveis fósseis?", "Recursos renováveis rápidos", "Recursos finitos gerados há milhões de anos", "Energia vinda de plantas novas", "Energia magnética", "B", -1, 1));
        listaMaster.add(new Pergunta("SORTE", "Qual a principal vantagem da energia eólica?", "Funciona sem vento", "Não emite poluentes na operação", "Ocupa espaço zero", "É barata para instalar no quintal", "B", 1, 0));

        // --- DILEMAS DE ESCOLHA (Tomada de Decisão Econômica vs Ambiental) ---
        listaMaster.add(new Pergunta("ESCOLHA", "Investir em filtros industriais na sua fábrica?", "Sim (Gasta 2 Moedas, -1 Poluição)", "Não (Ganha 1 Moeda, +2 Poluição)", "", "", "A", 1, -2));
        listaMaster.add(new Pergunta("ESCOLHA", "Substituir frotas antigas por veículos elétricos?", "Sim (Gasta 3 Moedas, +2 Sustentabilidade)", "Não (Mantém moedas, +1 Poluição)", "", "", "A", 2, -3));
        listaMaster.add(new Pergunta("ESCOLHA", "Instalar painéis solares na sede da empresa?", "Sim (Gasta 2 Moedas, +2 Energia)", "Não (Economiza moedas, continua dependente)", "", "", "A", 1, -2));
        listaMaster.add(new Pergunta("ESCOLHA", "Patrocinar uma ONG de reflorestamento?", "Sim (Gasta 1 Moeda, +2 Sustentabilidade)", "Não (Guarda dinheiro)", "", "", "A", 2, -1));
        listaMaster.add(new Pergunta("ESCOLHA", "Modernizar a rede elétrica para Smart Grids?", "Sim (Gasta 3 Moedas, +2 Energia)", "Não (Rede antiga falha, perca 1 energia)", "", "", "A", 1, -3));
        listaMaster.add(new Pergunta("ESCOLHA", "Fazer auditoria ambiental voluntária?", "Sim (Gasta 1 Moeda, ganha selo verde +1 Sust.)", "Não (Risco de denúncia futura)", "", "", "A", 1, -1));
        listaMaster.add(new Pergunta("ESCOLHA", "Comprar créditos de carbono de terceiros?", "Sim (Gasta 2 Moedas, limpa sua barra de poluição)", "Não (Investe em marketing tradicional)", "", "", "A", 0, -2));
        listaMaster.add(new Pergunta("ESCOLHA", "Trocar maquinário pesado por eficiência energética?", "Sim (Gasta 3 Moedas, reduz consumo de energia)", "Não (Consumo alto drena moedas aos poucos)", "", "", "A", 1, -3));
        listaMaster.add(new Pergunta("ESCOLHA", "Implementar reciclagem pesada nos escritórios?", "Sim (Gasta 1 Moeda, +1 Sustentabilidade)", "Não (Lixo vai para aterro comum)", "", "", "A", 1, -1));
        listaMaster.add(new Pergunta("ESCOLHA", "Trocar fornecedor de energia por cooperativa eólica?", "Sim (Taxa adesão 1 moeda, ganha bônus verde)", "Não (Continua no mercado fóssil)", "", "", "A", 1, -1));
        listaMaster.add(new Pergunta("ESCOLHA", "Pesquisar biocombustíveis de segunda geração?", "Sim (Gasta 2 Moedas, chance de inovação)", "Não (Foca no feijão com arroz corporativo)", "", "", "A", 2, -2));
        listaMaster.add(new Pergunta("ESCOLHA", "Instalar sensores de iluminação inteligente?", "Sim (Gasta 1 Moeda, economiza energia no futuro)", "Não (Deixa luzes acesas direto)", "", "", "A", 1, -1));
        listaMaster.add(new Pergunta("ESCOLHA", "Trocar ar-condicionado central por modelos inverter?", "Sim (Gasta 2 Moedas, +1 Energia)", "Não (Sua conta de luz continuará alta)", "", "", "A", 1, -2));
        listaMaster.add(new Pergunta("ESCOLHA", "Criar um programa de caronas solidárias na empresa?", "Sim (Gasta 1 Moeda em incentivos, reduz pegada)", "Não (Cada um vai com seu carro)", "", "", "A", 1, -1));
        listaMaster.add(new Pergunta("ESCOLHA", "Adotar embalagens biodegradáveis na sua linha?", "Sim (Custo de produção +2 Moedas, +2 Sust.)", "Não (Plástico comum segue destruindo)", "", "", "A", 2, -2));

        // --- EVENTOS GLOBAIS (Impacto Coletivo) ---
        listaMaster.add(new Pergunta("GLOBAL", "Acordo de Paris aprovado: Metas rígidas para todos os países mundiais.", "Todos ganham +1 Sustentabilidade", "", "", "", "A", 1, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Crise do Petróleo: Combustíveis fósseis batem recorde histórico de preço.", "Todos perdem 1 Moeda", "", "", "", "A", 0, -1));
        listaMaster.add(new Pergunta("GLOBAL", "Incentivo Verde Federal: Subsídios para transição energética liberados.", "Todos ganham +1 Energia", "", "", "", "A", 0, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Seca Extrema Mundial: Nível dos reservatórios de água despenca.", "Todos perdem 1 de Energia", "", "", "", "A", 0, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Conferência do Clima da ONU: Exigência de metas de descarbonização rápidas.", "Quem tiver mais Poluição perde 2 Moedas", "", "", "", "A", 0, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Descoberta Científica: Nova eficiência em células de silício solares.", "Todos ganham +1 Sustentabilidade", "", "", "", "A", 1, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Onda de Calor Global: Uso massivo de ar-condicionado sobrecarrega redes.", "Todos perdem 1 de Energia", "", "", "", "A", 0, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Imposto de Carbono Criado: Poluir agora gera multas governamentais caras.", "Todos com Poluição > 2 perdem 2 Moedas", "", "", "", "A", 0, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Dia Mundial do Meio Ambiente: Campanhas de conscientização massivas.", "Todos ganham +1 Sustentabilidade", "", "", "", "A", 1, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Rompimento de Oleoduto: Desastre ecológico de proporções internacionais.", "Aumenta +1 de Poluição geral da mesa", "", "", "", "A", -1, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Cúpula Extraordinária sobre Clima: Cobrança severa entre blocos econômicos.", "Todos pagam 1 Moeda de taxa de transição", "", "", "", "A", 0, -1));
        listaMaster.add(new Pergunta("GLOBAL", "Avanço na Fusão Nuclear: Energia limpa ilimitada dá seus primeiros passos.", "Todos ganham +2 Energia", "", "", "", "A", 0, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Microplásticos em Alta: Crise severa na cadeia alimentar marítima.", "Todos perdem 1 de Sustentabilidade", "", "", "", "A", -1, 0));
        listaMaster.add(new Pergunta("GLOBAL", "Festival Eco-Tech: Parcerias corporativas e comerciais exponenciais.", "Todos ganham 1 Moeda", "", "", "", "A", 0, 1));
        listaMaster.add(new Pergunta("GLOBAL", "Tratado de Descarbonização Marítima: Navios cargueiros mudam combustível.", "Todos ganham +1 Sustentabilidade", "", "", "", "A", 1, 0));
    }

    // Método principal para o jogo sortear uma pergunta sem repetir
    public static Pergunta sortearPerguntaPorTipo(String tipo) {
        List<Pergunta> filtradas = new ArrayList<>();
        for (Pergunta p : listaMaster) {
            if (p.getTipo().equalsIgnoreCase(tipo)) {
                filtradas.add(p);
            }
        }
        if (filtradas.isEmpty()) return null;

        // Embaralha as perguntas filtradas e pega a primeira da lista (Sorteio justo)
        Collections.shuffle(filtradas);
        return filtradas.get(0);
    }
}