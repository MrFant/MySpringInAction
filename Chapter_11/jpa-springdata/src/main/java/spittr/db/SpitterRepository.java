package spittr.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spittr.domain.Spitter;

/**
 * Repository interface with operations for {@link Spitter} persistence.
 * @author habuma
 */
/*
* @Comment : 要查询的对象类型是通过如何参数化JpaRepository接口来确定的，而不是方法名称中的主题。
*
* @Author  : yii.fant@gmail.com
* @Date    : 2019-04-22 22:05
*/
public interface SpitterRepository extends JpaRepository<Spitter, Long>, SpitterSweeper {
	  
	Spitter findByUsername(String username);
	
	List<Spitter> findByUsernameOrFullNameLike(String username, String fullName);

}
