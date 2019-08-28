package base.application.data.db.base.model.user.general;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import base.application.data.db.base.model.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class GUserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = false)
    protected User user;

    @Column
    protected String email;

    @Column(name = "date_registration")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    protected Date dateRegistration;

    @Column(name = "date_change")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateChange;

    @Override
    public String toString() {
        return email;
    }

}
