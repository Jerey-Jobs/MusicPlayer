package com.example.xiamin.musicplayer.Bean;



/**
 * Created by Xiamin on 2016/9/25.
 */
public class MusicDownLoadBean {
    private JBitrate bitrate;

    public JBitrate getBitrate() {
        return bitrate;
    }

    public void setBitrate(JBitrate bitrate) {
        this.bitrate = bitrate;
    }

    public static class JBitrate {
        int file_duration;
        String file_link;

        public int getFile_duration() {
            return file_duration;
        }

        public void setFile_duration(int file_duration) {
            this.file_duration = file_duration;
        }

        public String getFile_link() {
            return file_link;
        }

        public void setFile_link(String file_link) {
            this.file_link = file_link;
        }
    }
}


 /**
 * {
 "songinfo": {
 "special_type": 0,
 "pic_huge": "http://musicdata.baidu.com/data2/pic/9f69d7002561aa0ab5f14d2dd978fbf6/268810057/268810057.jpg",
 "resource_type": "0",
 "pic_premium": "http://musicdata.baidu.com/data2/pic/d808e82d38355a258f97fb8617aff531/268810059/268810059.jpg",
 "havehigh": 2,
 "author": "王俊凯",
 "toneid": "0",
 "has_mv": 0,
 "song_id": "268810037",
 "piao_id": "0",
 "artist_id": "14947058",
 "lrclink": "http://musicdata.baidu.com/data2/lrc/b2b1ba655f1a9056020bf3700291a79b/268810692/268810692.lrc",
 "relate_status": "0",
 "learn": 0,
 "pic_big": "http://musicdata.baidu.com/data2/pic/518827cd2888633ac49f1fafc7002913/268809938/268809938.jpg",
 "play_type": 0,
 "album_id": "268810041",
 "album_title": "树读",
 "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"-1|-1\"}",
 "song_source": "web",
 "all_artist_id": "14947058",
 "all_artist_ting_uid": "10559334",
 "all_rate": "64,128,256,320,flac",
 "charge": 0,
 "copy_type": "0",
 "is_first_publish": 0,
 "korean_bb_song": "1",
 "pic_radio": "http://musicdata.baidu.com/data2/pic/ab2bff86793168e0027bb1d0323b2301/268809933/268809933.jpg",
 "has_mv_mobile": 0,
 "title": "树读",
 "pic_small": "http://musicdata.baidu.com/data2/pic/07e059f82a74da54cdce398b5e422fe9/268809943/268809943.jpg",
 "album_no": "1",
 "resource_type_ext": "1",
 "ting_uid": "10559334"
 },
 "error_code": 22000,
 "bitrate": {
 "show_link": "http://zhangmenshiting.baidu.com/data2/music/cb63055a12a9c1a2e2b5aa2674626dec/268810194/268810194.mp3?xcode=e79a93dfe04504d62b8f63f9ac723f36",
 "free": 1,
 "song_file_id": 268810194,
 "file_size": 1895077,
 "file_extension": "mp3",
 "file_duration": 236,
 "file_bitrate": 64,
 "file_link": "http://yinyueshiting.baidu.com/data2/music/cb63055a12a9c1a2e2b5aa2674626dec/268810194/268810194.mp3?xcode=e79a93dfe04504d62b8f63f9ac723f36",
 "hash": "a922d4ae6b6c847e3308423cad7ff6fb68459e44"
 }
 }
 */




