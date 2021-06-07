package kz.spring.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSequenceGenerator")
    @SequenceGenerator(name = "roleSequenceGenerator", sequenceName = "roleSequence", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles" , fetch = FetchType.EAGER)
    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}