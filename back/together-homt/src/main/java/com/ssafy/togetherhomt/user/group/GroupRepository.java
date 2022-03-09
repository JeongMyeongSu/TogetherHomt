package com.ssafy.togetherhomt.user.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByMainCategory(String mainCategory);
    List<Group> findByMiddleCategory(String middleCategory);
    List<Group> findBySubCategory(String subCategory);

    Group findByName(String name);

}
