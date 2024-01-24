package guru.qa.niffler.jupiter;

import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.TestData;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.jupiter.User.UserType.*;

public class UsersQueueExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

	public static final ExtensionContext.Namespace NAMESPACE
			= ExtensionContext.Namespace.create(UsersQueueExtension.class);

	private static Map<User.UserType, Queue<UserJson>> users = new ConcurrentHashMap<>();

	static {
		Queue<UserJson> commonQueue = new ConcurrentLinkedQueue<>();
		commonQueue.add(user("commonUser", "12345", COMMON));
		users.put(COMMON, commonQueue);

		Queue<UserJson> friendsQueue = new ConcurrentLinkedQueue<>();
		friendsQueue.add(user("duck", "12345", WITH_FRIENDS));
		friendsQueue.add(user("friend", "12345", WITH_FRIENDS));
		users.put(WITH_FRIENDS, friendsQueue);

		Queue<UserJson> invitationSentQueue = new ConcurrentLinkedQueue<>();
		invitationSentQueue.add(user("invitationSentUser", "12345", INVITATION_SENT));
		users.put(INVITATION_SENT, invitationSentQueue);


		Queue<UserJson> invitationReceivedQueue = new ConcurrentLinkedQueue<>();
		invitationReceivedQueue.add(user("invitationReceivedUser", "12345", RECEIVED));
		users.put(RECEIVED, invitationReceivedQueue);
	}

	@Override
	public void beforeEach(ExtensionContext context) {
		List<Parameter> beforeMethodParameters = Arrays.stream(context.getRequiredTestClass().getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(BeforeEach.class))
				.map(Executable::getParameters)
				.flatMap(Arrays::stream)
				.filter(parameter -> parameter.isAnnotationPresent(User.class))
				.toList();


		List<Parameter> methodParameters = Arrays.stream(context.getRequiredTestMethod().getParameters())
				.filter(parameter -> parameter.isAnnotationPresent(User.class))
				.toList();

		List<Parameter> matchedParameters = new ArrayList<>();
		matchedParameters.addAll(beforeMethodParameters);
		matchedParameters.addAll(methodParameters);


		Map<User.UserType, UserJson> testCandidates = new HashMap<>();

		for (Parameter parameter : matchedParameters) {

			User annotation = parameter.getAnnotation(User.class);
			if (testCandidates.containsKey(annotation.value())) {
				continue;
			}

			UserJson testCandidate = null;
			Queue<UserJson> queue = users.get(annotation.value());
			while (testCandidate == null) {
				testCandidate = queue.poll();
			}
			testCandidates.put(annotation.value(), testCandidate);
			;
		}
		context.getStore(NAMESPACE).put(context.getUniqueId(), testCandidates);
	}


	@Override
	public void afterTestExecution(ExtensionContext context) {
		Map<User.UserType, UserJson> testCandidates = (Map<User.UserType, UserJson>) context.getStore(NAMESPACE)
				.get(context.getUniqueId(), Map.class);

		testCandidates.values().forEach(
				user -> users.get(user.testData().userType()).add(user)
		);
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
		return parameterContext.getParameter()
				.getType()
				.isAssignableFrom(UserJson.class) &&
				parameterContext.getParameter().isAnnotationPresent(User.class);
	}

	@Override
	public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
		return (UserJson) extensionContext.getStore(NAMESPACE)
				.get(extensionContext.getUniqueId(), Map.class)
				.get(parameterContext.getParameter().getAnnotation(User.class).value());
	}

	private static UserJson user(String username, String password, User.UserType userType) {
		return new UserJson(
				null,
				username,
				null,
				null,
				CurrencyValues.RUB,
				null,
				null,
				new TestData(
						password,
						userType
				)
		);
	}
}
