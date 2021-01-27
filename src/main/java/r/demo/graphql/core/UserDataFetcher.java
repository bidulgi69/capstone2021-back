package r.demo.graphql.core;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Service;
import r.demo.graphql.annotation.Gql;
import r.demo.graphql.annotation.GqlDataFetcher;
import r.demo.graphql.annotation.GqlType;
import r.demo.graphql.domain.user.UserInfoRepo;

@Gql
@Service
public class UserDataFetcher {
    private final UserInfoRepo userRepo;

    public UserDataFetcher(UserInfoRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GqlDataFetcher(type = GqlType.QUERY)
    public DataFetcher<?> allUsers() {
        return environment -> userRepo.findAll();
    }

    @GqlDataFetcher(type = GqlType.QUERY)
    public DataFetcher<?> user() {
        return environment -> userRepo.findById(environment.getArgument("id"));
    }
}
