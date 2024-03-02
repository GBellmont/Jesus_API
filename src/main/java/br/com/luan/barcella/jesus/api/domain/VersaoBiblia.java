package br.com.luan.barcella.jesus.api.domain;

import static java.util.Arrays.stream;

import java.util.Arrays;

import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoesResponse.VersaoResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum VersaoBiblia {

    ACF("Almeida Corrigida Fiel", "acf", "A versão Almeida Corrigida Fiel | acf (revisado segundo o novo acordo ortográfico da língua portuguesa em 2011) é uma tradução fiel do Velho Testamento em Hebraico (Massorético) e do Novo Testamento em Grego (Textus Receptus), segundo o método de tradução formal (traduzindo cada palavra e mantendo a beleza linguística)."),
    APEE("La Bible de l'épée", "apee", "La première édition de la Bible de l’Épée remonte à 1540.  Elle fut l’œuvre du grand Réformateur français, Jean Calvin, qui révisa la Bible Vaudoise de son cousin, Pierre Robert Olivetan, qu’il publia en 1535 et 1537.  Cette révision de Calvin fut imprimée chez Jean Gérard sans autre caractère distinctif que la représentation d’un glaive sur la page titre.  De là le nom sous lequel elle est connue comme Bible de l’Épée.  Après la mort tragique d’Olivetan, qui fut empoisonné a Rome en 1538, Calvin devint le fidèle administrateur de l’héritage spirituel laissé par son cousin Vaudois à l’Église de Jésus-Christ.  Outre l’édition de 1540, celles de 1551 et de 1560, se distinguent par les améliorations apportées par sa main savante, soit à la traduction, soit aux notes marginales.  Théodore de Bèze, son collègue à Genève, lui rend ce témoignage { qu’il a diligemment travaillé en la translation française de toute la Bible…  qu’il l’a souventes fois, amendée en quelques passages…  et nommément, celle du Nouveau Testament, laquelle il a fréquemment revue et conférée avec le texte grec ( le Texte Reçu), autant soigneusement que lui ont permis les continuelles occupations de son office ."),
    BBE("Bíblia Brasileira de Estudo", "bbe", "A BBE foi inteiramente escrita por autores nacionais e dirigida ao povo brasileiro que vive a realidade singular do nosso país e da nossa cultura. A obra menciona personalidades que marcam a história do Brasil. Cita, por exemplo, o amor de Dom Pedro II pela Palavra e faz referência a poemas célebres de Castro Alves e Manoel Bandeira."),
    KJA("King James Version", "kjv", "A bíblia King James possui uma das traduções mais fiéis aos textos hebraicos e gregos, os quais foram escritos centenas de anos atrás. O trabalho de tradução dessa versão bíblica começou em 1603, sendo finalizada e publicada em 1611. Muitas outras foram baseadas na King James, por se tratar de uma obra confiável e, então, ser uma ótima opção de embasamento."),
    NVI("Nova Versão Internacional", "nvi", "A Nova Versão Internacional (NVI) é uma tradução ou versão evangélica da Bíblia em português de 2001, traduzida pela International Bible Society, atualmente chamada \"Biblica Brasil\", e publicada pela Editora Vida.[1] Portanto, a NVI em português, embora inspirada na NIV em inglês, não se trata de uma mera tradução da sua irmã anglófona para o português, isto é, uma tradução da tradução, mas uma nova tradução diretamente dos idiomas originais da Bíblia (hebraico, aramaico e grego)."),
    RA("Almeida Revista e Atualizada", "ra", "A Almeida Revista e Atualizada (RA) foi traduzida por João Ferreira de Almeida e é conhecida como a Edição Revista e Atualizada (1959, 1993). Ela conserva as características principais da tradução de equivalência formal de Almeida, sendo o resultado de mais de uma década de revisão e atualização teológica e linguística da Edição Revista e Corrigida."),
    RVR("Reina-Valera", "rvr", "La conocida versión de la Biblia llamada Reina-Valera, que alcanzó una amplia difusión durante la Reforma Protestante del siglo XVI, representa la primera traducción castellana completa, directa y literal de los textos bíblicos en griego, hebreo y arameo, y debe su nombre a la suma de esfuerzos de Casiodoro de Reina, su autor principal, materializado en la Biblia del Oso (Basilea, Suiza, 1569), y de Cipriano de Valera, su primer revisor, materializado en la Biblia del Lanzador (Ámsterdam, Países Bajos, 1602).");

    private final String nome;
    private final String codigoBibliaDigital;
    private final String descricao;

    public static VersaoBiblia fromCodigoBibliaDigital(final String codigo) {
        return stream(VersaoBiblia.values())
            .filter(versao -> versao.codigoBibliaDigital.equals(codigo))
            .findFirst()
            .orElse(null);
    }
}
