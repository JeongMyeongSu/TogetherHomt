package com.ssafy.togetherhomt.user;

import com.ssafy.togetherhomt.user.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);
    User findByGroupAndRole(Group group, String role);

}
