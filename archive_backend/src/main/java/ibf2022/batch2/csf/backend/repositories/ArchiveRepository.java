package ibf2022.batch2.csf.backend.repositories;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveRepository {

    @Autowired
    MongoTemplate mongoTemplate;

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method

	// db.archives.findOneAndUpdate(
    //     {_id: 123},
    //     {
    //         $setOnInsert: {
    //             bundleId: 123,
    //             title: 'asd',
    //             name: 'asd',
    //             comments: 'dwq',
    //             urls: ['url1', 'url2'],
    //             date: new Date()
    //             }
    //     },
    //     {
    //         upsert: true
    //     }
    // );
	
	public String recordBundle(String key, String name, String title, String comments, List<URL> urlList) {

        Query query = new Query(Criteria.where("bundleId").is(key));
        Update update = new Update().set("_id", key)
        .set("date", LocalDateTime.now())
        .set("title", title)
        .set("name", name)
        .set("comments", comments)
        .set("urls", urlList);

        mongoTemplate.upsert(query, update, Document.class, "archives");

		return key;
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method

	// db.archives.findOne(
    //     { bundleId: <bundleId> },
    //     { _id: 0}
    // )

	public Document getBundleByBundleId(String bundleId) {
        Query query = new Query(Criteria.where("bundleId").is(bundleId));
        Document doc = mongoTemplate.findOne(query, Document.class, "archives");

		return doc;
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method

	// db.archives.aggregate(
    //     [
    //         { $sort : { date: -1, title: 1 }}
    //     ]
    // )

	public List<Document> getBundles() {
        Query query = new Query(Criteria.where("bundleId").exists(true));
        query.with(Sort.by(Direction.DESC, "date"))
        .with(Sort.by(Direction.ASC, "title"));
        List<Document> docs = mongoTemplate.find(query, Document.class, "archives");

		return docs;
	}
}
