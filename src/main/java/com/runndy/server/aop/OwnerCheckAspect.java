package com.runndy.server.aop;

import java.nio.file.AccessDeniedException;
import java.util.Objects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OwnerCheckAspect {

  @Before("@annotation(ownerOnly)")
  public void checkOwner(JoinPoint jp, OwnerOnly ownerOnly) throws AccessDeniedException {
    String paramName = ownerOnly.param();
    MethodSignature sig = (MethodSignature) jp.getSignature();
    String[] names = sig.getParameterNames();
    Object[] args = jp.getArgs();
    Object targetValue = null;
    for (int i = 0; i < names.length; i++) {
      if (names[i].equals(paramName)) {
        targetValue = args[i];
      }
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !Objects.equals(auth.getName(), String.valueOf(targetValue))) {
      throw new AccessDeniedException("owner only");
    }
  }
}
