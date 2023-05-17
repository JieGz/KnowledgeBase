package com.knowledge.flatbuffers;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.flatbuffers.FlatBufferBuilder;
import com.knowledge.flatbuffers.models.Person;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

        List<String> lines = Lists.newArrayList("1", "2", "3");

        final List<Map<Object, Object>> maps = lines.stream().map(line -> {
            if (Objects.equals(line, "2")) {
                return Collections.emptyMap();
            }
            Map<Object, Object> map = new HashMap<>(1);
            map.put(line, line);
            return map;
        }).filter(map -> map.size() != 0).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(maps));

    }


}
