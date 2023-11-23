package com.restapi.elk.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.*;
import com.restapi.elk.domain.Technology;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping(value="/rest/api")
public class ElasticSearchController
{

    private final ElasticsearchClient  elasticsearchClient;


    public ElasticSearchController(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @GetMapping("/indexes")
    public String[] getIndexes() throws IOException {
        GetIndexRequest request = new GetIndexRequest.Builder().index("*").build();
        GetIndexResponse response = elasticsearchClient.indices().get(request);

        Map<String, ?> indicesMap = response.result();
        return indicesMap.keySet().toArray(new String[0]);
    }

    @PostMapping
    public ResponseEntity<String> createIndex(@RequestBody String indexName) throws IOException {
        CreateIndexResponse createResponse = elasticsearchClient.indices().create(
                new CreateIndexRequest.Builder()
                        .index(indexName)
                        .build());
        return new ResponseEntity<>("Index created successfully", HttpStatus.CREATED);
    }

    @PostMapping(value = "/{indexname}/document", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createDocument(@PathVariable String indexname, @RequestBody Technology document) throws IOException {
        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexname)
                .id(String.valueOf(document.getId()))
                .document(document)
        );

        return new ResponseEntity<>("Document created successfully", HttpStatus.CREATED);
    }

    @GetMapping(value="/{indexname}/document/{docid}")
    public ResponseEntity<String> getDocument(@PathVariable String indexname, @PathVariable String docid) throws IOException {
        Technology technology = elasticsearchClient.get(s -> s.index(indexname).id(docid), Technology.class).source();
        boolean technologyFound = Objects.nonNull(technology);
        String res = technologyFound ? technology.toString() : "Document (" + docid + ") not Found";
        return new ResponseEntity<String>(res, technologyFound ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteIndexes() throws  IOException {
        GetIndexRequest request = new GetIndexRequest.Builder().index("*").build();
        GetIndexResponse response = elasticsearchClient.indices().get(request);

        Map<String, ?> indicesMap = response.result();
        for ( String indexName: indicesMap.keySet().toArray(new String[0])) {
            elasticsearchClient.indices().delete(new DeleteIndexRequest.Builder().index(indexName).build());
        }

        return new ResponseEntity<>("Indexes deleted", HttpStatus.OK);
    }
}
