package com.runndy.server.domain.user.repository;

import com.runndy.server.domain.user.entity.User;
import com.runndy.server.domain.user.service.dto.request.CreateUserRequestDto;
import com.runndy.server.domain.user.service.dto.response.SelectLoginUserResponseDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = """
         SELECT email
              , usr_nm AS userName
              , scl_typ AS socialType
              , scl_id AS socialId
              , usr_typ AS userType
           FROM users
          WHERE email = :email
            AND scl_typ = :socialType
            AND scl_id = :socialId
            AND activated = true
      """,
      nativeQuery = true)
  Optional<SelectLoginUserResponseDto> findUserByEmailAndSclTyp(String email, String socialType,
      String socialId);

  @Modifying
  @Query(value = """
      INSERT INTO users (
                      email
                    , usr_nm
                    , scl_typ
                    , scl_id
                    , usr_typ
                    , activated
                    , created_at
                          )
            VALUES (:#{#req.email}
                  , :#{#req.userName}
                  , :#{#req.socialType}
                  , :#{#req.socialId}
                  , :#{#req.userType}
                  , true
                  , NOW()
                    )
      """,
      nativeQuery = true)
  int createUser(@Param("req") CreateUserRequestDto requestDto);

}
