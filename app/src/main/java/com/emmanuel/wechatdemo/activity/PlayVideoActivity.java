package com.emmanuel.wechatdemo.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.emmanuel.wechatdemo.R;
import com.emmanuel.wechatdemo.util.ToastUtil;
import com.emmanuel.wechatdemo.view.VideoTextureView;

import java.io.File;

/**
 * Created by user on 2016/9/22.
 */
public class PlayVideoActivity extends BaseActivity {

    private static final String TAG = "PlayVideoActivity";

    private VideoView videoView;
    private VideoTextureView videoTextureView;
    private ImageView ivTip;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_video);
        setTitle("视频播放");
//        initVideoView();
        initTextureView();
    }

    private void initVideoView() {
        videoView = (VideoView) findViewById(R.id.video_view);
        File file = new File("/sdcard/videocapture_example.mp4"); // 获取SD卡上要播放的文件
        if(file.exists()) {
            videoView.setVideoPath(file.getAbsolutePath());
            videoView.requestFocus(); // 让VideoView获得焦点
            videoView.start();
        }
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // 在播放完毕被回调
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 发生错误重新播放
                ToastUtil.showMessage("播放出错", Toast.LENGTH_SHORT, true);
                return false;
            }
        });
    }

    private void initTextureView() {
        videoTextureView = (VideoTextureView)findViewById(R.id.video_texture_view);
        ivTip = (ImageView)findViewById(R.id.iv_play);
        videoTextureView.setIvTip(ivTip);
        videoTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!videoTextureView.getPlayStatus())
                    videoTextureView.startMediaPlayer();
                else{
                    videoTextureView.stopMediaPlayer();
                }
            }
        });
    }

    private void videoFullScreen(){
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView != null)
            videoView = null;
    }
}
