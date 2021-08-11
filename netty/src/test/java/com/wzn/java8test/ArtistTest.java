package com.wzn.java8test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ArtistTest {

    private static List<Album> albums = Lists.newArrayList();

    static {
        Track wjh_1 = new Track("寻找朱丽叶",2);
        Track wjh_2 = new Track("身体会唱歌",1);
        Track wjh_3 = new Track("想象十个你",3);

        Album wjh_album = new Album();
        wjh_album.setMusicians(Lists.newArrayList("吴建豪"));
        wjh_album.setName("寻找朱丽叶");
        wjh_album.setTracks(Lists.newArrayList(wjh_1,wjh_2,wjh_3));
        albums.add(wjh_album);

        Artist wjh = new Artist();
        wjh.setName("吴建豪");
        wjh.setOrigin("台湾");
        wjh.setMembers(Lists.newArrayList("吴建豪"));

        Track beyond_1 = new Track("光辉岁月",1);
        Track beyond_2 = new Track("大地",1);
        Track beyond_3 = new Track("真的爱你",4);

        Album beyond_album = new Album();
        beyond_album.setMusicians(Lists.newArrayList("黄家驹","黄家强","叶世荣","黄贯中"));
        beyond_album.setName("光辉岁月");
        beyond_album.setTracks(Lists.newArrayList(beyond_1, beyond_2, beyond_3));
        albums.add(beyond_album);


        Artist beyond = new Artist();
        beyond.setName("beyond");
        beyond.setOrigin("香港");
        beyond.setMembers(Lists.newArrayList("黄家驹","黄家强","叶世荣","黄贯中"));
    }

    @Test
    public void getTrackLengthGreaterThanOneTest1(){
        log.info("--------------------------------");
        log.info("albums的信息{}",albums);
        log.info("原始的找set的方法:【{}】",findLongTracks(Lists.newArrayList(albums)));
//        albums.stream().flatMap(album -> album.getTracks().stream()).collect(Collectors.toList()).forEach(System.out::println);
        log.info("较高级的找set的方法:【{}】",findLOngTracksAdv(Lists.newArrayList(albums)));


    }/**

    /**
     * 比较原始的，两次循环来找
     * @param albums
     * @return
     */
    public Set<String> findLongTracks(List<Album> albums){
        Set<String> trackNameSet = Sets.newHashSet();
        for (Album album : albums){
            for (Track track: album.getTracks()){
                int length = track.getLength();
                if (length > 1){
                    trackNameSet.add(track.getName());
                }
            }
        }
        return trackNameSet;
    }

    /**
     * 更高级的使用流来找
     * @param albums
     * @return
     */
    public Set<String> findLOngTracksAdv(List<Album> albums) {
//        Set<String> trackNameSet = Sets.newHashSet();
        return albums.stream().flatMap(album -> album.getTracks().stream())
                .filter(track -> track.getLength() > 1)
                .map(track -> track.getName())
                .collect(Collectors.toSet());
    }
}
