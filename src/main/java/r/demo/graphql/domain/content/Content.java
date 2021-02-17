package r.demo.graphql.domain.content;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import r.demo.graphql.domain.category.Category;
import r.demo.graphql.domain.user.UserInfo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(schema = "demo", name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = { CascadeType.DETACH })
    @JoinTable(name = "category_contents",
            joinColumns = @JoinColumn(name = "contents_idx", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_idx", referencedColumnName = "id"))
    private final Set<Category> category = new HashSet<>();

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "registerer", referencedColumnName = "id")
    private UserInfo registerer;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private java.util.Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private java.util.Date modified;

    @Builder
    public Content(String title, UserInfo user, Set<Category> categories) {
        this.title = title;
        this.registerer = user;
        this.category.addAll(categories);
    }
}
