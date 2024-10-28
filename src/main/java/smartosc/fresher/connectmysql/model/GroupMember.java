package smartosc.fresher.connectmysql.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "group_members")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Group group;

    @Column(name = "created_by")
    private String createdBy;
}
