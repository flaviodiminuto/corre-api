package com.flaviodiminuto.corre.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flaviodiminuto.corre.domain.entity.User;
import com.flaviodiminuto.corre.presenter.PresenterRegisterUser;
import com.flaviodiminuto.corre.presenter.PresenterUser;
import com.flaviodiminuto.corre.repository.RepositoryUser;

/**
 * Classe responsável pelo mapeamento entre a entidade {@link User} e ({@link PresenterUser})
 * @author flaviodiminuto
 *
 */
@Component
public class MapperUser {
	
	@Autowired
	RepositoryUser repository;
	
	/**
	 * Mapeia uma instancia do tipo {@link PresenterRegisterUser} nao persistido para uma instancia do tipo {@link User}<br>
	 * o campo <b>"Presenter.id"</b> é ignorado
	 * @param presenterUser -  Recebe um um {@link PresenterRegisterUser}
	 * @return retornar um {@link User} <br/>
	 * sem id<br/>
	 * com data atual como data de criacao <br/>
	 */
	public User presenterToNewEntity(PresenterRegisterUser presenterUser) {
		User user = new User();
		user.setLogin(presenterUser.getLogin());
		user.setPassword(presenterUser.getPassword());
		user.setCreatedDate(LocalDate.now());
		user.setCreatedTime(LocalTime.now());
		return user;
	}
	
	/**
	 * Converte uma instancia do tipo PresenterUser para uma instancia do tio User
	 * @param presenterUser - Recebe um objeto do tipo {@link PresenterUser}
	 * @return retorna um {@link Optional} contendo um usuario caso este esteja persistido no banco
	 */
	public Optional<User> presenterToEntity(PresenterUser presenterUser) {
		Optional<User> optional =  repository.findById(presenterUser.getId());
		PresenterUser validator = entityToPresenter(optional.get());
		
		if(optional.isPresent() && validator.equals(presenterUser))
			return optional;
		else
			return Optional.empty();
	}
	
	/**
	 * Mapeia uma lista de instancias do tipo {@link PresenterUser} para uma lista de instancias do tipo {@link User}
	 * @param presenterUserList - Lista de instancias do tipo {@link PresenterUser}
	 * @return Lista do tipo de instancias do tipo {@link User}
	 */
	public List<User> presenterToEntity(List<PresenterUser> presenterUserList) {
		Set<Long> ids = presenterUserList
		.stream()
		.map(PresenterUser::getId)
		.collect(Collectors.toSet());
		
		return (List<User>) repository.findAllById(ids);
	}
	
	/**
	 * Mapeia uma instancia do tipo {@link User} para uma instancia do tipo {@link PresenterUser}
	 * @param user - Instancia do tipo {@link User}
	 * @return Instancia do tipo {@link PresenterUser}
	 */
	public PresenterUser entityToPresenter(User user) {
		PresenterUser presentUser = new PresenterUser();
		presentUser.setId(user.getId());
		presentUser.setLogin(user.getLogin());
		presentUser.setPassword(user.getPassword());
		
		return presentUser;
	}
	
	/**
	 * Mapeia uma lista de instancias do tipo {@link User} para uma lista de instancias do tipo {@link PresenterUser}
	 * @param userList - Lista de instancias do tipo {@link User}
	 * @return Lista de instancias do tipo {@link PresenterUser}
	 */
	public List<PresenterUser> entityToPresenter(List<User> userList){
		List<PresenterUser> presenterUserList = new ArrayList<>();
		userList.forEach(user ->{
			PresenterUser presenterUser = this.entityToPresenter(user);
			presenterUserList.add(presenterUser);
		});
		return presenterUserList;
	}
	
	
}
