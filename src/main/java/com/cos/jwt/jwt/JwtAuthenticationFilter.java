package com.cos.jwt.jwt;

import com.cos.jwt.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음.
// /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을함.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    //login요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter  로그인 실행중");

        // 1. username, password 받아서
        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while ((input = br.readLine()) != null){
//                System.out.println(input);
//            }
            ObjectMapper om = new ObjectMapper(); //json데이터 파싱시켜줌
            User user = om.readValue(request.getInputStream(), User.class);// user에 담아줌
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            //PrincipalDetailsService의 loadUserByUsername이 실행된 후 저장이면 authentication이 리턴됨
            //DB에 있는 username과 password가 일치한다.
            // authentication에 내 로그인정보가 담김
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            //authentication객체가 session영역에 저장됨. => 로그인이 되었다는 뜻.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인완료됨? "+principalDetails.getUser().getUsername());//로그인이 정상적으로 저장되었다는 뜻.
            //authentication 객체가 session영역에 저장됨.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 2. 정상인지 로그인 시도, authenticationManager로 로그인 시도를 하면
        // PrincipalDetailsService가 호출

        // 3. PrincipalDetails를 세션에 담고(권한 관리를 위해서)

        // 4. JWT토큰을 만들어서 응담해주면 됨.
        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    //JWT토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response해주면됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication실행됨 : 인증이 완료되었다는뜻");
        super.successfulAuthentication(request,response,chain,authResult);
    }
}
