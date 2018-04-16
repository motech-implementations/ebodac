package org.motechproject.ebodac.util.json.serializer;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.motechproject.ebodac.domain.SubjectEnrollments;

import java.io.IOException;

public class CustomEnrollmentSerializer extends JsonSerializer<SubjectEnrollments> {

    @Override
    public void serialize(SubjectEnrollments enrollment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (enrollment != null) {
            jsonGenerator.writeString(enrollment.getStatus().getValue());
        } else {
            jsonGenerator.writeString("");
        }
    }
}
