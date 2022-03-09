package com.ssafy.togetherhomt.user.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.togetherhomt.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`group`")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(name = "main_category")
    private String mainCategory;

    @Column(name = "middle_category")
    private String middleCategory;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties({"group"})
    private List<User> members;

}
