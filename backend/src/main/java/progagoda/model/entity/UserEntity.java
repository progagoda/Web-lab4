package progagoda.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "users"
)
public class UserEntity implements Serializable {
    @Id
    @Column(
            name = "id"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "users_next_id"
    )
    private Long id;
    @Column(
            name = "login"
    )
    private String login;
    @Column(
            name = "password"
    )
    private byte[] password;
    @Column(
            name = "salt"
    )
    private byte[] salt;

    public UserEntity(String login, byte[] password, byte[] salt) {
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public Long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public byte[] getPassword() {
        return this.password;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public UserEntity() {
    }
}
