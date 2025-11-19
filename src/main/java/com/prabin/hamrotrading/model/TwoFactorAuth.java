package com.prabin.hamrotrading.model;

import com.prabin.hamrotrading.enums.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled;
    private VerificationType sendTo;
}
