package com.example.model.repository;

import com.example.model.AllowedActivity;
import com.example.model.UserActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    Page<UserActivity> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM user_activity where user_id = :userId", nativeQuery = true)
    List<UserActivity> activitiesByUser(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "insert into user_activity (user_id, activity_id, activity_date, activity_duration) values (:userId,(select activity_id from activity where activity_name = :activityName||activity_ua = :activityName),:date,:duration)", nativeQuery = true)
    void saveActivityForUser(@Param("userId") Long userId, @Param("activityName") String activityName, @Param("date") LocalDate date, @Param("duration") Integer duration);

    @Modifying
    @Transactional
    @Query(value = "delete from user_activity where user_id = :userId and activity_id = (select activity_id from activity where activity_name = :activityName||activity_ua = :activityName) and activity_date = :date", nativeQuery = true)
    void deleteActivityForUser(@Param("userId") Long userId, @Param("activityName") String activityName, @Param("date") LocalDate date);
}
