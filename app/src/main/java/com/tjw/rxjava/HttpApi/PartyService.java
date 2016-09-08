package com.tjw.rxjava.HttpApi;

import com.tjw.rxjava.bean.StudyMaterials;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * ^-^
 * Created by tang-jw on 9/8.
 */
public interface PartyService {
	
	//lianyi/MtsNews/getNews.do
	@GET("lianyi/MtsMaterial/getAllMaterial.do")
	Call<StudyMaterials> getStudyMaterials();
	
}
