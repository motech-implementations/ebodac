package org.motechproject.ebodac.util.json.serializer;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.motechproject.ebodac.domain.Subject;

import java.io.IOException;

public class CustomSubjectSerializer extends JsonSerializer<Subject> {

    @Override
    public void serialize(Subject subject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        subject.setVisits(null);
        subject.setEnrollment(null);
        jsonGenerator.writeObject(subject);
    }
}
