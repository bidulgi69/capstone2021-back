package r.demo.graphql.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String population;
}
