package com.project.notice_mybatis.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// preHandle, postHandle, afterCompletion, afterConcurrentHandlingStarted 4개 포함.
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //컨트롤러의 메서드에 매핑된 특정 uri를 호출했을때 컨트롤러에 접근하기전 실행되는 메서드.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.debug("===============================================");
        logger.debug("==================== BEGIN ====================");
        logger.debug("Request URI ===> " + request.getRequestURI());
        logger.debug("===============================================");

        return super.preHandle(request, response, handler);
    }

    //컨트롤러 경유 후 view로 결과 전달하기 전에 실행되는 메서드.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("==================== END ======================");
        logger.debug("===============================================");
    }

}
