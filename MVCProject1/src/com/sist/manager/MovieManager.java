package com.sist.manager;
import java.util.*;
import java.text.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.MovieDAO;
import com.sist.dao.MovieVO;
public class MovieManager {

	public static void main(String[] args) {
		MovieManager m = new MovieManager();
		//m.movieLinkData();
		m.movieDetailData();
		System.out.println("저장 완료");

	}
	//http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date=20171205
	/*
	 * <td class="title">
		<div class="tit5">
			<a href="/movie/bi/mi/basic.nhn?code=17421" title="쇼생크 탈출">쇼생크 탈출</a>
		</div>

	 */
	public List<String> movieLinkData(){
		List<String> list = new ArrayList<String>();
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int k = 1;
			for(int i=1;i<=40;i++) {
				Document doc = Jsoup.connect("http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date="+sdf.format(date)+"&page="+i).get();
				Elements elem = doc.select("td.title div.tit5 a");
				for(int j=0;j<elem.size();j++) {
					Element code = elem.get(j);
					//System.out.println(k+":"+code.attr("href"));
					//movie/bi/mi/basic.nhn?code=106540
					list.add("http://movie.naver.com"+code.attr("href"));
					k++;
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	public List<MovieVO> movieDetailData() {
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieDAO dao = new MovieDAO();
		try {
			List<String> linkList = movieLinkData();
			for(int i=0;i<linkList.size();i++) {
				try {
					String link = linkList.get(i);
					Document doc = Jsoup.connect(link).get();
					Element title = doc.select("div.mv_info h3.h_movie a").first();
					/*
					 * <div class="info_spec2">
						<dl class="step1">
							<dt>감독</dt>
							<dd>
								<a href="/movie/bi/pi/basic.nhn?code=87206">장창원</a>
					 */
					Element director = doc.select("div.info_spec2 dl.step1 dd a").first();
					Element actor = doc.select("div.info_spec2 dl.step2 dd a").first();
					Elements temp = doc.select("p.info_spec span");
					Element genre = temp.get(0);
					Element time = temp.get(2);
					Element regdate = temp.get(3);
					Element grade = temp.get(4);
					Element poster = doc.select("div.poster a img").first();
					//Element good = doc.select("").first();
					Element story = doc.selectFirst("div.story_area p.con_tx");
					//text()는 태그와 태그사이의값 가져오기, attr은 <a href="이값" > 가져오기
					System.out.println((i+1)+":"+title.text());
					MovieVO vo = new MovieVO();
					vo.setMno(i+1);
					vo.setTitle(title.text());
					vo.setDirector(director.text());
					vo.setActor(actor.text());
					vo.setPoster(poster.attr("src"));
					vo.setGenre(genre.text());
					vo.setGrade(grade.text());
					vo.setTime(time.text());
					vo.setRegdate(regdate.text());
					String s = story.text();
					s = s.replace("'", "");		//작은따옴표를 공백으로
					s = s.replace("\"", "");
					vo.setStory(s);
					dao.movieInsert(vo);
					list.add(vo);
					/*Element director;
					Element actor;
					Element poster;
					Element genre;
					Element time;
					Element regdate;
					Element grade;
					Element good;
					Element story;*/
				} catch(Exception e) {}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

}


















