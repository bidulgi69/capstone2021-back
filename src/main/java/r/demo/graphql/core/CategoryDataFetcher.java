package r.demo.graphql.core;

import graphql.schema.DataFetcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import r.demo.graphql.annotation.Gql;
import r.demo.graphql.annotation.GqlDataFetcher;
import r.demo.graphql.annotation.GqlType;
import r.demo.graphql.domain.category.Category;
import r.demo.graphql.domain.category.CategoryRepo;

@Gql
@Service
public class CategoryDataFetcher {
    private final CategoryRepo categoryRepo;

    public CategoryDataFetcher(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GqlDataFetcher(type = GqlType.QUERY)
    public DataFetcher<?> categories() {
        return environment -> categoryRepo.findAll();
    }

    @GqlDataFetcher(type = GqlType.MUTATION)
    public DataFetcher<?> createCategory() {
        return environment -> {
            try {
                String title = environment.getArgument("title");
                if (categoryRepo.existsByName(title)) throw new RuntimeException();
                else {
                    categoryRepo.save(Category.builder().name(title).build());
                }
                return HttpStatus.OK.value();
            } catch (RuntimeException e) {
                return HttpStatus.CONFLICT.value();
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
        };
    }
}
