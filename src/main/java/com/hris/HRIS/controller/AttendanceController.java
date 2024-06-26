package com.hris.HRIS.controller;

import com.hris.HRIS.model.AttendanceModel;
import com.hris.HRIS.model.CredentialsModel;
import com.hris.HRIS.repository.AttendanceRepository;
import com.hris.HRIS.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Endpoint to create a new attendance record
    @PostMapping("/create")
    public ResponseEntity<AttendanceModel> createAttendance(@RequestBody AttendanceModel attendanceModel) {
        AttendanceModel createdAttendance = attendanceService.createAttendance(attendanceModel);
        return new ResponseEntity<>(createdAttendance, HttpStatus.CREATED);
    }

    // Endpoint to retrieve all attendance records
    @GetMapping("/get/all")
    public ResponseEntity<List<AttendanceModel>> getAllAttendance() {
        List<AttendanceModel> allAttendance = attendanceService.getAllAttendance();
        return new ResponseEntity<>(allAttendance, HttpStatus.OK);
    }

    // Endpoint to retrieve a specific attendance record by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<AttendanceModel> getAttendanceById(@PathVariable String id) {
        Optional<AttendanceModel> attendanceModelOptional = attendanceRepository.findById(id);

        return attendanceModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // Endpoint to update an existing attendance record
    @PutMapping("/update/{id}")
    public ResponseEntity<AttendanceModel> updateAttendance(@PathVariable("id") String id, @RequestBody AttendanceModel attendanceModel) {
        AttendanceModel updatedAttendance = attendanceService.updateAttendance(id, attendanceModel);
        if (updatedAttendance != null) {
            return new ResponseEntity<>(updatedAttendance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete an attendance record by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable("id") String id) {
        boolean deleted;
        deleted = attendanceService.deleteAttendance(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Rest API to calculate late minutes
//    @GetMapping("/calculate-late-minutes/{id}")
//    public ResponseEntity<Long> calculateLateMinutes(@PathVariable String id, @RequestParam Date expectedInTime) {
//        AttendanceModel attendanceModel = attendanceService.getAttendanceById(id);
//        if (attendanceModel == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        long lateMinutes = attendanceService.calculateLateMinutes(attendanceModel, expectedInTime);
//        return new ResponseEntity<>(lateMinutes, HttpStatus.OK);
//    }

    // Rest API to calculate early departures
//    @GetMapping("/calculate-early-departure-minutes/{id}")
//    public ResponseEntity<Long> calculateEarlyDepartureMinutes(@PathVariable String id, @RequestParam Date expectedOutTime) {
//        AttendanceModel attendanceModel = attendanceService.getAttendanceById(id);
//        if (attendanceModel == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        long earlyDepartureMinutes = attendanceService.calculateEarlyDepartureMinutes(attendanceModel, expectedOutTime);
//        return new ResponseEntity<>(earlyDepartureMinutes, HttpStatus.OK);
//    }
}

