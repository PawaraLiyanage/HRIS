package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.PayItemModel;
import com.hris.HRIS.repository.PayItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/payitem")
public class PayItemController {
    @Autowired
    PayItemRepository payItemRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> savePayItem(@RequestBody PayItemModel payItemModel) {
        payItemRepository.save(payItemModel);

        ApiResponse apiResponse = new ApiResponse("Pay item added successfully.");
        return ResponseEntity.ok(apiResponse);
    }
    
    @GetMapping("/get/all")
    public List<PayItemModel> getAllPayItems(){
        return payItemRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<PayItemModel> getPayItemById(@PathVariable String id){
        Optional<PayItemModel> payItemModelOptional = payItemRepository.findById(id);

        return payItemModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updatePayItem(@PathVariable String id, @RequestBody PayItemModel payItemModel){
        Optional<PayItemModel> payItemModelOptional = payItemRepository.findById(id);

        if(payItemModelOptional.isPresent()){
            PayItemModel existingPayItem = payItemModelOptional.get();
            existingPayItem.setItemName(payItemModel.getItemName());
            existingPayItem.setDescription(payItemModel.getItemType());
            existingPayItem.setPaymentType(payItemModel.getPaymentType());
            existingPayItem.setStatus(payItemModel.getStatus());

            payItemRepository.save(existingPayItem);
        }

        ApiResponse apiResponse = new ApiResponse("Pay item updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deletePayItem(@PathVariable String id){
        payItemRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Pay item deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}