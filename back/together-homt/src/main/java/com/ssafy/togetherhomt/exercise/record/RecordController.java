package com.ssafy.togetherhomt.exercise.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise/records")
public class RecordController {

    private RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }


    // 운동 완료 후 기록 생성
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RecordDto recordDto){

        String result = recordService.create(recordDto);

        if (result.equals("success")) {
            return ResponseEntity.ok("success");
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }



    // 한 사람이 운동을 한 기록(달력)
    @GetMapping("/{email}")
    public ResponseEntity<List<RecordDto>> personalRecords(@PathVariable String email){

        List<RecordDto> result = recordService.personalRecord(email);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // 개인 날짜별 상세 조회
    @GetMapping("/{email}/{date}")
    public ResponseEntity<List<RecordDto>> personalRecords(@PathVariable String email,@PathVariable String date){
        List<RecordDto> result = recordService.personalDayRecord(email, date);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
