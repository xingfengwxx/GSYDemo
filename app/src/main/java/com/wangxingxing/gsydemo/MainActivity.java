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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        LogUtils.i("kyeCode=" + keyCode + ", keyEvent=" + event);
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_CENTER:
//                LogUtils.i("currentState=" + detailPlayer.getCurrentState());
//                LogUtils.i("isPlaying=" + detailPlayer.isInPlayingState());
//                if (detailPlayer.isInPlayingState()) {
//                    GSYVideoManager.onPause();
////                    isPlaying = false;
//                    detailPlayer.onVideoPause();
//                } else {
//                    GSYVideoManager.onResume();
////                    isPlaying = true;
//                    detailPlayer.onVideoResume();
//                }
//
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
