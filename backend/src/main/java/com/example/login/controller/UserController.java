package com.example.login.controller;


import com.example.login.dto.blog.UserDto;
import com.example.login.exception.ApiResponse;
import com.example.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <用戶相關API></用戶相關API>
 */
@RestController
@Tag(name = "用戶(user)")
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    @Operation(summary = "尋找所有用戶")
    public ResponseEntity<List<UserDto>> findAllUser() {
        return ResponseEntity.ok().body(userService.findAllUser());
    }

    @GetMapping("/keyword/user")
    @Operation(summary = "關鍵字查詢用戶")
    public ResponseEntity<List<UserDto>> findUserByTitle(@RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok().body(userService.findUserByTitle(keyword));
    }

    @PutMapping("/{userId}/info")
    @Operation(summary = "編輯用戶個人資料")
    public ResponseEntity<UserDto> editUserInfo(@RequestParam(value = "name") String name,
                                                @RequestParam(value = "mobile") String mobile,
                                                @PathVariable Integer userId) {

        return ResponseEntity.ok(userService.editUserInfo(name, mobile, userId));
    }

    @PutMapping("/{userId}/password")
    @Operation(summary = "編輯用戶密碼")
    public ResponseEntity<UserDto> editUserPassword(@RequestParam(value = "password") String password,
                                                    @PathVariable Integer userId) {

        return ResponseEntity.ok(userService.editUserPassword(password, userId));
    }

    @DeleteMapping("/{deleteId}")
    @Operation(summary = "刪除用戶")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer deleteId) {
        userService.deleteUser(deleteId);
        return ResponseEntity.ok(new ApiResponse("user" + deleteId + " is deleted", true));
    }


}

