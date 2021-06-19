package com.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity @IdClass(value = UserActivity.class)
@Table(name = "user_activity")
public class UserActivity implements Serializable {
    @Id
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;
    @Column(name = "activity_date")
    private LocalDate activityDate;
    @Column(name = "activity_duration")
    private Integer activityDuration;


    public UserActivity() {
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

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public Integer getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(Integer activityDuration) {
        this.activityDuration = activityDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserActivity)) return false;
        UserActivity that = (UserActivity) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(activity, that.activity) &&
                Objects.equals(activityDate, that.activityDate) &&
                Objects.equals(activityDuration, that.activityDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, activity, activityDate, activityDuration);
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "user=" + user +
                ", activity=" + activity +
                ", activityDate=" + activityDate +
                ", activityDuration=" + activityDuration +
                '}';
    }
}