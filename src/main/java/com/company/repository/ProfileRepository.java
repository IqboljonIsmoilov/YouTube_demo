package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByEmailAndPassword(String email, String pswd);

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findById(String id);

    Optional<ProfileEntity> findByNameAndSurname(String name, String surname);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set surname = :surname, name=:name, email=:email, password=:password where id=:id")
    Integer updateEmail(@Param("name") String name,
                        @Param("surname") String surname,
                        @Param("email") String email,
                        @Param("password") String password,
                        @Param("id") String id);


    @Transactional
    @Modifying
    @Query("update ProfileEntity set surname = :surname, name=:name where id=:id")
    Integer updateProfileDetail(@Param("name") String name,
                                @Param("surname") String surname,
                                @Param("id") String id);


    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where id = :id")
    int updateStatus(@Param("status") ProfileStatus status,
                     @Param("id") String id);
}