package r.demo.graphql.core;

import org.springframework.stereotype.Service;
import r.demo.graphql.annotation.Gql;
import r.demo.graphql.domain.content.Content;
import r.demo.graphql.domain.content.ContentRepo;

@Gql
@Service
public class ContentDataFetcher {
    private final ContentRepo contentRepo;

    public ContentDataFetcher(ContentRepo contentRepo) {
        this.contentRepo = contentRepo;
    }


    public boolean deleteContentDetails(long contentKey) {
        try {
            Content content = contentRepo.findById(contentKey).orElseThrow(IndexOutOfBoundsException::new);


            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
