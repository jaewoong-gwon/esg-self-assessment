package com.ict.esg.controller;

import com.ict.esg.service.NaverSignUpService;
import com.ict.esg.service.SignUpService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/esg/signUp")
class SnsSignUpController {

  private final NaverSignUpService naverLoginService;
  private final SignUpService signUpService;

  @GetMapping("/naver")
  public ResponseEntity<?> naver(@RequestParam Map<String, String> params) {
    log.info("naver - parameter : {}", params);
    //프론트로부터 access_token 받아와서 Naver 에 UserInfo 요청
    // 이후 받아온 사용자 정보를 통한 db 확인, db 확인후 response return.
    return naverLoginService.getUserInfo(params.get("access_token"));
  }

  @PostMapping("/new/user")
  public ResponseEntity<?> singUp(@RequestBody Map<String, String> signUpInfo) {
    log.info("new/user - parameter : {}", signUpInfo);
    return signUpService.signUp(signUpInfo);
  }
}
