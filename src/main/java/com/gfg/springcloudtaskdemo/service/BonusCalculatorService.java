package com.gfg.springcloudtaskdemo.service;


import org.springframework.stereotype.Service;

@Service
public class BonusCalculatorService {

    public double calculateBonus(double salary) {
        // Example: Calculate 10% bonus
        return salary * 0.1;
    }
}

