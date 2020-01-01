package com.zwkj.ceng.lock.account.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zwkj.ceng.lock.account.dao.Account;
import com.zwkj.ceng.lock.account.dao.AccountRequest;
import com.zwkj.ceng.lock.account.error.AccountErrorCode;
import com.zwkj.ceng.lock.account.service.AccountService;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
	@Autowired
	AccountService accountService;

	@GetMapping("/single/{id}")
	public Account getAccountById(@PathVariable("id") String id) {
		return accountService.getAccountById(id);
	}

	@GetMapping("/balance/{id}")
	public BigDecimal getBalcanceById(@PathVariable("id") String id) {
		return accountService.getBalanceById(id);
	}

	@PostMapping("/transferNoUesLock/")
	public AccountErrorCode transferNoUesLock(@RequestBody @Valid AccountRequest accountRequest) {
		return accountService.transferNoUesLock(accountRequest.getId(), accountRequest.getDeduction());
	}

	@PostMapping("/transferWithLock/")
	public AccountErrorCode transferWithLock(@RequestBody @Valid AccountRequest accountRequest) {
		return accountService.transferWithLock(accountRequest.getId(), accountRequest.getDeduction());
	}

	@PostMapping("/transferWithSyn/")
	public AccountErrorCode transferWithSyn(@RequestBody @Valid AccountRequest accountRequest) {
		return accountService.transferWithSyn(accountRequest.getId(), accountRequest.getDeduction());
	}

	@PostMapping("/transferWithConcurentLink/")
	public AccountErrorCode transferWithConcurentLink(@RequestBody @Valid AccountRequest accountRequest) {
		return accountService.transferWithConcurentLink(accountRequest.getId(), accountRequest.getDeduction());
	}

	@PostMapping("/transferWithRedis/")
	public AccountErrorCode transferWithRedis(@RequestBody @Valid AccountRequest accountRequest) {
		return accountService.transferWithRedis(accountRequest);
	}

}
