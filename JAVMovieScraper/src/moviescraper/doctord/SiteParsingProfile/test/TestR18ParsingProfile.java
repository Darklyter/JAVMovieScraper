package moviescraper.doctord.SiteParsingProfile.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import moviescraper.doctord.SearchResult;
import moviescraper.doctord.Thumb;
import moviescraper.doctord.SiteParsingProfile.specific.R18ParsingProfile;
import moviescraper.doctord.controller.SpecificScraperAction;
import moviescraper.doctord.dataitem.Actor;
import moviescraper.doctord.dataitem.Genre;
import moviescraper.doctord.dataitem.ID;
import moviescraper.doctord.dataitem.Plot;
import moviescraper.doctord.dataitem.Set;
import moviescraper.doctord.dataitem.Title;
import moviescraper.doctord.dataitem.Year;

import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestR18ParsingProfile {
	static boolean scrapeInEnglish = true;
	static File file = new File("C:/Temp/ONSD-646.avi");
	R18ParsingProfile parser = new R18ParsingProfile();
	
	private static R18ParsingProfile profile;
	
	@BeforeClass
	public static void initialize() {
		profile = new R18ParsingProfile();
		String searchString = profile.createSearchString(file);
		System.out.println("searchString = " + searchString);
		try {
			SearchResult[] searchResults = profile.getSearchResults(searchString);
			Document document = SpecificScraperAction.downloadDocument(searchResults[0]);
			System.out.println("document set to " + document.baseUri());
			profile.setDocument(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("static-access")
	@Test
	public void testFindID() {
		String findIDTagFromFile = parser.findIDTagFromFile(file);
		System.out.println(findIDTagFromFile);
		assertEquals(findIDTagFromFile, "ONSD-646");
	}
	
	@Test
	public void testTitle(){
		Title testTitle = profile.scrapeTitle();
		assertEquals("Wrong title", "Yuma Asami 2012 S.1 8 Hours Special", testTitle.getTitle());
	}
	
	@Test
	public void testSet(){
		Set testSet = profile.scrapeSet();
		assertEquals("Wrong set", "S1 GIRLS COLLECTION", testSet.getSet());
	}
	
	@Test
	public void testPlot(){
		Plot testPlot = profile.scrapePlot();
		assertEquals("Wrong plot", "A special that looks back", testPlot.getPlot().substring(0,25));
	}
	
	@Test
	public void testRuntime(){
		moviescraper.doctord.dataitem.Runtime testRuntime = profile.scrapeRuntime();
		assertEquals("Wrong runtime", "478", testRuntime.getRuntime());
	}
	
	@Test
	public void testID() {
		ID scrapeID = profile.scrapeID();
		System.out.println("scrapeID = " + scrapeID);
		assertEquals("Found wrong ID", "ONSD-646", scrapeID.getId());
	}
	
	@Test
	public void testYear() {
		Year scrapeYear = profile.scrapeYear();	
		assertEquals("Found wrong year", "2012", scrapeYear.getYear());
	}
	
	@Test
	public void testGenre()
	{
		ArrayList<Genre> testGenre = profile.scrapeGenres();
		System.out.println(testGenre);
		assertTrue("There should be 5 genres", testGenre.size() == 5);
	}
	
	@Test
	public void testActor()
	{
		ArrayList<Actor> testActor = profile.scrapeActors();
		System.out.println("actor = " + testActor.get(0));
		assertEquals("Found wrong actor", "Yuma Asami", testActor.get(0).getName());
	}
	
	@Test
	public void testExtraFanart() throws IOException
	{
		Thumb  [] extraFanart = profile.scrapeExtraFanart();
		TestGenericProfile.showImage("Extrafanart", extraFanart[0].getThumbImage());
	}
	
	@Test
	public void testPoster() throws IOException {
		Thumb[] poster = profile.scrapePosters();
		assertTrue("There should be 1 Fanart.", poster.length == 1);
		
		TestGenericProfile.showImage("Poster", poster[0].getThumbImage());
	}
	

	

}
