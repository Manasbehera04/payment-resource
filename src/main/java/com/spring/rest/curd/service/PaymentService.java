package com.spring.rest.curd.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.spring.rest.curd.dao.PaymentDao;
import com.spring.rest.curd.dto.PaymentResponse;
import com.spring.rest.curd.model.Payment;

@Service
@Transactional
public class PaymentService {
	@Autowired
	private PaymentDao paymentDao;

	public PaymentResponse pay(Payment payment) {
		payment.setPaymentDate(new Date());
		String message = paymentDao.payNow(payment);
		PaymentResponse paymentResponse = new PaymentResponse();
		if (!StringUtils.isEmpty(message)) {
			paymentResponse.setStatus("Success");
			paymentResponse.setMessage(message);
		} else {
			paymentResponse.setStatus("Failure");
			paymentResponse.setMessage(message);
		}

		paymentResponse.setTxDate(new SimpleDateFormat("dd/mm/yyyy HH:mm:ss a").format(new Date()));
		return paymentResponse;
	}

	public PaymentResponse getTx(String vendor) {
		PaymentResponse paymentResponse = new PaymentResponse();
		List<Payment> payments = paymentDao.getTransactionInfo(vendor);
		if (!CollectionUtils.isEmpty(payments)) {
			paymentResponse.setStatus("Success");
			paymentResponse.setPayments(payments);
		} else {
			paymentResponse.setStatus("Failure");

		}
		return paymentResponse;

	}
}
