package com.challenge.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

// create by Hengchao
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "user_uuid")
    private UUID userUUID;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<QuestionsHistory> history = new ArrayList<>();

    @Column(nullable = false, name = "question_occurd")
//    @OneToMany(mappedBy = "questionId", fetch=FetchType.EAGER)
    @ElementCollection
    @JsonIgnore
    private List<Long> occurd = new ArrayList<>();


    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

//    public Long getSiteId() {
//        return siteId;
//    }
//
//    public void setSiteId(Long siteId) {
//        this.siteId = siteId;
//    }

    public List<QuestionsHistory> getHistory() {
        return history;
    }

    public void setHistory(List<QuestionsHistory> history) {
        this.history = history;
    }

    public List<Long> getOccurd() {
        return occurd;
    }

    public void setOccurd(List<Long> occurd) {
        this.occurd = occurd;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userUUID, user.userUUID) &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(updatedAt, user.updatedAt);
//                Objects.equals(siteId, user.siteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userUUID, createdAt, updatedAt);
    }
}
