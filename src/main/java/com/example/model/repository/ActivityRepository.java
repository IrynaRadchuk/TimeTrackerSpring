package com.example.model.repository;

import com.example.model.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    @Query(value = "SELECT activity.activity_id, activity.activity_ua, activity.activity_name, activity.category_id, activity_category.category_name, count(user_allowed_activity.user_id) as \"user_quantity\" from activity left join activity_category on activity_category.category_id = activity.category_id left join user_allowed_activity on activity.activity_id = user_allowed_activity.activity_id group by user_allowed_activity.activity_id", nativeQuery = true)
    List<Activity> activityWithUsersCount();

    @Query(value = "select * from activity where activity_name not in(select activity_name from activity left join user_allowed_activity on activity.activity_id = user_allowed_activity.activity_id where user_id=40)||activity_ua not in(select activity_ua from activity left join user_allowed_activity on activity.activity_id = user_allowed_activity.activity_id where user_id=:userId)", nativeQuery = true)
    List<Activity> absent(@Param("userId") Long userId);

    Optional<Activity> findByActivityName(String name);
}
