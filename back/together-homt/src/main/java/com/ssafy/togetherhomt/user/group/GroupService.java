package com.ssafy.togetherhomt.user.group;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.feed.Feed;
import com.ssafy.togetherhomt.feed.FeedRepository;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserDto;
import com.ssafy.togetherhomt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    /*** Service ***/
    private final CommonService commonService;
    /*** Repository ***/
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;


    @Transactional
    public void create(GroupDto groupDto) {
        Group group = Group.builder()
                .mainCategory(groupDto.getMainCategory())
                .middleCategory(groupDto.getMiddleCategory())
                .subCategory(groupDto.getSubCategory())
                .name(groupDto.getGroupName())
                .build();
        groupRepository.save(group);
    }

    public List<GroupDto> search(Map<String, String> searchOption) {
        List<Group> groups = new ArrayList<>();
        if (searchOption.containsKey("sub"))
            groups = groupRepository.findBySubCategory(searchOption.get("sub"));
        else if (searchOption.containsKey("middle"))
            groups = groupRepository.findByMiddleCategory(searchOption.get("middle"));
        else if (searchOption.containsKey("main"))
            groups = groupRepository.findByMainCategory(searchOption.get("main"));
        groups.size();
        return null;
    }

    public GroupDto search(String name) {
        Group group = groupRepository.findByName(name);
        return null;
    }



    @Transactional
    public String joinMembers(Long groupId, List<UserDto> members) {
        Optional<Group> optGroup = groupRepository.findById(groupId);
        if (!optGroup.isPresent())
            return null;

        Group group = optGroup.get();

        int nNonSaved = 0;
        for (UserDto userDto : members) {
            User member = userRepository.findByEmail(userDto.getEmail());
            if (member == null)
                ++nNonSaved;
            else {
                member.setGroup(group);
                userRepository.save(member);
            }
        }

        if (nNonSaved == 0)
            return "success";
        else
            return String.valueOf(nNonSaved);
    }

    public List<UserDto> getMembers(Long groupId) {
        Optional<Group> optGroup = groupRepository.findById(groupId);
        if (!optGroup.isPresent())
            return null;

        Group group = optGroup.get();

        List<UserDto> members = new ArrayList<>();
        for (User member : group.getMembers()) {
            members.add(commonService.builder(member, false));
        }

        return members;
    }

    @Transactional
    public String ejectMembers(Long groupId, List<UserDto> members) {
        Optional<Group> optGroup = groupRepository.findById(groupId);
        if (!optGroup.isPresent())
            return null;

        Group group = optGroup.get();

        int nNonEjected = 0;
        for (UserDto userDto : members) {
            User member = userRepository.findByEmail(userDto.getEmail());
            if (member == null || !group.equals(member.getGroup()))
                ++nNonEjected;
            else {
                member.setGroup(null);
                userRepository.save(member);
            }
        }

        if (nNonEjected == 0)
            return "success";
        else
            return String.valueOf(nNonEjected);
    }


    public List<FeedDto> getGroupFeeds(Long groupId) {
        Optional<Group> optGroup = groupRepository.findById(groupId);
        if (!optGroup.isPresent())
            return null;

        Group group = optGroup.get();

        User groupAdmin = userRepository.findByGroupAndRole(group, "ROLE_ADMIN");

        List<FeedDto> groupFeedList = new ArrayList<>();
        for (Feed feed : feedRepository.findByUser(groupAdmin))
            groupFeedList.add(commonService.builder(feed));
        return groupFeedList;
    }

}
