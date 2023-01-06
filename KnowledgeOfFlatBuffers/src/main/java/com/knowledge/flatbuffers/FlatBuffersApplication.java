package com.knowledge.flatbuffers;

import com.google.flatbuffers.FlatBufferBuilder;
import com.knowledge.flatbuffers.models.Person;

import java.nio.ByteBuffer;

/**
 * @author jieguangzhi
 * @date 2022-12-12
 */
public class FlatBuffersApplication {
    public static void main(String[] args) {
        final FlatBufferBuilder builder = new FlatBufferBuilder();
        builder.finish(Person.createPerson(builder, builder.createString("Luke"), 18));

        final byte[] array = builder.sizedByteArray();

        final ByteBuffer buffer = ByteBuffer.wrap(array);
        final Person person = Person.getRootAsPerson(buffer);
        person.mutateAge(19);
        System.out.println(person.name() + " " + person.age());

    }
}
