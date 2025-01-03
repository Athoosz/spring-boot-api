package com.athosfs.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.athosfs.todosimple.models.Task;
import com.athosfs.todosimple.models.projection.TaskProjection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  @Query(nativeQuery = true, value = "SELECT * FROM task t WHERE t.user_id = :id")
  List<TaskProjection> findByUserId(@Param("id") Long id);
  
}
