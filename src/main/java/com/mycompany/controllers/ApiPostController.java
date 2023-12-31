/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.DTO.AuctionStatusDTO;
import com.mycompany.DTO.CommentResponseDTO;
import com.mycompany.DTO.LikePostDTO;
import com.mycompany.DTO.PostRequestDTO;
import com.mycompany.DTO.PostResponseDTO;
import com.mycompany.DTO.UserResponseDTO;
import com.mycompany.pojo.AuctionStatus;
import com.mycompany.pojo.Comment;
import com.mycompany.pojo.LikePost;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.service.AuctionStatusService;
import com.mycompany.service.CommentService;
import com.mycompany.service.LikeService;
import com.mycompany.service.PostService;
import com.mycompany.service.UserService;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vuongthai1205
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiPostController {
    public static final SimpleDateFormat F = new SimpleDateFormat("dd-MM-yyyy");
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private AuctionStatusService auctionStatusService;
    @Autowired
    private Environment env;

    @Autowired
    private CommentService commentService;

    @GetMapping("/post/")
    @CrossOrigin
    public ResponseEntity<List<PostResponseDTO>> getPosts(@RequestParam Map<String, String> params) {
        List<PostResponseDTO> postResponseDTOs = new ArrayList<>();
        List<Post> posts = this.postService.getPostList(params);

        posts.forEach(post -> {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            PostResponseDTO postResponseDTO = new PostResponseDTO();
            AuctionStatusDTO auctionStatusDTO = new AuctionStatusDTO();

            List<LikePost> likePosts = this.likeService.getLikePosts(post);
            List<LikePostDTO> likePostDTOs = new ArrayList<>();
            likePosts.forEach(likePost -> {
                LikePostDTO likePostDTO = new LikePostDTO();
                likePostDTO.setId(likePost.getIdUser().getId());
                likePostDTO.setImage(likePost.getIdUser().getAvatar());
                likePostDTO.setUsername(likePost.getIdUser().getUsername());

                likePostDTOs.add(likePostDTO);
            });

            List<Comment> listComments = this.commentService.listCommentPost(post);
            List<CommentResponseDTO> listCommentDTOs = new ArrayList<>();
            listComments.forEach(cmt -> {
                CommentResponseDTO commentDTO = new CommentResponseDTO();
                commentDTO.setId(cmt.getId());
                commentDTO.setImage(cmt.getIdUser().getAvatar());
                commentDTO.setUsername(cmt.getIdUser().getUsername());
                commentDTO.setContent(cmt.getContent());
                commentDTO.setIdUser(cmt.getIdUser().getId());
                listCommentDTOs.add(commentDTO);
            });
            auctionStatusDTO.setId(post.getAuctionStatus().getId());
            auctionStatusDTO.setName(post.getAuctionStatus().getNameAuctionStatus());

            postResponseDTO.setId(post.getId());
            postResponseDTO.setTitle(post.getTitle());
            postResponseDTO.setContent(post.getContent());
            postResponseDTO.setImage(post.getImage());
            postResponseDTO.setCreateAt(post.getCreateAt());
            postResponseDTO.setUpdateAt(post.getUpdateAt());

            postResponseDTO.setAuctionStatus(auctionStatusDTO);
            postResponseDTO.setLikePost(likePostDTOs);
            postResponseDTO.setListComment(listCommentDTOs);
            
            userResponseDTO.setUsername(post.getIdUser().getUsername());
            userResponseDTO.setAvatar(post.getIdUser().getAvatar());
            userResponseDTO.setId(post.getIdUser().getId());
            userResponseDTO.setAddress(post.getIdUser().getAddress());
            userResponseDTO.setCreateAt(post.getIdUser().getCreateAt());
            userResponseDTO.setUpdateAt(post.getIdUser().getUpdateAt());
            userResponseDTO.setDateOfBirth(post.getIdUser().getDateOfBirth());
            userResponseDTO.setEmail(post.getIdUser().getEmail());
            userResponseDTO.setFirstName(post.getIdUser().getFirstName());
            userResponseDTO.setLastName(post.getIdUser().getLastName());
            userResponseDTO.setPhone(post.getIdUser().getPhone());
            userResponseDTO.setGender(post.getIdUser().getGender());
            if (post.getStartPrice() != null) {
                postResponseDTO.setStartPrice(post.getStartPrice());
            }

            postResponseDTO.setUser(userResponseDTO);
            postResponseDTOs.add(postResponseDTO);
            if (post.getAuctionEndTime() != null) {
                postResponseDTO.setEndAuctionTime(F.format(post.getAuctionEndTime()));
            }
            
            if (post.getAuctionStartTime() != null) {
                 postResponseDTO.setStartAuctionTime(F.format(post.getAuctionStartTime()));
            }
            
           
        });

        return new ResponseEntity<>(postResponseDTOs, HttpStatus.OK);
    }

    @PostMapping(path = "/post/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPost(@RequestBody PostRequestDTO postRequest, Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        Post post = new Post();
        post.setIdUser(u);
        post.setImage(postRequest.getImage());
        post.setContent(postRequest.getContent());
        post.setTitle(postRequest.getTitle());
        AuctionStatus auctionStatus = this.auctionStatusService.getAuctionStatus(postRequest.getAuctionStatus());

        post.setAuctionStatus(auctionStatus);
        post.setStartPrice(postRequest.getStartPrice());
        post.setAuctionStartTime(postRequest.getAuctionStartTime());
        post.setAuctionEndTime(postRequest.getAuctionEndTime());
        boolean isAddedOrUpdated = postService.addOrUpdatePost(post);
        
        if (isAddedOrUpdated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Post added or updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add or update post");
        }
    }

    @PutMapping(path = "/post/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePost(@RequestBody PostRequestDTO postRequest, Principal user, @PathVariable(value = "id") int id) {
        User u = this.userService.getUserByUsername(user.getName());

        Post post = this.postService.getPostById(id);
        if (post.getIdUser().equals(u)) {
            post.setTitle(postRequest.getTitle());
            post.setImage(postRequest.getImage());
            post.setContent(postRequest.getContent());
            AuctionStatus auctionStatus = this.auctionStatusService.getAuctionStatus(postRequest.getAuctionStatus());

            post.setAuctionStatus(auctionStatus);
            post.setStartPrice(postRequest.getStartPrice());
            post.setAuctionStartTime(postRequest.getAuctionStartTime());
            post.setAuctionEndTime(postRequest.getAuctionEndTime());
            if (this.postService.addOrUpdatePost(post)) {
                return ResponseEntity.status(HttpStatus.OK).body("Post updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add or update post");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can not permission to edit the post");
        }

    }

    @DeleteMapping("/post/{id}/")
    public ResponseEntity<String> delete(@PathVariable(value = "id") int id, Principal user) {

        User u = this.userService.getUserByUsername(user.getName());

        Post post = this.postService.getPostById(id);

        if (post.getIdUser().equals(u)) {
            if (this.postService.deletePost(post.getId())) {
                return ResponseEntity.status(HttpStatus.OK).body("Post delete successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete post");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can not permission to delete the post");
        }

    }

    @GetMapping("/post/{id}/")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable(value = "id") int id) {
        Post post = this.postService.getPostById(id);
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        AuctionStatusDTO auctionStatusDTO = new AuctionStatusDTO();

        List<LikePost> likePosts = this.likeService.getLikePosts(post);
        List<LikePostDTO> likePostDTOs = new ArrayList<>();
        likePosts.forEach(likePost -> {
            LikePostDTO likePostDTO = new LikePostDTO();
            likePostDTO.setId(likePost.getIdUser().getId());
            likePostDTO.setImage(likePost.getIdUser().getAvatar());
            likePostDTO.setUsername(likePost.getIdUser().getUsername());

            likePostDTOs.add(likePostDTO);
        });
        auctionStatusDTO.setId(post.getAuctionStatus().getId());
        auctionStatusDTO.setName(post.getAuctionStatus().getNameAuctionStatus());

        postResponseDTO.setId(post.getId());
        postResponseDTO.setTitle(post.getTitle());
        postResponseDTO.setContent(post.getContent());
        postResponseDTO.setImage(post.getImage());
        postResponseDTO.setCreateAt(post.getCreateAt());
        postResponseDTO.setUpdateAt(post.getUpdateAt());

        postResponseDTO.setAuctionStatus(auctionStatusDTO);
        postResponseDTO.setLikePost(likePostDTOs);
        userResponseDTO.setUsername(post.getIdUser().getUsername());
        userResponseDTO.setAvatar(post.getIdUser().getAvatar());

        postResponseDTO.setUser(userResponseDTO);
        postResponseDTO.setStartPrice(post.getStartPrice());
        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/post/count-pages/")
    public ResponseEntity<?> getPages() {
        int count = this.postService.countPost();
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        int result = (int) Math.ceil(count * 1.0 / pageSize);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
