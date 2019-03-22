package spittr.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import spittr.Spitter;

import java.util.ArrayList;
import java.util.List;

public class SpitterUserService implements UserDetailsService {

    private final SpitterRepository spitterRepository;

    /*
    * @Comment : 自定义用户服务的关键在于这里，它不关心用户数据存储在哪里，可以从任何地方获
    *           取数据（关系型数据库、文档数据库、图数据库甚至伪造的）。它只是要获取一个User对象（UserDetails的具体实现)
    *
    * @Author  : yii.fant@gmail.com
    * @Date    : 2019-03-21
    */
    public SpitterUserService(SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Spitter spitter=spitterRepository.findByUsername(username);
        Spitter spitter=new Spitter("fafafa","fafafa","ttttt","sdfafa","1241234124@qq.com");
        if (null != spitter){
            List<GrantedAuthority> authorities= new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(
                    spitter.getUsername(),
                    spitter.getPassword(),
                    authorities
            );
        }

        throw new UsernameNotFoundException(
                "User '"+ username +"' not found."
        );
    }
}
