package com.wanted.clone.oneport.payments.representation.web.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentCancelMessage {
    private final String cancelReason;
    private final int cancelAmount;
}
