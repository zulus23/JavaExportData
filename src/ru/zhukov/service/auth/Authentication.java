package ru.zhukov.service.auth;

import ru.zhukov.domain.Database;
import ru.zhukov.dto.CurrentUser;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Zhukov on 15.03.2016.
 */
public interface Authentication {
    CompletableFuture authentication(String username, String password, Database database);
}
