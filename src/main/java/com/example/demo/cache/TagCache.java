package com.example.demo.cache;

import com.example.demo.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("心情");
        //喜怒哀乐悲
        program.setTags(Arrays.asList("喜", "怒", "哀", "乐", "悲"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("心理");
        //
        framework.setTags(Arrays.asList("抑郁", "强迫", "焦虑", "分裂", "适应障碍"));
        tagDTOS.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("现象");
        server.setTags(Arrays.asList("自闭", "内省", "温和", "社会交往", "压力", "失眠"));
        tagDTOS.add(server);

        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
