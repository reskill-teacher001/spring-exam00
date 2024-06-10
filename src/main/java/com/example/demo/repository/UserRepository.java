package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public List<User> findByNameLike(String keyword);
	
	public Optional<User> findByEmailAndPassword(String email, String password);
	
	@Modifying
	@Transactional
	@Query(
		value="INSERT INTO users(name, email, password) VALUES(?1, ?2, ?3)",
		nativeQuery=true
	)
	public int regist(String name, String email, String password);
	
	@Modifying
	@Transactional
	@Query(
		value="UPDATE users SET name = ?1, email = ?2, password = ?3 WHERE id = ?4",
		nativeQuery=true
	)
	public int update(String name, String email, String password, Integer id);

	@Modifying
	@Transactional
	@Query(
		value="DELETE FROM users WHERE id = ?1",
		nativeQuery=true
	)
	public void delete(Integer id);

}
