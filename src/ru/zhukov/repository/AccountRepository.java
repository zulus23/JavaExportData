package ru.zhukov.repository;

import ru.zhukov.domain.AccountRecord;

import java.util.List;

/**
 * Created by Gukov on 28.03.2016.
 */
public interface AccountRepository {

    List<AccountRecord> listAccountRecords();

}
