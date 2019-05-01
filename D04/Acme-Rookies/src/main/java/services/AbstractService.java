/*
 * AbstractService.java
 * 
 * Copyright (c) 2019 Group 16 of Design and Testing II, University of Seville
 */

package services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.AbstractRepository;
import domain.DomainEntity;

@Service
@Transactional
public abstract class AbstractService<R extends AbstractRepository<E>, E extends DomainEntity> {

	////////////////////////////////////////////////////////////////////////////////
	// Managed repository

	@Autowired
	protected R			repository;

	////////////////////////////////////////////////////////////////////////////////
	// Validator

	@Autowired
	protected Validator	validator;

	////////////////////////////////////////////////////////////////////////////////
	// Other fields

	protected Class<R>	repositoryClass;
	protected Class<E>	domainClass;


	////////////////////////////////////////////////////////////////////////////////
	// Constructor

	@SuppressWarnings("unchecked")
	public AbstractService() {
		super();
		this.repositoryClass = (Class<R>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.domainClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	////////////////////////////////////////////////////////////////////////////////
	// Utility methods

	@SuppressWarnings("rawtypes")
	public <C> C instanceClass(final Class<C> c) {
		C instance = null;
		try {
			instance = c.newInstance();
			for (final Method method : c.getMethods())
				if (method.getName().startsWith("set"))
					switch (method.getParameterTypes()[0].getName()) {
					case "boolean":
					case "java.lang.Boolean":
						method.invoke(instance, Boolean.FALSE);
						break;
					case "byte":
					case "java.lang.Byte":
						method.invoke(instance, Byte.valueOf((byte) 0));
						break;
					case "short":
					case "java.lang.Short":
						method.invoke(instance, Short.valueOf((short) 0));
						break;
					case "int":
					case "java.lang.Integer":
						method.invoke(instance, Integer.valueOf(0));
						break;
					case "long":
					case "java.lang.Long":
						method.invoke(instance, Long.valueOf(0L));
						break;
					case "float":
					case "java.lang.Float":
						method.invoke(instance, Float.valueOf(0.0f));
						break;
					case "double":
					case "java.lang.Double":
						method.invoke(instance, Double.valueOf(0.0d));
						break;
					case "char":
					case "java.lang.Character":
						method.invoke(instance, ' ');
						break;
					case "java.lang.CharSequence":
					case "java.lang.String":
						method.invoke(instance, "");
						break;
					case "java.lang.Iterable":
					case "java.util.Collection":
					case "java.util.List":
					case "java.util.AbstractList":
					case "java.util.ArrayList":
						method.invoke(instance, new ArrayList());
						break;
					case "java.util.Map":
					case "java.util.AbstractMap":
					case "java.util.HashMap":
						method.invoke(instance, new HashMap());
						break;
					case "java.util.Date":
						method.invoke(instance, new Date());
						break;
					default:
						method.invoke(instance, new Object[] {
							null
						});
						break;
					}
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
		return instance;
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	public E create() {
		return this.instanceClass(this.domainClass);
	}

	public E save(final E entity) {
		Assert.notNull(entity);
		return this.repository.save(entity);
	}

	public Iterable<E> save(final Iterable<E> entities) {
		Assert.notNull(entities);
		return ((CrudRepository<E, Integer>) this.repository).save(entities);
	}

	public void delete(final E entity) {
		Assert.notNull(entity);
		this.repository.delete(entity);
	}

	public void delete(final Iterable<E> entities) {
		Assert.notNull(entities);
		this.repository.delete(entities);
	}

	public E findOne(final int id) {
		return this.repository.findOne(id);
	}

	public List<E> findAll() {
		return this.repository.findAll();
	}

}
