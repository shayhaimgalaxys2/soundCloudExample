package com.example.shay.soundcloudexample.project.dataObjects;

import com.example.shay.soundcloudexample.project.parser.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shay on 17/02/2016.
 */
public class SearchResultResponse {

    private List<Collection> collectionList;
    private int total_results;
    private String next_href;

    public SearchResultResponse(JSONObject jsonObject) {

        collectionList = new ArrayList<>();

        try {
            JSONArray collectionsJson = jsonObject.getJSONArray("collection");
            for (int i = 0; i < collectionsJson.length(); i++) {

                JSONObject jsonObject1 = (JSONObject) collectionsJson.get(i);
                Collection collection = new Collection(jsonObject1);
                collectionList.add(collection);


            }


            this.total_results = Parser.jsonParse(jsonObject, "total_results", this.total_results);
            this.next_href = Parser.jsonParse(jsonObject, "next_href", Parser.createTempString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Collection> getCollectionList() {
        return collectionList;
    }

    public int getTotal_results() {
        return total_results;
    }

    public String getNext_href() {
        return next_href;
    }
}
