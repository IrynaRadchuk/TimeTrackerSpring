package com.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity @IdClass(value = AllowedActivity.class)
@Table(name = "user_allowed_activity")
public class AllowedActivity implements Serializable {
    @Id
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public AllowedActivity() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllowedActivity)) return false;
        AllowedActivity that = (AllowedActivity) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(activity, that.activity) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, activity, status);
    }
}
