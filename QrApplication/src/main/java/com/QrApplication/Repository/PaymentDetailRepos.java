package com.QrApplication.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.PaymentDetail;

@Repository
public interface PaymentDetailRepos extends JpaRepository<PaymentDetail, UUID> {

}
