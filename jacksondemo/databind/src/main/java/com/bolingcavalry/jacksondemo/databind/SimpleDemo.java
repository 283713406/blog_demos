package com.bolingcavalry.jacksondemo.databind;

import com.bolingcavalry.jacksondemo.beans.TwitterEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: (这里用一句话描述这个类的作用)
 * @author: willzhao E-mail: zq2599@gmail.com
 * @date: 2020/8/24 8:25
 */
public class SimpleDemo {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDemo.class);

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        logger.info("以下是序列化操作");

        // 对象 -> 字符串
        TwitterEntry twitterEntry = new TwitterEntry();
        twitterEntry.setId(123456L);
        twitterEntry.setFromUserId(101);
        twitterEntry.setToUserId(102);
        twitterEntry.setText("this is a message for serializer test");
        twitterEntry.setLanguageCode("zh");

        String jsonStr = mapper.writeValueAsString(twitterEntry);
        logger.info("序列化的字符串：{}", jsonStr);

        // 对象 -> 文件
        mapper.writeValue(new File("twitter.json"), twitterEntry);

        // 对象 -> byte数组
        byte[] array = mapper.writeValueAsBytes(twitterEntry);


        logger.info("\n\n以下是反序列化操作");

        // 字符串 -> 对象
        String objectJsonStr = "{\n" +
                "  \"id\":1125687077,\n" +
                "  \"text\":\"@stroughtonsmith You need to add a \\\"Favourites\\\" tab to TC/iPhone. Like what TwitterFon did. I can't WAIT for your Twitter App!! :) Any ETA?\",\n" +
                "  \"fromUserId\":855523, \n" +
                "  \"toUserId\":815309,\n" +
                "  \"languageCode\":\"en\"\n" +
                "}";


        TwitterEntry tFromStr = mapper.readValue(objectJsonStr, TwitterEntry.class);
        logger.info("从字符串反序列化的对象：{}", tFromStr);

        // 文件 -> 对象
        TwitterEntry tFromFile = mapper.readValue(new File("twitter.json"), TwitterEntry.class);
        logger.info("从文件反序列化的对象：{}", tFromStr);

        // 文件 -> 对象
        TwitterEntry tFromBytes = mapper.readValue(array, TwitterEntry.class);
        logger.info("从byte数组反序列化的对象：{}", tFromBytes);

        // 字符串网络地址 -> 对象
        String testJsonDataUrl = "https://raw.githubusercontent.com/zq2599/blog_demos/master/files/twitteer_message.json";

        TwitterEntry tFromUrl = mapper.readValue(new URL(testJsonDataUrl), TwitterEntry.class);
        logger.info("从网络地址反序列化的对象：{}", tFromUrl);


        logger.info("\n\n以下是集合序列化操作");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "tom");
        map.put("age", 11);

        Map<String, String> addr = new HashMap<>();
        addr.put("city","深圳");
        addr.put("street", "粤海");

        map.put("addr", addr);

        String mapJsonStr = mapper.writeValueAsString(map);
        logger.info("HashMap序列化的字符串：{}", mapJsonStr);

        logger.info("\n\n以下是集合反序列化操作");
        Map<String, Object> mapFromStr = mapper.readValue(mapJsonStr, new TypeReference<Map<String, Object>>() {});
        logger.info("从字符串反序列化的HashMap对象：{}", mapFromStr);

        //
        JsonNode jsonNode = mapper.readTree(mapJsonStr);
        String name = jsonNode.get("name").asText();
        int age = jsonNode.get("age").asInt();
        String city = jsonNode.get("addr").get("city").asText();
        String street = jsonNode.get("addr").get("street").asText();

        logger.info("用JsonNode对象和API反序列化得到的数：name[{}]、age[{}]、city[{}]、street[{}]", name, age, city, street);

    }
}
