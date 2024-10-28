package smartosc.fresher.connectmysql.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;

    @Transient
    private List<GroupMember> groupMembers;

    public static class ROLE {
        private ROLE() {
        }

        public static final String USER_READ = "user:read";
        public static final String ADMIN_READ = "admin:read";
        public static final String ADMIN_CREATE = "admin:create";
        public static final String ADMIN_UPDATE = "admin:update";
        public static final String SUPER_ADMIN_READ = "super_admin:read";
        public static final String SUPER_ADMIN_CREATE = "super_admin:create";
        public static final String SUPER_ADMIN_UPDATE = "super_admin:update";
        public static final String SUPER_ADMIN_DELETE = "super_admin:delete";
    }
}
