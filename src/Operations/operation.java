package Operations;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import Song.songs;

class songComparator implements Comparator<songs>{
	public int compare(songs x, songs y) {
		int res = x.getName().compareTo(y.getName());
		if(res > 0) {
			return 1;
		} else if(res == 0) {
			int r2 = x.getArtist().compareTo(y.getArtist());
			if(r2 > 0) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}
}

public class operation {
	private static Set<String> map = new HashSet<>();
	private static List<songs> res;
	
	public static List<songs> loadlib() throws IOException{
		String content;
		
		res = new ArrayList<>();
		
//		Path pathToFile = Paths.get("songs.txt");
//	    System.out.println(pathToFile.toAbsolutePath());
	    
		content = new String(Files.readAllBytes(Paths.get("src/Operations/songs.txt")));
//		System.out.println(content);
		
		String[] cont = content.split("\\n");
		
		
		for(String str : cont) {
			String[] present = str.split(",");
			songs s = new songs(present[0],present[1]);
			s.setAlbum(present[2]);
			s.setYear(Integer.parseInt(present[3]));
			res.add(s);
			map.add(present[0]+" "+present[1]);
		}
		
		sort(res);
		return res;
	}
	
	public static void sort(List<songs> lst) {
		Collections.sort(lst, new songComparator());
	}
	
	public static List<songs> add(String name, String artist, String album, String year) {
		
		name = name.toLowerCase();
		artist = artist.toLowerCase();
		album = album.toLowerCase();
		if(map.contains(name + " " + artist)) {
			return null;
		}
		songs s = new songs(name, artist);
		s.setAlbum(album);
		s.setYear(Integer.valueOf(year));
		res.add(s);
		map.add(name + " " + artist);
		sort(res);
		return res;
	}
	public static List<songs> edit(int index, String name, String artist, String album, String year) {
		name = name.toLowerCase();
		artist = artist.toLowerCase();
		album = album.toLowerCase();
		songs pt = res.get(index);
		if(map.contains(name + " " + artist)) {
			System.out.println(pt.getName()+", "+name);
			System.out.println(pt.getArtist()+", "+artist);
			if(!pt.getName().equals(name) || !pt.getArtist().equals(artist)) {
				return null;
			} else {
				System.out.println("not change name");
				pt.setAlbum(album);
				pt.setYear(Integer.valueOf(year));
				return res;
			}
		}
		map.remove(pt.getName()+" "+pt.getArtist());
		pt.setName(name);
		pt.setArtist(artist);
		pt.setAlbum(album);
		pt.setYear(Integer.valueOf(year));
		map.add(name + " " + artist);
		System.out.println("change names");
		sort(res);
		System.out.println(res.toString());
		return res;
	}
	public static boolean delete(int index) {
		songs pt = res.get(index);
		res.remove(index);
		map.remove(pt.getName() + " " + pt.getArtist());
		return true;
	}
}
