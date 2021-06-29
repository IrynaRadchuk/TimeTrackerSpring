package com.example.model.repository;

import com.example.model.AllowedActivity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface to handle statements to allowed activity table in database
 *
 * @author Iryna Radchuk
 */
public interface AllowedActivityRepository extends CrudRepository<AllowedActivity, Long> {

    @Query(value = "SELECT * FROM user_allowed_activity where user_id = :userId", nativeQuery = true)
    List<AllowedActivity> activitiesByUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM user_allowed_activity where user_id = :userId and status = 'APPROVED'", nativeQuery = true)
    List<AllowedActivity> activitiesByUserApproved(@Param("userId") Long userId);

    @Query("SELECT u FROM AllowedActivity u where u.status = 'PENDING'")
    List<AllowedActivity> activitiesPending();

    @Modifying
    @Transactional
    @Query(value = "insert into user_allowed_activity (user_id, activity_id, status) values (:userId,(select activity_id from activity where activity_name = :activityName||activity_ua = :activityName), 'PENDING')", nativeQuery = true)
    void request(@Param("userId") Long userId, @Param("activityName") String activityName);

    @Transactional
    @Modifying
    @Query(value = "update user_allowed_activity set status = 'APPROVED' where user_id =:userId and activity_id=(select activity_id from activity where activity_name =:activityName||activity_ua = :activityName)", nativeQuery = true)
    void approveActivity(@Param("userId") Long userId, @Param("activityName") String activityName);

    @Transactional
    @Modifying
    @Query(value = "delete from user_allowed_activity where user_id =:userId and activity_id=(select activity_id from activity where activity_name =:activityName||activity_ua = :activityName)", nativeQuery = true)
    void denyActivity(@Param("userId") Long userId, @Param("activityName") String activityName);
}
