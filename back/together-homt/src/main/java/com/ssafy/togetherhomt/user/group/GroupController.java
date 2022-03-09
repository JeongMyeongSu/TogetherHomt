package com.ssafy.togetherhomt.user.group;

import com.ssafy.togetherhomt.feed.Feed;
import com.ssafy.togetherhomt.user.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("'그룹' 기능 접근 방법")
@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @ApiOperation(value = "그룹 생성", notes = "그룹 생성")
    @ApiResponse(code = 200, message = "그룹 생성에 성공하였습니다.")
    @PostMapping("/groups")
    public ResponseEntity<Void> create(@RequestBody GroupDto groupDto) {
        groupService.create(groupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "그룹 정보 조회", notes = "그룹의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청입니다. 존재하지 않는 그룹입니다."),
            @ApiResponse(code = 200, message = "요청한 그룹의 정보를 성공적으로 조회했습니다.")
    })
    @GetMapping("/groups")
    public ResponseEntity<?> search(@RequestParam Map<String, String> searchOption) {
        if (searchOption.containsKey("name"))
            groupService.search(searchOption.get("name"));
        else
            groupService.search(searchOption);
        return new ResponseEntity<>("현재 개발 중입니다.", HttpStatus.OK);
    }



    @ApiOperation(value = "그룹에 유저 추가", notes = "그룹에 유저를 추가합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Such group with given ID doesn't exist"),
            @ApiResponse(code = 200, message = "유저를 그룹에 성공적으로 추가하였습니다. (반환값 : 그룹에 추가하는 데 실패한 유저 수)")
    })
    @PostMapping("/groups/{group-id}/members")
    public ResponseEntity<?> joinMembers(@PathVariable("group-id") Long groupId, @RequestBody List<UserDto> members) {
        String result = groupService.joinMembers(groupId, members);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if ("success".equals(result))
            return new ResponseEntity<>(0, HttpStatus.OK);
        else
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "그룹에 소속된 유저 조회", notes = "해당 그룹에 소속된 모든 유저를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Such group with given ID doesn't exist"),
            @ApiResponse(code = 200, message = "그룹에 속한 유저 목록을 성공적으로 조회하였습니다.")
    })
    @GetMapping("/groups/{group-id}/members")
    public ResponseEntity<?> getMembers(@PathVariable("group-id") Long groupId) {
        List<UserDto> members = groupService.getMembers(groupId);
        if (members == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(members, HttpStatus.OK);
    }

    @ApiOperation(value = "유저의 소속 그룹 삭제", notes = "유저의 소속 그룹을 없앱니다. 요청한 그룹에서 해당 유저가 사라집니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Such group with given ID doesn't exist"),
            @ApiResponse(code = 200, message = "유저를 그룹에서 성공적으로 제외하였습니다. (반환값 : 그룹에서 제외하는 데 실패한 유저 수)")
    })
    @PutMapping("/groups/{group-id}/members")
    public ResponseEntity<?> ejectMembers(@PathVariable("group-id") Long groupId, @RequestBody List<UserDto> members) {
        String result = groupService.ejectMembers(groupId, members);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if ("success".equals(result))
            return new ResponseEntity<>(0, HttpStatus.OK);
        else
            return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/groups/{group-id}/feeds")
    public ResponseEntity<?> getGroupFeeds(@PathVariable("group-id") Long groupId) {
        List<FeedDto> groupFeedList = groupService.getGroupFeeds(groupId);
        if (groupFeedList == null)
            return new ResponseEntity<>("No such group with requested ID", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(groupFeedList, HttpStatus.OK);
    }

}
