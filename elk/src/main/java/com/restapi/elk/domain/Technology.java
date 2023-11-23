package com.restapi.elk.domain;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName="technology")
public class Technology {

    @Id
    private Integer id;

    @Field(type=FieldType.Text, name = "name")
    private String name;

    @Field(type=FieldType.Text, name = "selfRating")
    private String selfRating;
}