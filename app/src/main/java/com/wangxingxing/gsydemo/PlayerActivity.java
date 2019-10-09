package com.wangxingxing.gsydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.lang.reflect.Method;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnVideoSizeChangedListener {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private Button btnFull;

    private String url = "https://baidu.com-l-baidu.com/20190817/14650_5960339e/index.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        init();
    }

    private void init() {
        surfaceView = findViewById(R.id.surface_view);
        btnFull = findViewById(R.id.btn_full_screen);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(new SurfaceCallback());

        playVideo();

        btnFull.setOnClickListener(v -> {
            LogUtils.i("全屏播放");
//            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) surfaceView.getLayoutParams( );
//            lp.leftMargin = 0;
//            lp.topMargin = 0;
//            lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
//            lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
//            surfaceView.setLayoutParams(lp);

//            mediaPlayer.seekTo(500000);
            seekToCustom(500000);
        });

        btnFull.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                btnFull.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
            } else {
                btnFull.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            }
        });
    }

    /**
     * 播放视频
     */
    public void playVideo() {
        // 初始化MediaPlayer
        mediaPlayer = new MediaPlayer();
        // 重置mediaPaly,建议在初始滑mediaplay立即调用。
        mediaPlayer.reset();
        // 设置声音效果
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 设置播放完成监听
        mediaPlayer.setOnCompletionListener(this);
        // 设置媒体加载完成以后回调函数。
        mediaPlayer.setOnPreparedListener(this);
        // 错误监听回调函数
        mediaPlayer.setOnErrorListener(this);
        // 设置缓存变化监听
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnVideoSizeChangedListener(this);
        Uri uri = Uri.parse(url);
        try {
            // mediaPlayer.reset();
            mediaPlayer.setDataSource(PlayerActivity.this, uri);
            // 设置异步加载视频，包括两种方式 prepare()同步，prepareAsync()异步
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeVideoSize(float surfaceWidth, float surfaceHeight) {
        int videoWidth = mediaPlayer.getVideoWidth();
        int videoHeight = mediaPlayer.getVideoHeight();

        //根据视频尺寸去计算->视频可以在sufaceView中放大的最大倍数。
        float max;
        if (getResources().getConfiguration().orientation== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            //竖屏模式下按视频宽度计算放大倍数值
            max = Math.max((float) videoWidth / (float) surfaceWidth,(float) videoHeight / (float) surfaceHeight);
        } else{
            //横屏模式下按视频高度计算放大倍数值
            max = Math.max(((float) videoWidth/(float) surfaceHeight),(float) videoHeight/(float) surfaceWidth);
        }

        //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
        videoWidth = (int) Math.ceil((float) videoWidth / max);
        videoHeight = (int) Math.ceil((float) videoHeight / max);

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
        surfaceView.setLayoutParams(new FrameLayout.LayoutParams(videoWidth, videoHeight));
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        LogUtils.d(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        LogUtils.i("播放完成");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        LogUtils.e("what=" + what + ", extra=" + extra);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // 当视频加载完毕以后，隐藏加载进度条
//        progressBar.setVisibility(View.GONE);
//        // 判断是否有保存的播放位置,防止屏幕旋转时，界面被重新构建，播放位置丢失。
//        if (Constants.playPosition >= 0) {
//            mediaPlayer.seekTo(Constants.playPosition);
//            Constants.playPosition = -1;
//            // surfaceHolder.unlockCanvasAndPost(Constants.getCanvas());
//        }
        // 播放视频
        mediaPlayer.start();
        // 设置显示到屏幕
        mediaPlayer.setDisplay(surfaceHolder);
        // 设置surfaceView保持在屏幕上
        mediaPlayer.setScreenOnWhilePlaying(true);
        surfaceHolder.setKeepScreenOn(true);
        // 设置控制条,放在加载完成以后设置，防止获取getDuration()错误
//        seekBar.setProgress(0);
//        seekBar.setMax(mediaPlayer.getDuration());
//        // 设置播放时间
//        videoTimeString = getShowTime(mediaPlayer.getDuration());
//        vedioTiemTextView.setText("00:00:00/" + videoTimeString);
//        // 设置拖动监听事件
//        seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
//        // 设置按钮监听事件
//        // 重新播放
//        replayButton.setOnClickListener(SurfaceViewTestActivity.this);
//        // 暂停和播放
//        playButton.setOnClickListener(SurfaceViewTestActivity.this);
//        // 截图按钮
//        screenShotButton.setOnClickListener(SurfaceViewTestActivity.this);
//        seekBarAutoFlag = true;
//        // 开启线程 刷新进度条
//        thread.start();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//        changeVideoSize(width, height);
    }

    private void seekToCustom(int position) {
        try {
            Class clazz = Class.forName("android.media.MediaPlayer");
            Method method = clazz.getMethod("seekTo", int.class);
            method.setAccessible(true);
            method.invoke(mediaPlayer, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SurfaceView的callBack
    private class SurfaceCallback implements SurfaceHolder.Callback {
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // SurfaceView的大小改变
        }

        public void surfaceCreated(SurfaceHolder holder) {
            // surfaceView被创建
            // 设置播放资源
            mediaPlayer.setDisplay(holder);
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // surfaceView销毁
            // 如果MediaPlayer没被销毁，则销毁mediaPlayer
            if (null != mediaPlayer) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }
}
