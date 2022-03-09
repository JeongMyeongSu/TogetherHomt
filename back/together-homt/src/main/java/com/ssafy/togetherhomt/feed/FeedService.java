package com.ssafy.togetherhomt.feed;

import com.ssafy.togetherhomt.config.auth.PrincipalDetails;
import com.ssafy.togetherhomt.config.media.GlobalConfig;
import com.ssafy.togetherhomt.feed.comment.Comment;
import com.ssafy.togetherhomt.feed.comment.CommentDto;
import com.ssafy.togetherhomt.feed.comment.CommentListDto;
import com.ssafy.togetherhomt.feed.comment.CommentRepository;
import com.ssafy.togetherhomt.feed.like.Like;
import com.ssafy.togetherhomt.feed.like.LikeRepository;
import com.ssafy.togetherhomt.feed.tag.Tag;
import com.ssafy.togetherhomt.feed.tag.TagDto;
import com.ssafy.togetherhomt.feed.tag.TagRepository;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserRepository;
import com.ssafy.togetherhomt.user.follow.Follow;
import com.ssafy.togetherhomt.user.follow.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FeedService {

    public FeedRepository feedRepository;
    public UserRepository userRepository;
    public CommentRepository commentRepository;
    public FollowRepository followRepository;
    private GlobalConfig config;
    public TagRepository tagRepository;
    public LikeRepository likeRepository;

    @Autowired
    public FeedService(FeedRepository feedRepository, UserRepository userRepository, CommentRepository commentRepository, FollowRepository followRepository, GlobalConfig config, TagRepository tagRepository, LikeRepository likeRepository) {
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.followRepository = followRepository;
        this.config = config;
        this.tagRepository = tagRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public ResponseEntity<?> postFeed(FeedDto feedDto, TagDto tagDto){

        MultipartFile multipartFile = feedDto.getMedia();

        // 파일 없음 || 파일이 비어있음
        if(multipartFile == null || multipartFile.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 현재 날짜 폴더만들어서 저장
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        String uploadFilePath = config.getUploadFilePath()+currentDate+"/";

        // 랜덤이름 + . 확장자 가져오기
        String postfix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1, multipartFile.getOriginalFilename().length());
        String filename = UUID.randomUUID().toString() + "." + postfix;

        // 폴더 없으면 만들기
        File folder = new File(uploadFilePath);
        if(!folder.isDirectory()){
            folder.mkdirs();
        }

        String pathname = uploadFilePath+filename;
        String resourcePathname = config.getUploadResourcePath()+currentDate+"/"+filename;

        // url 경로 출력
        System.out.println("resourcePathname = " + resourcePathname);

        // 새로 파일 만들기
        File dest = new File(pathname);
        try {
            multipartFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get User
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userTemp = principalDetails.getUser();
        User user = userRepository.findByEmail(userTemp.getEmail());

        // Tag
        List<Tag> resTags = new ArrayList<>();


        if (tagDto != null) {
            for (String tempName : tagDto.getTagList()) {
                Tag tempTag = tagRepository.findByName(tempName);
                if (tempTag != null) {
                    resTags.add(tempTag);
                } else {
                    Tag newTag = Tag.builder()
                            .name(tempName)
                            .build();
                    tagRepository.save(newTag);
                    resTags.add(newTag);
                }
            }
        }
        
        // Create Feed
        Feed feed = Feed.builder()
                .title(feedDto.getTitle())
                .content(feedDto.getContent())
                .likeCnt(0L)
                .mediaUrl(resourcePathname)
                .user(user)
                .tags(resTags)
                .build();

        feedRepository.save(feed);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<FeedDto> getAllFeeds() {

        // Get User
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userTemp = principalDetails.getUser();
        User user = userRepository.findByEmail(userTemp.getEmail());

        List<FeedDto> result = new ArrayList<>();

        for (Feed feed : feedRepository.findAll()) {

            FeedDto feedDto = new FeedDto();

            feedDto.setFeedId(feed.getFeedId());
            feedDto.setTitle(feed.getTitle());
            feedDto.setContent(feed.getContent());
            feedDto.setMediaUrl(feed.getMediaUrl());
            feedDto.setLikeCnt(feed.getLikeCnt());
            feedDto.setUsername(feed.getUser().getUsername());
            feedDto.setCreatedAt(feed.getCreatedAt());
            feedDto.setUpdatedAt(feed.getUpdatedAt());
            feedDto.setEmail(feed.getUser().getEmail());

            // Check like_status
            Like like_flag = likeRepository.findByUserAndFeed(user, feed);
            if (like_flag != null) {
                feedDto.setLikeStatus(true);
            } else {
                feedDto.setLikeStatus(false);
            }

            List<String> tempTags = new ArrayList<>();
            for (Tag tag : feed.getTags()) {
                tempTags.add(tag.getName());
            };
            feedDto.setTags(tempTags);

            result.add(feedDto);
        }

        // Sorting By Created time
        result.sort(new Comparator<FeedDto>() {
            @Override
            public int compare(FeedDto o1, FeedDto o2) {
                return o2.getFeedId().intValue() - o1.getFeedId().intValue();
            }
        });

        return result;
    }

    public List<FeedListDto> getFollowerFeeds() {

        // Get User
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userTemp = principalDetails.getUser();
        User user = userRepository.findByEmail(userTemp.getEmail());

        // Return Feed List
        List<FeedListDto> result = new ArrayList<>();

        // Get Followings by User
        List<Follow> followings = followRepository.findByFollower(user);

        // Add Following's Feed
        for (Follow following : followings) {

            User toUser = following.getFollowing();
            List<Feed> feeds = toUser.getFeeds();

            for (Feed feed : feeds) {
                FeedListDto feedListDto = new FeedListDto();

                feedListDto.setFeedId(feed.getFeedId());
                feedListDto.setUsername(feed.getUser().getUsername());
                feedListDto.setProfileUrl(feed.getUser().getImagePath());
                feedListDto.setMediaUrl(feed.getMediaUrl());
                feedListDto.setLikeCnt(feed.getLikeCnt());
                feedListDto.setContent(feed.getContent());
                feedListDto.setEmail(feed.getUser().getEmail());

                List<String> tempTags = new ArrayList<>();
                for (Tag tag : feed.getTags()) {
                    tempTags.add(tag.getName());
                };
                feedListDto.setTags(tempTags);

                feedListDto.setCreatedAt(feed.getCreatedAt());
                feedListDto.setUpdatedAt(feed.getUpdatedAt());

                // Check like_status
                Like like_flag = likeRepository.findByUserAndFeed(user, feed);
                if (like_flag != null) {
                    feedListDto.setLikeStatus(true);
                } else {
                    feedListDto.setLikeStatus(false);
                }

                result.add(feedListDto);
            }
        }

        // Add My Feed
        List<Feed> feeds = feedRepository.findByUser(user);
        for (Feed feed : feeds) {
            FeedListDto feedListDto = new FeedListDto();

            feedListDto.setFeedId(feed.getFeedId());
            feedListDto.setUsername(feed.getUser().getUsername());
            feedListDto.setProfileUrl(feed.getUser().getImagePath());
            feedListDto.setMediaUrl(feed.getMediaUrl());
            feedListDto.setLikeCnt(feed.getLikeCnt());
            feedListDto.setContent(feed.getContent());
            feedListDto.setEmail(feed.getUser().getEmail());

            List<String> tempTags = new ArrayList<>();
            for (Tag tag : feed.getTags()) {
                tempTags.add(tag.getName());
            };
            feedListDto.setTags(tempTags);

            feedListDto.setCreatedAt(feed.getCreatedAt());
            feedListDto.setUpdatedAt(feed.getUpdatedAt());

            // Check like_status
            Like like_flag = likeRepository.findByUserAndFeed(user, feed);
            if (like_flag != null) {
                feedListDto.setLikeStatus(true);
            } else {
                feedListDto.setLikeStatus(false);
            }

            result.add(feedListDto);
        }

        // Sorting By Created time
        result.sort(new Comparator<FeedListDto>() {
            @Override
            public int compare(FeedListDto o1, FeedListDto o2) {
                return o2.getFeedId().intValue() - o1.getFeedId().intValue();
            }
        });

        return result;
    }

    public FeedListDto getFeed(Long feedId) {

        // Get User
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userTemp = principalDetails.getUser();
        User user = userRepository.findByEmail(userTemp.getEmail());

        // Get Feed
        Optional<Feed> optFeed = feedRepository.findById(feedId);
        if (!optFeed.isPresent()) {
            return null;
        }
        Feed feed = optFeed.get();

        FeedListDto result = new FeedListDto();

        result.setFeedId(feed.getFeedId());
        result.setContent(feed.getContent());
        result.setMediaUrl(feed.getMediaUrl());
        result.setLikeCnt(feed.getLikeCnt());
        result.setUsername(feed.getUser().getUsername());
        result.setCreatedAt(feed.getCreatedAt());
        result.setUpdatedAt(feed.getUpdatedAt());
        result.setProfileUrl(feed.getUser().getImagePath());
        result.setEmail(feed.getUser().getEmail());

        // Check like_status
        Like like_flag = likeRepository.findByUserAndFeed(user, feed);
        if (like_flag != null) {
            result.setLikeStatus(true);
        } else {
            result.setLikeStatus(false);
        }

        List<String> tempTags = new ArrayList<>();
        for (Tag tag : feed.getTags()) {
            tempTags.add(tag.getName());
        };
        result.setTags(tempTags);

        return result;
    }

    public List<FeedDto> getProfileFeeds(String email) {

        // Get User
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userTemp = principalDetails.getUser();
        User loginUser = userRepository.findByEmail(userTemp.getEmail());

        User user = userRepository.findByEmail(email);

        List<FeedDto> result = new ArrayList<>();
        for (Feed feed : feedRepository.findByUser(user)) {
            FeedDto feedDto = new FeedDto();

            feedDto.setFeedId(feed.getFeedId());
            feedDto.setTitle(feed.getTitle());
            feedDto.setContent(feed.getContent());
            feedDto.setMediaUrl(feed.getMediaUrl());
            feedDto.setLikeCnt(feed.getLikeCnt());
            feedDto.setUsername(feed.getUser().getUsername());
            feedDto.setEmail(feed.getUser().getEmail());

            List<String> tempTags = new ArrayList<>();
            for (Tag tag : feed.getTags()) {
                tempTags.add(tag.getName());
            };
            feedDto.setTags(tempTags);

            // Check like_status
            Like like_flag = likeRepository.findByUserAndFeed(loginUser, feed);
            if (like_flag != null) {
                feedDto.setLikeStatus(true);
            } else {
                feedDto.setLikeStatus(false);
            }

            result.add(feedDto);
        }

        // Sorting By Created time
        result.sort(new Comparator<FeedDto>() {
            @Override
            public int compare(FeedDto o1, FeedDto o2) {
                return o2.getFeedId().intValue() - o1.getFeedId().intValue();
            }
        });
        return result;
    }

    public List<FeedListDto> getSearchFeeds() {

        // Get User
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userTemp = principalDetails.getUser();
        User user = userRepository.findByEmail(userTemp.getEmail());

        List<FeedListDto> result = new ArrayList<>();

        List<Feed> allFeed = feedRepository.findAll();

        for (Feed feed : allFeed) {
            FeedListDto feedListDto = new FeedListDto();

            feedListDto.setFeedId(feed.getFeedId());
            feedListDto.setUsername(feed.getUser().getUsername());
            feedListDto.setMediaUrl(feed.getMediaUrl());
            feedListDto.setLikeCnt(feed.getLikeCnt());
            feedListDto.setContent(feed.getContent());

            List<String> tempTags = new ArrayList<>();
            for (Tag tag : feed.getTags()) {
                tempTags.add(tag.getName());
            };
            feedListDto.setTags(tempTags);

            feedListDto.setCreatedAt(feed.getCreatedAt());
            feedListDto.setUpdatedAt(feed.getUpdatedAt());
            feedListDto.setProfileUrl(feed.getUser().getImagePath());
            feedListDto.setEmail(feed.getUser().getEmail());

            // Check like_status
            Like like_flag = likeRepository.findByUserAndFeed(user, feed);
            if (like_flag != null) {
                feedListDto.setLikeStatus(true);
            } else {
                feedListDto.setLikeStatus(false);
            }

            result.add(feedListDto);
        }

        // Sorting By Created time
        result.sort(new Comparator<FeedListDto>() {
            @Override
            public int compare(FeedListDto o1, FeedListDto o2) {
                return o2.getFeedId().intValue() - o1.getFeedId().intValue();
            }
        });

        return result;
    }

    public List<FeedProfileDto> getProfileFeeds_temp(String email) {

        // Get User
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userTemp = principalDetails.getUser();
        User loginUser = userRepository.findByEmail(userTemp.getEmail());

        User user = userRepository.findByEmail(email);
        List<Feed> feeds = feedRepository.findByUser(user);
        List<FeedProfileDto> result= new ArrayList<>();

        for (Feed feed : feeds) {
            FeedProfileDto feedProfileDto = new FeedProfileDto();

            feedProfileDto.setFeedId(feed.getFeedId());
            feedProfileDto.setMediaUrl(feed.getMediaUrl());
            feedProfileDto.setLikeCnt(feed.getLikeCnt());
            feedProfileDto.setContent(feed.getContent());
            feedProfileDto.setEmail(feed.getUser().getEmail());

            List<String> tempTags = new ArrayList<>();
            for (Tag tag : feed.getTags()) {
                tempTags.add(tag.getName());
            };
            feedProfileDto.setTags(tempTags);

            feedProfileDto.setCreatedAt(feed.getCreatedAt());
            feedProfileDto.setUpdatedAt(feed.getUpdatedAt());

            // Check like_status
            Like like_flag = likeRepository.findByUserAndFeed(loginUser, feed);
            if (like_flag != null) {
                feedProfileDto.setLikeStatus(true);
            } else {
                feedProfileDto.setLikeStatus(false);
            }

            result.add(feedProfileDto);
        }

        // Sorting By Created time
        result.sort(new Comparator<FeedProfileDto>() {
            @Override
            public int compare(FeedProfileDto o1, FeedProfileDto o2) {
                return o2.getFeedId().intValue() - o1.getFeedId().intValue();
            }
        });
        return result;
    }

    public FeedDto getUpdateFeedInfo(Long feed_id) {

        // Get Feed
        Optional<Feed> optFeed = feedRepository.findById(feed_id);
        if (!optFeed.isPresent()) {
            return null;
        }
        Feed feed = optFeed.get();

        FeedDto feedDto = new FeedDto();

        feedDto.setFeedId(feed.getFeedId());
        feedDto.setTitle(feed.getTitle());
        feedDto.setContent(feed.getContent());
        feedDto.setCreatedAt(feed.getCreatedAt());
        feedDto.setUpdatedAt(feed.getUpdatedAt());
        feedDto.setMediaUrl(feed.getMediaUrl());
        feedDto.setUsername(feed.getUser().getUsername());
        feedDto.setEmail(feed.getUser().getEmail());

        List<String> tempTags = new ArrayList<>();
        for (Tag tag : feed.getTags()) {
            tempTags.add(tag.getName());
        };
        feedDto.setTags(tempTags);

        return feedDto;
    }

    @Transactional
    public ResponseEntity<?> updateFeed(Long feedId, FeedDto feedDto) {

        // Get Feed
        Optional<Feed> optFeed = feedRepository.findById(feedId);
        if (!optFeed.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Feed feed = optFeed.get();

        List<Tag> resTags = new ArrayList<>();

        for (String tempName : feedDto.getTags()) {
            Tag tempTag = tagRepository.findByName(tempName);
            if (tempTag != null) {
                resTags.add(tempTag);
            } else {
                Tag newTag = Tag.builder()
                        .name(tempName)
                        .build();
                tagRepository.save(newTag);
                resTags.add(newTag);
            }
        }

        // Update Feed
        feed.setTitle(feedDto.getTitle());
        feed.setContent(feedDto.getContent());
        feed.setTags(resTags);
        feedRepository.save(feed);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteFeed(Long feed_id) {

        // Get Feed
        Optional<Feed> feed = feedRepository.findById(feed_id);
        if (!feed.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Check Existence
        if (!feed.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            feedRepository.delete(feed.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public List<CommentListDto> getComments(Long feed_id) {

        // Get Feed
        Optional<Feed> feed = feedRepository.findById(feed_id);
        if (!feed.isPresent()) {
            return null;
        }
        List<CommentListDto> result = new ArrayList<>();

        for (Comment comment : commentRepository.findByFeed(feed.get())) {
            CommentListDto commentListDto = new CommentListDto();

            commentListDto.setCommentId(comment.getCommentId());
            commentListDto.setProfileUrl(comment.getUser().getImagePath());
            commentListDto.setEmail(comment.getUser().getEmail());
            commentListDto.setUsername(comment.getUser().getUsername());
            commentListDto.setContent(comment.getContent());
            commentListDto.setCreatedAt(comment.getCreatedAt());

            result.add(commentListDto);
        }

        return result;
    }

    @Transactional
    public ResponseEntity<?> postComment(Long feed_id, CommentDto commentDto) {

        // Get User
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userTemp = principalDetails.getUser();
        User user = userRepository.findByEmail(userTemp.getEmail());

        // Get Feed
        Optional<Feed> feed = feedRepository.findById(feed_id);
        if (!feed.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Build & Save Comment
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .feed(feed.get())
                .user(user)
                .build();

        commentRepository.save(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long comment_id) {

        commentRepository.deleteById(comment_id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
