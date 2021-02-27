package r.demo.graphql.core;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import graphql.schema.DataFetcher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import r.demo.graphql.annotation.Gql;
import r.demo.graphql.annotation.GqlDataFetcher;
import r.demo.graphql.annotation.GqlType;
import r.demo.graphql.response.ParseResponse;
import r.demo.graphql.types.Paragraph;
import r.demo.graphql.utils.StanfordLemmatizer;

import java.util.*;
import java.util.stream.Collectors;

@Gql
@Service
public class Parser {
    private final StanfordLemmatizer lemmatizer;

    public Parser(@Lazy StanfordLemmatizer lemmatizer) {
        this.lemmatizer = lemmatizer;
    }

    @GqlDataFetcher(type = GqlType.QUERY)
    public DataFetcher<?> parse() {
        return environment -> {
            String captions = environment.getArgument("captions");
            try {
                CoreDocument coreDocument = lemmatizer.parse(captions);
                List<Paragraph> sentences = coreDocument.sentences().stream().map(coreSentence -> new Paragraph(coreSentence.text(), "")).collect(Collectors.toList()),
                        words = coreDocument.tokens().stream()
                                .filter(token -> token.tag().matches("^[A-Z]+$"))
                                .map(token -> new Paragraph(token.originalText(), "")).collect(Collectors.toList());

                return ParseResponse.builder().sentences(sentences).words(words).build();
            } catch (Exception e) {
                List<Paragraph> empty = Collections.emptyList();
                return ParseResponse.builder().sentences(empty).words(empty).build();
            }
        };
    }
}
