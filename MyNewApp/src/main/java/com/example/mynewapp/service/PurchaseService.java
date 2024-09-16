package com.example.mynewapp.service;

import com.example.mynewapp.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    List<Purchase> getPurchaseByUserId(Long UserId);
    Purchase addPurchase(Purchase purchase);
}
