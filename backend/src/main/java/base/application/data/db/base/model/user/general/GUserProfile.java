package base.application.data.db.base.model.user.general;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // createDate and lastmodifieddate
public abstract class GUserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @Column
    protected String email;

    @Column(name = "date_registration")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date dateRegistration;

    @Column(name = "date_change")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date dateChange;

    @Override
    public String toString() {
        return email;
    }

}
