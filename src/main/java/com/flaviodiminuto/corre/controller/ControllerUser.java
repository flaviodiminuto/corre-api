package com.flaviodiminuto.corre.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flaviodiminuto.corre.domain.entity.User;
import com.flaviodiminuto.corre.mapper.MapperUser;
import com.flaviodiminuto.corre.presenter.PresenterRegisterUser;
import com.flaviodiminuto.corre.presenter.PresenterUser;
import com.flaviodiminuto.corre.repository.RepositoryUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Spring Rest Controller para transacionar Usuarios
 * @author flaviodiminuto
 *
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*/*")
public class ControllerUser {
	
	@Autowired
	RepositoryUser repository;
	@Autowired
	MapperUser mapper;
	
    private static final Logger logger = LoggerFactory.getLogger(ControllerUser.class);
    private static final String USER_NOT_FOUND_MESSAGE = "{\"message\":\"User not found\"}";
    
    @GetMapping
    @ApiOperation(value = "retuns an users list")
    public ResponseEntity<List<PresenterUser>> getAll(){
    	logger.info("GET- Searching all users");
    	List<User> userList = (List<User>) repository.findAll();
    	List<PresenterUser> presenterUserList= mapper.entityToPresenter(userList);

    	logger.info(String.format("%d found users", presenterUserList.size()));
    	return ResponseEntity.ok(presenterUserList);
    }
    
    @PostMapping
    @ApiOperation(value = "Saves a new User")
    public ResponseEntity<PresenterUser> save(@RequestBody PresenterRegisterUser presenterUser){
    	logger.info("POST - Registering a new user");
    	User persistedUser = repository.save(mapper.presenterToNewEntity(presenterUser));
    	PresenterUser persistedPresenterUser = mapper.entityToPresenter(persistedUser);
    	
    	logger.info(String.format("Registered new User - ID : %d", persistedUser.getId()));
    	return ResponseEntity.status(HttpStatus.CREATED).body(persistedPresenterUser);
    }
    
    @PutMapping
    @ApiOperation(value = "Updates an existing User")
    public ResponseEntity<?> update(@RequestBody PresenterUser presenterUser){
    	logger.info(String.format("PUT - Updating a user ID : %d",presenterUser.getId()));
    	
		//Campos mutaveis
		String login = presenterUser.getLogin();
		String password = presenterUser.getPassword();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		
		int changes = repository.update(presenterUser.getId(),login,password,date,time);
		if(changes > 0) {
    		logger.info(String.format("Update user sucessful - ID : %d", presenterUser.getId()));
    		return ResponseEntity.ok().body(presenterUser);
    	}else {
    		logger.info(String.format("Update user stopped - User not found - ID : %s", String.valueOf(presenterUser.getId())));
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_MESSAGE);
    	}
    }
    
    @DeleteMapping
    @ApiOperation(value = "Delete an User")
    public ResponseEntity<String> delete(@RequestBody PresenterUser presenterUser){
    	logger.info(String.format("DELETE - Deleting user - ID : %d", presenterUser.getId()));
    	Optional<User> user = repository.findById(presenterUser.getId());
    	if(user.isPresent()) {
    		PresenterUser persistedUser = mapper.entityToPresenter(user.get());
    		if(persistedUser.equals(presenterUser)) {
	    		repository.delete(user.get());
	    		logger.info(String.format("Delete user sucessful - ID : %d", presenterUser.getId()));
	    		return ResponseEntity.ok().body("{\"message\":\"User deleted\"}");
    		}
    	}
    	
		logger.info(String.format("Delete user stopped - User not found - ID : %s", String.valueOf(presenterUser.getId())));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_MESSAGE);
    }
}
