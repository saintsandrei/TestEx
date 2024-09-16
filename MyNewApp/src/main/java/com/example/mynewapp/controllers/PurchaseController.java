package com.example.mynewapp.controllers;

import com.example.mynewapp.entity.Purchase;
import com.example.mynewapp.entity.User;
import com.example.mynewapp.mapper.PurchaseMapper;
import com.example.mynewapp.repositories.UserRepository;
import com.example.mynewapp.dto.PurchaseDto;
import com.example.mynewapp.service.PurchaseService;
import com.example.mynewapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    PurchaseMapper purchaseMapper;

    @GetMapping("/{userId}/all")
    public ResponseEntity<List<PurchaseDto>> getPurchaseByUserId(@PathVariable Long userId){
        User user = userService.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        List<Purchase> purchases = purchaseService.getPurchaseByUserId(user.getId());
        List<PurchaseDto> purchaseDtos = purchases.stream().map(purchaseMapper::purchaseToPurchaseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(purchaseDtos);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addPurchaseByUserId(@PathVariable Long userId,
                                                        @RequestBody PurchaseDto purchaseDto){
        User user = userService.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Purchase purchase = purchaseMapper.purchaseDtoToPurchase(purchaseDto);
        purchase.setUser(user);
        purchaseService.addPurchase(purchase);
        return ResponseEntity.ok("Purchase saved successfully");
    }
}
