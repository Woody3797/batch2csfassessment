package ibf2022.batch2.csf.backend.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Bundle {
    String bundleId;
    String date;
    String title;
    String name;
    String comments;
    List<String> urls = new ArrayList<>();

    public String getBundleId() {
        return bundleId;
    }
    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public Bundle() {
    }

    public Bundle(String bundleId, String date, String title, String name, String comments, List<String> urls) {
        this.bundleId = bundleId;
        this.date = date;
        this.title = title;
        this.name = name;
        this.comments = comments;
        this.urls = urls;
    }

    public static Bundle convertFromDoc(Document doc) {
        Bundle b = new Bundle();
        b.setBundleId(doc.getString("bundleId"));
        b.setDate(doc.getDate("date").toString());
        b.setTitle(doc.getString("title"));
        b.setName(doc.getString("name"));
        b.setComments(doc.getString("comments"));
        b.setUrls(doc.getList("urls", String.class));

        return b;
    }

    public JsonObject toJson() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (String url: urls) {
            jab.add(url);
        }

        return Json.createObjectBuilder()
        .add("bundleId", getBundleId())
        .add("date", getDate())
        .add("title", getTitle())
        .add("name", getName())
        .add("comments", getComments())
        .add("urls", jab.build())
        .build();
    }
    
}
