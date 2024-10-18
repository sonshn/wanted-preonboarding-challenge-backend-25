package com.wanted.clone.oneport.payments.application.service;

import com.wanted.clone.oneport.payments.application.port.out.pg.PgWidget;
import com.wanted.clone.oneport.payments.application.service.dto.PaymentRequestDto;
import com.wanted.clone.oneport.payments.representation.port.in.PaymentCommonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentCommonService implements PaymentCommonUseCase {
    private final Set<PgWidget> pgWidgets;
    private final Map<String, PgWidget> pgWidgetSelector = new HashMap<>();

    @PostConstruct
    public void init() {
        pgWidgets.forEach(pgWidget -> {
            String originalPgName = pgWidget.getClass().getSimpleName().split("Widget")[0];
            pgWidgetSelector.put(originalPgName, pgWidget);
        });
    }

    @Override
    public String renderPgUi(PaymentRequestDto paymentRequestDto, String pageType) throws Exception {
        String pgCorpName = Optional.ofNullable(paymentRequestDto.getPgCorpName())
                .orElseThrow(() -> new IllegalArgumentException("PG Corp Name cannot be null"))
                .toLowerCase();

        PgWidget pgWidget = pgWidgetSelector.get(pgCorpName);
        switch (pageType) {
            case "checkout":
                return pgWidget.checkout();
            case "success":
                return pgWidget.success();
            case "fail":
                return pgWidget.fail();
            default:
                throw new IllegalArgumentException("Invalid pageType name: " + pageType);
        }

    }
}
