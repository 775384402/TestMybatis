package com.zwkj.ceng.lock.account.error;

public enum AccountErrorCode {

    SUCCESS(1000, "SUCCESS"), SAVE_ERROR(-1000, "保存失败"), DEDUCT_ERROR(-1001, "扣款失败"), LOCK_ERROR(-1002, "获取锁失败");


    int num;
    String message;

    AccountErrorCode(int num, String message) {
        this.num = num;
        this.message = message;
    }
}
