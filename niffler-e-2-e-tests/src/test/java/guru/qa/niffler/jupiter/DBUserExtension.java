package guru.qa.niffler.jupiter;

import com.github.javafaker.Faker;
import guru.qa.niffler.db.model.*;
import guru.qa.niffler.db.repository.UserRepository;
import guru.qa.niffler.db.repository.UserRepositoryJdbc;
import guru.qa.niffler.jupiter.annotation.DbUser;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;


import java.util.*;

public class DBUserExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {
	public static final ExtensionContext.Namespace NAMESPACE
			= ExtensionContext.Namespace.create(DBUserExtension.class);

	Faker faker = new Faker();

	private final UserRepository userRepository = new UserRepositoryJdbc();


	@Override
	public void beforeEach(ExtensionContext extensionContext) {
		Map<String, Object> entities = new HashMap<>();
		Optional<DbUser> annotation = AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), DbUser.class);

		if (annotation.isPresent()) {
			DbUser annotationData = annotation.get();

			String username = annotationData.username().isEmpty() ? faker.name().username() : annotationData.username();
			String password = annotationData.password().isEmpty() ? faker.internet().password() : annotationData.password();

			UserAuthEntity userAuth = new UserAuthEntity();
			userAuth.setUsername(username);
			userAuth.setPassword(password);
			userAuth.setEnabled(true);
			userAuth.setAccountNonExpired(true);
			userAuth.setAccountNonLocked(true);
			userAuth.setCredentialsNonExpired(true);

			UserEntity user = new UserEntity();
			user.setUsername(username);
			user.setCurrency(CurrencyValues.RUB);

			UserAuthEntity createdUserAuth = userRepository.createInAuth(userAuth);
			UserEntity createdUserEntity = userRepository.createInUserdata(user);

			entities.put("userAuth", createdUserAuth);
			entities.put("user", createdUserEntity);

		}
		extensionContext.getStore(NAMESPACE).put(extensionContext.getUniqueId(), entities);

	}

	@Override
	public void afterTestExecution(ExtensionContext extensionContext) {
		Map<String, Object> entities = (Map<String, Object>) extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class);

		UserEntity userEntity = (UserEntity) entities.get("user");
		UserAuthEntity userAuthEntity = (UserAuthEntity) entities.get("userAuth");

		userRepository.deleteInAuthById(userAuthEntity.getId());
		userRepository.deleteInUserdataById(userEntity.getId());
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
		return parameterContext.getParameter()
				.getType()
				.isAssignableFrom(UserAuthEntity.class);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
		return extensionContext.getStore(NAMESPACE)
				.get(extensionContext.getUniqueId(), Map.class)
				.get("userAuth");
	}
}
