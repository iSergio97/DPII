
package services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	// Constructor

	public AbstractService() {
		super();
	}

	////////////////////////////////////////////////////////////////////////////////
	// CRUD methods

	@SuppressWarnings("rawtypes")
	public E create() {
		@SuppressWarnings("unchecked")
		final Class<E> domainClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		E bean = null;
		try {
			bean = domainClass.newInstance();
			for (final Method method : domainClass.getMethods())
				if (method.getName().startsWith("set"))
					switch (method.getParameterTypes()[0].getName()) {
					case "boolean":
					case "java.lang.Boolean":
						method.invoke(bean, Boolean.FALSE);
						break;
					case "byte":
					case "java.lang.Byte":
						method.invoke(bean, Byte.valueOf((byte) 0));
						break;
					case "short":
					case "java.lang.Short":
						method.invoke(bean, Short.valueOf((short) 0));
						break;
					case "int":
					case "java.lang.Integer":
						method.invoke(bean, Integer.valueOf(0));
						break;
					case "long":
					case "java.lang.Long":
						method.invoke(bean, Long.valueOf(0L));
						break;
					case "float":
					case "java.lang.Float":
						method.invoke(bean, Float.valueOf(0.0f));
						break;
					case "double":
					case "java.lang.Double":
						method.invoke(bean, Double.valueOf(0.0d));
						break;
					case "char":
					case "java.lang.Character":
						method.invoke(bean, ' ');
						break;
					case "java.lang.CharSequence":
					case "java.lang.String":
						method.invoke(bean, "");
						break;
					case "java.lang.Iterable":
					case "java.util.Collection":
					case "java.util.List":
					case "java.util.AbstractList":
					case "java.util.ArrayList":
						method.invoke(bean, new ArrayList());
						break;
					case "java.util.Map":
					case "java.util.AbstractMap":
					case "java.util.HashMap":
						method.invoke(bean, new HashMap());
						break;
					default:
						method.invoke(bean, new Object[] {
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
		return bean;
	}

	public E save(final E entity) {
		Assert.notNull(entity);
		return this.repository.save(entity);
	}

	public List<E> save(final Iterable<E> entities) {
		Assert.notNull(entities);
		return this.repository.save(entities);
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
