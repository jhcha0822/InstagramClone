package com.project.instagramclone.controller;

import jakarta.validation.Valid;
import com.project.instagramclone.dto.MemberDto;
import com.project.instagramclone.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signup(
            @Valid @RequestBody MemberDto memberDto
    ) {
        return ResponseEntity.ok(memberService.signup(memberDto));
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDto> login(
//
//    )

    @GetMapping("/auth")
    public String auth() {
        return "authentication successful";
    }
}