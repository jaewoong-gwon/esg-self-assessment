package com.ict.esg.controller;

import com.ict.esg.service.EvaluationResultService;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/esg")
class EvaluationResultController {

  private EvaluationResultService evaluationResultService;


  @GetMapping("/result/all")
  public ResponseEntity<?> findEvalResult(@RequestParam Map<String, String> params) {

    // email 을 parameter 로 받아서 평가 결과 List 조회.
    log.info("result/all - parameter : {}", params);
    Map<String, Object> response = evaluationResultService.getEvaluationResultList(params);
    if ((Integer) response.get("status") == 200) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }

  @GetMapping("/result")
  public ResponseEntity<?> findEval(@RequestParam Map<String, Object> params) {
    log.info("/result - parameter : {}", params);
    Map<String, String> response = new HashMap<>();
    // e val_idx 를 parameter 로 받아서 결과 조회.
    try {
      response = evaluationResultService.getEvaluationResult(params);
      if (response.get("status").equals("200")) {
        return ResponseEntity.status(HttpStatus.OK).body(response);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @PostMapping("/new/result")
  public ResponseEntity<?> createEval(@RequestBody Map<String, Object> params) {
    log.info("new/result - parameter : {}", params);
    // Request Parameter 로 기업 담당자 이메일(ent_mrg_email), 평가 날짜(eval_date) 를 받아 전달.
    Map<String, Object> response = evaluationResultService.createEvaluationResult(params);
    int status = (int) response.get("status");
    if (status == 200) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }
}
