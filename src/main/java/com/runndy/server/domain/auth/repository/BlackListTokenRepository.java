package com.runndy.server.domain.auth.repository;

import com.myclass360.server.domain.auth.entity.BlackListToken;
import org.springframework.data.repository.CrudRepository;

public interface BlackListTokenRepository extends CrudRepository<BlackListToken, String> {
}
