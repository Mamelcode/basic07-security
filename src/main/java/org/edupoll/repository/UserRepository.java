package org.edupoll.repository;

import java.util.Optional;

import org.edupoll.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value = "select * from users u join user_roles r on u.id = r.user_id where id = :id" , nativeQuery = true)
	Optional<User> findByUserId(@Param(value = "id") String id);
}
