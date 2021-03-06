package com.backend.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.backend.models.enums.Division;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.models.Essentials;
import com.google.common.collect.Lists;

public class SchoolExtra extends School
{
	public final Division 			division;
	public final DateTime			designEvalTime;
	public final DateTime			constructionEvalTime;
	public final String				websiteURL;
	public final String				videoURL;
	public final String				tutorialURL;
	
	public SchoolExtra(	@JsonProperty("_id") 					ObjectId _schoolId,
						@JsonProperty("name") 					String _name,
						@JsonProperty("division") 				Division _division,
						@JsonProperty("designEvalTime") 		DateTime _designEvalTime,
						@JsonProperty("constructionEvalTime") 	DateTime _constructionEvalTime,
						@JsonProperty("websiteURL") 			String	_websiteURL,
						@JsonProperty("videoURL") 				String	_videoURL,
						@JsonProperty("tutorialURL") 			String	_tutorialURL
					)
	{
		super(_schoolId, _name);
		division 	= _division;
		designEvalTime = _designEvalTime;
		constructionEvalTime = _constructionEvalTime;
		websiteURL = _websiteURL;
		videoURL = _videoURL;
		tutorialURL = _tutorialURL;
	}
	
	public SchoolExtra(	School school, 
						Division _division,
						DateTime _designEvalTime,
						DateTime _constructionEvalTime,
						String	_websiteURL,
						String	_videoURL,
						String _tutorialURL
		)
	{
		super(school._id, school.name);
		division 	= _division;
		designEvalTime = _designEvalTime;
		constructionEvalTime = _constructionEvalTime;
		websiteURL = _websiteURL;
		videoURL = _videoURL;
		tutorialURL = _tutorialURL;
	}
	
	public static ArrayList<SchoolExtra> getSchoolsExtra(Essentials essentials)
	{
		return Lists.newArrayList(essentials.database.find(SchoolExtra.class, "{ }"));
	}
}