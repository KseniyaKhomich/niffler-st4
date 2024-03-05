package guru.qa.niffler.db.logging;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;

import java.util.Objects;

public class JsonAllureAppender {
	private final String templateName = "json.ftl";
	private final AttachmentProcessor<AttachmentData> attachmentProcessor = new DefaultAttachmentProcessor();

	public void logJson(String name, Object objectToJson) throws JsonProcessingException {
		if (!Objects.isNull(objectToJson)) {
			JsonAttachment jsonAttachment = new JsonAttachment(
					name,
					new ObjectMapper()
							.writerWithDefaultPrettyPrinter()
							.writeValueAsString(objectToJson)
			);
			attachmentProcessor.addAttachment(jsonAttachment, new FreemarkerAttachmentRenderer(templateName));
		}
	}
}
