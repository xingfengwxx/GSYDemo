package com.wangxingxing.gsydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView;
import com.wangxingxing.gsydemo.db.ObjectBox;
import com.wangxingxing.gsydemo.db.table.Favorite;
import com.wangxingxing.gsydemo.db.table.History;
import com.wangxingxing.gsydemo.db.table.Video;
import com.wangxingxing.gsydemo.db.table.Video_;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;

public class MainActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> {

    private TvVideoPlayer detailPlayer;
    private Button btnFullScreen;
    private String url = "https://cdn-7.haku88.com/hls/2019/08/10/itkJnN7A/playlist.m3u8";
    private String videoUrl = "https://baidu.com-l-baidu.com/20190817/14650_5960339e/index.m3u8";
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailPlayer = findViewById(R.id.detail_player);
        btnFullScreen = findViewById(R.id.btn_full_screen);
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
//        detailPlayer.getBackButton().setVisibility(View.GONE);

        initVideoBuilderMode();

        detailPlayer.startPlayLogic();

        btnFullScreen.setOnClickListener(v -> {
            LogUtils.i("进入全屏模式");
            detailPlayer.startWindowFullscreen(MainActivity.this, false, false);
            btnFullScreen.setVisibility(View.GONE);
            detailPlayer.requestFocus();
        });

        testDB();
    }

    @Override
    public void onQuitFullscreen(String url, Object... objects) {
        LogUtils.i("退出全屏模式");
        btnFullScreen.setVisibility(View.VISIBLE);
    }

    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        //loadCover(imageView, url);
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(videoUrl)
                .setCacheWithPlay(true)
                .setVideoTitle(" ")
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)//打开动画
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return false;
    }

    public void testDB() {
        Box<Video> box = ObjectBox.get().boxFor(Video.class);

//        for (int i = 0; i < 10; i++) {
//            if (i < 5) {
//                History history = new History();
//                Video video = new Video(0, "video" + i, new Date());
//                video.history.setTarget(history);
//                box.put(video);
//            } else {
//                Favorite favorite = new Favorite();
//                Video video = new Video(0, "video" + i, new Date());
//                video.favorite.setTarget(favorite);
//                box.put(video);
//            }
//        }

        Box<Favorite> boxFav = ObjectBox.get().boxFor(Favorite.class);
        LogUtils.i("fav list: \n");
        long ids[] = new long[boxFav.getAll().size()];
        for (int j = 0; j < boxFav.getAll().size(); j++) {
            LogUtils.i(boxFav.getAll().get(j).toString());
            ids[j] = boxFav.getAll().get(j).id;
        }

        List<Video> favVideos = new ArrayList<>();
        for (int m = 0; m < boxFav.getAll().size(); m++) {
            Video fav = box.query().equal(Video_.favoriteId, boxFav.getAll().get(m).id).build().findUnique();
            favVideos.add(fav);
            LogUtils.i(fav.toString());
        }
    }
}
