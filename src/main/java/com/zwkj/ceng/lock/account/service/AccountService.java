package com.zwkj.ceng.lock.account.service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zwkj.ceng.lock.RedisLock;
import com.zwkj.ceng.lock.account.dao.Account;
import com.zwkj.ceng.lock.account.dao.AccountRequest;
import com.zwkj.ceng.lock.account.error.AccountErrorCode;
import com.zwkj.ceng.mapper.AccountMapper;

@Service
public class AccountService {

	@Autowired
	AccountMapper accountMapper;

	static final Lock LOCK = new ReentrantLock();

	ConcurrentLinkedQueue<BigDecimal> linkedQueue = new ConcurrentLinkedQueue();

	ConcurrentHashMap<String, ConcurrentLinkedQueue<BigDecimal>> concurrentHashMap = new ConcurrentHashMap<>();
	@Autowired
	RedisLock redisLock;

//	// 根据id获取用户信息
//	public Account getAccountById(String id) {
//		return accountMapper.selectByPrimaryKey(id);
//	}
//
//	// 根据id保存用户信息
//	public int updateAccountById(Account account) {
//		return accountMapper.updateByPrimaryKey(account);
//	}
//
//	// 根据id获取账户余额
//	public BigDecimal getBalanceById(String id) {
//		return accountMapper.getBalanceById(id);
//	}

//	// 1.不考虑并发问题
//	public AccountErrorCode transferNoUesLock(String id, BigDecimal deduction) {
//		BigDecimal balance = getBalanceById(id);
//		Account account = getAccountById(id);
//		return balanceCompareAndSave(deduction, balance, account);
//	}
//
//	// 2.使用Lock
//	public AccountErrorCode transferWithLock(String id, BigDecimal deduction) {
//		if (!LOCK.tryLock()) {
//			return AccountErrorCode.LOCK_ERROR;
//		}
//		BigDecimal balance = getBalanceById(id);
//		Account account = getAccountById(id);
//		try {
//			return balanceCompareAndSave(deduction, balance, account);
//		} finally {
//			LOCK.unlock();
//		}
//	}
//
//	// 3.使用synchronized
//	public AccountErrorCode transferWithSyn(String id, BigDecimal deduction) {
//		synchronized (this) {
//			BigDecimal balance = this.getBalanceById(id);
//			Account account = this.getAccountById(id);
//			if (balance.compareTo(deduction) != -1) {
//				balance = balance.subtract(deduction);
//				account.setBalance(balance);
//				int result = updateAccountById(account);
//				if (result == -1) {
//					return AccountErrorCode.SAVE_ERROR;
//				} else {
//					return AccountErrorCode.SUCCESS;
//				}
//			} else {
//				return AccountErrorCode.DEDUCT_ERROR;
//			}
//		}
//	}
//
//	// 4.使用linkedQueue
//	public AccountErrorCode transferWithConcurentLink(String id, BigDecimal deduction) {
//		linkedQueue.add(deduction);
//		ConcurrentLinkedQueue<BigDecimal> linkedQueue = concurrentHashMap.get(id);
//		if (linkedQueue == null)
//			if (!LOCK.tryLock()) {
//				return AccountErrorCode.LOCK_ERROR;
//			}
//		try {
//			Iterator iterator = linkedQueue.iterator();
//			BigDecimal balance = getBalanceById(id);
//			Account account = getAccountById(id);
//			boolean lessBalance = false;
//			while (iterator.hasNext()) {
//				BigDecimal deduct = (BigDecimal) iterator.next();
//				if (balance.compareTo(deduct) != -1) {
//					balance = balance.subtract(deduct);
//					linkedQueue.poll();
//				} else {
//					lessBalance = true;
//					break;
//				}
//			}
//			account.setBalance(balance);
//			int result = updateAccountById(account);
//			if (result == -1) {
//				return AccountErrorCode.SAVE_ERROR;
//			} else {
//				if (lessBalance) {
//					return AccountErrorCode.DEDUCT_ERROR;
//				}
//				return AccountErrorCode.SUCCESS;
//			}
//		} finally {
//			// linkedQueue.clear(); 清空队列
//			LOCK.unlock();
//		}
//	}
//
//	public AccountErrorCode transferWithRedis(AccountRequest accountRequest) {
//		try {
//			if (redisLock.lockRetry(accountRequest.getId())) {
//				BigDecimal deduction = accountRequest.getDeduction();
//				Account account = getAccountById(accountRequest.getId());
//				BigDecimal balance = account.getBalance();
//				if (balance.compareTo(deduction) >= 0) {
//					balance = balance.subtract(deduction);
//					account.setBalance(balance);
//					int result = updateAccountById(account);
//					if (result == -1) {
//						return AccountErrorCode.SAVE_ERROR;
//					} else {
//						return AccountErrorCode.SUCCESS;
//					}
//				}
//				return AccountErrorCode.DEDUCT_ERROR;
//			} else {
//				return AccountErrorCode.LOCK_ERROR;
//			}
//		} finally {
//			redisLock.unlock(accountRequest.getId());
//		}
//	}
//
//	private AccountErrorCode balanceCompareAndSave(BigDecimal deduction, BigDecimal old, Account account) {
//		BigDecimal balance = old;
//		if (balance.compareTo(deduction) >= 0) {
//			balance = balance.subtract(deduction);
//			account.setBalance(balance);
//			int result = updateAccountById(account);
//			if (result == -1) {
//				return AccountErrorCode.SAVE_ERROR;
//			} else {
//				return AccountErrorCode.SUCCESS;
//			}
//		}
//		return AccountErrorCode.DEDUCT_ERROR;
//	}
}
