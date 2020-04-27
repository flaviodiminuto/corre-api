package com.flaviodiminuto.corre.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.flaviodiminuto.corre.domain.entity.User;

@Repository
public interface RepositoryUser extends CrudRepository<User, Long> {
	
	@Transactional
	@Modifying
	@Query("UPDATE User  SET login = :login, "
			+ " password = :password, "
			+ " updatedDate = :date, "
			+ " updatedTime = :time "
			+ " where id = :id ")
	int update(	@Param("id") Long id,
				@Param("login") String login,
				@Param("password") String password,
				@Param("date") LocalDate date,
				@Param("time") LocalTime time );
}
