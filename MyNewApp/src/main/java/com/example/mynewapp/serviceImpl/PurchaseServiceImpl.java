package com.example.mynewapp.serviceImpl;

import com.example.mynewapp.entity.Purchase;
import com.example.mynewapp.entity.User;
import com.example.mynewapp.mapper.PurchaseMapper;
import com.example.mynewapp.repositories.PurchaseRepository;
import com.example.mynewapp.repositories.UserRepository;
import com.example.mynewapp.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    UserRepository userRepository;
    private final PurchaseMapper purchaseMapper;
    @Override
    public List<Purchase> getPurchaseByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        return user.getPurchases();
    }

    @Override
    public Purchase addPurchase(Purchase purchase) {
        purchaseRepository.save(purchase);

        return purchase;
    }
}
