package r.demo.graphql.core;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import r.demo.graphql.annotation.Gql;
import r.demo.graphql.annotation.GqlDataFetcher;
import r.demo.graphql.annotation.GqlType;
import r.demo.graphql.domain.CityRepository;

@Component
public class CityDataFetcher {
    private final CityRepository cityRepository;

    public CityDataFetcher(CityRepository repository) {
        this.cityRepository = repository;
    }

    @GqlDataFetcher(type = GqlType.QUERY)
    public DataFetcher<?> allCities() {
        return environment -> cityRepository.findAll();
    }

    @GqlDataFetcher(type = GqlType.QUERY)
    public DataFetcher<?> city() {
        return environment -> cityRepository.findById(environment.getArgument("id"));
    }
}