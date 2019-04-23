package spittr.db.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import spittr.db.SpitterRepository;
import spittr.domain.Spitter;

@Repository
public class JpaSpitterRepository implements SpitterRepository {
	/*
	* @Comment : 由于EntityManager并不是线程安全的，本来并不适合直接注入到repository这样共享的bean里面
	*  但是使用PersistenceContext 可以给EntityManager一个代理，使得线程安全
	* @Author  : yii.fant@gmail.com
	* @Date    : 2019-04-22
	*/
	@PersistenceContext
	private EntityManager entityManager;

	public long count() {
		return findAll().size();
	}

	public Spitter save(Spitter spitter) {
		entityManager.persist(spitter);
		return spitter;
	}

	public Spitter findOne(long id) {
		return entityManager.find(Spitter.class, id);
	}

	public Spitter findByUsername(String username) {		
		return (Spitter) entityManager.createQuery("select s from Spitter s where s.username=?").setParameter(1, username).getSingleResult();
	}

	public List<Spitter> findAll() {
		return (List<Spitter>) entityManager.createQuery("select s from Spitter s").getResultList();
	}
	
}
